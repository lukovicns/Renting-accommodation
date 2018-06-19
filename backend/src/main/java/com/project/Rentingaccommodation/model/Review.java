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
	@JoinColumn(name="apartment_id")
	private Apartment apartment;
	
	@Column(name="comment", columnDefinition="VARCHAR(999)")
	private String comment;
	
	@Column(name="date", columnDefinition="VARCHAR(50)", nullable=false)
	private String date;
	
	@Column(name="time", columnDefinition="VARCHAR(50)", nullable=false)
	private String time;
	
	@Enumerated(EnumType.STRING)
	private ReviewStatus status;
	
//	@ManyToOne
//	protected Accommodation accommodation;

	public Review() {
		
	}
	
	public Review(User user, Apartment apartment, String comment, String date, String time) {
		super();
		this.user = user;
		this.apartment = apartment;
		this.comment = comment;
		this.date = date;
		this.time = time;
		this.status = ReviewStatus.WAITING;
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

	public Apartment getApartment() {
		return apartment;
	}

	public void setApartment(Apartment apartment) {
		this.apartment = apartment;
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

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public ReviewStatus getStatus() {
		return status;
	}

	public void setStatus(ReviewStatus status) {
		this.status = status;
	}
}
