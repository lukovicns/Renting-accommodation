package com.project.Rentingaccommodation.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.project.Rentingaccommodation.model.Apartment;
import com.project.Rentingaccommodation.model.Review;
import com.project.Rentingaccommodation.model.User;
import com.project.Rentingaccommodation.security.JwtUser;
import com.project.Rentingaccommodation.security.JwtValidator;
import com.project.Rentingaccommodation.service.ApartmentService;
import com.project.Rentingaccommodation.service.ReviewService;
import com.project.Rentingaccommodation.service.UserService;

@RestController
@RequestMapping(value = "/api/reviews")
public class ReviewController {

    @Autowired
    private ReviewService service;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private ApartmentService apartmentService;
    
    @Autowired
    private JwtValidator jwtValidator;
    
	@RequestMapping(value="", method=RequestMethod.GET)
    public ResponseEntity<List<Review>> getReviews() {
    	return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
    }
	
	@RequestMapping(value="/approved", method=RequestMethod.GET)
    public ResponseEntity<List<Review>> getApprovedReviews() {
    	return new ResponseEntity<>(service.findApprovedReviews(), HttpStatus.OK);
    }
	
	@RequestMapping(value="/declined", method=RequestMethod.GET)
    public ResponseEntity<List<Review>> getDeclinedReviews() {
    	return new ResponseEntity<>(service.findDeclinedReviews(), HttpStatus.OK);
    }
	
	@RequestMapping(value="/waiting", method=RequestMethod.GET)
    public ResponseEntity<List<Review>> getWaitingReviews() {
    	return new ResponseEntity<>(service.findWaitingReviews(), HttpStatus.OK);
    }
    
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
    public ResponseEntity<Object> getReview(@PathVariable Long id) {
		Review review = service.findOne(id);
		if (review == null) {
			return new ResponseEntity<>("Review not found.", HttpStatus.NOT_FOUND);
		}
        return new ResponseEntity<>(review, HttpStatus.OK);
    }
	
	@RequestMapping(value="/apartment/{id}", method=RequestMethod.GET)
    public List<Review> getAllReviewsForApartment(@PathVariable Long id) {
        return service.findApartmentReviews(id);
    }
    
	@RequestMapping(value="", method=RequestMethod.POST)
    public ResponseEntity<Object> addReview(@RequestBody Review data, @RequestHeader("Authorization") String authHeader) {
		try {
			String token = authHeader.split(" ")[1].trim();
			JwtUser jwtUser = jwtValidator.validate(token);
			if (jwtUser != null) {
				if (data.getUser().getId() == null || Long.valueOf(data.getUser().getId()) == 0 ||
					data.getApartment().getId() == null || Long.valueOf(data.getApartment().getId()) == 0 ||
					data.getComment() == null || data.getComment() == "") {
					return new ResponseEntity<>("All fields are required (user id, apartment id, comment, rating).", HttpStatus.FORBIDDEN);
				}
				User user = userService.findByEmail(jwtUser.getEmail());
				Apartment apartment = apartmentService.findOne(data.getApartment().getId());
				
				if (user == null) {
					return new ResponseEntity<>("User not found.", HttpStatus.NOT_FOUND);
				}
				
				if (apartment == null) {
					return new ResponseEntity<>("Apartment not found.", HttpStatus.NOT_FOUND);
				}
				
				SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
				SimpleDateFormat timeFormatter = new SimpleDateFormat("HH:mm:ss");
				String date = dateFormatter.format(new Date());
				String time = timeFormatter.format(new Date());
				
				Review review = new Review(user, apartment, data.getComment(), date, time);
				
		        return new ResponseEntity<>(service.save(review), HttpStatus.OK);
			} else {
				return new ResponseEntity<>("User with this email doesn't exist.", HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return new ResponseEntity<>("Token not provided.", HttpStatus.FORBIDDEN);
		}
    }

	@RequestMapping(value="/{id}", method=RequestMethod.PUT)
    public ResponseEntity<Object> updateReview(@RequestBody Review review, @PathVariable Long id) {
        return null;
    }

	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
    public ResponseEntity<Object> deleteReview(@PathVariable Long id) {
		Review review = service.findOne(id);
		if (review == null) {
			return new ResponseEntity<>("Review not found.", HttpStatus.NOT_FOUND);
		}
		service.delete(review);
        return new ResponseEntity<>(review, HttpStatus.OK);
    }
	
//	@RequestMapping(value="/accommodation/{id}/grade", method=RequestMethod.GET)
//    public double calculateReview(@PathVariable Long id){
//        return service.calculateAverageGrade(id);
//    }
}
