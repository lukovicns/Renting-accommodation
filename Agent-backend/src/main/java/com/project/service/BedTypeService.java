package com.project.service;

import java.util.List;

import com.project.model.BedType;

public interface BedTypeService {
	
	BedType findOne(Long id);
	List<BedType> findAll();
	BedType save(BedType bedType);
	List<BedType> save(List<BedType> bedTypes);
	void delete(Long id);
	void delete(List<Long> ids);

}
