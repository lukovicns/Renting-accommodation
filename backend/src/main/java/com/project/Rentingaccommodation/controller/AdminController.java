package com.project.Rentingaccommodation.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.Rentingaccommodation.model.Agent;
import com.project.Rentingaccommodation.model.Review;
import com.project.Rentingaccommodation.model.User;
import com.project.Rentingaccommodation.service.AdminService;
import com.project.Rentingaccommodation.service.impl.ReviewService;

@RestController
@RequestMapping("/admin")
@CrossOrigin(origins = "http://localhost:3306", maxAge = 3600, allowCredentials = "true")
public class AdminController {

	private static final Logger logger = LoggerFactory.getLogger(AdminController.class);
	
	private AdminService adminService;
	
	private ReviewService reviewService;
	
	public AdminController(AdminService adminService, ReviewService reviewService) {
		this.adminService = adminService;
		this.reviewService = reviewService;
	}
	
	@PostMapping("/create-agent")
	public ResponseEntity<Agent> createAgent(@RequestBody Agent agent) {
		logger.debug("Create agent endpoint!");
		Agent a = this.adminService.createAgent(agent);
		if (a != null) return new ResponseEntity<>(a, HttpStatus.CREATED);
		return new ResponseEntity<>(a, HttpStatus.BAD_REQUEST);
	}
	
	@PutMapping("/activate-user")
	public ResponseEntity<User> activateUser(@RequestBody User user) {
		logger.debug("Activate user endpoint.");
		System.out.println("ACTIVATED" + user.getEmail());
		User u = this.adminService.activateUser(user, "ACTIVATED");
		if (u != null) return new ResponseEntity<>(u, HttpStatus.OK);
		return new ResponseEntity<>(u, HttpStatus.BAD_REQUEST);
	}
	
	@PutMapping("/block-user")
	public ResponseEntity<User> blockUser(@RequestBody User user) {
		logger.debug("Block user endpoint.");
		User u = this.adminService.activateUser(user, "BLOCKED");
		if (u != null) return new ResponseEntity<>(u, HttpStatus.OK);
		return new ResponseEntity<>(u, HttpStatus.BAD_REQUEST);
	}
	
	@SuppressWarnings("rawtypes")
	@DeleteMapping("/delete-user")
	public ResponseEntity deleteUser(@RequestBody User user) {
		logger.debug("Delete user endpoint.");
		boolean deleted = this.adminService.deleteUser(user);
		if (deleted) return new ResponseEntity(HttpStatus.OK);
		return new ResponseEntity(HttpStatus.BAD_REQUEST);
	}
	
	@PutMapping("/allow-review")
	public ResponseEntity<Review> allowReview(@RequestBody Review review) {
		logger.debug("Allow reivew endpoint!");
		Review r = this.reviewService.allowReview(review, true);
		if (r != null) return new ResponseEntity<>(r, HttpStatus.OK);
		return new ResponseEntity<>(r, HttpStatus.BAD_REQUEST);
	}
	
	@PutMapping("/decline-review")
	public ResponseEntity<Review> declineReview(@RequestBody Review review) {
		logger.debug("Allow reivew endpoint!");
		Review r = this.reviewService.allowReview(review, false);
		if (r != null) return new ResponseEntity<>(r, HttpStatus.OK);
		return new ResponseEntity<>(r, HttpStatus.BAD_REQUEST);
	}
}
