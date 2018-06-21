package com.project.Rentingaccommodation.model;

public class Token {

	private String token;
	private String error;
	
	public Token() {
		
	}
	
	public Token(String token, String error) {
		super();
		this.token = token;
		this.error = error;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}
}
