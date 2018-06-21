package com.project.Rentingaccommodation.repository;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.Rentingaccommodation.model.City;
import com.project.Rentingaccommodation.model.Country;
import java.util.List;

@Repository
@EntityScan(basePackages= {"com.project.Rentingaccommodation.model"})
public interface CityRepository extends JpaRepository<City, Long>{
	List<City> findByCountry(Country country);
}
