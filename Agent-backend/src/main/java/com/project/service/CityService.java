package com.project.service;

import java.util.List;

import com.project.model.City;


public interface CityService {
	
	City findOne(Long id);
	List<City> findAll();
	City save(City city);
	List<City> save(List<City> cities);
	City delete(Long id);
	void delete(List<Long> ids);
}
