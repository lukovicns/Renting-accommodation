package com.project.Rentingaccommodation.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.project.Rentingaccommodation.model.Admin;
import com.project.Rentingaccommodation.service.AdminService;

@RestController
@RequestMapping(value="api/admins")
public class AdminController {
	
	@Autowired
	private AdminService adminService;

	@RequestMapping(value="", method=RequestMethod.GET)
	public ResponseEntity<List<Admin>> getAdmins() {
		return new ResponseEntity<>(adminService.findAll(), HttpStatus.OK);
	}
}
