package com.project.Rentingaccommodation.service;

import java.util.List;

import com.project.Rentingaccommodation.model.AccommodationType;


public interface AccommodationTypeService {

	AccommodationType save(AccommodationType at);
	boolean delete(String id);
	List<AccommodationType> getAll();
}
