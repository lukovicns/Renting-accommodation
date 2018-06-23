package com.project.Rentingaccommodation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.project.Rentingaccommodation.model.Accommodation;
import com.project.Rentingaccommodation.model.Apartment;
import com.project.Rentingaccommodation.model.Image;
import com.project.Rentingaccommodation.service.AccommodationService;
import com.project.Rentingaccommodation.service.ApartmentService;
import com.project.Rentingaccommodation.service.ImageService;

@RestController
@RequestMapping(value="/api/images")
public class ImageController {
	
	@Autowired
	private ImageService service;

	@Autowired
	private AccommodationService accommodationService;
	
	@Autowired
	private ApartmentService apartmentService;
	
	@RequestMapping(value="", method=RequestMethod.GET)
	public ResponseEntity<Object> getImages() {
		return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
	}
	
	@RequestMapping(value="/accommodation/{id}", method=RequestMethod.GET)
	public ResponseEntity<Object> getAccommodationImages(@PathVariable Long id) {
		Accommodation accommodation = accommodationService.findOne(id);
		if (accommodation == null) {
			return new ResponseEntity<>("Accommodation not found.", HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(service.findAccommodationImages(id), HttpStatus.OK);
	}
	
	@RequestMapping(value="/accommodation/{id}/first", method=RequestMethod.GET)
	public ResponseEntity<Object> getFirstImage(@PathVariable Long id) {
		Accommodation accommodation = accommodationService.findOne(id);
		if (accommodation == null) {
			return new ResponseEntity<>("Accommodation not found.", HttpStatus.NOT_FOUND);
		}
		Image image = service.findFirstAccommodationImage(id);
		return new ResponseEntity<>(image, HttpStatus.OK);
	}
	
	@RequestMapping(value="/accommodation/{id}/others", method=RequestMethod.GET)
	public ResponseEntity<Object> getOtherAccommodationImages(@PathVariable Long id) {
		Accommodation accommodation = accommodationService.findOne(id);
		if (accommodation == null) {
			return new ResponseEntity<>("Accommodation not found.", HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(service.findOtherAccommodationImages(id), HttpStatus.OK);
	}
	
	@RequestMapping(value="/apartment/{id}", method=RequestMethod.GET)
	public ResponseEntity<Object> getApartmentImages(@PathVariable Long id) {
		Apartment apartment = apartmentService.findOne(id);
		if (apartment == null) {
			return new ResponseEntity<>("Apartment not found.", HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(service.findApartmentImages(id), HttpStatus.OK);
	}
	
	@RequestMapping(value="/apartment/{id}/first", method=RequestMethod.GET)
	public ResponseEntity<Object> getFirstApartmentImages(@PathVariable Long id) {
		Apartment apartment = apartmentService.findOne(id);
		if (apartment == null) {
			return new ResponseEntity<>("Apartment not found.", HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(service.findFirstApartmentImage(id), HttpStatus.OK);
	}
	
	@RequestMapping(value="/apartment/{id}/others", method=RequestMethod.GET)
	public ResponseEntity<Object> getOtherApartmentImages(@PathVariable Long id) {
		Apartment apartment = apartmentService.findOne(id);
		if (apartment == null) {
			return new ResponseEntity<>("Apartment not found.", HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(service.findOtherApartmentImages(id), HttpStatus.OK);
	}
}
