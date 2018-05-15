package com.project.Rentingaccommodation.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="agent")
public class Agent {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "agent_id", updatable = false, nullable = false, insertable=false)
	private Long id;
	
	@Column(name="name", nullable=false)
	private String name;
	
	@Column(name="surname", nullable=false)
	private String surname;
	
	@GeneratedValue
	@Column(name="salt",unique=true, nullable=false)
	private byte[] salt;
	
	@Column(name="hashedPassword", nullable=false)
	private String hashedPassword;
	
	@Column(name="email", unique=true, nullable=false)
	private String email;
	
	@Column(name="country", nullable=false)
	private String country;
	
	@Column(name="city", nullable=false)
	private String city;
	
	@Column(name="address", nullable=false)
	private String address;
	
	@Column(name="phone", nullable=false)
	private String phone;

	@Column(name="bussinessId", unique=true, nullable=false)
	private int businessId;

	public Agent() {
		
	}
	
	public Agent(Long id, String name, String surname, String email, String country, String city, String address,
			String phone, int businessId) {
		super();
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.country = country;
		this.city = city;
		this.address = address;
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

	public byte[] getSalt() {
		return salt;
	}

	public void setSalt(byte[] salt) {
		this.salt = salt;
	}

	public String getHashedPassword() {
		return hashedPassword;
	}

	public void setHashedPassword(String hashedPassword) {
		this.hashedPassword = hashedPassword;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
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
