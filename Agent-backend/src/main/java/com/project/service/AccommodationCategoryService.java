package com.project.service;

import java.util.List;

import com.project.model.AccommodationCategory;

public interface AccommodationCategoryService {

	AccommodationCategory findOne(Long id);
	List<AccommodationCategory> findAll();
}
