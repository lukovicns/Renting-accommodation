package com.project.Rentingaccommodation.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.Rentingaccommodation.model.Agent;
import com.project.Rentingaccommodation.repository.AgentRepository;
import com.project.Rentingaccommodation.service.AgentService;

@Transactional
@Service
public class JpaAgentService implements AgentService {

	@Autowired
	private AgentRepository repository;
	
	@Override
	public Agent findOne(Long id) {
		return repository.getOne(id);
	}

	@Override
	public List<Agent> findAll() {
		return repository.findAll();
	}

	@Override
	public Agent save(Agent agent) {
		return repository.save(agent);
	}

	@Override
	public void delete(Long id) {
		repository.deleteById(id);
	}

	@Override
	public Agent findByEmail(String email) {
		return repository.findByEmail(email);
	}
}
