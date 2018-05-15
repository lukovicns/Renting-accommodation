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
@Table(name="accommodation")
public class Accommodation {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "accommodation_id", updatable = false, nullable = false, insertable=false)
	private Long id;
	
	@Column(name="name", nullable=false)
	private String name;
	
	@OneToOne
	@JoinColumn(name="accommodation_type_id")
	private AccommodationType type;
	
	@Column(name="email", nullable=false)
	private String email;
	
	@Column(name="country", nullable=false)
	private String country;
	
	@Column(name="city", nullable=false)
	private String city;
	
	@Column(name="address", nullable=false)
	private String address;
	
	@Column(name="phone", nullable=false)
	private String phone;
	
	@Column(name="description", nullable=false)
	private String description;
	
	@OneToOne
	@JoinColumn(name="accommodation_category_id")
	private AccommodationCategory category;
	
	@OneToOne
	@JoinColumn(name="agent_id")
	private Agent agent;
	
	@Column(name = "images", columnDefinition = "LONGBLOB")
	private byte[] images;
	
	@Transient
	private List<String> imageList;
	
	public Accommodation() {
		
	}

	public Accommodation(Long id, String name, AccommodationType type, String email, String country, String city,
			String address, String phone, String description, AccommodationCategory category, Agent agent) {
		super();
		this.id = id;
		this.name = name;
		this.type = type;
		this.email = email;
		this.country = country;
		this.city = city;
		this.address = address;
		this.phone = phone;
		this.description = description;
		this.category = category;
		this.agent = agent;
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

	public AccommodationType getType() {
		return type;
	}

	public void setType(AccommodationType type) {
		this.type = type;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public AccommodationCategory getCategory() {
		return category;
	}

	public void setCategory(AccommodationCategory category) {
		this.category = category;
	}

	public Agent getAgent() {
		return agent;
	}

	public void setAgent(Agent agent) {
		this.agent = agent;
	}

	public byte[] getImages() {
		return images;
	}

	public void setImages(byte[] images) {
		this.images = images;
	}

	public List<String> getImageList() {
		return imageList;
	}

	public void setImageList(List<String> imageList) {
		this.imageList = imageList;
	}
}
