package com.project.Rentingaccommodation.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.project.Rentingaccommodation.model.AccommodationCategory;
import com.project.Rentingaccommodation.model.AccommodationType;
import com.project.Rentingaccommodation.model.AdditionalService;
import com.project.Rentingaccommodation.model.Apartment;
import com.project.Rentingaccommodation.model.ApartmentAdditionalService;
import com.project.Rentingaccommodation.model.City;
import com.project.Rentingaccommodation.repository.ApartmentRepository;
import com.project.Rentingaccommodation.service.ApartmentAdditionalServiceService;
import com.project.Rentingaccommodation.service.ApartmentService;
import com.project.Rentingaccommodation.service.ReservationService;

@Transactional
@Service
public class JpaApartmentService implements ApartmentService {

	@Autowired
	private ApartmentRepository repository;
	
	@Autowired
	private ReservationService reservationService;
	
	@Autowired
	private ApartmentAdditionalServiceService apartmentAdditionalServiceService;

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
	public List<Apartment> findByBasicQueryParams(City city, int persons, String startDate, String endDate) {
		List<Apartment> apartments = new ArrayList<Apartment>();
		for (Apartment apartment : findAll()) {
		if (reservationService.isAvailable(apartment, startDate, endDate) &&
			apartment.getAccommodation().getCity().getId() == city.getId() &&
			apartment.getMaxNumberOfGuests() >= persons) {
				apartments.add(apartment);
			}
		}
		return apartments;
	}

	@Override
		public List<Apartment> findByAdvancedQueryParams(City city, int persons, String startDate, String endDate,
				AccommodationCategory category, AccommodationType type, List<AdditionalService> additionalServices) {
		List<Apartment> apartments = new ArrayList<Apartment>();
		for (Apartment apartment : findAll()) {
		boolean flag = false;
		if (reservationService.isAvailable(apartment, startDate, endDate) &&
			apartment.getAccommodation().getCity().getId() == city.getId() &&
			apartment.getMaxNumberOfGuests() >= persons) {
				// Advanced search logic
			for (AdditionalService additionalService : additionalServices) {
				for (ApartmentAdditionalService apartmentAdditionalService : apartmentAdditionalServiceService.findByApartment(apartment)) {
						if (apartmentAdditionalService.getAdditionalService().getId() == additionalService.getId()) {
							flag = true;
							break;
						}
					}
				}
				if (flag) {
					apartments.add(apartment);
				}
			}
		}
		return apartments;
	}
}
