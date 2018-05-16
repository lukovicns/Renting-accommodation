package com.project.Rentingaccommodation.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.project.Rentingaccommodation.model.Agent;
import com.project.Rentingaccommodation.model.City;
import com.project.Rentingaccommodation.service.AgentService;
import com.project.Rentingaccommodation.service.CityService;
import com.project.Rentingaccommodation.service.CountryService;

@RestController
@RequestMapping(value="/api/agents")
public class AgentController {

	@Autowired
	private AgentService service;
	
	@RequestMapping(value="", method=RequestMethod.GET)
	public ResponseEntity<List<Agent>> getAgents() {
		List<Agent> agents = service.findAll();
		if (agents == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(agents, HttpStatus.OK);
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<Agent> getAgent(@PathVariable Long id) {
		Agent agent = service.findOne(id);
		if (agent == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(agent, HttpStatus.OK);
	}
	
	@RequestMapping(value="", method=RequestMethod.POST)
	public ResponseEntity<Agent> addAgent(@RequestBody Agent agent) {
		Agent foundAgent = service.findOne(agent.getId());
		if (foundAgent != null) {
			return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
		}
		service.save(agent);
		return new ResponseEntity<>(agent, HttpStatus.OK);
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.PUT)
	public ResponseEntity<Agent> updateAgent(@PathVariable Long id, @RequestBody Agent agent) {
		Agent foundAgent = service.findOne(id);
		if (foundAgent != null) {
			foundAgent = agent;
			service.save(foundAgent);
			return new ResponseEntity<>(foundAgent, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<Agent> deleteAgent(@PathVariable Long id) {
		Agent agent = service.delete(id);
		if (agent == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(agent, HttpStatus.OK);
	}
}
