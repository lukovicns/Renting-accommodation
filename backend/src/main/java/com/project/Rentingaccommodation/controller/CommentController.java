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
import com.project.Rentingaccommodation.model.Comment;
import com.project.Rentingaccommodation.model.User;
import com.project.Rentingaccommodation.security.JwtUser;
import com.project.Rentingaccommodation.security.JwtValidator;
import com.project.Rentingaccommodation.service.ApartmentService;
import com.project.Rentingaccommodation.service.CommentService;
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
    private JwtValidator jwtValidator;
    
	@RequestMapping(value="", method=RequestMethod.GET)
    public ResponseEntity<List<Comment>> getComments() {
    	return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
    }
	
	@RequestMapping(value="/approved", method=RequestMethod.GET)
    public ResponseEntity<List<Comment>> getApprovedComments() {
    	return new ResponseEntity<>(service.findApprovedComments(), HttpStatus.OK);
    }
	
	@RequestMapping(value="/declined", method=RequestMethod.GET)
    public ResponseEntity<List<Comment>> getDeclinedComments() {
    	return new ResponseEntity<>(service.findDeclinedComments(), HttpStatus.OK);
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
    
	@RequestMapping(value="", method=RequestMethod.POST)
    public ResponseEntity<Object> addComment(@RequestBody Comment data, @RequestHeader("Authorization") String authHeader) {
		try {
			String token = authHeader.split(" ")[1].trim();
			JwtUser jwtUser = jwtValidator.validate(token);
			if (jwtUser != null) {
				User user = userService.findOne(jwtUser.getId());
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
				
				Comment comment = new Comment(user, apartment, data.getComment(), date, time);
				
		        return new ResponseEntity<>(service.save(comment), HttpStatus.OK);
			} else {
				return new ResponseEntity<>("User with this email doesn't exist.", HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return new ResponseEntity<>("Token not provided.", HttpStatus.FORBIDDEN);
		}
    }

	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
    public ResponseEntity<Object> deleteComment(@PathVariable Long id) {
		Comment comment = service.findOne(id);
		if (comment == null) {
			return new ResponseEntity<>("Comment not found.", HttpStatus.NOT_FOUND);
		}
		service.delete(comment);
        return new ResponseEntity<>(comment, HttpStatus.OK);
    }
	
//	@RequestMapping(value="/accommodation/{id}/grade", method=RequestMethod.GET)
//    public double calculateReview(@PathVariable Long id){
//        return service.calculateAverageGrade(id);
//    }
}
