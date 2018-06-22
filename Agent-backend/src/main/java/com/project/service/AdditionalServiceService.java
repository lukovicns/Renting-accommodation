package com.project.service;

import java.util.List;
import java.util.Optional;

import com.project.model.AdditionalService;

public interface AdditionalServiceService {

	Optional<AdditionalService> findOne(Long id);
	List<AdditionalService> findAll();
	AdditionalService save(AdditionalService additionalService);
	List<AdditionalService> save(List<AdditionalService> additionalService);
	void delete(Long id);
	void delete(List<Long> ids);
}
