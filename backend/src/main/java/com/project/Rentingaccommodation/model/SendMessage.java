package com.project.Rentingaccommodation.model;

public class SendMessage {

	private Long apartment;
	private Long user;
	private Long agent;
	private String text;
	
	public SendMessage() {
		
	}
	
	public SendMessage(Long apartment, Long user, Long agent, String text) {
		super();
		this.apartment = apartment;
		this.user = user;
		this.agent = agent;
		this.text = text;
	}
	
	public Long getApartment() {
		return apartment;
	}

	public void setApartment(Long apartment) {
		this.apartment = apartment;
	}

	public Long getUser() {
		return user;
	}

	public void setUser(Long user) {
		this.user = user;
	}

	public Long getAgent() {
		return agent;
	}

	public void setAgent(Long agent) {
		this.agent = agent;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
}
