package com.project.Rentingaccommodation.service.impl;


import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.Rentingaccommodation.model.AccommodationCategory;
import com.project.Rentingaccommodation.repository.AccommodationCategoryRepository;
import com.project.Rentingaccommodation.service.AccommodationCatService;

@Transactional
@Service
public class AccommodationCatServiceImpl implements AccommodationCatService{

	@Autowired
	private AccommodationCategoryRepository accomodationCategoryRepository;

	@Override
	public AccommodationCategory save(AccommodationCategory ac) {
		return this.accomodationCategoryRepository.save(ac);
	}

	@Override
	public boolean delete(String id) {
		if (this.accomodationCategoryRepository.findById(Integer.parseInt(id)).isPresent()) {
			this.accomodationCategoryRepository.delete(this.accomodationCategoryRepository.findById(Integer.parseInt(id)).get());;
			return true;
		}
		return false;
	}

	@Override
	public List<AccommodationCategory> getAll() {
		return this.accomodationCategoryRepository.findAll();
	}
}

