package com.project.Rentingaccommodation.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.project.Rentingaccommodation.model.Country;
import com.project.Rentingaccommodation.repository.CountryRepository;
import com.project.Rentingaccommodation.service.CountryService;

@Service
public class JpaCountryService implements CountryService {
	
	@Autowired
	private CountryRepository repository;

	@Override
	public Country findOne(Long id) {
		for (Country c : repository.findAll()) {
			if (c.getId() == id) {
				return c;
			}
		}
		return null;
	}

	@Override
	public List<Country> findAll() {
		return repository.findAll();
	}

	@Override
	public Country save(Country country) {
		return repository.save(country);
	}

	@Override
	public List<Country> save(List<Country> countries) {
		return repository.saveAll(countries);
	}

	@Override
	public Country delete(Long id) {
		Country country = findOne(id);
		if (country != null) {
			repository.delete(country);
			return country;
		}
		return null;
	}

	@Override
	public void delete(List<Long> ids) {
		for (Long id: ids) {
			repository.deleteById(id);
		}
	}
}
