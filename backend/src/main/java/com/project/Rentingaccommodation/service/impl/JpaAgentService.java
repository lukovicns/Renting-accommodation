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
		for (Agent agent : repository.findAll()) {
			if (agent.getId() == id) {
				return agent;
			}
		}
		return null;
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
	public List<Agent> save(List<Agent> agents) {
		return repository.saveAll(agents);
	}

	@Override
	public Agent delete(Long id) {
		Agent agent = findOne(id);
		if (agent != null) {
			return agent;
		}
		return null;
	}

	@Override
	public void delete(List<Long> ids) {
		for (Long id : ids) {
			repository.deleteById(id);
		}
	}
}
