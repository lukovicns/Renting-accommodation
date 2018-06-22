package com.project.service;

import java.util.List;
import java.util.Optional;

import com.project.model.Accommodation;

public interface AccommodationService {
	Optional<Accommodation> findOne(Long id);
	List<Accommodation> findAll();
	Accommodation save(Accommodation accommodation);
	List<Accommodation> save(List<Accommodation> accommodations);
	void delete(Long id);
	void delete(List<Long> ids);
}
