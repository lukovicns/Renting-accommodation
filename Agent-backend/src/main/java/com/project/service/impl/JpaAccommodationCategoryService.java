package com.project.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.project.model.AccommodationCategory;
import com.project.repository.AccommodationCategoryRepository;
import com.project.service.AccommodationCategoryService;

@Service
@Transactional
public class JpaAccommodationCategoryService implements AccommodationCategoryService{

	private static AccommodationCategoryRepository repository;
		
	public JpaAccommodationCategoryService(AccommodationCategoryRepository repository) {
		if(repository != null)
		JpaAccommodationCategoryService.repository = repository;
	}
	
	@Override
	public AccommodationCategory findOne(Long id) {
		// TODO Auto-generated method stub
		return repository.getOne(id);
	}

	@Override
	public List<AccommodationCategory> findAll() {
		// TODO Auto-generated method stub
		return repository.findAll();
	}
	
	

}
