package com.project.web_service.wrappers.requests;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "addApartmentRequest", namespace = "http://com.project/web_service/wrappers")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "addApartmentRequest", namespace = "http://com.project/web_service/wrappers", propOrder = {"accommodationId", "name", "bedType", "size", 
		"numOfRooms", "numOfGuests", "description", "image", "additionalService", "pricePlans"})

public class AddApartmentRequest {

	@XmlElement(name = "accommodationId")
    private String accommodationId;
	
	@XmlElement(name = "name")
    private String name;
 	
	@XmlElement(name = "bedType")
    private String bedType;

    @XmlElement(name = "size")
    private String size;
    
    @XmlElement(name = "numOfRooms")
    private String numOfRooms;
    
    @XmlElement(name = "numOfGuests")
    private String numOfGuests;
    
    @XmlElement(name = "description")
    private String description;
    
    @XmlElement(name = "image")
    private String image;
    
    @XmlElement(name = "additionalService")
    private String additionalService;
    
    @XmlElement(name = "pricePlans")
    private String pricePlans;

    public String getAccommodationId() {
		return accommodationId;
	}

	public void setAccommodationId(String accommodationId) {
		this.accommodationId = accommodationId;
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
