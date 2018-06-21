package com.project.Rentingaccommodation.service;

import java.util.List;

import com.project.Rentingaccommodation.model.AccommodationType;


public interface AccommodationTypeService {

	AccommodationType findOne(Long id);
	List<AccommodationType> findAll();
	AccommodationType save(AccommodationType type);
	void delete(AccommodationType type);
	List<AccommodationType> findActiveTypes();
	List<AccommodationType> findInactiveTypes();
	AccommodationType findByTypeName(String name);
}
