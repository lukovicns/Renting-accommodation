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
import com.project.Rentingaccommodation.service.ApartmentService;

@RestController
@RequestMapping(value="api/apartments")
public class ApartmentController {

	@Autowired
	private ApartmentService service;
	
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
}
