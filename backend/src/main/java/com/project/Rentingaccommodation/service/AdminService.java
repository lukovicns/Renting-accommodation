package com.project.Rentingaccommodation.service;

import java.util.List;
import com.project.Rentingaccommodation.model.Admin;

public interface AdminService {
	
	List<Admin> findAll();
	Admin findOne(Long id);
	Admin findByEmail(String email);
	Admin save(Admin admin);
	Admin delete(Long id);
}
