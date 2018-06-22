package com.project.Rentingaccommodation.service;

import java.util.List;

import com.project.Rentingaccommodation.model.Apartment;
import com.project.Rentingaccommodation.model.Rating;
import com.project.Rentingaccommodation.model.User;

public interface RatingService {

	List<Rating> findAll();
	List<Rating> findUserRatings(User user);
	List<Rating> findApartmentRatings(Apartment apartment);
	double findAverageRatingForApartment(Apartment apartment);
	Rating findOne(Long id);
	Rating save(Rating rating);
	void delete(Rating rating);
	Rating findUserRatingForApartment(User user, Apartment apartment);
}
