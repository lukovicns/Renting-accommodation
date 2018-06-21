package com.project.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="accommodation_type")
public class AccommodationType {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "accommodation_type_id", updatable = false, nullable = false, insertable=false)
	private Long id;
	
	@Column(name="accommodation_type_name", columnDefinition="VARCHAR(50)", unique=true, nullable=false)
	private String name;
	
	public AccommodationType() {
		
	}

	public AccommodationType(String name) {
		super();
		this.name = name;
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
}
