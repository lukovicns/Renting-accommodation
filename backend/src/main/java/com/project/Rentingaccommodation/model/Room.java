package com.project.Rentingaccommodation.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="room")
public class Room {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "room_id", updatable = false, nullable = false, insertable=false)
	private Long id;
	
	@Column(name="name", nullable=false)
	private String name;
	
	@OneToOne
	@JoinColumn(name="bed_type_id")
	private BedType type;
	
	@Column(name="description", nullable=false)
	private String description;
	
	@OneToOne
	@JoinColumn(name="accommodation_id")
	private Accommodation accommodation;
	
	@Column(name="size", nullable=false)
	private int size;

	@Column(name="number_of_guests", nullable=false)
	private int maxNumberOfGuests;
	
	@Column(name="number_of_rooms", nullable=false)
	private int numberOfRooms;
	
	@Column(name = "images", columnDefinition = "LONGBLOB")
	private byte[] images;
	
	@Transient
	private List<String> imageList;
	
}
