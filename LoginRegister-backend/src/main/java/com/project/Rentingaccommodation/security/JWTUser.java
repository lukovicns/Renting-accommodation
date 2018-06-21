package com.project.Rentingaccommodation.security;

import com.project.Rentingaccommodation.model.UserRoles;

public class JWTUser {
	
    private String email;
    private UserRoles role;
	
    public JWTUser() {
    	
    }
    
	public JWTUser(String email) {
		super();
		this.email = email;
	}

	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}

	public UserRoles getRole() {
		return role;
	}

	public void setRole(UserRoles role) {
		this.role = role;
	}
}
