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
	private Room room;
	
	@Column(name="date", nullable=false)
	private String date;
	
	@Column(name="message_text", nullable=false)
	private String text;
	
	@Enumerated(EnumType.STRING)
	private MessageStatus status;
	
	@Enumerated(EnumType.STRING)
	private Direction direction;
	
}

