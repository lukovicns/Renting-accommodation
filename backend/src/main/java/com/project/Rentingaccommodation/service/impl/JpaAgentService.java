package com.project.Rentingaccommodation.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.Rentingaccommodation.model.Agent;
import com.project.Rentingaccommodation.repository.AgentRepository;
import com.project.Rentingaccommodation.service.AgentService;

@Transactional
@Service
public class JpaAgentService implements AgentService {

	@Autowired
	private AgentRepository repository;
	
	@Override
	public List<Agent> findAll() {
		return repository.findAll();
	}

	@Override
	public Agent findOne(Long id) {
		for (Agent agent : findAll()) {
			if (agent.getId() == id) {
				return agent;
			}
		}
		return null;
	}

	@Override
	public Agent findByBusinessId(Long id) {
		for (Agent agent : findAll()) {
			if (agent.getBusinessId() == id) {
				return agent;
			}
		}
		return null;
	}
}
