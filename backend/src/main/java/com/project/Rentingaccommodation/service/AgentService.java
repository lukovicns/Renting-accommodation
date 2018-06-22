package com.project.Rentingaccommodation.service;

import java.util.List;

import com.project.Rentingaccommodation.model.Agent;

public interface AgentService {
	
	List<Agent> findAll();
	Agent findOne(Long id);
	Agent findByBusinessId(Long id);
	Agent findByEmail(String email);
	Agent findByIdAndEmail(Long id, String email);
	Agent save(Agent regAgent);
	List<Agent> findApprovedAgents();
	Agent findApprovedAgent(Agent agent);
	List<Agent> findWaitingAgents();
	Agent findWaitingAgent(Agent agent);
	List<Agent> findDeclinedAgents();
	Agent findDeclinedAgent(Agent agent);
}
