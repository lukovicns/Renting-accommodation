package com.project.Rentingaccommodation.service;

import java.util.List;

import com.project.Rentingaccommodation.model.Image;

public interface ImageService {

	List<Image> findAll();
	List<Image> findAccommodationImages(Long id);
	List<Image> findApartmentImages(Long id);
	Image findFirstAccommodationImage(Long id);
	List<Image> findOtherAccommodationImages(Long id);
	Image findFirstApartmentImage(Long id);
	List<Image> findOtherApartmentImages(Long id);
}
