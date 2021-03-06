package com.project.Rentingaccommodation.service;

import java.util.List;

import com.project.Rentingaccommodation.model.Country;

public interface CountryService {

	Country findOne(Long id);
	Country findByCodeAndName(String code, String name);
	List<Country> findAll();
	Country save(Country country);
	List<Country> save(List<Country> countries);
	Country delete(Long id);
	void delete(List<Long> ids);
}
