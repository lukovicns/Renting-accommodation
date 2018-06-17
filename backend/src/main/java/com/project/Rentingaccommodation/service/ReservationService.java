package com.project.Rentingaccommodation.service;

import java.util.List;

import com.project.Rentingaccommodation.model.Apartment;
import com.project.Rentingaccommodation.model.Reservation;

public interface ReservationService {

	List<Reservation> findAll();
	List<Reservation> findByApartmentId(long id);
	Reservation findOne(long id);
	void save(Reservation reservation);
	Reservation delete(Long id);
	boolean isAvailable(Apartment apartment, String startDate, String endDate);
	List<Reservation> findUserReservations(String email);
	List<Apartment> findByQueryParams(String city, String startDate, String endDate);
}
