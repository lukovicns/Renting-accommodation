package com.project.Rentingaccommodation.controller;

import java.io.IOException;
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

import com.project.Rentingaccommodation.logger.CommentLogger;
import com.project.Rentingaccommodation.model.Admin;
import com.project.Rentingaccommodation.model.Apartment;
import com.project.Rentingaccommodation.model.Comment;
import com.project.Rentingaccommodation.model.CommentStatus;
import com.project.Rentingaccommodation.model.Reservation;
import com.project.Rentingaccommodation.model.User;
import com.project.Rentingaccommodation.model.UserPrivileges;
import com.project.Rentingaccommodation.model.UserRoles;
import com.project.Rentingaccommodation.security.JwtUser;
import com.project.Rentingaccommodation.security.JwtUserPermissions;
import com.project.Rentingaccommodation.security.JwtValidator;
import com.project.Rentingaccommodation.service.AdminService;
import com.project.Rentingaccommodation.service.ApartmentService;
import com.project.Rentingaccommodation.service.CommentService;
import com.project.Rentingaccommodation.service.ReservationService;
import com.project.Rentingaccommodation.service.UserService;

@RestController
@RequestMapping(value = "/api/comments")
public class CommentController {

    @Autowired
    private CommentService service;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private ApartmentService apartmentService;
    
    @Autowired
    private AdminService adminService;
    
    @Autowired
    private ReservationService reservationService;
    
    @Autowired
    private JwtValidator jwtValidator;
    
    @Autowired
    private JwtUserPermissions jwtUserPermissions;
    
	@RequestMapping(value="", method=RequestMethod.GET)
    public ResponseEntity<List<Comment>> getComments() {
    	return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
    }
	
	@RequestMapping(value="/approved", method=RequestMethod.GET)
    public ResponseEntity<List<Comment>> getApprovedComments() {
    	return new ResponseEntity<>(service.findApprovedComments(), HttpStatus.OK);
    }
	
	@RequestMapping(value="/waiting", method=RequestMethod.GET)
    public ResponseEntity<List<Comment>> getWaitingComments() {
    	return new ResponseEntity<>(service.findWaitingComments(), HttpStatus.OK);
    }
    
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
    public ResponseEntity<Object> getComment(@PathVariable Long id) {
		Comment comment = service.findOne(id);
		if (comment == null) {
			return new ResponseEntity<>("Comment not found.", HttpStatus.NOT_FOUND);
		}
        return new ResponseEntity<>(comment, HttpStatus.OK);
    }
	
	@RequestMapping(value="/apartment/{id}", method=RequestMethod.GET)
    public List<Comment> getAllCommentsForApartment(@PathVariable Long id) {
        return service.findApartmentComments(id);
    }
	
	@RequestMapping(value="/apartment/{id}/approved", method=RequestMethod.GET)
    public List<Comment> getAllApprovedCommentsForApartment(@PathVariable Long id) {
        return service.findApartmentApprovedComments(id);
    }
    
	@RequestMapping(value="", method=RequestMethod.POST)
    public ResponseEntity<Object> addComment(@RequestBody Comment data, @RequestHeader("Authorization") String authHeader) throws SecurityException, IOException {
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
				
				if (data.getApartment() == null || Long.valueOf(data.getApartment().getId()) == 0 || data.getComment() == null || data.getComment() == "") {
					return new ResponseEntity<>("You must enter apartment and comment.", HttpStatus.FORBIDDEN);
				}
				
				Apartment apartment = apartmentService.findOne(data.getApartment().getId());
				
				if (apartment == null) {
					return new ResponseEntity<>("Apartment not found.", HttpStatus.NOT_FOUND);
				}
				
				SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
				SimpleDateFormat timeFormatter = new SimpleDateFormat("HH:mm:ss");
				String date = dateFormatter.format(new Date());
				String time = timeFormatter.format(new Date());

				List<Reservation> userReservations = reservationService.findUserReservationsByApartmentId(user, apartment.getId());
				
				if (userReservations.isEmpty()) {
					CommentLogger.log(Level.WARNING, "User " + user.getEmail() + " tried to make comment, but he doesn't have a reservation of this apartment.");
					return new ResponseEntity<>("You must first make reservations to make comments.", HttpStatus.FORBIDDEN);
				}
				
				for (Reservation reservation : userReservations) {
					try {
						Date endDate = dateFormatter.parse(reservation.getEndDate());
						if (endDate.compareTo(new Date()) > 0) {
							CommentLogger.log(Level.WARNING, "User " + user.getEmail() + " tried to make comment, but his reservation didn't pass yet.");
							return new ResponseEntity<>("You can comment after the reservation has passed.", HttpStatus.FORBIDDEN);
						}
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}
				
				for (Comment c : service.findAll()) {
					if (c.getApartment().getId() == apartment.getId() && c.getUser().getId() == user.getId()) {
						if (c.getStatus().equals(CommentStatus.APPROVED)) {
							CommentLogger.log(Level.WARNING, "User " + user.getEmail() + " tried to make comment, but he already commented this apartment.");
							return new ResponseEntity<>("User already commented this apartment.", HttpStatus.FORBIDDEN);
						} else {
							CommentLogger.log(Level.WARNING, "User " + user.getEmail() + " already made comment for this apartment.");
							return new ResponseEntity<>("User already commented this apartment. Waiting for approval.", HttpStatus.FORBIDDEN);
						}
					}
				}
				
				Comment comment = new Comment(user, apartment, data.getComment(), date, time);
				
		        return new ResponseEntity<>(service.save(comment), HttpStatus.OK);
			} else {
				CommentLogger.log(Level.WARNING, "Given token is not valid. User doesn't exist.");
				return new ResponseEntity<>("User with this email doesn't exist.", HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			CommentLogger.log(Level.WARNING, "Exception occured while validating token.");
			return new ResponseEntity<>("Error validating token.", HttpStatus.FORBIDDEN);
		}
    }

	@RequestMapping(value="/{id}/approve", method=RequestMethod.PUT)
    public ResponseEntity<Object> approveComment(@PathVariable Long id, @RequestHeader("Authorization") String authHeader) throws SecurityException, IOException {
		if(!jwtUserPermissions.hasRoleAndPrivilege(authHeader, UserRoles.ADMIN, UserPrivileges.WRITE_PRIVILEGE)) {
			return new ResponseEntity<>("Not authorized", HttpStatus.UNAUTHORIZED);
		}
		try {
			String token = authHeader.split(" ")[1].trim();
			JwtUser jwtUser = jwtValidator.validate(token);
			if (jwtUser != null) {
				Admin admin = adminService.findByIdAndEmail(jwtUser.getId(), jwtUser.getEmail());
				Comment comment = service.findOne(id);
				if (admin == null) {
					return new ResponseEntity<>("Admin not found.", HttpStatus.NOT_FOUND);
				}
				if (comment == null) {
					return new ResponseEntity<>("Comment not found.", HttpStatus.NOT_FOUND);
				}
				comment.setStatus(CommentStatus.APPROVED);
				return new ResponseEntity<>(service.save(comment), HttpStatus.OK);
			} else {
				CommentLogger.log(Level.WARNING, "Given token is not valid. User doesn't exist.");
				return new ResponseEntity<>("User with this email doesn't exist.", HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			CommentLogger.log(Level.WARNING, "Exception occured while validating token.");
			return new ResponseEntity<>("Error validating token.", HttpStatus.FORBIDDEN);
		}
    }
	
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
    public ResponseEntity<Object> deleteComment(@PathVariable Long id, @RequestHeader("Authorization") String authHeader) throws SecurityException, IOException {
		if(!jwtUserPermissions.hasRoleAndPrivilege(authHeader, UserRoles.ADMIN, UserPrivileges.READ_WRITE_PRIVILEGE)) {
			return new ResponseEntity<>("Not authorized", HttpStatus.UNAUTHORIZED);
		}
		try {
			String token = authHeader.split(" ")[1].trim();
			JwtUser jwtUser = jwtValidator.validate(token);
			if (jwtUser != null) {
				Admin admin = adminService.findByIdAndEmail(jwtUser.getId(), jwtUser.getEmail());
				Comment comment = service.findOne(id);
				if (admin == null) {
					return new ResponseEntity<>("Admin not found.", HttpStatus.NOT_FOUND);
				}
				if (comment == null) {
					return new ResponseEntity<>("Comment not found.", HttpStatus.NOT_FOUND);
				}
				service.delete(comment);
				return new ResponseEntity<>(comment, HttpStatus.OK);
			}  else {
				CommentLogger.log(Level.WARNING, "Given token is not valid. User doesn't exist.");
				return new ResponseEntity<>("User with this email doesn't exist.", HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			CommentLogger.log(Level.WARNING, "Exception occured while validating token.");
			return new ResponseEntity<>("Error validating token.", HttpStatus.FORBIDDEN);
		}
    }
}
