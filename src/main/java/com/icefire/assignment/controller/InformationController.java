package com.icefire.assignment.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PathVariable;
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
	public Information createInformation(@RequestBody Information information, Authentication authentication) {
		return informationService.createInformation(information);
	}
	
	@RequestMapping(method = RequestMethod.GET, path = "/list")
	public List<Information> findAllInformationsByUser(Authentication authentication) {
		// TODO PAROU AQUI, PARTE IMPORTANTE PARA PROXIMO DEV
		return informationService.findAllInformationsByUser(authentication.getPrincipal().toString());
	}
	
	@RequestMapping(method = RequestMethod.DELETE, path = "/{id}")
	public void removeInformation(@PathVariable("id") Long id) {
		informationService.removeInformation(id);
	}
}
