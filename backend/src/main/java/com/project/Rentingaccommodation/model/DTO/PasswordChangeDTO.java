package com.project.Rentingaccommodation.model.DTO;

public class PasswordChangeDTO {

	private String oldPassword;
	private String newPassword;
	private String token;
	
	public PasswordChangeDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getOldPassword() {
		return oldPassword;
	}
	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}
	public String getNewPassword() {
		return newPassword;
	}
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	
}
