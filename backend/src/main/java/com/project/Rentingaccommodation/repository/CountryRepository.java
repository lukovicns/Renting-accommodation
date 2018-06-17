package com.project.Rentingaccommodation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.Rentingaccommodation.model.Country;

@Repository
public interface CountryRepository extends JpaRepository<Country, Long> {

}
