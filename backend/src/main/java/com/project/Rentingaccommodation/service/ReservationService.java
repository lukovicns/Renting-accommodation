package com.project.Rentingaccommodation.service;

import java.util.List;

import com.project.Rentingaccommodation.model.Apartment;
import com.project.Rentingaccommodation.model.Reservation;

public interface ReservationService {

	List<Reservation> findAll();
	List<Reservation> findByApartmentId(Long id);
	Reservation findOne(Long id);
	void save(Reservation reservation);
	Reservation delete(Long id);
	boolean isAvailable(Apartment apartment, String startDate, String endDate);
	List<Reservation> findUserReservations(String email);
}
