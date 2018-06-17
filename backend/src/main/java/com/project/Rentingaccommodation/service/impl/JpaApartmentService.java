package com.project.Rentingaccommodation.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.Rentingaccommodation.model.Apartment;
import com.project.Rentingaccommodation.repository.ApartmentRepository;
import com.project.Rentingaccommodation.service.ApartmentService;

@Transactional
@Service
public class JpaApartmentService implements ApartmentService {

	@Autowired
	private ApartmentRepository repository;
	
	@Override
	public Apartment findOne(Long id) {
		for (Apartment a : repository.findAll()) {
			if (a.getId() == id) {
				return a;
			}
		}
		return null;
	}

	@Override
	public List<Apartment> findAll() {
		return repository.findAll();
	}

	@Override
	public Apartment save(Apartment apartment) {
		return repository.save(apartment);
	}
	
	@Override
	public void delete(Apartment apartment) {
		// TODO Auto-generated method stub
	}
}
