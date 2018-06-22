package com.project.service;

import java.util.List;

import com.project.model.Agent;

public interface AgentService {
	
	Agent findOne(Long id);
	List<Agent> findAll();
	Agent findByEmail(String email);

}
