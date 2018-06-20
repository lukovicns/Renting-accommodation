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

import com.project.Rentingaccommodation.model.AccommodationType;
import com.project.Rentingaccommodation.service.AccommodationTypeService;

@RestController
@RequestMapping(value = "/api/types")
public class AccommodationTypeController {

	@Autowired
	private AccommodationTypeService service;
	
	@RequestMapping(value="", method=RequestMethod.GET)
	public ResponseEntity<List<AccommodationType>> getTypes() {
		return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<Object> getType(@PathVariable Long id) {
		AccommodationType type = service.findOne(id);
		if (type == null) {
			return new ResponseEntity<>("Accommodation type not found.", HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(type, HttpStatus.OK);
	}
	
	@RequestMapping(value="", method=RequestMethod.POST)
	public ResponseEntity<Object> addAccommodationType(@RequestBody AccommodationType data) {
		if (data.getName() == null || data.getName() == "") {
			return new ResponseEntity<>("Name field is required.", HttpStatus.FORBIDDEN);
		}
		AccommodationType type = new AccommodationType(data.getName());
		return new ResponseEntity<>(service.save(type), HttpStatus.OK);
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.PUT)
	public ResponseEntity<Object> updateAccommodationType(@PathVariable Long id, @RequestBody AccommodationType data) {
		AccommodationType type = service.findOne(id);
		if (type == null) {
			return new ResponseEntity<>("Accommodation type not found.", HttpStatus.NOT_FOUND);
		}
		if (data.getName() == null || data.getName() == "") {
			return new ResponseEntity<>("Name field is required.", HttpStatus.FORBIDDEN);
		}
		type.setName(data.getName());
		return new ResponseEntity<>(service.save(type), HttpStatus.OK);
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<Object> deleteAccommodationType(@PathVariable Long id) {
		AccommodationType type = service.findOne(id);
		if (type == null) {
			return new ResponseEntity<>("Accommodation type not found.", HttpStatus.NOT_FOUND);
		}
		service.delete(type);
		return new ResponseEntity<>(type, HttpStatus.OK);
	}	
}
