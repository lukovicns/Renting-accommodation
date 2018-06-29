package com.project.model.DTO;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.PositiveOrZero;

public class PricePlanDTO {
	
	@NotNull @Pattern(regexp="[0-9]{4}-[0-9]{2}-[0-9]{2}")
	private String startDate;
	
	@NotNull @Pattern(regexp="[0-9]{4}-[0-9]{2}-[0-9]{2}")
	private String endDate;
	
	@NotNull
	@PositiveOrZero
	private int price;

	public PricePlanDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public PricePlanDTO(String startDate, String endDate, int price) {
		super();
		this.startDate = startDate;
		this.endDate = endDate;
		this.price = price;
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
