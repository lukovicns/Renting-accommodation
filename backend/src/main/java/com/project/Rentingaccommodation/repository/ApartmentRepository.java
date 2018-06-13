package com.project.Rentingaccommodation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.Rentingaccommodation.model.Apartment;

@Repository
public interface ApartmentRepository extends JpaRepository<Apartment, Long>{

}
