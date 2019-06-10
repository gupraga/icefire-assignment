package com.icefire.assignment.security;

public class SecurityConstants {

	public static final String SECRET = "Secrets";
	public static final String HEADER_PREFIX = "Authorization";
	public static final String TOKEN_PREFIX = "Bearer ";
	public static final Long EXPIRATION_TIME = 86400000L;
	
	/*CALCULATE ONE DAY FOR EXPIRATION_TIME
	public static void main(String[] args) {
		System.out.println(TimeUnit.MILLISECONDS.convert(1, TimeUnit.DAYS));
	}*/
}
