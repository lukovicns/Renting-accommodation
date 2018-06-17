package com.project.Rentingaccommodation.repository;

import com.project.Rentingaccommodation.model.Review;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
	
}
