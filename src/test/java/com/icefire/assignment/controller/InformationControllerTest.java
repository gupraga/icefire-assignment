package com.icefire.assignment.controller;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.icefire.assignment.entity.Information;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class InformationControllerTest {

	@LocalServerPort
	private int port;
	
	@Autowired
	private TestRestTemplate testRestTemplate;
	
	private HttpEntity<Void> authHeaders;
	
	private HttpEntity<Void> wrongHeaders;
	
	private String token;
	
	private JacksonJsonParser jsonParser;
	
	@Before
    public void setup() throws Exception {
		String user = "{\"username\":\"gustavo\", \"password\": \"icefirerocks\" }";
		String body = testRestTemplate.postForEntity("/login", user, String.class).getBody();
		jsonParser = new JacksonJsonParser();
	    this.token = jsonParser.parseMap(body).get("token").toString();
	    HttpHeaders headers = new HttpHeaders();
	    headers.add("Authorization", "Bearer "+token);
	    this.authHeaders = new HttpEntity<>(headers);
	    HttpHeaders wrongHeaders = new HttpHeaders();
	    wrongHeaders.add("Authorization", "Bearer "+token+"dummy");
	    this.wrongHeaders = new HttpEntity<>(wrongHeaders);
	}
	
	@Test
    public void testApiInformationListRequest() throws Exception {
		ResponseEntity<String> response = 
				testRestTemplate.exchange("/api/information/list",HttpMethod.GET, this.authHeaders, String.class);
		assertThat(response.getStatusCodeValue(), is(equalTo(200)));
		assertThat(response.getBody(), is(notNullValue()));
		assertThat(jsonParser.parseList(response.getBody()).size(), equalTo(1));
    }
	
	@Test
    public void testApiInformationListRequestWithoutPermissions() throws Exception {
		ResponseEntity<String> response = 
				testRestTemplate.exchange("/api/information/list",HttpMethod.GET, this.wrongHeaders, String.class);
		assertThat(response.getStatusCodeValue(), is(equalTo(500)));
		assertThat(response.getBody(), containsString("JWT signature does not match locally computed signature"));
    }
	
	@Test
    public void testApiInformationCreateRequest() throws Exception {
		Information information = new Information();
		information.setInformation("Message to be coded");
		ResponseEntity<Information> response = 
				testRestTemplate.exchange("/api/information/create",HttpMethod.POST, new HttpEntity<>(information, authHeaders.getHeaders()), Information.class);
		assertThat(response.getStatusCodeValue(), is(equalTo(200)));
		assertThat(response.getBody(), is(notNullValue()));
		assertThat(response.getBody().getInformationSecured(), is(notNullValue()));
		assertThat(response.getBody().getInformation(), is(equalTo(information.getInformation())));
    }
	
	@Test
    public void testApiInformationDecryptRequest() throws Exception {
		Information information = new Information();
		information.setInformationSecured("VEqQsmLqlkT2+l/zCeOAzSXtgnAJ2/MvOAbM1NxJ17olle+U3QonTyqsHVtaGvWQ3YpFCSwYMr5ej7p7gPROL/n649exmhj5pmYDkcb2IZgh4/sOBMY5K1wq2uTjLxBZPOHPoku+2qU2OMfOZ1JkuM+U57XoMOPmtE63R003Drs=");
		ResponseEntity<Information> response = 
				testRestTemplate.exchange("/api/information/decrypt",HttpMethod.POST, new HttpEntity<>(information, authHeaders.getHeaders()), Information.class);
		assertThat(response.getStatusCodeValue(), is(equalTo(200)));
		assertThat(response.getBody(), is(notNullValue()));
		assertThat(response.getBody().getInformation(), is(equalTo("Message to be coded")));
    }
}
