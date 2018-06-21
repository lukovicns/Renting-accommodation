package com.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.model.AccommodationType;

@Repository
public interface AccommodationTypeRepository extends JpaRepository<AccommodationType, Long>{

}
