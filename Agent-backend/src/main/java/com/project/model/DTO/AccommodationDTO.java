package com.project.model.DTO;

public class AccommodationDTO {
	
	private String id;
	
	private String name;
	
	private String type;

	private String city;
	
	private String country;
	
	private String street;
	
	private String description;
	
	private String category;
	
	private String image;

	public AccommodationDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public AccommodationDTO(String id, String name, String type, String city, String country, String street, String description,
			String category, String image) {
		super();
		this.id = id;
		this.name = name;
		this.type = type;
		this.city = city;
		this.country = country;
		this.street = street;
		this.description = description;
		this.category = category;
		this.image = image;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCity() {
		return city;
	}
	
	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}
	
	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}
	
}
