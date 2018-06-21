package com.project.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.project.model.Agent;
import com.project.repository.AgentRepository;
import com.project.service.AgentService;

@Service
@Transactional
public class JpaAgentService implements AgentService{

	private static AgentRepository repository;
	
	public JpaAgentService(AgentRepository repository) {
		if(repository != null)
			JpaAgentService.repository = repository;
	}
	
	@Override
	public Agent findOne(Long id) {
		// TODO Auto-generated method stub
		return repository.getOne(id);
	}

	@Override
	public List<Agent> findAll() {
		// TODO Auto-generated method stub
		return repository.findAll();
	}

	@Override
	public Agent findByEmail(String email) {
		// TODO Auto-generated method stub
		return repository.findByEmail(email);
	}

}
