package com.project.Rentingaccommodation.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.project.Rentingaccommodation.model.Apartment;
import com.project.Rentingaccommodation.model.PricePlan;
import com.project.Rentingaccommodation.repository.PricePlanRepository;
import com.project.Rentingaccommodation.service.PricePlanService;

@Transactional
@Service
public class JpaPricePlanService implements PricePlanService {

	@Autowired
	private PricePlanRepository repository;
	
	@Override
	public PricePlan findOne(Long id) {
		for (PricePlan pricePlan : findAll()) {
			if (pricePlan.getId() == id) {
				return pricePlan;
			}
		}
		return null;
	}

	@Override
	public List<PricePlan> findAll() {
		return repository.findAll();
	}

	@Override
	public List<PricePlan> findApartmentPricePlans(Long id) {
		List<PricePlan> apartmentPricePlans = new ArrayList<PricePlan>();
		for (PricePlan pricePlan : findAll()) {
			if (pricePlan.getApartment().getId() == id) {
				apartmentPricePlans.add(pricePlan);
			}
		}
		return apartmentPricePlans;
	}

	@Override
	public PricePlan setReservationPricePlan(Apartment apartment, String startDate, String endDate) {
		List<PricePlan> apartmentPricePlans = findApartmentPricePlans(apartment.getId());
		// Ako postoji samo jedan price plan, postavi taj.
		if (apartmentPricePlans.size() == 1) {
			return apartmentPricePlans.get(0);
		}
		SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
		try {
			Date reservationStartDate = dateFormatter.parse(startDate);
			Date reservationEndDate = dateFormatter.parse(endDate);
			for (PricePlan pricePlan : apartmentPricePlans) {
				Date pricePlanStartDate = dateFormatter.parse(pricePlan.getStartDate());
				Date pricePlanEndDate = dateFormatter.parse(pricePlan.getEndDate());
				if (reservationStartDate.compareTo(pricePlanStartDate) >= 0 && reservationEndDate.compareTo(pricePlanEndDate) <= 0) {
					return apartmentPricePlans.get(apartmentPricePlans.indexOf(pricePlan));
				}
				if (reservationStartDate.compareTo(pricePlanStartDate) < 0 && reservationEndDate.compareTo(pricePlanEndDate) <= 0 && reservationEndDate.compareTo(pricePlanStartDate) >= 0) {
					return apartmentPricePlans.get(apartmentPricePlans.indexOf(pricePlan));
				}
				if (reservationStartDate.compareTo(pricePlanStartDate) >= 0 && reservationStartDate.compareTo(pricePlanEndDate) <= 0 && reservationEndDate.compareTo(pricePlanEndDate) > 0) {
					return apartmentPricePlans.get(apartmentPricePlans.indexOf(pricePlan));
				}
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
}
