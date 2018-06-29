package com.project.model.DTO;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class MessageDTO {

	@Pattern(regexp ="[0-9]*")
	String id;
	
	@NotNull
	String name;
	
	@NotNull
	String surname;
	
	@NotNull
	String content;
	
	@NotNull @Pattern(regexp="[0-9]{4}-[0-9]{2}-[0-9]{2}")
	String date;
	
	@NotNull @Pattern(regexp="[0-24]{1}:[0-59]{1}")
	String time;
	
	@NotNull @Email
	String agentEmail;
	
	@NotNull @Email
	String userEmail;
	
	public MessageDTO(String id, String name, String surname, String content, String date, String time,
			String agentEmail, String userEmail) {
		super();
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.content = content;
		this.date = date;
		this.time = time;
		this.userEmail = userEmail;
		this.agentEmail = agentEmail;
	}
	public MessageDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getAgentEmail() {
		return agentEmail;
	}
	public void setAgentEmail(String agentEmail) {
		this.agentEmail = agentEmail;
	}
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	
	
	
}
