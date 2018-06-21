package com.project.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.project.model.AccommodationType;
import com.project.repository.AccommodationTypeRepository;
import com.project.service.AccommodationTypeService;

@Transactional
@Service
public class JpaAccommodationTypeService implements AccommodationTypeService{
	
	private static AccommodationTypeRepository repository;
	
	public AccommodationTypeRepository getRepository() {
		return repository;
	}

	public void setRepository(AccommodationTypeRepository repository) {
		JpaAccommodationTypeService.repository = repository;
	}

	public JpaAccommodationTypeService(AccommodationTypeRepository repository) {
		if(repository != null)
			JpaAccommodationTypeService.repository = repository;
	}
	
	@Override
	public AccommodationType findOne(Long id) {
		// TODO Auto-generated method stub
		return repository.getOne(id);
	}

	@Override
	public List<AccommodationType> findAll() {
		// TODO Auto-generated method stub
		return repository.findAll();
	}
	
	

}
