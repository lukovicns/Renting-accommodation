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
	
	@Enumerated(EnumType.STRING)
	private UserStatus status;
	
}

