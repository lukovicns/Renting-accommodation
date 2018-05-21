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
@Table(name="reservation")
public class Reservation {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "reservation_id", updatable = false, nullable = false, insertable=false)
	private Long id;
	
	@OneToOne
	@JoinColumn(name="user_id")
	private User user;
	
	@OneToOne
	@JoinColumn(name="room_id")
	private Apartment room;
	
	@Column(name="start_date", columnDefinition="VARCHAR(50)", nullable=false)
	private String startDate;
	
	@Column(name="end_date", columnDefinition="VARCHAR(50)", nullable=false)
	private String endDate;
	
	@Column(name="price", nullable=false)
	private int price;
	
	@Enumerated(EnumType.STRING)
	private ReservationStatus status;
	
	public Reservation() {
		
	}

	public Reservation(Long id, User user, Apartment room, String startDate, String endDate, int price,
			ReservationStatus status) {
		super();
		this.id = id;
		this.user = user;
		this.room = room;
		this.startDate = startDate;
		this.endDate = endDate;
		this.price = price;
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

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public ReservationStatus getStatus() {
		return status;
	}

	public void setStatus(ReservationStatus status) {
		this.status = status;
	}
}
