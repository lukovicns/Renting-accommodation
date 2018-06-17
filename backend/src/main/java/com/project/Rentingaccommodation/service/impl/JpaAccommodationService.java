package com.project.Rentingaccommodation.service.impl;

import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.project.Rentingaccommodation.model.Accommodation;
import com.project.Rentingaccommodation.model.City;
import com.project.Rentingaccommodation.repository.AccommodationRepository;
import com.project.Rentingaccommodation.service.AccommodationService;

@Transactional
@Service
public class JpaAccommodationService implements AccommodationService {

	@Autowired
	private AccommodationRepository repository;
	
	@Override
	public Accommodation findByCity(City city) {
		for (Accommodation accommodation : findAll()) {
			if (accommodation.getCity().getId() == city.getId()) {
				return accommodation;
			}
		}
		return null;
	}
	
	@Override
	public Accommodation findOne(Long id) {
		for (Accommodation a : repository.findAll()) {
			if (a.getId() == id) {
				return a;
			}
		}
		return null;
	}

	@Override
	public List<Accommodation> findAll() {
		return repository.findAll();
	}

	@Override
	public Accommodation save(Accommodation accommodation) {
		return repository.save(accommodation);
	}

	@Override
	public void delete(Accommodation accommodation) {
		repository.delete(accommodation);
	}
}
