package com.project.Rentingaccommodation.service;
import java.util.List;

import com.project.Rentingaccommodation.model.AdditionalService;

public interface AdditionalServiceService {

	List<AdditionalService> findAll();
	AdditionalService findOne(Long id);
	AdditionalService save(AdditionalService additionalService);
	void delete(AdditionalService additionalService);
}
