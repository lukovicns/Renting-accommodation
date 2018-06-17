package com.project.Rentingaccommodation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.Rentingaccommodation.model.AccommodationType;

import java.util.List;

@Repository
public interface AccommodationTypeRepository  extends JpaRepository<AccommodationType, Long> {

    public List<AccommodationType> findByIdIn(List<Long> ids);

}

