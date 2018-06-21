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
@Table(name="price_plan")
public class PricePlan {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "price_plan_id", updatable = false, nullable = false, insertable=false)
	private Long id;
	
	@OneToOne
	@JoinColumn(name="apartment_id")
	private Apartment apartment;
	
	@Column(name="start_date", columnDefinition="VARCHAR(50)", nullable=false)
	private String startDate;
	
	@Column(name="end_date", columnDefinition="VARCHAR(50)", nullable=false)
	private String endDate;
	
	@Column(name="price", nullable=false)
	private int price;
	
	public PricePlan() {
		
	}

	public PricePlan(Long id, Apartment apartment, String startDate, String endDate, int price) {
		super();
		this.id = id;
		this.apartment = apartment;
		this.startDate = startDate;
		this.endDate = endDate;
		this.price = price;
	}
	
	public PricePlan(Apartment apartment, String startDate, String endDate, int price) {
		super();
		this.apartment = apartment;
		this.startDate = startDate;
		this.endDate = endDate;
		this.price = price;
	}
	
	public PricePlan(String startDate, String endDate, int price) {
		super();
		this.startDate = startDate;
		this.endDate = endDate;
		this.price = price;
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

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}
}
