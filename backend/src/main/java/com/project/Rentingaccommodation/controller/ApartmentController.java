package com.project.Rentingaccommodation.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.Rentingaccommodation.model.Accommodation;
import com.project.Rentingaccommodation.model.AccommodationCategory;
import com.project.Rentingaccommodation.model.AccommodationType;
import com.project.Rentingaccommodation.model.Apartment;
import com.project.Rentingaccommodation.model.City;
import com.project.Rentingaccommodation.service.AccommodationCategoryService;
import com.project.Rentingaccommodation.service.AccommodationService;
import com.project.Rentingaccommodation.service.AccommodationTypeService;
import com.project.Rentingaccommodation.service.ApartmentService;
import com.project.Rentingaccommodation.service.CityService;
import com.project.Rentingaccommodation.service.ReservationService;

@RestController
@RequestMapping(value="api/apartments")
public class ApartmentController {

	@Autowired
	private ApartmentService service;
	
	@Autowired
	private CityService cityService;
	
	@Autowired
	private ReservationService reservationService;
	
	@Autowired
	private AccommodationTypeService typeService;
	
	@Autowired
	private AccommodationCategoryService categoryService;
	
	@Autowired
	private AccommodationService accommodationService;
	
	@RequestMapping(value="", method=RequestMethod.GET)
	public ResponseEntity<List<Apartment>> getApartments() {
		return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<Object> getApartment(@PathVariable Long id) {
		Apartment apartment = service.findOne(id);
		if (apartment == null) {
			return new ResponseEntity<>("Apartment not found.", HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(apartment, HttpStatus.OK);
	}
	
	@RequestMapping(value="/accommodation/{id}", method=RequestMethod.GET)
	public ResponseEntity<Object> getApartmentsByAccommodationId(@PathVariable Long id) {
		return new ResponseEntity<>(service.findApartmentsByAccommodationId(id), HttpStatus.OK);
	}
	
	@RequestMapping(value="/{id}/accommodation/{accommodationId}", method=RequestMethod.GET)
	public ResponseEntity<Object> getApartmentsByAccommodationId(@PathVariable Long id, @PathVariable Long accommodationId) {
		Accommodation accommodation = accommodationService.findOne(id);
		if (accommodation == null) {
			return new ResponseEntity<>("Accommodation not found.", HttpStatus.NOT_FOUND);
		}
		Apartment apartment = service.findApartmentByAccommodationId(id, accommodationId);
		if (apartment == null) {
			return new ResponseEntity<>("Apartment not found.", HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(apartment, HttpStatus.OK);
	}
	
	@RequestMapping(value="/search", method=RequestMethod.GET)
	public ResponseEntity<Object> searchForApartments(@RequestParam("city") Long cityId,
		@RequestParam("persons") int persons, @RequestParam("startDate") String startDate,
		@RequestParam("endDate") String endDate) {
		if (cityId == null || Long.valueOf(cityId) == 0 || startDate == null || startDate == "" || endDate == null ||
			endDate == "" || Integer.valueOf(persons) == null || Integer.valueOf(persons) == 0) {
			return new ResponseEntity<>("All query parameters are required (city, startDate, endDate, persons).", HttpStatus.FORBIDDEN);
		} else {
			City city = cityService.findOne(cityId);
			
			if (city == null) {
				return new ResponseEntity<>("City not found.", HttpStatus.NOT_FOUND);
			}
			
			if (!reservationService.checkDates(startDate, endDate)) {
				return new ResponseEntity<>("Start date must be lower than end date.", HttpStatus.FORBIDDEN);
			}
			
			List<Apartment> queryApartments = service.findByQueryParams(city, startDate, endDate, persons);
			return new ResponseEntity<>(queryApartments, HttpStatus.OK);
		}
	}
	
	@RequestMapping(value="/advanced-search", method=RequestMethod.GET)
	public ResponseEntity<Object> advancedSearchForApartments(@RequestParam("city") Long cityId, @RequestParam("persons") int persons,
		@RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate, @RequestParam("type") Long typeId,
		@RequestParam("category") Long categoryId) {
		if (cityId == null || Long.valueOf(cityId) == 0 || startDate == null || startDate == "" || endDate == null ||
			endDate == "" || Integer.valueOf(persons) == null || Integer.valueOf(persons) == 0 || typeId == null ||
			Long.valueOf(typeId) == 0 || categoryId == null || Long.valueOf(categoryId) == 0) {
			return new ResponseEntity<>("All query parameters are required (city, startDate, endDate, persons, type id, category id).", HttpStatus.FORBIDDEN);
		} else {
			City city = cityService.findOne(cityId);
			AccommodationType type = typeService.findOne(typeId);
			AccommodationCategory category = categoryService.findOne(categoryId);
			
			if (city == null) {
				return new ResponseEntity<>("City not found.", HttpStatus.NOT_FOUND);
			}
			
			if (type == null) {
				return new ResponseEntity<>("Type not found.", HttpStatus.NOT_FOUND);
			}
			
			if (category == null) {
				return new ResponseEntity<>("Category not found.", HttpStatus.NOT_FOUND);
			}
			
			if (!reservationService.checkDates(startDate, endDate)) {
				return new ResponseEntity<>("Start date must be lower than end date.", HttpStatus.FORBIDDEN);
			}
			
			List<Apartment> queryApartments = service.findByQueryParams(city, startDate, endDate, persons, type, category);
			return new ResponseEntity<>(queryApartments, HttpStatus.OK);
		}
	}
}
