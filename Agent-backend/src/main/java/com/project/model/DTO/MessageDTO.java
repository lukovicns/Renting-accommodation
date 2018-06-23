package com.project.model.DTO;

public class MessageDTO {

	String id;
	String name;
	String surname;
	String content;
	String date;
	String time;
	
	
	
	public MessageDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public MessageDTO(String id, String name, String surname, String content, String date, String time) {
		super();
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.content = content;
		this.date = date;
		this.time = time;
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
	
	
	
}
