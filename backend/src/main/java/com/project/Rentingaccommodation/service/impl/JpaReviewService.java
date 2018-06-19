package com.project.Rentingaccommodation.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.project.Rentingaccommodation.model.Review;
import com.project.Rentingaccommodation.model.ReviewStatus;
import com.project.Rentingaccommodation.model.User;
import com.project.Rentingaccommodation.repository.ReviewRepository;
import com.project.Rentingaccommodation.service.ReviewService;

@Transactional
@Service
public class JpaReviewService implements ReviewService {

    @Autowired
    ReviewRepository repository;

	@Override
	public Review findOne(Long id) {
		for (Review review : repository.findAll()) {
			if (review.getId() == id) {
				return review;
			}
		}
		return null;
	}

	@Override
	public List<Review> findAll() {
		return repository.findAll();
	}
	
	@Override
	public List<Review> findApprovedReviews() {
		List<Review> approvedReviews = new ArrayList<Review>();
		for (Review review : findAll()) {
			if (review.getStatus().equals(ReviewStatus.APPROVED)) {
				approvedReviews.add(review);
			}
		}
		return approvedReviews;
	}

	@Override
	public List<Review> findDeclinedReviews() {
		List<Review> declinedReviews = new ArrayList<Review>();
		for (Review review : findAll()) {
			if (review.getStatus().equals(ReviewStatus.DECLINED)) {
				declinedReviews.add(review);
			}
		}
		return declinedReviews;
	}
	
	@Override
	public List<Review> findWaitingReviews() {
		List<Review> waitingReviews = new ArrayList<Review>();
		for (Review review : findAll()) {
			if (review.getStatus().equals(ReviewStatus.WAITING)) {
				waitingReviews.add(review);
			}
		}
		return waitingReviews;
	}
	
	@Override
	public List<Review> findUserReviews(User user) {
		List<Review> userReviews = new ArrayList<Review>();
		for (Review review : findAll()) {
			if (review.getUser().getId() == user.getId()) {
				userReviews.add(review);
			}
		}
		return userReviews;
	}
	
	@Override
	public List<Review> findApartmentReviews(Long id) {
		List<Review> apartmentReviews = new ArrayList<Review>();
		for (Review review : findAll()) {
			if (review.getApartment().getId() == id) {
				apartmentReviews.add(review);
			}
		}
		return apartmentReviews;
	}
	
	@Override
	public Review save(Review review) {
		return repository.save(review);
	}

	@Override
	public void delete(Review review) {
		repository.delete(review);
	}
}
