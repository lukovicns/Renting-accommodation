package com.project.Rentingaccommodation.service;

import java.util.List;

import com.project.Rentingaccommodation.model.Apartment;
import com.project.Rentingaccommodation.model.PricePlan;

public interface PricePlanService {
	PricePlan findOne(Long id);
	List<PricePlan> findAll();
	List<PricePlan> findApartmentPricePlans(Long apartmentId);
	PricePlan setReservationPricePlan(Apartment apartment, String startDate, String endDate);
}
