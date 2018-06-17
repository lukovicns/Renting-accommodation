package com.project.Rentingaccommodation.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.Rentingaccommodation.model.AccommodationCategory;
import com.project.Rentingaccommodation.service.AccommodationCatService;


@RestController
@RequestMapping("/api/accomodation-service")
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowCredentials = "true")
public class AccommodationCategoryController {

	@Autowired
	private AccommodationCatService accomodationCatService;
	
	@PostMapping
	public ResponseEntity<AccommodationCategory> save(@RequestBody AccommodationCategory as) {
		AccommodationCategory accommodationCategory = this.accomodationCatService.save(as);
		if (accommodationCategory != null) return new ResponseEntity<>(accommodationCategory, HttpStatus.CREATED);
		return new ResponseEntity<>(accommodationCategory, HttpStatus.BAD_REQUEST);
	}
	
	@SuppressWarnings("rawtypes")
	@DeleteMapping("/{id}")
	public ResponseEntity delete(@PathVariable String id) {
		boolean deleted = this.accomodationCatService.delete(id);
		if (deleted) return new ResponseEntity(HttpStatus.OK);
		return new ResponseEntity(HttpStatus.BAD_REQUEST);
	}
	
	@PutMapping
	public ResponseEntity<AccommodationCategory> update(@RequestBody AccommodationCategory as) {
		AccommodationCategory accomodationCategory = this.accomodationCatService.save(as);
		if (accomodationCategory != null) return new ResponseEntity<>(accomodationCategory, HttpStatus.OK);
		return new ResponseEntity<>(accomodationCategory, HttpStatus.BAD_REQUEST);
	}
	
	@GetMapping
	public ResponseEntity<List<AccommodationCategory>> getAll() {
		return new ResponseEntity<>(this.accomodationCatService.getAll(), HttpStatus.OK);
	}
	
}
