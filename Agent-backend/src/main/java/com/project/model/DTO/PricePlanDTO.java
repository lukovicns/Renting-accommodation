package com.project.model.DTO;

public class PricePlanDTO {
	
	private String startDate;
	
	private String endDate;
	
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
