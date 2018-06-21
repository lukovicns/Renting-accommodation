package com.project.Rentingaccommodation.model.DTO;

public class LoginDTO {

	private String password;
	
	private String email;

	public LoginDTO() {
		super();
	}

	public LoginDTO(String password, String email) {
		super();
		this.password = password;
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	
}
