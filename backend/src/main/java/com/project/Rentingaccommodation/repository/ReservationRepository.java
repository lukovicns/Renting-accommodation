package com.project.Rentingaccommodation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.Rentingaccommodation.model.Reservation;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
	
}
