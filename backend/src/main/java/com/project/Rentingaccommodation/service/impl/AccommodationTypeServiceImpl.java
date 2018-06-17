package com.project.Rentingaccommodation.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.Rentingaccommodation.model.AccommodationType;
import com.project.Rentingaccommodation.repository.AccommodationTypeRepository;
import com.project.Rentingaccommodation.service.AccommodationTypeService;

@Transactional
@Service
public class AccommodationTypeServiceImpl implements AccommodationTypeService{

	@Autowired
	private AccommodationTypeRepository accommodationTypeRepository;
	
	@Override
	public AccommodationType save(AccommodationType at) {
		return this.accommodationTypeRepository.save(at);
	}

	@Override
	public boolean delete(String id) {
		if (this.accommodationTypeRepository.findById(Integer.parseInt(id)).isPresent()) {
			this.accommodationTypeRepository.delete(this.accommodationTypeRepository.findById(Integer.parseInt(id)).get());
			return true;
		}
		return false;
	}

	@Override
	public List<AccommodationType> getAll() {
		return this.accommodationTypeRepository.findAll();
	}

}

