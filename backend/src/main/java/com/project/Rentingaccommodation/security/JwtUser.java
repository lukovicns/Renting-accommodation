package com.project.Rentingaccommodation.security;

public class JwtUser {
	
    private String email;
    private String role;
	
    public JwtUser() {
    	
    }
    
	public JwtUser(String email, String role) {
		super();
		this.email = email;
	}

	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
}
