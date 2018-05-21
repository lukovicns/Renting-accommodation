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

import com.project.Rentingaccommodation.model.City;
import com.project.Rentingaccommodation.model.User;
import com.project.Rentingaccommodation.service.CityService;
import com.project.Rentingaccommodation.service.UserService;

@RestController
@RequestMapping(value="/api/users")
public class UserController {
	
	@Autowired
	private UserService service;
	
	@Autowired
	private CityService cityService;
	
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
	
//	@RequestMapping(value="", method=RequestMethod.POST)
//	public ResponseEntity<Object> addUser(@RequestBody User user) {
//		if (user.getName() != null && user.getSurname() != null && user.getPassword() != null && user.getCity() != null && user.getStreet() != null && user.getPhone() != null) {
//			User foundUser = service.findByEmail(user.getEmail());
//			if (foundUser != null) {
//				City city = cityService.findOne(user.getCity().getId());
//				if (city == null) {
//					return new ResponseEntity<>("City not found.", HttpStatus.NOT_FOUND);
//				}
//				user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
//				User newUser = new User(user.getName(), user.getSurname(), user.getPassword(), user.getEmail(), user.getCity(), user.getStreet(), user.getPhone());
//				service.save(newUser);
//				return new ResponseEntity<>("User is saved.", HttpStatus.OK);
//			} else {
//				return new ResponseEntity<Object>("User with this email already exists.", HttpStatus.METHOD_NOT_ALLOWED);
//			}
//		}
//		return new ResponseEntity<Object>("All fields are required (name, surname, password, city, street, phone).", HttpStatus.OK);
//	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<User> deleteUser(@PathVariable Long id) {
		User user = service.delete(id);
		if (user == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(user, HttpStatus.OK);
	}
}
