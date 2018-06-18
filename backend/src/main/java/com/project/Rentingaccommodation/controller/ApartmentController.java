package com.project.Rentingaccommodation.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.project.Rentingaccommodation.model.Apartment;
import com.project.Rentingaccommodation.model.City;
import com.project.Rentingaccommodation.security.JwtUser;
import com.project.Rentingaccommodation.security.JwtValidator;
import com.project.Rentingaccommodation.service.ApartmentService;
import com.project.Rentingaccommodation.service.CityService;

@RestController
@RequestMapping(value="api/apartments")
public class ApartmentController {

	@Autowired
	private ApartmentService service;
	
	@Autowired
	private CityService cityService;
	
	@Autowired
	private JwtValidator jwtValidator;
	
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
	public ResponseEntity<Object> getApartmentByAccommodationId(@PathVariable Long id) {
		return new ResponseEntity<>(service.findByAccommodationId(id), HttpStatus.OK);
	}
	
	@RequestMapping(value="/search", method=RequestMethod.GET)
	public ResponseEntity<Object> searchApartments(@RequestHeader("Authorization") String authHeader,
			@RequestParam("city") Long cityId, @RequestParam("persons") int persons,
			@RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate) {
		try {
			String token = authHeader.split(" ")[1].trim();
			JwtUser jwtUser = jwtValidator.validate(token);
			if (jwtUser != null) {
				if (Long.valueOf(cityId) == null || Long.valueOf(cityId) == 0 || startDate == null || startDate == "" || endDate == null ||
					endDate == "" || Integer.valueOf(persons) == null || Integer.valueOf(persons) == 0) {
					return new ResponseEntity<>("All query parameters are required (city, startDate, endDate, persons).", HttpStatus.FORBIDDEN);
				} else {
					City city = cityService.findOne(cityId);
					if (city == null) {
						return new ResponseEntity<>("City not found.", HttpStatus.NOT_FOUND);
					}
					List<Apartment> queryApartments = service.findByQueryParams(city, startDate, endDate, persons);
					return new ResponseEntity<>(queryApartments, HttpStatus.OK);
				}
			} else {
				return new ResponseEntity<>("User with this email doesn't exist.", HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return new ResponseEntity<>("Token not provided.", HttpStatus.FORBIDDEN);
		}
	}
}
