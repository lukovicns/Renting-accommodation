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
	public ResponseEntity<Object> getCountry(@PathVariable Long id) {
		Country country = service.findOne(id);
		if (country == null) {
			return new ResponseEntity<>("Country not found.", HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(country, HttpStatus.OK);
	}
	
	@RequestMapping(value="", method=RequestMethod.POST)
	public ResponseEntity<Object> addCountry(@RequestBody Country country) {
		if (country.getCode() == null || country.getCode() == "" ||
			country.getName() == null || country.getName() == "") {
			return new ResponseEntity<>("All fields are required.", HttpStatus.FORBIDDEN);
		}
		Country foundCountry = service.findByCodeAndName(country.getCode(), country.getName());
		if (foundCountry != null) {
			return new ResponseEntity<>("Country already exists.", HttpStatus.NOT_ACCEPTABLE);
		}
		service.save(country);
		return new ResponseEntity<>(country, HttpStatus.OK);
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.PUT)
	public ResponseEntity<Object> updateCountry(@PathVariable Long id, @RequestBody Country country) {
		if (country.getCode() == null || country.getCode() == "" ||
			country.getName() == null || country.getName() == "") {
			return new ResponseEntity<>("All fields are required (code, name).", HttpStatus.FORBIDDEN);
		}
		Country foundCountry = service.findOne(id);
		if (foundCountry != null) {
			foundCountry.setCode(country.getCode());
			foundCountry.setName(country.getName());
			service.save(foundCountry);
			return new ResponseEntity<>(foundCountry, HttpStatus.OK);
		}
		return new ResponseEntity<>("Country not found.", HttpStatus.NOT_FOUND);
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<Object> deleteCountry(@PathVariable Long id) {
		Country country = service.delete(id);
		if (country == null) {
			return new ResponseEntity<>("Country not found.", HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(country, HttpStatus.OK);
	}
}
