
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
	public void delete(User user) {
		repository.delete(user);
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