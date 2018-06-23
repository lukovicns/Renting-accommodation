package com.project.Rentingaccommodation.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.Rentingaccommodation.model.Image;
import com.project.Rentingaccommodation.repository.ImageRepository;
import com.project.Rentingaccommodation.service.ImageService;

@Transactional
@Service
public class JpaImageService implements ImageService {
	
	@Autowired
	private ImageRepository repository;
	
	@Override
	public List<Image> findAll() {
		return repository.findAll();
	}
	
	@Override
	public List<Image> findAccommodationImages(Long id) {
		List<Image> accommodationImages = new ArrayList<Image>();
		for (Image image : findAll()) {
			if (image.getAccommodation() != null) {
				if (image.getAccommodation().getId() == id) {
					accommodationImages.add(image);
				}
			}
		}
		return accommodationImages;
	}

	@Override
	public List<Image> findApartmentImages(Long id) {
		List<Image> apartmentImages = new ArrayList<Image>();
		for (Image image : findAll()) {
			if (image.getApartment() != null) {
				if (image.getApartment().getId() == id) {
					apartmentImages.add(image);
				}
			}
		}
		return apartmentImages;
	}

	@Override
	public Image findFirstAccommodationImage(Long id) {
		return findAccommodationImages(id).get(0);
	}

	@Override
	public List<Image> findOtherAccommodationImages(Long id) {
		List<Image> images = findAccommodationImages(id);
		List<Image> otherAccommodationImages = new ArrayList<Image>();
		for (int i = 0; i < images.size(); i++) {
			if (i != 0) {
				otherAccommodationImages.add(images.get(i));
			}
		}
		return otherAccommodationImages;
	}

	@Override
	public Image findFirstApartmentImage(Long id) {
		return findApartmentImages(id).get(0);
	}

	@Override
	public List<Image> findOtherApartmentImages(Long id) {
		List<Image> images = findApartmentImages(id);
		List<Image> otherApartmentImages = new ArrayList<Image>();
		for (int i = 0; i < images.size(); i++) {
			if (i != 0) {
				otherApartmentImages.add(images.get(i));
			}
		}
		return otherApartmentImages;
	}
}
