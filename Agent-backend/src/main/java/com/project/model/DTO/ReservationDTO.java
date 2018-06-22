package com.project.model.DTO;

public class ReservationDTO {

	private String id;
	
	private String userSurname;
	
	private String userName;
	
	private String apartment;
	
	private String accommodation;

	private String startDate;
	
	private String endDate;
	
	private String price;
	
	private String status;

	public ReservationDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ReservationDTO(String id, String userName, String userSurname, String apartment, String accommodation, String startDate, String endDate, String price,
			String status) {
		super();
		this.id = id;
		this.userName = userName;
		this.userSurname = userSurname;
		this.apartment = apartment;
		this.accommodation = accommodation;
		this.startDate = startDate;
		this.endDate = endDate;
		this.price = price;
		this.status = status;
	}

	public ReservationDTO(String userName, String userSurname, String apartment, String accommodation, String startDate, String endDate, String price,
			String status) {
		
		this.userName = userName;
		this.userSurname = userSurname;
		this.apartment = apartment;
		this.accommodation = accommodation;
		this.startDate = startDate;
		this.endDate = endDate;
		this.price = price;
		this.status = status;
		
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserSurname() {
		return userSurname;
	}

	public void setUserSurname(String userSurname) {
		this.userSurname = userSurname;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getApartment() {
		return apartment;
	}

	public void setApartment(String apartment) {
		this.apartment = apartment;
	}
	
	public String getAccommodation() {
		return accommodation;
	}

	public void setAccommodation(String accommodation) {
		this.accommodation = accommodation;
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

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
}
