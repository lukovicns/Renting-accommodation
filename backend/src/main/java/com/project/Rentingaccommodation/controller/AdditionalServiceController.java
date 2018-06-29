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
import com.project.Rentingaccommodation.model.AdditionalService;
import com.project.Rentingaccommodation.service.AdditionalServiceService;

@RestController
@RequestMapping(value = "/api/additional-services")
public class AdditionalServiceController {

	@Autowired
	private AdditionalServiceService service;
	
	@RequestMapping(value="", method=RequestMethod.GET)
	public ResponseEntity<List<AdditionalService>> getAdditionalServices() {
		return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<Object> getAdditionalService(@PathVariable Long id) {
		AdditionalService additionalService = service.findOne(id);
		if (additionalService == null) {
			return new ResponseEntity<>("Additional service not found.", HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(additionalService, HttpStatus.OK);
	}
	
	@RequestMapping(value="", method=RequestMethod.POST)
	public ResponseEntity<Object> addAdditionalService(@RequestBody AdditionalService data) {
		if (data.getName() == null || data.getName() == "") {
			return new ResponseEntity<>("Name field is required.", HttpStatus.FORBIDDEN);
		}
		AdditionalService foundAdditionalService = service.findByName(data.getName());
		if (foundAdditionalService != null) {
			return new ResponseEntity<>("Additional service with this name already exists.", HttpStatus.FORBIDDEN);
		}
		AdditionalService additionalService = new AdditionalService(data.getName());
		return new ResponseEntity<>(service.save(additionalService), HttpStatus.OK);
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.PUT)
	public ResponseEntity<Object> updateAdditionalService(@PathVariable Long id, @RequestBody AdditionalService data) {
		AdditionalService additionalService = service.findOne(id);
		if (additionalService == null) {
			return new ResponseEntity<>("Additional service not found.", HttpStatus.NOT_FOUND);
		}
		if (data.getName() == null || data.getName() == "") {
			return new ResponseEntity<>("Name field is required.", HttpStatus.FORBIDDEN);
		}
		additionalService.setName(data.getName());
		return new ResponseEntity<>(service.save(additionalService), HttpStatus.OK);
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<Object> deleteAdditionalService(@PathVariable Long id) {
		AdditionalService additionalService = service.findOne(id);
		if (additionalService == null) {
			return new ResponseEntity<>("Additional service not found.", HttpStatus.NOT_FOUND);
		}
		service.delete(additionalService);
		return new ResponseEntity<>(additionalService, HttpStatus.OK);
	}	
}
