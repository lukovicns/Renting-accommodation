package com.project.Rentingaccommodation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.Rentingaccommodation.model.Agent;

@Repository
public interface AgentRepository extends JpaRepository<Agent, Long> {

	Agent findByEmail(String email);

}
