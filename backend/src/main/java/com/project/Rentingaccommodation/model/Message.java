package com.project.Rentingaccommodation.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="message")
public class Message {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "message_id", updatable = false, nullable = false, insertable=false)
	private Long id;
	
	@OneToOne
	@JoinColumn(name="user_id")
	private User user;
	
	@OneToOne
	@JoinColumn(name="agent_id")
	private Agent agent;
	
	@OneToOne
	@JoinColumn(name="room_id")
	private Apartment room;
	
	@Column(name="date", nullable=false)
	private String date;
	
	@Column(name="message_text", columnDefinition="VARCHAR(999)", nullable=false)
	private String text;
	
	@Enumerated(EnumType.STRING)
	private MessageStatus status;
	
	@Enumerated(EnumType.STRING)
	private Direction direction;
	
	public Message() {
		
	}

	public Message(Long id, User user, Agent agent, Apartment room, String date, String text, MessageStatus status, Direction direction) {
		super();
		this.id = id;
		this.user = user;
		this.agent = agent;
		this.room = room;
		this.date = date;
		this.text = text;
		this.status = status;
		this.direction = direction;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Agent getAgent() {
		return agent;
	}

	public void setAgent(Agent agent) {
		this.agent = agent;
	}

	public Apartment getRoom() {
		return room;
	}

	public void setRoom(Apartment room) {
		this.room = room;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public MessageStatus getStatus() {
		return status;
	}

	public void setStatus(MessageStatus status) {
		this.status = status;
	}

	public Direction getDirection() {
		return direction;
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
	}
}

