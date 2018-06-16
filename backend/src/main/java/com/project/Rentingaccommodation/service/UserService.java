package com.project.Rentingaccommodation.service;

import java.util.List;

import com.project.Rentingaccommodation.model.User;

public interface UserService {
	User findOne(Integer id);
	List<User> findAll();
	User save(User user);
	List<User> save(List<User> users);
	User delete(Integer id);
	void delete(List<Integer> ids);
	User findByEmail(String email);
}
