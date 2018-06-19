package com.project.Rentingaccommodation.service;

import java.util.List;

import com.project.Rentingaccommodation.model.Apartment;
import com.project.Rentingaccommodation.model.ApartmentAdditionalService;

public interface ApartmentAdditionalServiceService {
	
	ApartmentAdditionalService findOne(Long id);
	List<ApartmentAdditionalService> findAll();
	List<ApartmentAdditionalService> findByApartment(Apartment apartment);
}
