package com.project.Rentingaccommodation.service;

import java.util.List;
import com.project.Rentingaccommodation.model.Admin;
import com.project.Rentingaccommodation.model.User;

public interface AdminService {
	
	List<Admin> findAll();
	Admin findOne(Long id);
	Admin findByEmail(String email);
	Admin findByIdAndEmail(Long id, String email);
	Admin save(Admin admin);
	void delete(Admin admin);
	User activateUser(User user);
	User deactivateUser(User user);
}
