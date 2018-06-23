package com.project.Rentingaccommodation.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="image")
public class Image {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "image_id", updatable = false, nullable = false, insertable=false)
	private Long id;
	
	@Column(name="url", columnDefinition="VARCHAR(500)", nullable=false)
	private String url;
	
	@OneToOne
	@JoinColumn(name="accommodation_id", nullable = true)
	private Accommodation accommodation;
	
	@OneToOne
	@JoinColumn(name="apartment_id", nullable = true)
	private Apartment apartment;
	
	public Image() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Image(String url, Accommodation accommodation, Apartment apartment) {
		super();
		this.url = url;
		this.accommodation = accommodation;
		this.apartment = apartment;
	}

	public Image(String url, Accommodation accommodation)
	{
		this.url = url;
		this.accommodation = accommodation;
	}
	
	public Image(String url, Apartment apartment)
	{
		super();
		this.url = url;
		this.apartment = apartment;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Accommodation getAccommodation() {
		return accommodation;
	}

	public void setAccommodation(Accommodation accommodation) {
		this.accommodation = accommodation;
	}

	public Apartment getApartment() {
		return apartment;
	}

	public void setApartment(Apartment apartment) {
		this.apartment = apartment;
	}
	
}
