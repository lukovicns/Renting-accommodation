package com.project.Rentingaccommodation.controller;

import java.nio.charset.Charset;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.project.Rentingaccommodation.model.Admin;
import com.project.Rentingaccommodation.service.AdminService;
import com.project.Rentingaccommodation.service.UserService;
import com.project.Rentingaccommodation.utils.PasswordUtil;
import com.project.Rentingaccommodation.utils.UserUtils;

@RestController
@RequestMapping(value="api/admins")
public class AdminController {
	
	@Autowired
	private AdminService adminService;
	
	@Autowired
	private UserService userService;

	@RequestMapping(value="", method=RequestMethod.GET)
	public ResponseEntity<List<Admin>> getAdmins() {
		return new ResponseEntity<>(adminService.findAll(), HttpStatus.OK);
	}
	
	@RequestMapping(value = "", method = RequestMethod.POST)
	public ResponseEntity<Object> addAdmin(@RequestBody Admin data, @RequestHeader String authHeader) {
		if (data.getName() == null || data.getName() == "" ||
			data.getSurname() == null || data.getSurname() == "" ||
			data.getEmail() == null || data.getEmail() == "" ||
			data.getPassword() == null || data.getPassword() == "") {
			return new ResponseEntity<>("All fields are required (name, surname, email, password).", HttpStatus.FORBIDDEN);
		}
		
		if (UserUtils.userExists(data.getEmail(), userService, adminService)) {
			return new ResponseEntity<>("User with this email already exists.", HttpStatus.FORBIDDEN);
		}
		
		if (data.getPassword().length() < 8) {
			return new ResponseEntity<>("Password must be at least 8 characters long.", HttpStatus.NOT_ACCEPTABLE);
		}
		String password = PasswordUtil.hash(data.getPassword().toCharArray(), Charset.forName("UTF-8"));
		Admin admin = new Admin(data.getName(), data.getSurname(), data.getEmail(), password);
		adminService.save(admin);
		return new ResponseEntity<>(admin, HttpStatus.OK);
	}
}
