package com.project.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.project.model.City;
import com.project.repository.CityRepository;
import com.project.service.CityService;

@Service
public class JpaCityService implements CityService {
	
	private static CityRepository repository;
	
	public JpaCityService(CityRepository repository) {
		if(repository != null)
			JpaCityService.repository = repository;
	}
	
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
		for (Long id : ids) {
			repository.deleteById(id);
		}
	}
}
