package com.project.web_service.wrappers.requests;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementRefs;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.w3._2000._09.xmldsig.SignatureType;

@XmlRootElement(name = "addReservationRequest", namespace = "http://com.project/web_service/wrappers")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "addReservationRequest", namespace = "http://com.project/web_service/wrappers", propOrder = {"apartmentId", "startDate", "endDate", "Signature"})
public class AddReservationRequest {

	@XmlElement(name = "apartmentId")
    private String apartmentId;
	
	@XmlElement(name = "startDate")
    private String startDate;
 	
	@XmlElement(name = "endDate")
    private String endDate;

	@XmlElement(name = "Signature", namespace = "http://www.w3.org/2000/09/xmldsig#")
	private SignatureType Signature;
	
	public String getApartmentId() {
		return apartmentId;
	}

	public void setApartmentId(String apartmentId) {
		this.apartmentId = apartmentId;
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

	public SignatureType getSignature() {
		return Signature;
	}

	public void setSignature(SignatureType signature) {
		this.Signature = signature;
	}

	
	
}	
