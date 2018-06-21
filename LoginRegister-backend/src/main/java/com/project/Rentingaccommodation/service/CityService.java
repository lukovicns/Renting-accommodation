package com.project.Rentingaccommodation.service;

import java.util.List;

import com.project.Rentingaccommodation.model.City;

public interface CityService {
	
	City findOne(Long id);
	List<City> findAll();
	City save(City city);
	List<City> save(List<City> cities);
	City delete(Long id);
	void delete(List<Long> ids);
	List<City> findByCountry(Long id);
}
