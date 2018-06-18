package com.project.Rentingaccommodation.service;

import java.util.List;

import com.project.Rentingaccommodation.model.AccommodationCategory;
import com.project.Rentingaccommodation.model.AccommodationType;
import com.project.Rentingaccommodation.model.Apartment;
import com.project.Rentingaccommodation.model.City;

public interface ApartmentService {

	Apartment findOne(Long id);
	List<Apartment> findByAccommodationId(Long id);
	List<Apartment> findAll();
	Apartment save(Apartment apartment);
	void delete(Apartment apartment);
	List<Apartment> findByQueryParams(City city, String startDate, String endDate, int persons);
	List<Apartment> findByQueryParams(City city, String startDate, String endDate, int persons, AccommodationType type, AccommodationCategory category);
}
