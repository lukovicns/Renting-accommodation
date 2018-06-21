package com.project.Rentingaccommodation.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="accommodation_category")
public class AccommodationCategory {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "accommodation_category_id", updatable = false, nullable = false, insertable=false)
	private Long id;
	
	@Column(name="accommodation_category_name", columnDefinition="VARCHAR(50)", unique=true, nullable=false)
	private String name;
	
	@Enumerated(EnumType.STRING)
	private AccommodationCategoryStatus status;
	
	public AccommodationCategory() {
		
	}

	public AccommodationCategory(String name, AccommodationCategoryStatus status) {
		super();
		this.name = name;
		this.status = status;
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

	public AccommodationCategoryStatus getStatus() {
		return status;
	}

	public void setStatus(AccommodationCategoryStatus status) {
		this.status = status;
	}
}