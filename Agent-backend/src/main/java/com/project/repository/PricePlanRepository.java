package com.project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.project.model.PricePlan;

public interface PricePlanRepository extends JpaRepository<PricePlan, Long>{

	List<PricePlan> findByApartmentId(Long id);

}
