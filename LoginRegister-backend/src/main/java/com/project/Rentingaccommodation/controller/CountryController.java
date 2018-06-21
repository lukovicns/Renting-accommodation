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

import com.project.Rentingaccommodation.model.Country;
import com.project.Rentingaccommodation.service.CountryService;

@RestController
@RequestMapping(value="/api/countries")
public class CountryController {
	
	@Autowired
	private CountryService service;
	
	@RequestMapping(value="", method=RequestMethod.GET)
	public ResponseEntity<List<Country>> getCountries() {
		List<Country> countries = service.findAll();
		return new ResponseEntity<>(countries, HttpStatus.OK);
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<Country> getCountry(@PathVariable Long id) {
		Country country = service.findOne(id);
		if (country == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(country, HttpStatus.OK);
	}
	
	@RequestMapping(value="", method=RequestMethod.POST)
	public ResponseEntity<Country> addCountry(@RequestBody Country country) {
		Country foundCountry = service.findOne(country.getId());
		if (foundCountry != null) {
			return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
		}
		service.save(country);
		return new ResponseEntity<>(country, HttpStatus.OK);
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.PUT)
	public ResponseEntity<Country> updateCountry(@PathVariable Long id, @RequestBody Country country) {
		Country foundCountry = service.findOne(id);
		if (foundCountry != null) {
			foundCountry.setCode(country.getCode());
			foundCountry.setName(country.getName());
			service.save(foundCountry);
			return new ResponseEntity<>(foundCountry, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<Country> deleteCountry(@PathVariable Long id) {
		Country country = service.delete(id);
		if (country == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(country, HttpStatus.OK);
	}
}
