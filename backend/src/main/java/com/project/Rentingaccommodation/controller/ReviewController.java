package com.project.Rentingaccommodation.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.project.Rentingaccommodation.model.Review;
import com.project.Rentingaccommodation.service.impl.ReviewService;

@RestController
@RequestMapping("/api/reviews")
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600, allowCredentials = "true")
public class ReviewController {

    @Autowired
    ReviewService reviewService;

    @GetMapping("/not-allowed")
    public ResponseEntity<List<Review>> getNotAllowedReviews(){
    	return new ResponseEntity<List<Review>>(this.reviewService.getReviewsByAllowed(false), HttpStatus.OK);
    }
    
	@RequestMapping(value="", method=RequestMethod.GET)
    public ResponseEntity<List<Review>> getAll() {
    	return new ResponseEntity<List<Review>>(this.reviewService.getAll(), HttpStatus.OK);
    }
    
	@RequestMapping(value="", method=RequestMethod.POST)
    public ResponseEntity<Review> createReview(@RequestBody Review review) throws URISyntaxException{
        if(review.getId() >= 0) {
            return null;
        }

        Review retVal = reviewService.createReview(review);
        if(retVal != null){
            return ResponseEntity.created(new
                    URI("/reviews/"+retVal.getId()))
                    .body(retVal);
        }

        return null;
    }

	@RequestMapping(value="/{reviewId}", method=RequestMethod.PUT)
    public ResponseEntity<Review> editReview(@RequestBody Review review, @RequestParam int reviewId) {
        if(review.getId() < 0 || review.getId() != reviewId) {
            return null;
        }

        Review retVal = reviewService.editReview(review);
        if(retVal != null){
            return ResponseEntity.ok(retVal);
        }

        return null;
    }

	@RequestMapping(value="/{reviewId}", method=RequestMethod.DELETE)
    public void deleteReview(@RequestParam int reviewId){
        reviewService.deleteReview(reviewId);
    }

	@RequestMapping(value="/{reviewId}", method=RequestMethod.GET)
    public Review getOneReview(@RequestParam int reviewId){
        return reviewService.getReview(reviewId);
    }

	@RequestMapping(value="/accommodation/{accommodationId}", method=RequestMethod.GET)
    public List<Review> getAllReviewsForPlace(@RequestParam int accommodationId){
        return reviewService.getAllReviewsForPlace(accommodationId);
    }
	
	@RequestMapping(value="/accommodation/{accommodationId}/grade", method=RequestMethod.GET)
    public double calculateReview(@RequestParam int accommodationId){
        return reviewService.calculateAverageGrade(accommodationId);
    }
}
