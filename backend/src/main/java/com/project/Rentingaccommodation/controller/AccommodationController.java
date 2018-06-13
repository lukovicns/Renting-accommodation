package com.project.Rentingaccommodation.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.project.Rentingaccommodation.model.Accommodation;
import com.project.Rentingaccommodation.service.AccommodationService;

@RestController
@RequestMapping(value="/api/accommodations")
public class AccommodationController {

	@Autowired
	private AccommodationService service;
	
	@RequestMapping(value="", method=RequestMethod.GET)
	public ResponseEntity<List<Accommodation>> getAccommodations() {
		return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<Object> getAccommodation(@PathVariable Long id) {
		Accommodation accommodation = service.findOne(id);
		if (accommodation == null) {
			return new ResponseEntity<>("Accommodation not found.", HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(accommodation, HttpStatus.OK);
	}
}
