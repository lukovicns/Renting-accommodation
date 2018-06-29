package com.project.Rentingaccommodation.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.project.Rentingaccommodation.model.AdditionalService;
import com.project.Rentingaccommodation.repository.AdditionalServiceRepository;
import com.project.Rentingaccommodation.service.AdditionalServiceService;

@Transactional
@Service
public class JpaAdditionalServiceService implements AdditionalServiceService {

	@Autowired
	private AdditionalServiceRepository repository;
	
	@Override
	public List<AdditionalService> findAll() {
		return repository.findAll();
	}

	@Override
	public AdditionalService findOne(Long id) {
		for (AdditionalService additionalService : findAll()) {
			if (additionalService.getId() == id) {
				return additionalService;
			}
		}
		return null;
	}

	@Override
	public AdditionalService save(AdditionalService additionalService) {
		return repository.save(additionalService);
	}

	@Override
	public void delete(AdditionalService additionalService) {
		repository.delete(additionalService);
	}

	@Override
	public AdditionalService findByName(String name) {
		for (AdditionalService additionalService : findAll()) {
			if (additionalService.getName().toLowerCase().equals(name.toLowerCase())) {
				return additionalService;
			}
		}
		return null;
	}
}
