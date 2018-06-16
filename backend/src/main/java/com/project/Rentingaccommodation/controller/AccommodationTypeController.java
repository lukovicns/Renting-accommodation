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

import com.project.Rentingaccommodation.model.AccommodationType;
import com.project.Rentingaccommodation.service.AccommodationTypeService;



@RestController
@RequestMapping("/accomodation-type")
@CrossOrigin(origins = "http://localhost:3306", maxAge = 3600, allowCredentials = "true")
public class AccommodationTypeController {
	
		@Autowired
		private AccommodationTypeService accommodationTypeService;
		
		@PostMapping
		public ResponseEntity<AccommodationType> save(@RequestBody AccommodationType at) {
			AccommodationType accomodationType = this.accommodationTypeService.save(at);
			if (accomodationType != null) return new ResponseEntity<>(accomodationType, HttpStatus.CREATED);
			return new ResponseEntity<>(accomodationType, HttpStatus.BAD_REQUEST);
		}
		
		@SuppressWarnings("rawtypes")
		@DeleteMapping("/{id}")
		public ResponseEntity delete(@PathVariable String id) {
			boolean deleted = this.accommodationTypeService.delete(id);
			if (deleted) return new ResponseEntity(HttpStatus.OK);
			return new ResponseEntity(HttpStatus.BAD_REQUEST);
		}
		
		@PutMapping
		public ResponseEntity<AccommodationType> update(@RequestBody AccommodationType at) {
			AccommodationType accomodationType = this.accommodationTypeService.save(at);
			if (accomodationType != null) return new ResponseEntity<>(accomodationType, HttpStatus.OK);
			return new ResponseEntity<>(accomodationType, HttpStatus.BAD_REQUEST);
		}
		
		@GetMapping
		public ResponseEntity<List<AccommodationType>> getAll() {
			return new ResponseEntity<>(this.accommodationTypeService.getAll(), HttpStatus.OK);
		}
		
	}

