package com.project.Rentingaccommodation.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.project.Rentingaccommodation.model.City;
import com.project.Rentingaccommodation.model.User;
import com.project.Rentingaccommodation.service.CityService;
import com.project.Rentingaccommodation.service.UserService;

@RestController
@RequestMapping(value="/api/users")
public class UserController {
	
	@Autowired
	private UserService service;
	
	@RequestMapping(value="", method=RequestMethod.GET)
	public ResponseEntity<List<User>> getUsers() {
		return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<User> getUser(@PathVariable Long id) {
		User user = service.findOne(id);
		if (user == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(user, HttpStatus.OK);
	}
	
	@RequestMapping(value="", method=RequestMethod.POST)
	public ResponseEntity<User> addUser(@RequestBody User user) {
		user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
		service.save(user);
		return new ResponseEntity<>(user, HttpStatus.OK);
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<User> deleteUser(@PathVariable Long id) {
		User user = service.delete(id);
		if (user == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(user, HttpStatus.OK);
	}
}
