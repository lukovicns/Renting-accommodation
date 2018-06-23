package com.project.Rentingaccommodation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.Rentingaccommodation.model.PricePlan;

@Repository
public interface PricePlanRepository extends JpaRepository<PricePlan, Long> {

}
