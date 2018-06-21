package com.project.Rentingaccommodation.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.project.Rentingaccommodation.model.City;
import com.project.Rentingaccommodation.service.CityService;

@RestController
@RequestMapping(value="/api/cities")
public class CityController {
	@Autowired
	private CityService service;

	@RequestMapping(value="", method=RequestMethod.GET)
	public ResponseEntity<List<City>> getCities() {
		List<City> cities = service.findAll();
		return new ResponseEntity<>(cities, HttpStatus.OK);
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<City> getCity(@PathVariable Long id) {
		City city = service.findOne(id);
		if (city == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(city, HttpStatus.OK);
	}
	
	@RequestMapping(value="", method=RequestMethod.POST)
	public ResponseEntity<City> addCity(@RequestBody City city) {
		City foundCity = service.findOne(city.getId());
		if (foundCity != null) {
			return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
		}
		service.save(city);
		return new ResponseEntity<>(city, HttpStatus.OK);
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.PUT)
	public ResponseEntity<City> updateCity(@PathVariable Long id, @RequestBody City city) {
		City foundCity = service.findOne(id);
		if (foundCity != null) {
			foundCity.setCountry(city.getCountry());
			foundCity.setName(city.getName());
			foundCity.setZipcode(city.getZipcode());
			service.save(foundCity);
			return new ResponseEntity<>(foundCity, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<City> deleteCity(@PathVariable Long id) {
		City city = service.delete(id);
		if (city == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(city, HttpStatus.OK);
	}
}
