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
@Table(name="agent")
public class Agent {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "agent_id", updatable = false, nullable = false, insertable=false)
	private Long id;
	
	@Column(name="name", columnDefinition="VARCHAR(50)", nullable=false)
	private String name;
	
	@Column(name="surname", columnDefinition="VARCHAR(50)", nullable=false)
	private String surname;
	
//	@GeneratedValue
//	@Column(name="salt",unique=true, nullable=false)
//	private byte[] salt;
	
	@Column(name="password", columnDefinition="VARCHAR(100)", nullable=false)
	private String password;
	
	@Column(name="email", columnDefinition="VARCHAR(50)", unique=true, nullable=false)
	private String email;
	
	@OneToOne
	@JoinColumn(name = "city_id", nullable = false)
	private City city;
	
	@Column(name="street", columnDefinition="VARCHAR(50)", nullable=false)
	private String street;
	
	@Column(name="phone", columnDefinition="VARCHAR(50)", nullable=false)
	private String phone;

	@Column(name="bussiness_id", unique=true, nullable=false)
	private int businessId;

	public Agent() {
		
	}
	
	public Agent(Long id, String name, String surname, String email, City city, String street,
			String phone, int businessId) {
		super();
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.city = city;
		this.street = street;
		this.phone = phone;
		this.businessId = businessId;
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

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

//	public byte[] getSalt() {
//		return salt;
//	}
//
//	public void setSalt(byte[] salt) {
//		this.salt = salt;
//	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public int getBusinessId() {
		return businessId;
	}

	public void setBusinessId(int businessId) {
		this.businessId = businessId;
	}
}
