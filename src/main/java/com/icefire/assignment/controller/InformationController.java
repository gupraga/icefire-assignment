package com.icefire.assignment.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.icefire.assignment.entity.Information;
import com.icefire.assignment.service.InformationService;

@RestController
@RequestMapping("/api/information")
public class InformationController {

	@Autowired
	InformationService informationService;
	
	@RequestMapping(method = RequestMethod.POST, path = "/create")
	public Information createInformation(@RequestBody Information information, Authentication authentication) throws Exception {
		return informationService.createInformation(information, getUsername(authentication));
	}
	
	@RequestMapping(method = RequestMethod.POST, path = "/decrypt")
	public Information decryptInformation(@RequestBody Information information, Authentication authentication) throws Exception {
		return informationService.decryptInformation(information, getUsername(authentication));
	}
	
	@RequestMapping(method = RequestMethod.GET, path = "/list")
	public List<Information> findAllInformationsByUser(Authentication authentication) {
		return informationService.findAllInformationsByUser(getUsername(authentication));
	}
	
	/*@RequestMapping(method = RequestMethod.DELETE, path = "/{id}")
	public void removeInformation(@PathVariable("id") Long id) {
		informationService.removeInformation(id);
	}*/
	
	private String getUsername(Authentication authentication) {
		return authentication.getPrincipal().toString();
	}
}
