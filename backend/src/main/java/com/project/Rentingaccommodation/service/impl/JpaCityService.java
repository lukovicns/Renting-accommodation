package com.project.Rentingaccommodation.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.project.Rentingaccommodation.model.City;
import com.project.Rentingaccommodation.repository.CityRepository;
import com.project.Rentingaccommodation.service.CityService;

@Service
public class JpaCityService implements CityService {
	
	@Autowired
	private CityRepository repository;

	@Override
	public City findOne(Long id) {
		for(City c : repository.findAll()) {
			if (c.getId() == id) {
				return c;
			}
		}
		return null;
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
