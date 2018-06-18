package com.project.Rentingaccommodation.service;

import java.util.List;

import com.project.Rentingaccommodation.model.Agent;

public interface AgentService {
	
	List<Agent> findAll();
	Agent findOne(Long id);
	Agent findByBusinessId(Long id);
}
