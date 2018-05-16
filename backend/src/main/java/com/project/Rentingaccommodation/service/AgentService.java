package com.project.Rentingaccommodation.service;

import java.util.List;

import com.project.Rentingaccommodation.model.Agent;

public interface AgentService {
	
	Agent findOne(Long id);
	List<Agent> findAll();
	Agent save(Agent agent);
	List<Agent> save(List<Agent> agents);
	Agent delete(Long id);
	void delete(List<Long> ids);
}
