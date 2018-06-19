package com.project.Rentingaccommodation.service;

import java.util.List;
import com.project.Rentingaccommodation.model.Review;
import com.project.Rentingaccommodation.model.User;

public interface ReviewService {

	Review findOne(Long id);
	List<Review> findAll();
	List<Review> findApprovedReviews();
	List<Review> findDeclinedReviews();
	List<Review> findWaitingReviews();
	List<Review> findUserReviews(User user);
	List<Review> findApartmentReviews(Long id);
	Review save(Review review);
	void delete(Review review);
}
