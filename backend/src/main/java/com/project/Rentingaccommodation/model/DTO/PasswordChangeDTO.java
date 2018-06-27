package com.project.Rentingaccommodation.model.DTO;

public class PasswordChangeDTO {

	private String oldPassword;
	private String newPassword;
	private String token;
	
	public PasswordChangeDTO() {
		
	}
	
	public PasswordChangeDTO(String oldPassword, String newPassword, String token) {
		super();
		this.oldPassword = oldPassword;
		this.newPassword = newPassword;
		this.token = token;
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
