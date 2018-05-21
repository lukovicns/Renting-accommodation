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
@Table(name="review")
public class Review {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "review_id", updatable = false, nullable = false, insertable=false)
	private Long id;
	
	@OneToOne
	@JoinColumn(name="user_id")
	private User user;
	
	@OneToOne
	@JoinColumn(name="room_id")
	private Apartment room;
	
	@Column(name="comment", columnDefinition="VARCHAR(999)")
	private String comment;
	
	@Column(name="date", columnDefinition="VARCHAR(50)", nullable=false)
	private String date;
	
	@Column(name="rating", nullable=false)
	private int rating;
	
	@Enumerated(EnumType.STRING)
	private ReviewStatus status;

	public Review() {
		
	}
	
	public Review(Long id, User user, Apartment room, String comment, String date, int rating, ReviewStatus status) {
		super();
		this.id = id;
		this.user = user;
		this.room = room;
		this.comment = comment;
		this.date = date;
		this.rating = rating;
		this.status = status;
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

	public Apartment getRoom() {
		return room;
	}

	public void setRoom(Apartment room) {
		this.room = room;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public ReviewStatus getStatus() {
		return status;
	}

	public void setStatus(ReviewStatus status) {
		this.status = status;
	}
}
