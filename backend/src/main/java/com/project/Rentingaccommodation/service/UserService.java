package com.project.Rentingaccommodation.service;

import java.util.List;

import com.project.Rentingaccommodation.model.User;

public interface UserService {
	User findOne(Long id);
	List<User> findAll();
	User save(User user);
	void delete(User user);
	User findByEmail(String email);
	User findByIdAndEmail(Long id, String email);
}
