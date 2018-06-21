package com.project.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.project.model.PricePlan;
import com.project.repository.PricePlanRepository;
import com.project.service.PricePlanService;

@Service
@Transactional
public class JpaPricePlanService implements PricePlanService {

	private static PricePlanRepository repository;
	
	public JpaPricePlanService(PricePlanRepository repository)
	{
		if(repository != null)
			JpaPricePlanService.repository = repository;
	}

	@Override
	public PricePlan findOne(Long id) {
		// TODO Auto-generated method stub
		return repository.getOne(id);
	}

	@Override
	public List<PricePlan> findAll() {
		// TODO Auto-generated method stub
		return repository.findAll();
	}

	@Override
	public PricePlan save(PricePlan pricePlan) {
		// TODO Auto-generated method stub
		return repository.save(pricePlan);
	}

	@Override
	public List<PricePlan> save(List<PricePlan> pricePlan) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		repository.deleteById(id);
	}

	@Override
	public void delete(List<Long> ids) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<PricePlan> findByApartmentId(Long id) {
		// TODO Auto-generated method stub
		return repository.findByApartmentId(id);
	}
}
