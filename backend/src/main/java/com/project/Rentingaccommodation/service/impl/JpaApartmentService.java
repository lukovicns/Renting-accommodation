package com.project.Rentingaccommodation.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.Rentingaccommodation.model.AccommodationCategory;
import com.project.Rentingaccommodation.model.AccommodationType;
import com.project.Rentingaccommodation.model.Apartment;
import com.project.Rentingaccommodation.model.City;
import com.project.Rentingaccommodation.repository.ApartmentRepository;
import com.project.Rentingaccommodation.service.ApartmentService;
import com.project.Rentingaccommodation.service.ReservationService;

@Transactional
@Service
public class JpaApartmentService implements ApartmentService {

	@Autowired
	private ApartmentRepository repository;
	
	@Autowired
	private ReservationService reservationService;

	@Override
	public List<Apartment> findAll() {
		return repository.findAll();
	}
	
	@Override
	public Apartment findOne(Long id) {
		for (Apartment a : findAll()) {
			if (a.getId() == id) {
				return a;
			}
		}
		return null;
	}

	@Override
	public List<Apartment> findApartmentsByAccommodationId(Long id) {
		List<Apartment> accommodationApartments = new ArrayList<Apartment>();
		for (Apartment apartment : repository.findAll()) {
			if (apartment.getAccommodation().getId() == id) {
				accommodationApartments.add(apartment);
			}
		}
		return accommodationApartments;
	}
	
	@Override
	public Apartment findApartmentByAccommodationId(Long id, Long accommodationId) {
		for (Apartment apartment : findApartmentsByAccommodationId(accommodationId)) {
			if (apartment.getId() == id) {
				return apartment;
			}
		}
		return null;
	}

	@Override
	public Apartment save(Apartment apartment) {
		return repository.save(apartment);
	}
	
	@Override
	public void delete(Apartment apartment) {
		// TODO Auto-generated method stub
	}
	
	@Override
	public List<Apartment> findByQueryParams(City city, String startDate, String endDate, int persons) {
		List<Apartment> availableApartments = new ArrayList<Apartment>();
		for (Apartment apartment : findAll()) {
			if (reservationService.isAvailable(apartment, startDate, endDate) &&
				apartment.getAccommodation().getCity().getId() == city.getId() &&
				apartment.getMaxNumberOfGuests() >= persons) {
				availableApartments.add(apartment);
			}
		}
		return availableApartments;
	}

	@Override
	public List<Apartment> findByQueryParams(City city, String startDate, String endDate, int persons, AccommodationType type, AccommodationCategory category) {
		List<Apartment> availableApartments = new ArrayList<Apartment>();
		for (Apartment apartment : findAll()) {
			if (reservationService.isAvailable(apartment, startDate, endDate) &&
				apartment.getAccommodation().getCity().getId() == city.getId() &&
				apartment.getMaxNumberOfGuests() >= persons &&
				apartment.getAccommodation().getType().getId() == type.getId() &&
				apartment.getAccommodation().getCategory().getId() == category.getId()) {
				availableApartments.add(apartment);
			}
		}
		return availableApartments;
	}
}
