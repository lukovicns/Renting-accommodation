package com.project.Rentingaccommodation.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="room_service")
public class RoomService {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "room_service_id", updatable = false, nullable = false, insertable=false)
	private Long id;
	
	@OneToOne
	@JoinColumn(name="room_id")
	private Room room;
	
	@OneToOne
	@JoinColumn(name="additional_service_id")
	private AdditionalService additionalService;
	
	public RoomService() {
		
	}

	public RoomService(Long id, Room room, AdditionalService additionalService) {
		super();
		this.id = id;
		this.room = room;
		this.additionalService = additionalService;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}

	public AdditionalService getAdditionalService() {
		return additionalService;
	}

	public void setAdditionalService(AdditionalService additionalService) {
		this.additionalService = additionalService;
	}
}
