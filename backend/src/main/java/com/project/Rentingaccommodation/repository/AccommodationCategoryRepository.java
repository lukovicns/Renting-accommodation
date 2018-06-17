package com.project.Rentingaccommodation.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.project.Rentingaccommodation.model.AccommodationCategory;

@Repository
public interface AccommodationCategoryRepository  extends JpaRepository<AccommodationCategory, Long> {

}
