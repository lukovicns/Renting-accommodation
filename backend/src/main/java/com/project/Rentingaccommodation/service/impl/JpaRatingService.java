package com.project.Rentingaccommodation.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.Rentingaccommodation.model.Apartment;
import com.project.Rentingaccommodation.model.Rating;
import com.project.Rentingaccommodation.model.User;
import com.project.Rentingaccommodation.repository.RatingRepository;
import com.project.Rentingaccommodation.service.RatingService;

@Transactional
@Service
public class JpaRatingService implements RatingService {
	
	@Autowired
	private RatingRepository repository;
	
	@Override
	public List<Rating> findAll() {
		return repository.findAll();
	}

	@Override
	public Rating findOne(Long id) {
		for (Rating rating : findAll()) {
			if (rating.getId() == id) {
				return rating;
			}
		}
		return null;
	}
	
	@Override
	public List<Rating> findUserRatings(User user) {
		List<Rating> userRatings = new ArrayList<Rating>();
		for (Rating rating : findAll()) {
			if (rating.getUser().getId() == user.getId()) {
				userRatings.add(rating);
			}
		}
		return userRatings;
	}

	@Override
	public List<Rating> findApartmentRatings(Apartment apartment) {
		List<Rating> apartmentRatings = new ArrayList<Rating>();
		for (Rating rating : findAll()) {
			if (rating.getApartment().getId() == apartment.getId()) {
				apartmentRatings.add(rating);
			}
		}
		return apartmentRatings;
	}
	
	@Override
	public double findAverageRatingForApartment(Apartment apartment) {
		long sum = 0;
		List<Rating> apartmentRatings = findApartmentRatings(apartment);
		for (Rating rating : apartmentRatings) {
			sum += rating.getRating();
		}
		System.out.println(sum);
		return sum / apartmentRatings.size();
	}

	@Override
	public Rating save(Rating rating) {
		return repository.save(rating);
	}

	@Override
	public void delete(Rating rating) {
		repository.delete(rating);
	}
}
