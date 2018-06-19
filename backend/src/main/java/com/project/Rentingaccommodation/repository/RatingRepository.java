package com.project.Rentingaccommodation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.Rentingaccommodation.model.Rating;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Long> {

}
