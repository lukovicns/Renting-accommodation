package com.project.service;

import java.util.List;
import java.util.Optional;

import com.project.model.Apartment;

public interface ApartmentService {

	Optional<Apartment> findOne(Long id);
	List<Apartment> findAll();
	Apartment save(Apartment apartment);
	List<Apartment> save(List<Apartment> apartments);
	void delete(Long id);
	void delete(List<Long> ids);
	List<Apartment> findByAccommodationId(Long accommodation);
}
