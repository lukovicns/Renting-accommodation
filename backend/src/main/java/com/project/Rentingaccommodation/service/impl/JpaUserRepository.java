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
public class JpaUserRepository implements UserService {

	@Autowired
	private UserRepository repository;
	
	@Override
	public User findOne(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<User> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User save(User user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<User> save(List<User> users) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User delete(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(List<Long> ids) {
		// TODO Auto-generated method stub
		
	}

}
