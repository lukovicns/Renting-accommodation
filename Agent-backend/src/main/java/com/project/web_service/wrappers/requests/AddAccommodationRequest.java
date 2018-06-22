package com.project.web_service.wrappers.requests;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.w3._2000._09.xmldsig.SignatureType;

@XmlRootElement(name = "addAccommodationRequest", namespace = "http://com.project/web_service/wrappers")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "addAccommodationRequest", namespace = "http://com.project/web_service/wrappers", propOrder = {"name", "type", "city", 
		"street", "description", "category", "image", "Signature"})

public class AddAccommodationRequest {

	 	@XmlElement(name = "name")
	    private String name;
	 	
		@XmlElement(name = "type")
	    private String type;

	    @XmlElement(name = "city")
	    private String city;
	    
	    @XmlElement(name = "street")
	    private String street;
	    
	    @XmlElement(name = "description")
	    private String description;
	    
	    @XmlElement(name = "category")
	    private String category;
	    
	    @XmlElement(name = "image")
	    private String image;
	    
	    @XmlElement(name = "Signature", namespace = "http://www.w3.org/2000/09/xmldsig#")
		private SignatureType Signature;

		public SignatureType getSignature() {
			return Signature;
		}

		public void setSignature(SignatureType signature) {
			Signature = signature;
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
