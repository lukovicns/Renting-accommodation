package com.project.Rentingaccommodation.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.Rentingaccommodation.model.Agent;
import com.project.Rentingaccommodation.model.AgentStatus;
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
		return repository.getOne(id);
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

	@Override
	public Agent findByEmail(String email) {
		return repository.findByEmail(email);
	}

	@Override
	public Agent save(Agent regAgent) {
		return repository.save(regAgent);
	}

	@Override
	public List<Agent> findApprovedAgents() {
		List<Agent> approvedAgents = new ArrayList<Agent>();
		for (Agent agent : findAll()) {
			if (agent.getStatus().equals(AgentStatus.APPROVED)) {
				approvedAgents.add(agent);
			}
		}
		return approvedAgents;
	}

	@Override
	public Agent findApprovedAgent(Agent agent) {
		for (Agent a : findApprovedAgents()) {
			if (agent.getId() == agent.getId()) {
				return a;
			}
		}
		return null;
	}

	@Override
	public List<Agent> findWaitingAgents() {
		List<Agent> waitingAgents = new ArrayList<Agent>();
		for (Agent agent : findAll()) {
			if (agent.getStatus().equals(AgentStatus.WAITING)) {
				waitingAgents.add(agent);
			}
		}
		return waitingAgents;
	}

	@Override
	public Agent findWaitingAgent(Agent agent) {
		for (Agent a : findWaitingAgents()) {
			if (agent.getId() == agent.getId()) {
				return a;
			}
		}
		return null;
	}

	@Override
	public List<Agent> findDeclinedAgents() {
		List<Agent> declinedAgents = new ArrayList<Agent>();
		for (Agent agent : findAll()) {
			if (agent.getStatus().equals(AgentStatus.DECLINED)) {
				declinedAgents.add(agent);
			}
		}
		return declinedAgents;
	}

	@Override
	public Agent findDeclinedAgent(Agent agent) {
		for (Agent a : findDeclinedAgents()) {
			if (agent.getId() == agent.getId()) {
				return a;
			}
		}
		return null;
	}

	@Override
	public Agent findByIdAndEmail(Long id, String email) {
		for (Agent agent : findAll()) {
			if (agent.getId() == id && agent.getEmail().equals(email)) {
				return agent;
			}
		}
		return null;
	}
}
