package com.project.Rentingaccommodation.service.impl;

import java.util.List;

import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.project.Rentingaccommodation.model.Admin;
import com.project.Rentingaccommodation.model.User;
import com.project.Rentingaccommodation.repository.AdminRepository;
import com.project.Rentingaccommodation.service.AdminService;

@Transactional
@Service
public class JpaAdminService implements AdminService {

	@Autowired
	private AdminRepository repository;
	
	@Override
	public Admin findOne(Long id) {
		for (Admin admin : repository.findAll()) {
			if (admin.getId() == id) {
				return admin;
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
	public void delete(Admin admin) {
		repository.delete(admin);
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

	@Override
	public User activateUser(User user) {
		return user;
	}

	@Override
	public User deactivateUser(User user) {
		return null;
	}

	@Override
	public Admin findByIdAndEmail(Long id, String email) {
		for (Admin a : repository.findAll()) {
			if (a.getId() == id && a.getEmail().equals(email)) {
				return a;
			}
		}
		return null;
	}
}
