package com.project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.model.Apartment;

@Repository
public interface ApartmentRepository extends JpaRepository<Apartment, Long>{

	List<Apartment> findByAccommodationId(Long accommodation);

}
