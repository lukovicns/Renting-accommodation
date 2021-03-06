package com.project.Rentingaccommodation.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.project.Rentingaccommodation.logger.ReservationLogger;
import com.project.Rentingaccommodation.model.Apartment;
import com.project.Rentingaccommodation.model.PricePlan;
import com.project.Rentingaccommodation.model.Reservation;
import com.project.Rentingaccommodation.model.ReservationStatus;
import com.project.Rentingaccommodation.model.User;
import com.project.Rentingaccommodation.model.UserPrivileges;
import com.project.Rentingaccommodation.model.UserRoles;
import com.project.Rentingaccommodation.security.JwtUser;
import com.project.Rentingaccommodation.security.JwtUserPermissions;
import com.project.Rentingaccommodation.security.JwtValidator;
import com.project.Rentingaccommodation.service.ApartmentService;
import com.project.Rentingaccommodation.service.PricePlanService;
import com.project.Rentingaccommodation.service.ReservationService;
import com.project.Rentingaccommodation.service.UserService;

@RestController
@RequestMapping(value="/api/reservations")
public class ReservationController {

	@Autowired
	private ReservationService service;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ApartmentService apartmentService;
	
	@Autowired
	private PricePlanService pricePlanService;
	
	@Autowired
	private JwtValidator jwtValidator;
	
	@Autowired
	private JwtUserPermissions jwtUserPermissions;
	
	@RequestMapping(value="/user", method=RequestMethod.GET)
	public ResponseEntity<Object> getUserReservations(@RequestHeader("Authorization") String authHeader) {
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
				List<Reservation> userReservations = service.findUserReservations(jwtUser.getEmail());
				return new ResponseEntity<>(userReservations, HttpStatus.OK);
			} else {
				return new ResponseEntity<>("User with this email doesn't exist.", HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return new ResponseEntity<>("Error validating token.", HttpStatus.FORBIDDEN);
		}
	}
	
	@RequestMapping(value="/user/{apartmentId}", method=RequestMethod.GET)
	public ResponseEntity<Object> getUserReservationByApartmentId(@RequestHeader("Authorization") String authHeader, @PathVariable Long apartmentId) {
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
				Reservation reservation = service.findUserReservationByApartmentId(user, apartmentId);
				return new ResponseEntity<>(reservation, HttpStatus.OK);
			} else {
				return new ResponseEntity<>("User with this email doesn't exist.", HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return new ResponseEntity<>("Token not provided.", HttpStatus.FORBIDDEN);
		}
	}
	
	@RequestMapping(value="", method=RequestMethod.GET)
	public ResponseEntity<List<Reservation>> getAllReservations() {
		return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
	}
	
	@RequestMapping(value="", method=RequestMethod.POST)
	public ResponseEntity<Object> makeReservation(@RequestBody Reservation reservation, @RequestHeader("Authorization") String authHeader) throws SecurityException, IOException {
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
				if (reservation.getStartDate() == null || reservation.getStartDate() == "" ||
					reservation.getEndDate() == null || reservation.getEndDate() == "" ||
					reservation.getApartment() == null) {
					return new ResponseEntity<>("All fields are required (apartment, start date, end date).", HttpStatus.FORBIDDEN);
				}
				Apartment apartment = apartmentService.findOne(reservation.getApartment().getId());

				if (apartment == null) {
					return new ResponseEntity<>("Apartment not found.", HttpStatus.NOT_FOUND);
				}
				
				Pattern pattern = Pattern.compile("^[0-3]?[0-9]/[0-3]?[0-9]/(?:[0-9]{2})?[0-9]{2}$");
				Matcher startDateMatcher = pattern.matcher(reservation.getStartDate());
				Matcher endDateMatcher = pattern.matcher(reservation.getEndDate());
				
				// Check date formats.
				if (!startDateMatcher.find()) {
					ReservationLogger.log(Level.WARNING, "User " + user.getEmail() + " tried to make reservation, but start date is invalid.");
					return new ResponseEntity<>("Start date must be in format dd/MM/yyyy.", HttpStatus.FORBIDDEN);
				}
				if (!endDateMatcher.find()) {
					ReservationLogger.log(Level.WARNING, "User " + user.getEmail() + " tried to make reservation, but end date is invalid.");
					return new ResponseEntity<>("End date must be in format dd/MM/yyyy.", HttpStatus.FORBIDDEN);
				}
				
				// Check if dates are past dates.
				SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
				Date startDate = dateFormatter.parse(reservation.getStartDate());
				Date endDate = dateFormatter.parse(reservation.getEndDate());
				if (startDate.before(new Date()) || endDate.before(new Date())) {
					ReservationLogger.log(Level.WARNING, "User " + user.getEmail() + " tried to make reservation, but he entered passed dates.");
					return new ResponseEntity<>("You must enter future dates.", HttpStatus.FORBIDDEN);
				}
				
				// Check if start date is before end date.
				if (!startDate.before(endDate)) {
					ReservationLogger.log(Level.WARNING, "User " + user.getEmail() + " tried to make reservation, but he entered start date greater than end date.");
					return new ResponseEntity<>("Start date must be before end date.", HttpStatus.FORBIDDEN);
				}
				
				// Check if apartment is available in that period.
				if (!service.isAvailable(reservation.getApartment(), reservation.getStartDate(), reservation.getEndDate())) {
					ReservationLogger.log(Level.WARNING, "User " + user.getEmail() + " tried to make reservation, but apartment #" + apartment.getId() + " is not available at the given period.");
					return new ResponseEntity<>("Apartment is not available at the given period.", HttpStatus.FORBIDDEN);
				}
				
				// Setup price plan for apartment based on given start and end date.
				PricePlan pricePlan = pricePlanService.setReservationPricePlan(apartment, reservation.getStartDate(), reservation.getEndDate());
				
				Reservation newReservation = new Reservation(
					user,
					reservation.getApartment(),
					reservation.getStartDate(),
					reservation.getEndDate(),
					pricePlan.getPrice(),
					ReservationStatus.RESERVATION
				);
				ReservationLogger.log(Level.INFO, "User " + user.getEmail() + " successfully made reservation for apartment #" + apartment.getId() + " from " + reservation.getStartDate() + " to " + reservation.getEndDate() + ".");
				return new ResponseEntity<>(service.save(newReservation), HttpStatus.OK);
			} else {
				ReservationLogger.log(Level.WARNING, "Given token is not valid. User doesn't exist.");
				return new ResponseEntity<>("User with this email doesn't exist.", HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			ReservationLogger.log(Level.WARNING, "Exception occured while validating token.");
			return new ResponseEntity<>("Token not provided.", HttpStatus.FORBIDDEN);
		}
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.PUT)
	public ResponseEntity<Object> editReservation(@PathVariable Long id, @RequestBody Reservation reservation, @RequestHeader("Authorization") String authHeader) {
		if(!jwtUserPermissions.hasRoleAndPrivilege(authHeader, UserRoles.USER, UserPrivileges.WRITE_PRIVILEGE)) {
			return new ResponseEntity<>("Not authorized", HttpStatus.UNAUTHORIZED);
		}
		try {
			String token = authHeader.split(" ")[1].trim();
			JwtUser jwtUser = jwtValidator.validate(token);
			if (jwtUser != null) {
				User user = userService.findByIdAndEmail(jwtUser.getId(), jwtUser.getEmail());
				if (user == null) {
					return new ResponseEntity<>("User doesnt exist.", HttpStatus.NOT_FOUND);
				}
				
				Reservation newReservation = service.findOne(id);
				if (newReservation == null || newReservation.getUser().getId() != user.getId()) {
					return new ResponseEntity<>("User reservation not found.", HttpStatus.NOT_FOUND);
				}
				
				if (reservation.getStartDate() == null || reservation.getStartDate() == "" ||
					reservation.getEndDate() == null || reservation.getEndDate() == "") {
					return new ResponseEntity<>("Start date and end date must be provided.", HttpStatus.FORBIDDEN);
				}
				
				Pattern pattern = Pattern.compile("^[0-3]?[0-9]/[0-3]?[0-9]/(?:[0-9]{2})?[0-9]{2}$");
				Matcher startDateMatcher = pattern.matcher(reservation.getStartDate());
				Matcher endDateMatcher = pattern.matcher(reservation.getEndDate());
				
				// Check date formats.
				if (!startDateMatcher.find()) {
					return new ResponseEntity<>("Start date must be in format dd/MM/yyyy.", HttpStatus.FORBIDDEN);
				}
				if (!endDateMatcher.find()) {
					return new ResponseEntity<>("End date must be in format dd/MM/yyyy.", HttpStatus.FORBIDDEN);
				}
				
				// Check if dates are past dates.
				SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
				Date startDate = dateFormatter.parse(reservation.getStartDate());
				Date endDate = dateFormatter.parse(reservation.getEndDate());
				
				if (startDate.before(new Date()) || endDate.before(new Date())) {
					return new ResponseEntity<>("You must enter future dates.", HttpStatus.FORBIDDEN);
				}
				
				// Check if start date is before end date.
				if (!startDate.before(endDate)) {
					return new ResponseEntity<>("Start date must be before end date.", HttpStatus.FORBIDDEN);
				}
				
				newReservation.setStartDate(reservation.getStartDate());
				newReservation.setEndDate(reservation.getEndDate());
				
				// Check if apartment is available in that period.
				if (!service.isAvailableForUpdate(newReservation)) {
					return new ResponseEntity<>("Apartment is not available at the given period.", HttpStatus.FORBIDDEN);
				}
				
				return new ResponseEntity<>(service.save(newReservation), HttpStatus.OK);
			} else {
				return new ResponseEntity<>("User with this email doesn't exist.", HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return new ResponseEntity<>("Token not provided.", HttpStatus.FORBIDDEN);
		}
	}

	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<Object> cancelReservation(@PathVariable Long id, @RequestHeader("Authorization") String authHeader) {
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
				Reservation reservation = service.findOne(id);
				if (reservation == null) {
					return new ResponseEntity<>("Reservation not found.", HttpStatus.NOT_FOUND);
				}
				reservation.setStatus(ReservationStatus.CANCELED);
				return new ResponseEntity<>(service.save(reservation), HttpStatus.OK);
			} else {
				return new ResponseEntity<>("User with this email doesn't exist.", HttpStatus.NOT_FOUND);
			}
		} catch (Exception ex) {
			return new ResponseEntity<>("oken not provided.", HttpStatus.NOT_FOUND);
		}
	}
}
