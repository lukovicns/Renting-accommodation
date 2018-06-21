package com.project.Rentingaccommodation.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.project.Rentingaccommodation.model.City;
import com.project.Rentingaccommodation.model.Country;
import com.project.Rentingaccommodation.repository.CityRepository;
import com.project.Rentingaccommodation.repository.CountryRepository;
import com.project.Rentingaccommodation.service.CityService;
import com.project.Rentingaccommodation.service.CountryService;

@Service
public class JpaCityService implements CityService {
	
	@Autowired
	private CityRepository repository;
	
	@Autowired
	private CountryService countryService;

	@Override
	public City findOne(Long id) {
		return repository.getOne(id);
	}

	@Override
	public List<City> findAll() {
		return repository.findAll();
	}

	@Override
	public City save(City city) {
		return repository.save(city);
	}

	@Override
	public List<City> save(List<City> cities) {
		return repository.saveAll(cities);
	}

	@Override
	public City delete(Long id) {
		City city = findOne(id);
		if (city != null) {
			repository.delete(city);
			return city;
		}
		return null;
	}

	@Override
	public void delete(List<Long> ids) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<City> findByCountry(Long id) {
		Country country = countryService.findOne(id);
		return repository.findByCountry(country);
	}

}
