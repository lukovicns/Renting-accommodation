package com.project.service;

import java.util.List;

import com.project.model.Apartment;
import com.project.model.ApartmentAdditionalService;

public interface ApartmentAdditionalServiceService {
	
	ApartmentAdditionalService findOne(Long id);
	List<ApartmentAdditionalService> findAll();
	ApartmentAdditionalService save(ApartmentAdditionalService apartmentService);
	List<ApartmentAdditionalService> save(List<ApartmentAdditionalService> apartmentServices);
	void delete(Long id);
	List<ApartmentAdditionalService> findByApartmentId(Long apartmentId);
	void deleteByApartmentId(Long apartmentId);
}
