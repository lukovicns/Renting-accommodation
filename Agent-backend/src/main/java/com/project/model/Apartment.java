package com.project.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="apartment")
public class Apartment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "apartment_id", updatable = false, nullable = false, insertable=false)
	private Long id;
	
	@Column(name="name", columnDefinition="VARCHAR(50)", nullable=false)
	private String name;
	
	@OneToOne
	@JoinColumn(name="bed_type_id")
	private BedType type;
	
	@Column(name="description", columnDefinition="VARCHAR(900)")
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
	
/*	@Column(name = "images", columnDefinition = "LONGBLOB")
	private byte[] images;
	
	@Transient
	private List<String> imageList;
*/	
	public Apartment() {
		
	}

	public Apartment(Long id, String name, BedType type, String description, Accommodation accommodation, int size,
			int maxNumberOfGuests, int numberOfRooms) {
		super();
		this.id = id;
		this.name = name;
		this.type = type;
		this.description = description;
		this.accommodation = accommodation;
		this.size = size;
		this.maxNumberOfGuests = maxNumberOfGuests;
		this.numberOfRooms = numberOfRooms;
	}
	
	public Apartment(String name, BedType type, String description, Accommodation accommodation, int size,
			int maxNumberOfGuests, int numberOfRooms) {
		super();
		this.name = name;
		this.type = type;
		this.description = description;
		this.accommodation = accommodation;
		this.size = size;
		this.maxNumberOfGuests = maxNumberOfGuests;
		this.numberOfRooms = numberOfRooms;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BedType getType() {
		return type;
	}

	public void setType(BedType type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Accommodation getAccommodation() {
		return accommodation;
	}

	public void setAccommodation(Accommodation accommodation) {
		this.accommodation = accommodation;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getMaxNumberOfGuests() {
		return maxNumberOfGuests;
	}

	public void setMaxNumberOfGuests(int maxNumberOfGuests) {
		this.maxNumberOfGuests = maxNumberOfGuests;
	}

	public int getNumberOfRooms() {
		return numberOfRooms;
	}

	public void setNumberOfRooms(int numberOfRooms) {
		this.numberOfRooms = numberOfRooms;
	}

}

