package com.project.Rentingaccommodation.service;

import java.util.List;
import com.project.Rentingaccommodation.model.Admin;

public interface AdminService {
	
	Admin findOne(Long id);
	List<Admin> findAll();
	Admin save(Admin admin);
	List<Admin> save(List<Admin> admins);
	Admin delete(Long id);
	void delete(List<Long> ids);
}
