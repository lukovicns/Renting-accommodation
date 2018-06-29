package com.project.Rentingaccommodation.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.project.Rentingaccommodation.logger.RatingLogger;
import com.project.Rentingaccommodation.model.Apartment;
import com.project.Rentingaccommodation.model.Rating;
import com.project.Rentingaccommodation.model.Reservation;
import com.project.Rentingaccommodation.model.User;
import com.project.Rentingaccommodation.model.UserPrivileges;
import com.project.Rentingaccommodation.model.UserRoles;
import com.project.Rentingaccommodation.security.JwtUser;
import com.project.Rentingaccommodation.security.JwtUserPermissions;
import com.project.Rentingaccommodation.security.JwtValidator;
import com.project.Rentingaccommodation.service.ApartmentService;
import com.project.Rentingaccommodation.service.RatingService;
import com.project.Rentingaccommodation.service.ReservationService;
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
    
    @Autowired
    private JwtUserPermissions jwtUserPermissions;
    
    @Autowired
    private ReservationService reservationService;
    
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
		if(!jwtUserPermissions.hasRoleAndPrivilege(authHeader, UserRoles.USER, UserPrivileges.WRITE_PRIVILEGE)) {
			return new ResponseEntity<>("Not authorized", HttpStatus.UNAUTHORIZED);
		}
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
	public ResponseEntity<Object> addRatingForApartment(@RequestBody Rating data, @RequestHeader("Authorization") String token) {
		if(!jwtUserPermissions.hasRoleAndPrivilege(token, UserRoles.USER, UserPrivileges.WRITE_PRIVILEGE)) {
			return new ResponseEntity<>("Not authorized", HttpStatus.UNAUTHORIZED);
		}
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
			RatingLogger.log(Level.WARNING, "User " + user.getEmail() + " tried to rate #" + apartment.getId() + " apartment, but he already rated it.");
			return new ResponseEntity<>("User already rated this apartment.", HttpStatus.FORBIDDEN);
		}
		
		SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
		SimpleDateFormat timeFormatter = new SimpleDateFormat("HH:mm:ss");
		String date = dateFormatter.format(new Date());
		String time = timeFormatter.format(new Date());
		
		List<Reservation> userReservations = reservationService.findUserReservationsByApartmentId(user, apartment.getId());
		
		if (userReservations.isEmpty()) {
			RatingLogger.log(Level.WARNING, "User " + user.getEmail() + " tried to rate #" + apartment.getId() + " apartment, but he doesn't have reservations for this apartment yet.");
			return new ResponseEntity<>("You must first make reservations to rate apartment.", HttpStatus.FORBIDDEN);
		}
		
		for (Reservation reservation : userReservations) {
			try {
				Date endDate = dateFormatter.parse(reservation.getEndDate());
				if (endDate.compareTo(new Date()) > 0) {
					RatingLogger.log(Level.WARNING, "User " + user.getEmail() + " tried to rate #" + apartment.getId() + " apartment, but his reservation didn't pass yet.");
					return new ResponseEntity<>("You can rate apartment after the reservation has passed.", HttpStatus.FORBIDDEN);
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		
		Rating rating = new Rating(user, apartment, date, time, data.getRating());
		RatingLogger.log(Level.INFO, "User " + user.getEmail() + " successfully rated #" + apartment.getId() + " apartment.");
		return new ResponseEntity<>(service.save(rating), HttpStatus.OK);
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<Object> deleteRating(@PathVariable Long id, @RequestHeader("Authorization") String token) {
		if(!jwtUserPermissions.hasRoleAndPrivilege(token, UserRoles.USER, UserPrivileges.WRITE_PRIVILEGE)) {
			return new ResponseEntity<>("Not authorized", HttpStatus.UNAUTHORIZED);
		}
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
