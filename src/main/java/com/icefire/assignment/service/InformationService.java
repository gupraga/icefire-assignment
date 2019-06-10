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
import com.icefire.assignment.entity.ApplicationUser;
import com.icefire.assignment.entity.Information;
import com.icefire.assignment.repository.ApplicationUserRepository;
import com.icefire.assignment.repository.InformationRepository;

@Service
public class InformationService {

	@Autowired
	InformationRepository informationRepository;
	
	@Autowired
	ApplicationUserRepository applicationUserRepository;
	
	public Information createInformation(Information information, String username) throws Exception {
		try {
			ApplicationUser user = applicationUserRepository.findByUsername(username);
			information.setUser(user);
			if(information.getInformationSecured() != null && !information.getInformationSecured().equals("")) {
				information = this.decryptInformation(information, username);
			}
			if(information.getInformationSecured() == null || information.getInformationSecured().equals("")) {
				information.setInformationSecured(Base64.getEncoder().encodeToString(
					RSAUtil.encrypt(information.getInformation(), user.getPublicKey())
				));
			}
			return informationRepository.saveAndFlush(information);
		} catch (InvalidKeyException | IllegalBlockSizeException | BadPaddingException | NoSuchAlgorithmException
				| NoSuchPaddingException e) {
			throw new Exception(e.getMessage());
		}
	}
	
	public List<Information> findAllInformations(){
		return informationRepository.findAll();
	}
	
	public List<Information> findAllInformationsByUser(String username){
		return informationRepository.findByUserUsername(username);
	}
	
	public Information decryptInformation(Information information, String username) throws Exception {
		ApplicationUser user = applicationUserRepository.findByUsername(username);
		try {
			String unsecuredInformation = RSAUtil.decrypt(information.getInformationSecured(), user.getPrivateKey());
			information.setInformation(unsecuredInformation);
			information.setInformationSecured("");
		} catch (InvalidKeyException | IllegalBlockSizeException | BadPaddingException | NoSuchAlgorithmException
				| NoSuchPaddingException e) {
			throw new Exception(e.getMessage());
		}
		return information;
	}
	
	public void removeInformation(Long id) {
		informationRepository.deleteById(id);
	}
	
	public Optional<Information> findById(Long id) {
		return informationRepository.findById(id);
	}
}
