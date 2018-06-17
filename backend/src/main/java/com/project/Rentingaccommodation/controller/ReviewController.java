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
import com.project.Rentingaccommodation.model.Review;
import com.project.Rentingaccommodation.service.ReviewService;

@RestController
@RequestMapping(value = "/api/reviews")
public class ReviewController {

    @Autowired
    private ReviewService service;
    
	@RequestMapping(value="", method=RequestMethod.GET)
    public ResponseEntity<List<Review>> getReviews() {
    	return new ResponseEntity<>(this.service.findAll(), HttpStatus.OK);
    }

    @RequestMapping(value="/not-allowed", method=RequestMethod.GET)
    public ResponseEntity<List<Review>> getNotAllowedReviews(){
    	return new ResponseEntity<List<Review>>(service.getNotAllowedReviews(), HttpStatus.OK);
    }
    
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
    public ResponseEntity<Object> getOneReview(@PathVariable Long id){
        return null;
    }
	
	@RequestMapping(value="/accommodation/{id}", method=RequestMethod.GET)
    public List<Review> getAllReviewsForPlace(@PathVariable Long id){
        return service.getAllReviewsForPlace(id);
    }
    
	@RequestMapping(value="", method=RequestMethod.POST)
    public ResponseEntity<Object> addReview(@RequestBody Review review) {
        return null;
    }

	@RequestMapping(value="/{id}", method=RequestMethod.PUT)
    public ResponseEntity<Object> updateReview(@RequestBody Review review, @PathVariable Long id) {
        return null;
    }

	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
    public ResponseEntity<Object> deleteReview(@PathVariable Long id){
        return null;
    }
	
	@RequestMapping(value="/accommodation/{id}/grade", method=RequestMethod.GET)
    public double calculateReview(@PathVariable Long id){
        return service.calculateAverageGrade(id);
    }
}
