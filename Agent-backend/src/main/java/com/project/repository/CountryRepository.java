package com.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.model.Country;

@Repository
public interface CountryRepository extends JpaRepository<Country, Long>{

	Country findByCodeAndName(String code, String name);

}
