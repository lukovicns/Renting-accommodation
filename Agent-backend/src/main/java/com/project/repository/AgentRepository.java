package com.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.model.Agent;

public interface AgentRepository extends JpaRepository<Agent, Long>{

	Agent findByEmail(String email);

}
