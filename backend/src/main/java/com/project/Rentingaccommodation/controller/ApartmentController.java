package com.project.Rentingaccommodation.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

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
import com.project.Rentingaccommodation.model.AdditionalService;
import com.project.Rentingaccommodation.model.Apartment;
import com.project.Rentingaccommodation.model.City;
import com.project.Rentingaccommodation.service.AccommodationCategoryService;
import com.project.Rentingaccommodation.service.AccommodationService;
import com.project.Rentingaccommodation.service.AccommodationTypeService;
import com.project.Rentingaccommodation.service.AdditionalServiceService;
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
	private AccommodationService accommodationService;
	
	@Autowired
	private AccommodationCategoryService accommodationCategoryService;
	
	@Autowired
	private AccommodationTypeService accommodationTypeService;
	
	@Autowired
	private AdditionalServiceService additionalServiceService;
	
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
		@RequestParam("endDate") String endDate, HttpServletRequest request) {
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

			List<Apartment> queryApartments = service.findByBasicQueryParams(city, persons, startDate, endDate);
			return new ResponseEntity<>(queryApartments, HttpStatus.OK);
		}
	}
	
	@RequestMapping(value="/advanced-search", method=RequestMethod.GET)
	public ResponseEntity<Object> advancedSearchForApartments(@RequestParam("city") Long cityId, @RequestParam("persons") int persons,
		@RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate, @RequestParam("category") Long categoryId,
		@RequestParam("type") Long typeId, HttpServletRequest request) {
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
			
			// Additional search params
			AccommodationCategory category = accommodationCategoryService.findOne(categoryId);
			if (category == null) {
				return new ResponseEntity<>("Accommodation category not found.", HttpStatus.NOT_FOUND);
			}
			AccommodationType type = accommodationTypeService.findOne(typeId);
			if (type == null) {
				return new ResponseEntity<>("Accommodation type not found.", HttpStatus.NOT_FOUND);
			}
			
			// Sifarnici
			List<AdditionalService> additionalServices = new ArrayList<AdditionalService>();
			
	        String[] splitter = request.getQueryString().split("&");
	        for (int i = 0; i < splitter.length; i++) {
	        	String[] keyValue = splitter[i].split("=");
	        	if (keyValue[0].startsWith("service") && keyValue[1].equals("on")) {
	        		try {
	        			Long index = Long.parseLong(keyValue[0].substring(7));
		    	        AdditionalService additionalService = additionalServiceService.findOne(index);
		    	        if (additionalService == null) {
		    	        	return new ResponseEntity<>("Additional service with id = " + index + " not found.", HttpStatus.NOT_FOUND);
		    	        }
		    	        additionalServices.add(additionalService);
	        		} catch (Exception e) {
	        			return new ResponseEntity<>("Service id must be a valid number.", HttpStatus.FORBIDDEN);
	        		}
	        	}
	        }
			List<Apartment> queryApartments = service.findByAdvancedQueryParams(city, persons, startDate, endDate, category, type, additionalServices);
			return new ResponseEntity<>(queryApartments, HttpStatus.OK);
		}
	}
}
