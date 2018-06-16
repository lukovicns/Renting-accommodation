package com.project.Rentingaccommodation.service;

import java.util.List;

import com.project.Rentingaccommodation.model.AccommodationCategory;


public interface AccommodationCatService {
	
	AccommodationCategory save(AccommodationCategory ac);
	boolean delete(String id);
	List<AccommodationCategory> getAll();

}
