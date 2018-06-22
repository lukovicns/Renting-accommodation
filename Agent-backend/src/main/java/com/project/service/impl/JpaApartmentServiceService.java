package com.project.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.project.model.ApartmentService;
import com.project.repository.ApartmentServiceRepository;
import com.project.service.ApartmentServiceService;

@Service
@Transactional
public class JpaApartmentServiceService implements ApartmentServiceService {

	private static ApartmentServiceRepository reprository;
	
	public JpaApartmentServiceService(ApartmentServiceRepository repositry)
	{
		if(repositry != null)
			reprository = repositry;
	}
	
	@Override
	public ApartmentService findOne(Long id) {
		// TODO Auto-generated method stub
		return reprository.getOne(id);
	}

	@Override
	public List<ApartmentService> findAll() {
		// TODO Auto-generated method stub
		return reprository.findAll();
	}

	@Override
	public ApartmentService save(ApartmentService apartmentService) {
		// TODO Auto-generated method stub
		return reprository.save(apartmentService);
	}

	@Override
	public List<ApartmentService> save(List<ApartmentService> apartmentServices) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		reprository.deleteById(id);
	}

	@Override
	public List<ApartmentService> findByApartmentId(Long apartmentId) {
		// TODO Auto-generated method stub
		return reprository.findByApartmentId(apartmentId);
	}

	@Override
	public void deleteByApartmentId(Long apartmentId) {
		// TODO Auto-generated method stub
		reprository.deleteByApartmentId(apartmentId);
	}

}
