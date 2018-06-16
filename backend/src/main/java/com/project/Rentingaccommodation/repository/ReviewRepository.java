package com.project.Rentingaccommodation.repository;

import com.project.Rentingaccommodation.model.Review;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {
	
	 public List<Review> findByAccommodationId(int accommodation_id);
	 public List<Review> findByAllowed(Boolean allowd);

    
}
