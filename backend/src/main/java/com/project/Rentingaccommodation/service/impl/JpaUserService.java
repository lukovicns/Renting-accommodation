package com.project.Rentingaccommodation.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.Rentingaccommodation.model.User;
import com.project.Rentingaccommodation.repository.UserRepository;
import com.project.Rentingaccommodation.service.UserService;

@Transactional
@Service
public class JpaUserService implements UserService {

	@Autowired
	private UserRepository repository;
	
	@Override
	public User findOne(Long id) {
		for (User u : repository.findAll()) {
			if (u.getId() == id) {
				return u;
			}
		}
		return null;
	}

	@Override
	public List<User> findAll() {
		return repository.findAll();
	}

	@Override
	public User save(User user) {
		return repository.save(user);
	}

	@Override
	public List<User> save(List<User> users) {
		return repository.saveAll(users);
	}

	@Override
	public User delete(Long id) {
		User user = findOne(id);
		if (user != null) {
			repository.delete(user);
			return user;	
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
	public User findByEmail(String email) {
		for (User u : repository.findAll()) {
			if (u.getEmail().equals(email)) {
				return u;
			}
		}
		return null;
	}
}
