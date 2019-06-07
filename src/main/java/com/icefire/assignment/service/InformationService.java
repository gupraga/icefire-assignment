package com.icefire.assignment.service;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.icefire.assignment.encryptation.RSAUtil;
import com.icefire.assignment.entity.Information;
import com.icefire.assignment.repository.InformationRepository;

@Service
public class InformationService {

	@Autowired
	InformationRepository informationRepository;
	
	public Information createInformation(Information information) {
		try {
			information.setInformationSecured(Base64.getEncoder().encodeToString(
				RSAUtil.encrypt(information.getInformation(), information.getUser().getPublicKey())
			));
			return informationRepository.saveAndFlush(information);
		} catch (InvalidKeyException | IllegalBlockSizeException | BadPaddingException | NoSuchAlgorithmException
				| NoSuchPaddingException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public List<Information> findAllInformations(){
		return informationRepository.findAll();
	}
	
	public List<Information> findAllInformationsByUser(String username){
		return informationRepository.findByUserUsername(username);
	}
	
	public void removeInformation(Long id) {
		informationRepository.deleteById(id);
	}
	
	public Optional<Information> findById(Long id) {
		return informationRepository.findById(id);
	}
}
