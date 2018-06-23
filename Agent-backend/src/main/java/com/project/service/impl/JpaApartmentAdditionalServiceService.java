package com.project.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.project.model.ApartmentAdditionalService;
import com.project.repository.ApartmentAdditionalServiceRepository;
import com.project.service.ApartmentAdditionalServiceService;

@Service
@Transactional
public class JpaApartmentAdditionalServiceService implements ApartmentAdditionalServiceService {

	private static ApartmentAdditionalServiceRepository reprository;
	
	public JpaApartmentAdditionalServiceService(ApartmentAdditionalServiceRepository repositry)
	{
		if(repositry != null)
			reprository = repositry;
	}
	
	@Override
	public ApartmentAdditionalService findOne(Long id) {
		// TODO Auto-generated method stub
		return reprository.getOne(id);
	}

	@Override
	public List<ApartmentAdditionalService> findAll() {
		// TODO Auto-generated method stub
		return reprository.findAll();
	}

	@Override
	public ApartmentAdditionalService save(ApartmentAdditionalService apartmentService) {
		// TODO Auto-generated method stub
		return reprository.save(apartmentService);
	}

	@Override
	public List<ApartmentAdditionalService> save(List<ApartmentAdditionalService> apartmentServices) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		reprository.deleteById(id);
	}

	@Override
	public List<ApartmentAdditionalService> findByApartmentId(Long apartmentId) {
		// TODO Auto-generated method stub
		return reprository.findByApartmentId(apartmentId);
	}

	@Override
	public void deleteByApartmentId(Long apartmentId) {
		// TODO Auto-generated method stub
		reprository.deleteByApartmentId(apartmentId);
	}
	
}