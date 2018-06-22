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
@Table(name="apartment_service")
public class ApartmentService {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "apartment_service_id", updatable = false, nullable = false, insertable=false)
	private Long id;
	
	@OneToOne
	@JoinColumn(name="apartment_id")
	private Apartment apartment;
	
	@OneToOne
	@JoinColumn(name="additional_service_id")
	private AdditionalService additionalService;
	
	public ApartmentService() {
		
	}

	public ApartmentService(Long id, Apartment apartment, AdditionalService additionalService) {
		super();
		this.id = id;
		this.apartment = apartment;
		this.additionalService = additionalService;
	}
	
	public ApartmentService(Apartment apartment, AdditionalService additionalService) {
		super();
		this.apartment = apartment;
		this.additionalService = additionalService;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Apartment getApartment() {
		return apartment;
	}

	public void setApartment(Apartment apartment) {
		this.apartment = apartment;
	}

	public AdditionalService getAdditionalService() {
		return additionalService;
	}

	public void setAdditionalService(AdditionalService additionalService) {
		this.additionalService = additionalService;
	}
}
