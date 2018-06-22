package com.project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.model.ApartmentService;

@Repository
public interface ApartmentServiceRepository extends JpaRepository<ApartmentService, Long>{

	List<ApartmentService> findByApartmentId(Long apartmentId);

	void deleteByApartmentId(Long apartmentId);

}
