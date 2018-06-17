package com.project.Rentingaccommodation.service;

import java.util.List;
import com.project.Rentingaccommodation.model.Review;

public interface ReviewService {

	Review findOne(Long id);
	List<Review> findAll();
	Review save(Review review);
	void delete(Review review);
	List<Review> getNotAllowedReviews();
	List<Review> getAllReviewsForPlace(Long id);
	double calculateAverageGrade(Long id);
}
