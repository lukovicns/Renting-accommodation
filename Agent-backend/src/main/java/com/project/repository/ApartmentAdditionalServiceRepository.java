package com.project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.model.ApartmentAdditionalService;

@Repository
public interface ApartmentAdditionalServiceRepository extends JpaRepository<ApartmentAdditionalService, Long>{

	List<ApartmentAdditionalService> findByApartmentId(Long apartmentId);

	void deleteByApartmentId(Long apartmentId);

}