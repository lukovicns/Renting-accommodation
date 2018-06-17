package com.project.Rentingaccommodation.service.impl;

import java.util.List;

import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.project.Rentingaccommodation.model.AccommodationType;
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
