package com.project.Rentingaccommodation.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.project.Rentingaccommodation.model.PricePlan;
import com.project.Rentingaccommodation.service.PricePlanService;

@RestController
@RequestMapping(value="/api/price-plans")
public class PricePlanController {

	@Autowired
	private PricePlanService service;
	
	@RequestMapping(value="", method=RequestMethod.GET)
	public ResponseEntity<List<PricePlan>> getPricePlans() {
		return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<Object> getPricePlan(@PathVariable Long id) {
		PricePlan pricePlan = service.findOne(id);
		if (pricePlan == null) {
			return new ResponseEntity<>("Price plan not found.", HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(pricePlan, HttpStatus.OK);
	}
	
	@RequestMapping(value="/apartment/{apartmentId}", method=RequestMethod.GET)
	public ResponseEntity<List<PricePlan>> getPricePlansForApartment(@PathVariable Long apartmentId) {
		return new ResponseEntity<>(service.findApartmentPricePlans(apartmentId), HttpStatus.OK);
	}
}
