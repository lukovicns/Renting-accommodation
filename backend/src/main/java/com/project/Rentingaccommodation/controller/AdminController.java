package com.project.Rentingaccommodation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.project.Rentingaccommodation.model.Review;
import com.project.Rentingaccommodation.model.User;
import com.project.Rentingaccommodation.service.AdminService;
import com.project.Rentingaccommodation.service.ReviewService;

@RestController
@RequestMapping(value = "/api/admins")
public class AdminController {

	@Autowired
	private AdminService adminService;
	
	@Autowired
	private ReviewService reviewService;
	
	@RequestMapping(value="/activate-user/{id}", method=RequestMethod.PUT)
	public ResponseEntity<Object> activateUser(@PathVariable Long id) {
		return null;
	}
	
	@RequestMapping(value="/block-user/{id}", method=RequestMethod.PUT)
	public ResponseEntity<Object> blockUser(@PathVariable Long id) {
		return null;
	}
	
	@RequestMapping(value="/delete-user/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<Object> deleteUser(@RequestBody User user) {
		return null;
	}
	
	@RequestMapping(value="/allow-review/{id}", method=RequestMethod.PUT)
	public ResponseEntity<Object> allowReview(@PathVariable Long id) {
		return null;
	}
	
	@RequestMapping(value="/decline-review/{id}", method=RequestMethod.PUT)
	public ResponseEntity<Review> declineReview(@PathVariable Long id) {
		return null;
	}
}
