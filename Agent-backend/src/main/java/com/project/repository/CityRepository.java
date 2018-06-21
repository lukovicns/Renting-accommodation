package com.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.model.City;

@Repository
public interface CityRepository extends JpaRepository<City, Long>{

}
