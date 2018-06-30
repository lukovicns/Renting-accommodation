package com.project.Rentingaccommodation.controller;

import java.io.IOException;
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

import com.project.Rentingaccommodation.logger.AccommodationCategoryLogger;
import com.project.Rentingaccommodation.model.AccommodationCategory;
import com.project.Rentingaccommodation.model.AccommodationCategoryStatus;
import com.project.Rentingaccommodation.model.UserPrivileges;
import com.project.Rentingaccommodation.model.UserRoles;
import com.project.Rentingaccommodation.security.JwtUserPermissions;
import com.project.Rentingaccommodation.service.AccommodationCategoryService;

@RestController
@RequestMapping(value = "/api/categories")
public class AccommodationCategoryController {

	@Autowired
	private AccommodationCategoryService service;
	
	@Autowired
	private JwtUserPermissions jwtUserPermissions;
	
	@RequestMapping(value="", method=RequestMethod.GET)
	public ResponseEntity<List<AccommodationCategory>> getCategories() {
		return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
	}
	
	@RequestMapping(value="/active", method=RequestMethod.GET)
	public ResponseEntity<List<AccommodationCategory>> getActiveCategories() {
		return new ResponseEntity<>(service.findActiveCategories(), HttpStatus.OK);
	}
	
	@RequestMapping(value="/inactive", method=RequestMethod.GET)
	public ResponseEntity<List<AccommodationCategory>> getInctiveCategories() {
		return new ResponseEntity<>(service.findInactiveCategories(), HttpStatus.OK);
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<Object> getCategory(@PathVariable Long id) {
		AccommodationCategory category = service.findOne(id);
		if (category == null) {
			return new ResponseEntity<>("Accommodation category not found.", HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(category, HttpStatus.OK);
	}
	
	@RequestMapping(value="", method=RequestMethod.POST)
	public ResponseEntity<Object> addAccommodationCategory(@RequestBody AccommodationCategory data, @RequestHeader(value="Authorization") String token) throws SecurityException, IOException {
		if(!jwtUserPermissions.hasRoleAndPrivilege(token, UserRoles.ADMIN, UserPrivileges.WRITE_PRIVILEGE)) {
			return new ResponseEntity<>("Not authorized", HttpStatus.UNAUTHORIZED);
		}
		if (data.getName() == null || data.getName() == "") {
			return new ResponseEntity<>("Name field is required.", HttpStatus.FORBIDDEN);
		}
		AccommodationCategory c = service.findByCategoryName(data.getName());
		if (c != null) {
			AccommodationCategoryLogger.log(Level.WARNING, "Tried to add accommodation category, but another accommodation category with this name already exists.");
			return new ResponseEntity<>("Category with this name already exists.", HttpStatus.FORBIDDEN);
		}
		AccommodationCategory category = new AccommodationCategory(data.getName(), AccommodationCategoryStatus.ACTIVE);
		AccommodationCategoryLogger.log(Level.INFO, "Accommodation category '" + category.getName() + "' is successfully added.");
		return new ResponseEntity<>(service.save(category), HttpStatus.OK);
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.PUT)
	public ResponseEntity<Object> updateAccommodationCategory(@PathVariable Long id, @RequestBody AccommodationCategory data, @RequestHeader(value="Authorization") String token) throws SecurityException, IOException {
		if(!jwtUserPermissions.hasRoleAndPrivilege(token, UserRoles.ADMIN, UserPrivileges.WRITE_PRIVILEGE)) {
			return new ResponseEntity<>("Not authorized", HttpStatus.UNAUTHORIZED);
		}
		AccommodationCategory category = service.findOne(id);
		if (category == null) {
			return new ResponseEntity<>("Category not found.", HttpStatus.NOT_FOUND);
		}
		if (data.getName() == null || data.getName() == "") {
			return new ResponseEntity<>("Name field is required.", HttpStatus.FORBIDDEN);
		}
		AccommodationCategory c = service.findByCategoryName(data.getName());
		if (c != null && c.getId() != category.getId()) {
			AccommodationCategoryLogger.log(Level.WARNING, "Tried to edit accommodation category, but another category with this name already exists.");
			return new ResponseEntity<>("Another category with this name already exists.", HttpStatus.FORBIDDEN);
		}
		category.setName(data.getName());
		AccommodationCategoryLogger.log(Level.INFO, "Accommodation category '" + category.getName() + "' is successfully updated.");
		return new ResponseEntity<>(service.save(category), HttpStatus.OK);
	}
	
	@RequestMapping(value="/{id}/activate", method=RequestMethod.PUT)
	public ResponseEntity<Object> activateCategory(@PathVariable Long id, @RequestHeader(value="Authorization") String token) throws SecurityException, IOException {
		if(!jwtUserPermissions.hasRoleAndPrivilege(token, UserRoles.ADMIN, UserPrivileges.WRITE_PRIVILEGE)) {
			return new ResponseEntity<>("Not authorized", HttpStatus.UNAUTHORIZED);
		}
		AccommodationCategory category = service.findOne(id);
		if (category == null) {
			return new ResponseEntity<>("Category not found.", HttpStatus.NOT_FOUND);
		}
		category.setStatus(AccommodationCategoryStatus.ACTIVE);
		AccommodationCategoryLogger.log(Level.INFO, "Accommodation category '" + category.getName() + "' is activated.");
		return new ResponseEntity<>(service.save(category), HttpStatus.OK);
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<Object> delete(@PathVariable Long id, @RequestHeader(value="Authorization") String token) throws SecurityException, IOException {
		if(!jwtUserPermissions.hasRoleAndPrivilege(token, UserRoles.ADMIN, UserPrivileges.WRITE_PRIVILEGE)) {
			return new ResponseEntity<>("Not authorized", HttpStatus.UNAUTHORIZED);
		}
		AccommodationCategory category = service.findOne(id);
		if (category == null) {
			return new ResponseEntity<>("Category not found.", HttpStatus.NOT_FOUND);
		}
		category.setStatus(AccommodationCategoryStatus.INACTIVE);
		AccommodationCategoryLogger.log(Level.INFO, "Accommodation category '" + category.getName() + "' is deactivated.");
		return new ResponseEntity<>(service.save(category), HttpStatus.OK);
	}
}
