package com.project.Rentingaccommodation.service;

import java.util.List;

import com.project.Rentingaccommodation.model.Accommodation;

public interface AccommodationService {
	
	Accommodation findOne(Long id);
	List<Accommodation> findAll();
	Accommodation save(Accommodation accommodation);
	void delete(Accommodation accommodation);
}
