package com.project.Rentingaccommodation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.Rentingaccommodation.model.ApartmentAdditionalService;

@Repository
public interface ApartmentAdditionalServiceRepository extends JpaRepository<ApartmentAdditionalService, Long> {

}
