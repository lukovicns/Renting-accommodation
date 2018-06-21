package com.project.Rentingaccommodation.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.Rentingaccommodation.model.AccommodationType;
import com.project.Rentingaccommodation.model.AccommodationTypeStatus;
import com.project.Rentingaccommodation.repository.AccommodationTypeRepository;
import com.project.Rentingaccommodation.service.AccommodationTypeService;

@Transactional
@Service
public class JpaAccommodationTypeServiceImpl implements AccommodationTypeService{

	@Autowired
	private AccommodationTypeRepository repository;
	
	@Override
	public AccommodationType findOne(Long id) {
		for (AccommodationType a : repository.findAll()) {
			if (a.getId() == id) {
				return a;
			}
		}
		return null;
	}
	
	@Override
	public AccommodationType findByTypeName(String name) {
		for (AccommodationType type : findAll()) {
			if (type.getName().toLowerCase().equals(name.toLowerCase())) {
				return type;
			}
		}
		return null;
	}

	@Override
	public List<AccommodationType> findActiveTypes() {
		List<AccommodationType> activeTypes = new ArrayList<AccommodationType>();
		for (AccommodationType type : findAll()) {
			if (type.getStatus().equals(AccommodationTypeStatus.ACTIVE)) {
				activeTypes.add(type);
			}
		}
		return activeTypes;
	}

	@Override
	public List<AccommodationType> findInactiveTypes() {
		List<AccommodationType> activeTypes = new ArrayList<AccommodationType>();
		for (AccommodationType type : findAll()) {
			if (type.getStatus().equals(AccommodationTypeStatus.INACTIVE)) {
				activeTypes.add(type);
			}
		}
		return activeTypes;
	}
	
	@Override
	public List<AccommodationType> findAll() {
		return repository.findAll();
	}

	@Override
	public AccommodationType save(AccommodationType type) {
		return repository.save(type);
	}

	@Override
	public void delete(AccommodationType type) {
		repository.delete(type);
	}
}
