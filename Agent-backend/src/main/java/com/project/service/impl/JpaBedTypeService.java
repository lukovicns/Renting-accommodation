package com.project.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.project.model.BedType;
import com.project.repository.BedTypeRepository;
import com.project.service.BedTypeService;

@Service
@Transactional
public class JpaBedTypeService implements BedTypeService{
	
	private static BedTypeRepository repository;
	
	public JpaBedTypeService(BedTypeRepository repository)
	{
		if(repository != null)
			JpaBedTypeService.repository = repository;
	}

	@Override
	public BedType findOne(Long id) {
		// TODO Auto-generated method stub
		return repository.getOne(id);
	}

	@Override
	public List<BedType> findAll() {
		// TODO Auto-generated method stub
		return repository.findAll();
	}

	@Override
	public BedType save(BedType bedType) {
		// TODO Auto-generated method stub
		return repository.save(bedType);
	}

	@Override
	public List<BedType> save(List<BedType> bedTypes) {
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
