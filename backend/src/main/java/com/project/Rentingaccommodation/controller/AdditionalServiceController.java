package com.project.Rentingaccommodation.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
}
