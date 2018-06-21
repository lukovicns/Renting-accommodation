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
@Table(name="accommodation")
public class Accommodation {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "accommodation_id", updatable = false, nullable = false, insertable=false)
	private Long id;
	
	@Column(name="name", columnDefinition="VARCHAR(50)", nullable=false)
	private String name;
	
	@OneToOne
	@JoinColumn(name="accommodation_type_id")
	private AccommodationType type;

	@OneToOne
	@JoinColumn(name="city_id")
	private City city;
	
	@Column(name="street", columnDefinition="VARCHAR(50)", nullable=false)
	private String street;
	
	@Column(name="description", columnDefinition="VARCHAR(900)")
	private String description;
	
	@OneToOne
	@JoinColumn(name="accommodation_category_id")
	private AccommodationCategory category;
	
	@OneToOne
	@JoinColumn(name="agent_id")
	private Agent agent;
	
	/*//prokomentarisati
	@Column(name = "images", columnDefinition = "LONGBLOB")
	private byte[] images;
	
	@Transient
	private List<String> imageList;*/
	
	public Accommodation() {
		
	}

	public Accommodation(String name, AccommodationType type, City city, String street, String description,
			AccommodationCategory category, Agent agent) {
		super();
		this.name = name;
		this.type = type;
		this.city = city;
		this.street = street;
		this.description = description;
		this.category = category;
		this.agent = agent;
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

	public AccommodationType getType() {
		return type;
	}

	public void setType(AccommodationType type) {
		this.type = type;
	}
	
	public City getCity() {
		return city;
	}

	public void setCity(City city) {
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

	public AccommodationCategory getCategory() {
		return category;
	}

	public void setCategory(AccommodationCategory category) {
		this.category = category;
	}

	public Agent getAgent() {
		return agent;
	}

	public void setAgent(Agent agent) {
		this.agent = agent;
	}

}
