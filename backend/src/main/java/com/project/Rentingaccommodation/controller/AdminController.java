package com.project.Rentingaccommodation.controller;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.project.Rentingaccommodation.model.Admin;
import com.project.Rentingaccommodation.model.Review;
import com.project.Rentingaccommodation.model.User;
import com.project.Rentingaccommodation.model.UserStatus;
import com.project.Rentingaccommodation.security.JwtGenerator;
import com.project.Rentingaccommodation.security.JwtUser;
import com.project.Rentingaccommodation.service.AdminService;
import com.project.Rentingaccommodation.service.UserService;

@RestController
@RequestMapping(value = "/api/admins")
public class AdminController {

	@Autowired
	private AdminService service;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private JwtGenerator jwtGenerator;
	
	@RequestMapping(value = "/login", method=RequestMethod.POST)
	public ResponseEntity<Object> loginAdmin(@RequestBody Admin data) {
		if (data.getEmail() == null || data.getEmail() == "" ||
				data.getPassword() == null || data.getPassword() == "") {
			return new ResponseEntity<>("Email and password not provided.", HttpStatus.NOT_ACCEPTABLE);
		}
		Admin admin = service.findByEmail(data.getEmail());
		
		if (admin == null) {
			return new ResponseEntity<>("Admin not found.", HttpStatus.NOT_FOUND);
		}
		
		if (!data.getPassword().equals(admin.getPassword())) {
			return new ResponseEntity<>("Password is invalid.", HttpStatus.FORBIDDEN);
		}
		
		String token = jwtGenerator.generateAdmin(new JwtUser(admin.getId(), admin.getEmail(), admin.getPassword()));
		HashMap<String, Object> response = new HashMap<String, Object>();
		response.put("token", token);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value="/activate-user/{id}", method=RequestMethod.PUT)
	public ResponseEntity<Object> activateUser(@PathVariable Long id) {
		User user = userService.findOne(id);
		if (user == null) {
			return new ResponseEntity<>("User not found.", HttpStatus.NOT_FOUND);
		}
		user.setStatus(UserStatus.ACTIVATED);
		return new ResponseEntity<>(userService.save(user), HttpStatus.OK);
	}
	
	@RequestMapping(value="/block-user/{id}", method=RequestMethod.PUT)
	public ResponseEntity<Object> blockUser(@PathVariable Long id) {
		User user = userService.findOne(id);
		if (user == null) {
			return new ResponseEntity<>("User not found.", HttpStatus.NOT_FOUND);
		}
		user.setStatus(UserStatus.BLOCKED);
		return new ResponseEntity<>(userService.save(user), HttpStatus.OK);
	}

	@RequestMapping(value="/allow-review/{id}", method=RequestMethod.PUT)
	public ResponseEntity<Object> allowReview(@PathVariable Long id) {
		return null;
	}
	
	@RequestMapping(value="/decline-review/{id}", method=RequestMethod.PUT)
	public ResponseEntity<Review> declineReview(@PathVariable Long id) {
		return null;
	}
	
	@RequestMapping(value="/delete-user/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<Object> deleteUser(@PathVariable Long id) {
		User user = userService.findOne(id);
		if (user == null) {
			return new ResponseEntity<>("User not found.", HttpStatus.NOT_FOUND);
		}
		userService.delete(user);
		return new ResponseEntity<>(user, HttpStatus.OK);
	}
}
