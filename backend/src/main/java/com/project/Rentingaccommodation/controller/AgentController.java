package com.project.Rentingaccommodation.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.project.Rentingaccommodation.model.Agent;
import com.project.Rentingaccommodation.service.AgentService;

@RestController
@RequestMapping(value = "/api/agents")
public class AgentController {

	@Autowired
	private AgentService service;
	
	@RequestMapping(value="", method=RequestMethod.GET)
	public ResponseEntity<List<Agent>> getAgents() {
		return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<Object> getAgent(@PathVariable Long id) {
		Agent agent = service.findOne(id);
		if (agent == null) {
			return new ResponseEntity<>("Agent not found.", HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(agent, HttpStatus.OK);	
	}
	
	@RequestMapping(value="/business-id/{businessId}", method=RequestMethod.GET)
	public ResponseEntity<Object> getAgentByBusinessId(@PathVariable Long businessId) {
		Agent agent = service.findByBusinessId(businessId);
		if (agent == null) {
			return new ResponseEntity<>("Agent not found.", HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(agent, HttpStatus.OK);	
	}
}
