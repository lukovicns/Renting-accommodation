package com.project.Rentingaccommodation.service;

import java.util.List;

import com.project.Rentingaccommodation.model.Apartment;

public interface ApartmentService {

	Apartment findOne(Long id);
	List<Apartment> findAll();
	Apartment save(Apartment apartment);
	void delete(Apartment apartment);
}
