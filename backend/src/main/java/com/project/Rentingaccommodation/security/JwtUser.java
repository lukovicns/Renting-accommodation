package com.project.Rentingaccommodation.security;

public class JwtUser {
	
	private Long id;
    private String email;
    private String role;
    private String status;
	
    public JwtUser() {
    	
    }
    
	public JwtUser(Long id, String email, String role, String status) {
		super();
		this.id = id;
		this.email = email;
		this.role = role;
		this.status = status;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
