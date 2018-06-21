package com.project.Rentingaccommodation.service.impl;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.Rentingaccommodation.model.Admin;
import com.project.Rentingaccommodation.repository.AdminRepository;
import com.project.Rentingaccommodation.service.AdminService;

@Transactional
@Service
public class JpaAdminService implements AdminService {

	@Autowired
	private AdminRepository repository;
	
	@Override
	public Admin findOne(Long id) {
		return repository.getOne(id);
	}

	@Override
	public List<Admin> findAll() {
		return repository.findAll();
	}

	@Override
	public Admin save(Admin admin) {
		return repository.save(admin);
	}

	@Override
	public Admin delete(Long id) {
		return null;
	}

}
