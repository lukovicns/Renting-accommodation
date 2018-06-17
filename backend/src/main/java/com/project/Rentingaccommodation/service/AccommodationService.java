package com.project.Rentingaccommodation.service;

import java.util.List;

import com.project.Rentingaccommodation.model.Accommodation;
import com.project.Rentingaccommodation.model.City;

public interface AccommodationService {
	
	Accommodation findOne(Long id);
	List<Accommodation> findAll();
	Accommodation save(Accommodation accommodation);
	void delete(Accommodation accommodation);
	Accommodation findByCity(City city);
}
