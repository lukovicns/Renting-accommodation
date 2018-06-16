package com.project.Rentingaccommodation.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.Rentingaccommodation.model.AccommodationCategory;

import java.util.List;

@Repository
public interface AccommodationCategoryRepository  extends JpaRepository<AccommodationCategory, Integer> {

    public List<AccommodationCategory> findByIdIn(List<Integer> ids);
}
