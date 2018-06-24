package com.project.model.DTO;

public class MakeReservationDTO {

	private String startDate;
	private String endDate;
	
	public MakeReservationDTO(String startDate, String endDate) {
		super();
		this.startDate = startDate;
		this.endDate = endDate;
	}

	public MakeReservationDTO() {
		super();
		// TODO Auto-generated constructor stub
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
	
	
	
}
