package com.project.service;

import java.util.List;

import com.project.model.AccommodationType;

public interface AccommodationTypeService {

	AccommodationType findOne(Long id);
	List<AccommodationType> findAll();
}
