package com.project.Rentingaccommodation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.Rentingaccommodation.model.Accommodation;

@Repository
public interface AccommodationRepository extends JpaRepository<Accommodation, Long>{

}
