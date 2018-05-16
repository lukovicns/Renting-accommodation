package com.project.Rentingaccommodation.service;

import java.util.List;

import com.project.Rentingaccommodation.model.User;

public interface UserService {
	User findOne(Long id);
	List<User> findAll();
	User save(User user);
	List<User> save(List<User> users);
	User delete(Long id);
	void delete(List<Long> ids);
}
