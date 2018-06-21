package com.project.web_service.wrappers.requests;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "deleteApartment", namespace = "http://com.project/web_service/wrappers")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "deleteApartment", namespace = "http://com.project/web_service/wrappers", propOrder = {"id"})

public class DeleteApartment {

	@XmlElement(name = "id")
    private String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
