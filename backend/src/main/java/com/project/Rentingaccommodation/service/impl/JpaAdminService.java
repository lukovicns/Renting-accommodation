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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Admin> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Admin save(Admin admin) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Admin> save(List<Admin> admins) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Admin delete(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(List<Long> ids) {
		// TODO Auto-generated method stub
		
	}

}
