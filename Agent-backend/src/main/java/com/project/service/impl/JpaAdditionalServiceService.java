package com.project.service.impl;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.project.model.AdditionalService;
import com.project.repository.AdditionalServiceRepository;
import com.project.service.AdditionalServiceService;

@Service
@Transactional
public class JpaAdditionalServiceService implements AdditionalServiceService{

	private static AdditionalServiceRepository repository;
	
	public JpaAdditionalServiceService(AdditionalServiceRepository repository) {
		if(repository != null)
			JpaAdditionalServiceService.repository = repository;
	}

	@Override
	public Optional<AdditionalService> findOne(Long id) {
		// TODO Auto-generated method stub
		return repository.findById(id);
	}

	@Override
	public List<AdditionalService> findAll() {
		// TODO Auto-generated method stub
		return repository.findAll();
	}

	@Override
	public AdditionalService save(AdditionalService additionalService) {
		// TODO Auto-generated method stub
		return repository.save(additionalService);
	}

	@Override
	public List<AdditionalService> save(List<AdditionalService> additionalService) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(List<Long> ids) {
		// TODO Auto-generated method stub
		
	}

}
