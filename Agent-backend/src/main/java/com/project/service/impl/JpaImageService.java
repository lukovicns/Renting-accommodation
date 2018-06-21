package com.project.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.project.model.Image;
import com.project.repository.ImageRepository;
import com.project.service.ImageService;

@Transactional
@Service
public class JpaImageService implements ImageService{
	
	private static ImageRepository repository;
	
	public JpaImageService(ImageRepository repository)
	{
		if(repository != null)
			JpaImageService.repository = repository;
	}

	@Override
	public Image findOne(Long id) {
		// TODO Auto-generated method stub
		return repository.getOne(id);
	}

	@Override
	public List<Image> findAll() {
		// TODO Auto-generated method stub
		return repository.findAll();
	}

	@Override
	public Image save(Image image) {
		// TODO Auto-generated method stub
		return repository.save(image);
	}

	@Override
	public List<Image> save(List<Image> images) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		repository.deleteById(id); 
	}

	@Override
	public void delete(List<Long> ids) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Image> findByAccommodationId(Long valueOf) {
		// TODO Auto-generated method stub
		return repository.findByAccommodationId(valueOf);
	}

	@Override
	public List<Image> findByApartmentId(Long id) {
		// TODO Auto-generated method stub
		return repository.findByApartmentId(id);
	}

}
