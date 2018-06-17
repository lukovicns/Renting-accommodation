package com.project.Rentingaccommodation.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.Rentingaccommodation.model.Review;
import com.project.Rentingaccommodation.model.ReviewStatus;
import com.project.Rentingaccommodation.repository.AccommodationRepository;
import com.project.Rentingaccommodation.repository.ReviewRepository;
import com.project.Rentingaccommodation.repository.UserRepository;
import com.project.Rentingaccommodation.service.ReviewService;


@Transactional
@Service
public class JpaReviewService implements ReviewService {

    @Autowired
    ReviewRepository reviewRepository;

    @Autowired
    AccommodationRepository accomodationRepository;

    @Autowired
    UserRepository userRepository;

//    public Review allowReview(Review review, boolean allow) {
//    	Optional<Review> optional = this.reviewRepository.findById(review.getId());
//    	if (!optional.isPresent()) return null;
//    	Review r = optional.get();
//    	r.setAllowed(allow);
//    	return this.reviewRepository.save(r);
//    }
//    
//    public List<Review> getReviewsByAllowed(boolean allowed) {
//    	return this.reviewRepository.findByAllowed(allowed);
//    }
//    
//    public Review createReview(Review review) {
//        if(review.getGrade() > 5 || review.getGrade() < 1){
//            return null;
//        }
//
//        if(accomodationRepository.getOne(review.getAccommodation().getId()) == null){
//            return null;
//        }
//
//        if(userRepository.getOne(review.getUser().getEmail()) == null){
//            return null;
//        }
//
//        if(review.isAllowed() != null && review.isAllowed()){
//            return null;
//        }
//
//        return reviewRepository.save(review);
//    }
//
//    public Review editReview(Review review) {
//        if(review.getGrade() > 5 || review.getGrade() < 1){
//            return null;
//        }
//
//        if(accomodationRepository.getOne(review.getAccommodation().getId()) == null){
//            return null;
//        }
//
//        if(userRepository.getOne(review.getUser().getEmail()) == null){
//            return null;
//        }
//
//        if(review.isAllowed() == null) {
//            return null;
//        }
//
//        return reviewRepository.save(review);
//    }
//
//    public Review getReview(int reviewId) {
//        return reviewRepository.getOne(reviewId);
//    }
//
//    public List<Review> getAllReviewsForPlace(int accommodationId) {
//        return  reviewRepository.findByAccommodationId(accommodationId);
//    }
//    
//    public List<Review> getAll(){
//    	return this.reviewRepository.findAll();
//    }
//
//    public void deleteReview(int reviewId) {
//        reviewRepository.deleteById(reviewId);
//    }

    public double calculateAverageGrade(Long accommodationId) {
        List<Review> reviews = getAllReviewsForPlace(accommodationId);
        if(reviews.size() > 0) {
            double sum = 0.0;
            for (Review r : reviews) {
                sum += r.getGrade();
            }

            return sum / reviews.size();
        }

        return 0.0;
    }

	@Override
	public Review findOne(Long id) {
		for (Review review : reviewRepository.findAll()) {
			if (review.getId() == id) {
				return review;
			}
		}
		return null;
	}

	@Override
	public List<Review> findAll() {
		return reviewRepository.findAll();
	}

	@Override
	public Review save(Review review) {
		return reviewRepository.save(review);
	}

	@Override
	public void delete(Review review) {
		reviewRepository.delete(review);
	}

	@Override
	public List<Review> getNotAllowedReviews() {
		List<Review> notAllowedReviews = new ArrayList<Review>();
		for (Review review : reviewRepository.findAll()) {
			if (review.getStatus().equals(ReviewStatus.DECLINED)) {
				notAllowedReviews.add(review);
			}
		}
		return notAllowedReviews;
	}

	@Override
	public List<Review> getAllReviewsForPlace(Long id) {
		List<Review> reviews = new ArrayList<Review>();
		for (Review review : reviewRepository.findAll()) {
			if (review.getApartment().getId() == id) {
				reviews.add(review);
			}
		}
		return reviews;
	}
}
