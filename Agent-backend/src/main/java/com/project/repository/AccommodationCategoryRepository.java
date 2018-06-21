package com.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.model.AccommodationCategory;

@Repository
public interface AccommodationCategoryRepository extends JpaRepository<AccommodationCategory, Long>{

}
