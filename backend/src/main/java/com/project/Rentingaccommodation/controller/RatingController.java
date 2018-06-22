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
import com.project.Rentingaccommodation.model.Rating;
import com.project.Rentingaccommodation.model.User;
import com.project.Rentingaccommodation.security.JwtUser;
import com.project.Rentingaccommodation.security.JwtValidator;
import com.project.Rentingaccommodation.service.ApartmentService;
import com.project.Rentingaccommodation.service.RatingService;
import com.project.Rentingaccommodation.service.UserService;

@RestController
@RequestMapping(value="/api/ratings")
public class RatingController {

    @Autowired
    private RatingService service;
    
    @Autowired
    private ApartmentService apartmentService;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private JwtValidator jwtValidator;
    
	@RequestMapping(value="", method=RequestMethod.GET)
	public ResponseEntity<List<Rating>> getRatings() {
		return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
	}
	
	@RequestMapping(value="/apartment/{id}", method=RequestMethod.GET)
	public ResponseEntity<Object> getApartmentRatings(@PathVariable Long id) {
		Apartment apartment = apartmentService.findOne(id);
		if (apartment == null) {
			return new ResponseEntity<>("Apartment not found.", HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(service.findApartmentRatings(apartment), HttpStatus.OK);
	}
	
	@RequestMapping(value="/apartment/{id}/user", method=RequestMethod.GET)
	public ResponseEntity<Object> getUserRatingForApartment(@PathVariable Long id, @RequestHeader("Authorization") String authHeader) {
		try {
			String token = authHeader.split(" ")[1].trim();
			JwtUser jwtUser = jwtValidator.validate(token);
			if (jwtUser != null) {
				User user = userService.findByIdAndEmail(jwtUser.getId(), jwtUser.getEmail());
				if (user == null) {
					return new ResponseEntity<>("User not found.", HttpStatus.NOT_FOUND);
				}
				Apartment apartment = apartmentService.findOne(id);
				if (apartment == null) {
					return new ResponseEntity<>("Apartment not found.", HttpStatus.NOT_FOUND);
				}
				Rating userRating = service.findUserRatingForApartment(user, apartment);
				return new ResponseEntity<>(userRating, HttpStatus.OK);
			} else {
				return new ResponseEntity<>("User with this email doesn't exist.", HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return new ResponseEntity<>("Error validating token.", HttpStatus.FORBIDDEN);
		}
	}
	
	@RequestMapping(value="", method=RequestMethod.POST)
	public ResponseEntity<Object> addRatingForApartment(@RequestBody Rating data) {
		if (data.getRating() <= 0 || data.getRating() > 5) {
			return new ResponseEntity<>("Rating must be value from 1 to 5.", HttpStatus.FORBIDDEN);
		}
		
		if (data.getUser() == null || data.getApartment() == null || Integer.valueOf(data.getRating()) == 0) {
			return new ResponseEntity<>("All fields are required (user, apartment, rating - 1 to 5).", HttpStatus.FORBIDDEN);
		}
		User user = userService.findOne(data.getUser().getId());
		Apartment apartment = apartmentService.findOne(data.getApartment().getId());
		
		if (user == null) {
			return new ResponseEntity<>("User not found.", HttpStatus.NOT_FOUND);
		}
		if (apartment == null) {
			return new ResponseEntity<>("Apartment not found.", HttpStatus.NOT_FOUND);
		}
		
		// Check if user already rated.
		Rating userRating = service.findUserRatingForApartment(user, apartment);
		if (userRating != null) {
			return new ResponseEntity<>("User already rated this apartment.", HttpStatus.FORBIDDEN);
		}
		
		SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
		SimpleDateFormat timeFormatter = new SimpleDateFormat("HH:mm:ss");
		String date = dateFormatter.format(new Date());
		String time = timeFormatter.format(new Date());
		
		Rating rating = new Rating(user, apartment, date, time, data.getRating());
		return new ResponseEntity<>(service.save(rating), HttpStatus.OK);
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<Object> deleteRating(@PathVariable Long id) {
		Rating rating = service.findOne(id);
		if (rating == null) {
			return new ResponseEntity<>("Rating not found.", HttpStatus.NOT_FOUND);
		}
		service.delete(rating);
		return new ResponseEntity<>(rating, HttpStatus.OK);
	}
	
	@RequestMapping(value="/apartment/{id}/average", method=RequestMethod.GET)
	public ResponseEntity<Object> getAverageApartmentRating(@PathVariable Long id) {
		Apartment apartment = apartmentService.findOne(id);
		if (apartment == null) {
			return new ResponseEntity<>("Apartment not found.", HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(service.findAverageRatingForApartment(apartment), HttpStatus.OK);
	}
}
