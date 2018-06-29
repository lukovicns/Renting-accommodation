package com.project.model.DTO;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class ApartmentDTO {
	
	@Pattern(regexp ="[0-9]*")
	private String id;
	
	@NotNull
	private String name;
	
	@NotNull
	private String bedType;

	@NotNull @Pattern(regexp ="[0-9]+")
	private String size;
	
	@NotNull @Pattern(regexp ="[0-9]+")
	private String numOfRooms;
	
	@NotNull @Pattern(regexp ="[0-9]+")
	private String numOfGuests;
	
	@Size(max = 900)
	private String description;
	
	@NotNull
	private String image;
	
	@NotNull
	private String additionalService;
	
	@NotNull 
	private String pricePlans;

	public ApartmentDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ApartmentDTO(String error) {
		this.name = error;
	}
	
	public ApartmentDTO(String id, String name, String bedType, String size, String numOfRooms, String numOfGuests,
			String description, String image, String additionalService, String pricePlans) {
		super();
		this.id = id;
		this.name = name;
		this.bedType = bedType;
		this.size = size;
		this.numOfRooms = numOfRooms;
		this.numOfGuests = numOfGuests;
		this.description = description;
		this.image = image;
		this.additionalService = additionalService;
		this.pricePlans = pricePlans;
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

	public String getBedType() {
		return bedType;
	}

	public void setBedType(String bedType) {
		this.bedType = bedType;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getNumOfRooms() {
		return numOfRooms;
	}

	public void setNumOfRooms(String numOfRooms) {
		this.numOfRooms = numOfRooms;
	}

	public String getNumOfGuests() {
		return numOfGuests;
	}

	public void setNumOfGuests(String numOfGuests) {
		this.numOfGuests = numOfGuests;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getAdditionalService() {
		return additionalService;
	}

	public void setAdditionalService(String additionalService) {
		this.additionalService = additionalService;
	}

	public String getPricePlans() {
		return pricePlans;
	}

	public void setPricePlans(String pricePlans) {
		this.pricePlans = pricePlans;
	}
	
}
