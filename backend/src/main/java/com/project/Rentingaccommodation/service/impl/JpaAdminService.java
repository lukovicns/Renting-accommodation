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
		for (Admin a : repository.findAll()) {
			if (a.getId() == id) {
				return a;
			}
		}
		return null;
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
	public List<Admin> save(List<Admin> admins) {
		return repository.saveAll(admins);
	}

	@Override
	public Admin delete(Long id) {
		Admin admin = findOne(id);
		if (admin != null) {
			repository.delete(admin);
			return admin;
		}
		return null;
	}

	@Override
	public void delete(List<Long> ids) {
		for (Long id : ids) {
			repository.deleteById(id);
		}
	}

	@Override
	public Admin findByEmail(String email) {
		for (Admin a : repository.findAll()) {
			if (a.getEmail().equals(email)) {
				return a;
			}
		}
		return null;
	}

}
