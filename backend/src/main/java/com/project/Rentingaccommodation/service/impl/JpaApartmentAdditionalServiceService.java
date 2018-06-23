package com.project.Rentingaccommodation.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.project.Rentingaccommodation.model.Apartment;
import com.project.Rentingaccommodation.model.ApartmentAdditionalService;
import com.project.Rentingaccommodation.repository.ApartmentAdditionalServiceRepository;
import com.project.Rentingaccommodation.service.ApartmentAdditionalServiceService;

@Transactional
@Service
public class JpaApartmentAdditionalServiceService implements ApartmentAdditionalServiceService {

	@Autowired
	private ApartmentAdditionalServiceRepository repository;
	
	@Override
	public ApartmentAdditionalService findOne(Long id) {
		for (ApartmentAdditionalService aas : findAll()) {
			if (aas.getId() == id) {
				return aas;
			}
		}
		return null;
	}

	@Override
	public List<ApartmentAdditionalService> findAll() {
		return repository.findAll();
	}

	@Override
	public List<ApartmentAdditionalService> findByApartment(Apartment apartment) {
		List<ApartmentAdditionalService> services = new ArrayList<ApartmentAdditionalService>();
		for (ApartmentAdditionalService aas : findAll()) {
			if (aas.getApartment().getId() == apartment.getId()) {
				services.add(aas);
			}
		}
		return services;
	}
}
