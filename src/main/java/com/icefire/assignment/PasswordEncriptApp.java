package com.icefire.assignment;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncriptApp {
	public static void main(String[] args) {
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		System.out.println(bCryptPasswordEncoder.encode("icefirerocks"));
	}
}
