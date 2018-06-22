package com.project.service;

import java.util.List;

import com.project.model.PricePlan;

public interface PricePlanService {

	PricePlan findOne(Long id);
	List<PricePlan> findAll();
	PricePlan save(PricePlan pricePlan);
	List<PricePlan> save(List<PricePlan> pricePlan);
	void delete(Long id);
	void delete(List<Long> ids);
	List<PricePlan> findByApartmentId(Long id);
}
