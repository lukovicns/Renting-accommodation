package com.project.Rentingaccommodation.model;

import javax.persistence.*;

@Entity
@Table(name="user")
public class User {
	
	@Id
	@Column(name = "user_id", updatable = false, nullable = false, insertable=false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="name", nullable=false)
	private String name;
	
	@Column(name="surname", nullable=false)
	private String surname;
	
	@GeneratedValue
	@Column(name="salt",unique=true/*, nullable=false*/)
	private byte[] salt;
	
	@Column(name="password", nullable=false)
	private String password;
	
	@Column(name="email", unique=true, nullable=false)
	private String email;

	@OneToOne
	@JoinColumn(name = "city_id", nullable = false)
	private City city;
	
	@Column(name="street", nullable=false)
	private String street;
	
	@Column(name="phone", nullable=false)
	private String phone;
	
	@Enumerated(EnumType.STRING)
	private UserStatus status;
	
	public User() {
		
	}

	public User(Long id, String name, String surname, byte[] salt, String password, String email, City city,
			String street, String phone, UserStatus status) {
		super();
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.salt = salt;
		this.password = password;
		this.email = email;
		this.city = city;
		this.street = street;
		this.phone = phone;
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

	public UserStatus getStatus() {
		return status;
	}

	public void setStatus(UserStatus status) {
		this.status = status;
	}
}

