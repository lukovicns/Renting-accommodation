package com.project.service;

import java.util.List;

import com.project.model.ApartmentService;

public interface ApartmentServiceService {

	ApartmentService findOne(Long id);
	List<ApartmentService> findAll();
	ApartmentService save(ApartmentService apartmentService);
	List<ApartmentService> save(List<ApartmentService> apartmentServices);
	void delete(Long id);
	List<ApartmentService> findByApartmentId(Long apartmentId);
	void deleteByApartmentId(Long apartmentId);
	
	
}
