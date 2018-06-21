package com.project.Rentingaccommodation.service;

import java.util.List;

import com.project.Rentingaccommodation.model.Agent;

public interface AgentService {
	Agent findOne(Long id);
	List<Agent> findAll();
	Agent save(Agent Agent);
	void delete(Long id);
	Agent findByEmail(String email);
}
