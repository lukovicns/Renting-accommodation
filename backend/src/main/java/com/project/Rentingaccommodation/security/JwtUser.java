package com.project.Rentingaccommodation.security;

public class JwtUser {
	
	private Long id;
    private String email;
    private String role;
    private String status;
    private String privilege;
    
    public JwtUser() {
    	
    }
    
	public JwtUser(Long id, String email, String role, String status, String privilege) {
		super();
		this.id = id;
		this.email = email;
		this.role = role;
		this.status = status;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
