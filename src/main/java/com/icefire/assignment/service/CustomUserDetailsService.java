package com.icefire.assignment.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.icefire.assignment.entity.ApplicationUser;
import com.icefire.assignment.repository.ApplicationUserRepository;

@Component
public class CustomUserDetailsService implements UserDetailsService{

	@Autowired
	ApplicationUserRepository applicationUserRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		ApplicationUser applicationUser = applicationUserRepository.findByUsername(username);
		if(applicationUser == null) {
			throw new UsernameNotFoundException("User not found");
		}
		
		return new User(applicationUser.getUsername(), 
				applicationUser.getPassword(), 
				AuthorityUtils.createAuthorityList("ROLE_ADMIN"));
	}

}
