package com.project.Rentingaccommodation.security;

import com.project.Rentingaccommodation.model.UserRoles;

public class JWTUser {
	
    private String email;
    private String password;
    private UserRoles role;
	
    public JWTUser() {
    	
    }
    
	public JWTUser(String email, String password) {
		super();
		this.email = email;
		this.password = password;
	}

	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public UserRoles getRole() {
		return role;
	}

	public void setRole(UserRoles role) {
		this.role = role;
	}
}
