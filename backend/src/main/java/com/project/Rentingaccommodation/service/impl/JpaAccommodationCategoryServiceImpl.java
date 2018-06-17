package com.project.Rentingaccommodation.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.Rentingaccommodation.model.AccommodationCategory;
import com.project.Rentingaccommodation.repository.AccommodationCategoryRepository;
import com.project.Rentingaccommodation.service.AccommodationCategoryService;

@Transactional
@Service
public class JpaAccommodationCategoryServiceImpl implements AccommodationCategoryService {

	@Autowired
	private AccommodationCategoryRepository repository;

	@Override
	public AccommodationCategory findOne(Long id) {
		for (AccommodationCategory category : repository.findAll()) {
			if (category.getId() == id) {
				return category;
			}
		}
		return null;
	}

	@Override
	public List<AccommodationCategory> findAll() {
		return repository.findAll();
	}

	@Override
	public AccommodationCategory save(AccommodationCategory category) {
		return repository.save(category);
	}

	@Override
	public void delete(AccommodationCategory category) {
		repository.delete(category);
	}
}

