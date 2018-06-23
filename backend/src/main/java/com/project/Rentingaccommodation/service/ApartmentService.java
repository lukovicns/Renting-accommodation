package com.project.Rentingaccommodation.service;

import java.util.List;

import com.project.Rentingaccommodation.model.AccommodationCategory;
import com.project.Rentingaccommodation.model.AccommodationType;
import com.project.Rentingaccommodation.model.AdditionalService;
import com.project.Rentingaccommodation.model.Apartment;
import com.project.Rentingaccommodation.model.City;

public interface ApartmentService {

	Apartment findOne(Long id);
	Apartment findApartmentByAccommodationId(Long id, Long accommodationId);
	List<Apartment> findApartmentsByAccommodationId(Long id);
	List<Apartment> findAll();
	Apartment save(Apartment apartment);
	void delete(Apartment apartment);
	List<Apartment> findByBasicQueryParams(City city, int persons, String startDate, String endDate);
	List<Apartment> findByAdvancedQueryParams(City city, int persons, String startDate, String endDate,
			AccommodationCategory category, AccommodationType type, List<AdditionalService> additionalServices);
}
