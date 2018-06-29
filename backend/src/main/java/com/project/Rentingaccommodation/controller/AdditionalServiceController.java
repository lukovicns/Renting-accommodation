package com.project.Rentingaccommodation.controller;

import java.util.List;
import java.util.logging.Level;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.project.Rentingaccommodation.logger.AdditionalServiceLogger;
import com.project.Rentingaccommodation.model.AdditionalService;
import com.project.Rentingaccommodation.model.UserPrivileges;
import com.project.Rentingaccommodation.model.UserRoles;
import com.project.Rentingaccommodation.security.JwtUserPermissions;
import com.project.Rentingaccommodation.service.AdditionalServiceService;

@RestController
@RequestMapping(value = "/api/additional-services")
public class AdditionalServiceController {

	@Autowired
	private AdditionalServiceService service;
	
	@Autowired
	private JwtUserPermissions jwtUserPermissions;
	
	@RequestMapping(value="", method=RequestMethod.GET)
	public ResponseEntity<List<AdditionalService>> getAdditionalServices() {
		return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<Object> getAdditionalService(@PathVariable Long id) {
		AdditionalService additionalService = service.findOne(id);
		if (additionalService == null) {
			return new ResponseEntity<>("Additional service not found.", HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(additionalService, HttpStatus.OK);
	}
	
	@RequestMapping(value="", method=RequestMethod.POST)
	public ResponseEntity<Object> addAdditionalService(@RequestBody AdditionalService data, @RequestHeader(value="Authorization") String token) {
		if(!jwtUserPermissions.hasRoleAndPrivilege(token, UserRoles.ADMIN, UserPrivileges.WRITE_PRIVILEGE)) {
			return new ResponseEntity<>("Not authorized", HttpStatus.UNAUTHORIZED);
		}
		if (data.getName() == null || data.getName() == "") {
			return new ResponseEntity<>("Name field is required.", HttpStatus.FORBIDDEN);
		}
		AdditionalService foundAdditionalService = service.findByName(data.getName());
		if (foundAdditionalService != null) {
			AdditionalServiceLogger.log(Level.WARNING, "Tried to add additional service, but another additional service with this name already exists.");
			return new ResponseEntity<>("Additional service with this name already exists.", HttpStatus.FORBIDDEN);
		}
		AdditionalService additionalService = new AdditionalService(data.getName());
		AdditionalServiceLogger.log(Level.INFO, "Additional service '" + additionalService.getName() + "' is successfully added.");
		return new ResponseEntity<>(service.save(additionalService), HttpStatus.OK);
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.PUT)
	public ResponseEntity<Object> updateAdditionalService(@PathVariable Long id, @RequestBody AdditionalService data, @RequestHeader(value="Authorization") String token) {
		if(!jwtUserPermissions.hasRoleAndPrivilege(token, UserRoles.ADMIN, UserPrivileges.WRITE_PRIVILEGE)) {
			return new ResponseEntity<>("Not authorized", HttpStatus.UNAUTHORIZED);
		}
		AdditionalService additionalService = service.findOne(id);
		if (additionalService == null) {
			return new ResponseEntity<>("Additional service not found.", HttpStatus.NOT_FOUND);
		}
		if (data.getName() == null || data.getName() == "") {
			return new ResponseEntity<>("Name field is required.", HttpStatus.FORBIDDEN);
		}
		AdditionalService as = service.findByName(data.getName());
		if (as != null && as.getId() != additionalService.getId()) {
			AdditionalServiceLogger.log(Level.WARNING, "Tried to update additional service, but another additional service with this name already exists.");
			return new ResponseEntity<>("Another additional service with this name already exists.", HttpStatus.FORBIDDEN);
		}
		additionalService.setName(data.getName());
		AdditionalServiceLogger.log(Level.INFO, "Additional service '" + additionalService.getName() +  "' is successfully updated.");
		return new ResponseEntity<>(service.save(additionalService), HttpStatus.OK);
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<Object> deleteAdditionalService(@PathVariable Long id, @RequestHeader(value="Authorization") String token) {
		if(!jwtUserPermissions.hasRoleAndPrivilege(token, UserRoles.ADMIN, UserPrivileges.WRITE_PRIVILEGE)) {
			return new ResponseEntity<>("Not authorized", HttpStatus.UNAUTHORIZED);
		}
		AdditionalService additionalService = service.findOne(id);
		if (additionalService == null) {
			return new ResponseEntity<>("Additional service not found.", HttpStatus.NOT_FOUND);
		}
		service.delete(additionalService);
		AdditionalServiceLogger.log(Level.INFO, "Additional service '" + additionalService.getName() + "' is successfully deleted.");
		return new ResponseEntity<>(additionalService, HttpStatus.OK);
	}	
}
