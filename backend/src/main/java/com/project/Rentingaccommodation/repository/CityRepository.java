package com.project.Rentingaccommodation.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.Rentingaccommodation.model.City;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {

	List<City> findByCountryId(Long id);

}
