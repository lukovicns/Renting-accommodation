package com.project.Rentingaccommodation.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.project.Rentingaccommodation.model.Apartment;
import com.project.Rentingaccommodation.model.ApartmentAdditionalService;
import com.project.Rentingaccommodation.service.ApartmentAdditionalServiceService;
import com.project.Rentingaccommodation.service.ApartmentService;

@RestController
@RequestMapping(value="api/apartment-additional-services")
public class ApartmentAdditionalServiceController {
	
	@Autowired
	private ApartmentAdditionalServiceService service;
	
	@Autowired
	private ApartmentService apartmentService;
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<Object> getApartmentAdditionalServices(@PathVariable Long id) {
		Apartment apartment = apartmentService.findOne(id);
		if (apartment == null) {
			return new ResponseEntity<>("Apartment is not found.", HttpStatus.NOT_FOUND);
		}
		List<ApartmentAdditionalService> apartmentAdditionalServices = service.findByApartment(apartment);
		return new ResponseEntity<>(apartmentAdditionalServices, HttpStatus.OK);
	}
}
