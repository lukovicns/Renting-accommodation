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
@Table(name = "city")
public class City {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "city_id", unique = true, nullable = false)
	private Long id;
	
	@OneToOne
	@JoinColumn(name = "country_id", nullable = false)
	private Country country;

	@Column(name = "name", columnDefinition="VARCHAR(50)", nullable = false)
	private String name;

	@Column(name = "zipcode", columnDefinition="VARCHAR(50)", nullable = false)
	private String zipcode;
	
	public City() {
		
	}

	public City(Country country, String name, String zipcode) {
		super();
		this.country = country;
		this.name = name;
		this.zipcode = zipcode;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}
}
