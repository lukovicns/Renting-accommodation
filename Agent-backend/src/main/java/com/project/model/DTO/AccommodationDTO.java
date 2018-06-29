package com.project.model.DTO;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class AccommodationDTO {
	
	@Pattern(regexp ="[0-9]*")
	private String id;
	
	@NotNull
	private String name;
	
	@NotNull
	private String type;

	@NotNull
	private String city;
	
	@NotNull
	private String country;
	
	@NotNull
	private String street;
	
	@NotNull @Size(max = 900)
	private String description;
	
	@NotNull
	private String category;
	
	@NotNull
	private String image;

	public AccommodationDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public AccommodationDTO(String error) {
		super();
		this.name = error;
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
