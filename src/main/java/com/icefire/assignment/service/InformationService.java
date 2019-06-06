package com.icefire.assignment.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.icefire.assignment.entity.Information;
import com.icefire.assignment.repository.InformationRepository;

@Service
public class InformationService {

	@Autowired
	InformationRepository informationRepository;
	
	public Information createInformation(Information information) {
		return informationRepository.saveAndFlush(information);
	}
	
	public List<Information> findAllInformations(){
		return informationRepository.findAll();
	}
	
	public void removeInformation(Long id) {
		informationRepository.deleteById(id);
	}
	
	public Optional<Information> findById(Long id) {
		return informationRepository.findById(id);
	}
}
