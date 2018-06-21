package com.project.Rentingaccommodation.service;

import java.util.List;
import com.project.Rentingaccommodation.model.AccommodationCategory;

public interface AccommodationCategoryService {
	
	AccommodationCategory findOne(Long id);
	List<AccommodationCategory> findAll();
	AccommodationCategory save(AccommodationCategory category);
	void delete(AccommodationCategory category);
	List<AccommodationCategory> findActiveCategories();
	List<AccommodationCategory> findInactiveCategories();
	AccommodationCategory findByCategoryName(String name);
}
