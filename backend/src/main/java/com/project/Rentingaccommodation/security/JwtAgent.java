package com.project.Rentingaccommodation.security;

public class JwtAgent {
	
	private Long id;
    private String email;
    private String role;
    private String privilege;
    
	public JwtAgent(Long id, String email, String role, String privilege) {
		super();
		this.id = id;
		this.email = email;
		this.role = role;
		this.privilege = privilege;
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

	public String getPrivilege() {
		return privilege;
	}

	public void setPrivilege(String privilege) {
		this.privilege = privilege;
	}
	
}
