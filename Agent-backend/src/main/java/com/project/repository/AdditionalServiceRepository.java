package com.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.model.AdditionalService;

@Repository
public interface AdditionalServiceRepository extends JpaRepository<AdditionalService, Long>{

}
