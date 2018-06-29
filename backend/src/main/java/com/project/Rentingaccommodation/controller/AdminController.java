package com.project.Rentingaccommodation.controller;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.project.Rentingaccommodation.logger.AdminLogger;
import com.project.Rentingaccommodation.model.Admin;
import com.project.Rentingaccommodation.model.AdminStatus;
import com.project.Rentingaccommodation.model.User;
import com.project.Rentingaccommodation.model.UserPrivileges;
import com.project.Rentingaccommodation.model.UserStatus;
import com.project.Rentingaccommodation.model.DTO.PasswordChangeDTO;
import com.project.Rentingaccommodation.model.DTO.SecurityQuestionDTO;
import com.project.Rentingaccommodation.security.JwtGenerator;
import com.project.Rentingaccommodation.security.JwtUser;
import com.project.Rentingaccommodation.security.JwtValidator;
import com.project.Rentingaccommodation.service.AdminService;
import com.project.Rentingaccommodation.service.UserService;
import com.project.Rentingaccommodation.utils.SendMail;

@RestController
@RequestMapping(value = "/api/admins")
public class AdminController {

	@Autowired
	private AdminService service;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private JwtGenerator jwtGenerator;
	
	@Autowired
	private JwtValidator jwtValidator;
	
	@RequestMapping(value="/email/{email}", method=RequestMethod.GET)
	public ResponseEntity<Object> getAdminByEmail(@PathVariable String email) {
		Admin admin = service.findByEmail(email);
		if (admin == null) {
			return new ResponseEntity<>("Admin not found.", HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(admin, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/login", method=RequestMethod.POST)
	public ResponseEntity<Object> loginAdmin(@RequestBody Admin data) {
		if (data.getEmail() == null || data.getEmail() == "" ||
				data.getPassword() == null || data.getPassword() == "") {
			return new ResponseEntity<>("Email and password not provided.", HttpStatus.NOT_ACCEPTABLE);
		}
		Admin admin = service.findByEmail(data.getEmail());
		
		if (admin == null) {
			return new ResponseEntity<>("Admin not found.", HttpStatus.NOT_FOUND);
		}
		
		Pattern passwordPattern = Pattern.compile("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[A-Z])(.{10,})$");
		Matcher passwordMatcher = passwordPattern.matcher(admin.getPassword());
		
		if (!passwordMatcher.find()) {
			AdminLogger.log(Level.WARNING, "Invalid regex pattern for password provided.");
			return new ResponseEntity<>("Password must be one uppercase, one lowercase, one number and at least 10 characters long.", HttpStatus.NOT_ACCEPTABLE);
		}
		
		SimpleDateFormat dateTimeFormatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		
		if (admin.getMax_tries() == 3) {
			try {
				String dateTime = dateTimeFormatter.format(new Date());
				Date currentDateTime = dateTimeFormatter.parse(dateTime);
				Date userBlockDateTime = dateTimeFormatter.parse(admin.getBlock_time());
				if (currentDateTime.getTime() - userBlockDateTime.getTime() >= 1*60*1000) {
					admin.setBlock_time(null);
					admin.setMax_tries(0);
					admin.setStatus(AdminStatus.ACTIVATED);
				    service.save(admin);
				} else {
					AdminLogger.log(Level.WARNING, "Login failed because admin " + admin.getEmail() + " is blocked for 10 minutes.");
					return new ResponseEntity<>("Admin is blocked for 10 minutes.", HttpStatus.FORBIDDEN);
				}
			} catch (java.text.ParseException e) {
				e.printStackTrace();
			}
		}
		
		if (admin.getStatus().equals(AdminStatus.BLOCKED)) {
			AdminLogger.log(Level.WARNING, "Login failed because admin " + admin.getEmail() + " is blocked.");
			return new ResponseEntity<>("This admin is blocked.", HttpStatus.FORBIDDEN);
		}
		
		if (!data.getPassword().equals(admin.getPassword())) {
			admin.setMax_tries(admin.getMax_tries() + 1);
			if (admin.getMax_tries() == 2) {
				admin.setStatus(AdminStatus.BLOCKED);
				admin.setMax_tries(3);
				admin.setBlock_time(dateTimeFormatter.format(new Date()));
				service.save(admin);
			}
			service.save(admin);
			AdminLogger.log(Level.WARNING, "Login failed because given password is invalid.");
			return new ResponseEntity<>("Password is invalid.", HttpStatus.FORBIDDEN);
		}
		
		admin.setMax_tries(0);

		String token = jwtGenerator.generateAdmin(new JwtUser(admin.getId(), admin.getEmail(), admin.getPassword(), admin.getStatus().toString(), UserPrivileges.READ_WRITE_PRIVILEGE.toString()));

		HashMap<String, Object> response = new HashMap<String, Object>();
		response.put("token", token);
		AdminLogger.log(Level.INFO, "Admin " + admin.getEmail() + " is successfully logged in.");
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/change", method = RequestMethod.POST)
	public ResponseEntity<Object> changePassword(@RequestBody PasswordChangeDTO passDTO) {
		if (passDTO.getOldPassword() == null || passDTO.getOldPassword() == "" ||
			passDTO.getNewPassword() == null || passDTO.getNewPassword() == "" ||
			passDTO.getToken() == null || passDTO.getToken() == "") {
			return new ResponseEntity<>("Old password, new password and token must be provided.", HttpStatus.FORBIDDEN);
		}
		
		Pattern passwordPattern = Pattern.compile("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[A-Z])(.{10,})$");
		Matcher oldPasswordMatcher = passwordPattern.matcher(passDTO.getOldPassword());
		Matcher newPasswordMatcher = passwordPattern.matcher(passDTO.getNewPassword());
		
		if (!oldPasswordMatcher.find()) {
			AdminLogger.log(Level.WARNING, "Invalid regex pattern for old password provided.");
			return new ResponseEntity<>("Old password must be one uppercase, one lowercase, one number and at least 10 characters long.", HttpStatus.NOT_ACCEPTABLE);
		}
		
		if (!newPasswordMatcher.find()) {
			AdminLogger.log(Level.WARNING, "Invalid regex pattern for new password provided.");
			return new ResponseEntity<>("New password must be one uppercase, one lowercase, one number and at least 10 characters long.", HttpStatus.NOT_ACCEPTABLE);
		}
		
		String oldPassword = passDTO.getOldPassword();
		String newPassword = passDTO.getNewPassword();
		
		SimpleDateFormat dateTimeFormatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		JwtUser jwtUser = jwtValidator.validate(passDTO.getToken());
		
		if (jwtUser == null) {
			return new ResponseEntity<>("User with the given token doesn't exist.", HttpStatus.NOT_FOUND);
		}

		Admin loggedInAdmin =  service.findByIdAndEmail(jwtUser.getId(), jwtUser.getEmail());
		
		if (oldPassword.equals(newPassword)) {
			loggedInAdmin.setMax_tries(loggedInAdmin.getMax_tries() + 1);
			if (loggedInAdmin.getMax_tries() == 3) {
				loggedInAdmin.setStatus(AdminStatus.BLOCKED);
				loggedInAdmin.setBlock_time(dateTimeFormatter.format(new Date()));
				loggedInAdmin.setMax_tries(3);
				service.save(loggedInAdmin);
				return new ResponseEntity<>(loggedInAdmin, HttpStatus.FORBIDDEN);
			}
			service.save(loggedInAdmin);
			AdminLogger.log(Level.WARNING, "Provided passwords must be different for successful password change.");
			return new ResponseEntity<>("Old password and new password must be different.", HttpStatus.NOT_ACCEPTABLE);
		}
		
		
		if (loggedInAdmin.getMax_tries() == 3) {
			try {
				String dateTime = dateTimeFormatter.format(new Date());
				Date currentDateTime = dateTimeFormatter.parse(dateTime);
				Date userBlockDateTime = dateTimeFormatter.parse(loggedInAdmin.getBlock_time());
				if (currentDateTime.getTime() - userBlockDateTime.getTime() >= 1*60*1000) {
					loggedInAdmin.setBlock_time(null);
					loggedInAdmin.setStatus(AdminStatus.ACTIVATED);
					loggedInAdmin.setMax_tries(0);
				} else {
					AdminLogger.log(Level.WARNING, "Admin " + loggedInAdmin.getEmail() + " doesn't have permissions, because he is blocked for 10 minutes.");
					return new ResponseEntity<>("This admin is blocked for 10 minutes.", HttpStatus.FORBIDDEN);
				}
			} catch (java.text.ParseException e) {
				e.printStackTrace();
			}
		}
		
		if(!oldPassword.equals(loggedInAdmin.getPassword())) {
			loggedInAdmin.setMax_tries(loggedInAdmin.getMax_tries() + 1);
			if (loggedInAdmin.getMax_tries() == 3) {
				loggedInAdmin.setStatus(AdminStatus.BLOCKED);
				loggedInAdmin.setBlock_time(dateTimeFormatter.format(new Date()));
				loggedInAdmin.setMax_tries(3);
				service.save(loggedInAdmin);
				return new ResponseEntity<>(loggedInAdmin, HttpStatus.FORBIDDEN);
			}
			service.save(loggedInAdmin);
			AdminLogger.log(Level.WARNING, "Unsuccessful password change, because old password is invalid.");
			return new ResponseEntity<>("Old password is incorrect.", HttpStatus.FORBIDDEN);
		}

		loggedInAdmin.setStatus(AdminStatus.ACTIVATED);
		loggedInAdmin.setPassword(newPassword);
		service.save(loggedInAdmin);
		AdminLogger.log(Level.INFO, "User changed password successfully.");
		return new ResponseEntity<>(jwtUser, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/question/{email}", method = RequestMethod.GET)
	public ResponseEntity<Object> getQuestion(@PathVariable String email) throws ParseException {
		if (email == null || email == "") {
			return new ResponseEntity<>("Email address is required.", HttpStatus.FORBIDDEN);
		}
		
		Admin admin = service.findByEmail(email);
		if (admin == null) {
			return new ResponseEntity<>(new String("This admin doesn't exist."), HttpStatus.NOT_FOUND);
		}
		
		String question = admin.getQuestion();
		String jsonString = "{\"question\":\""+question+"\"}";
		JSONParser parser = new JSONParser(); 
		JSONObject json = (JSONObject) parser.parse(jsonString);
		return new ResponseEntity<>(json, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/reset", method = RequestMethod.POST)
	public ResponseEntity<Object> resetPassword(@RequestBody SecurityQuestionDTO questionDTO) throws ParseException {
		if (questionDTO.getEmail() == null || questionDTO.getEmail() == "" ||
			questionDTO.getAnswer() == null || questionDTO.getAnswer() == "") {
			return new ResponseEntity<>("Email and answer must be provided.", HttpStatus.FORBIDDEN);
		}
		String email = questionDTO.getEmail();
		String answer = questionDTO.getAnswer();
		Admin admin = service.findByEmail(email);
		
		SimpleDateFormat dateTimeFormatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		
		if (admin.getMax_tries() == 3) {
			try {
				String dateTime = dateTimeFormatter.format(new Date());
				Date currentDateTime = dateTimeFormatter.parse(dateTime);
				Date userBlockDateTime = dateTimeFormatter.parse(admin.getBlock_time());
				if (currentDateTime.getTime() - userBlockDateTime.getTime() >= 1*60*1000) {
					admin.setBlock_time(null);
					admin.setStatus(AdminStatus.ACTIVATED);
					admin.setMax_tries(0);
				} else {
					AdminLogger.log(Level.WARNING, "Reset password failed because admin is blocked for 10 minutes.");
					return new ResponseEntity<>("This admin is blocked for 10 minutes.", HttpStatus.FORBIDDEN);
				}
			} catch (java.text.ParseException e) {
				e.printStackTrace();
			}
		}
		
		if(!answer.equals(admin.getAnswer())) {
			admin.setMax_tries(admin.getMax_tries() + 1);
			if (admin.getMax_tries() == 3) {
				admin.setStatus(AdminStatus.BLOCKED);
				admin.setBlock_time(dateTimeFormatter.format(new Date()));
				admin.setMax_tries(3);
				service.save(admin);
				return new ResponseEntity<>(admin, HttpStatus.FORBIDDEN);
			}
			service.save(admin);
			return new ResponseEntity<>("Answer is incorrect.", HttpStatus.NOT_ACCEPTABLE);
		}
		
		String randomPassword = SendMail.sendEmail("\""+email+"\"");
		
		admin.setPassword(randomPassword);
		service.save(admin);
		return new ResponseEntity<>(admin, HttpStatus.OK);
	}
	
	@RequestMapping(value="/activate-user/{id}", method=RequestMethod.PUT)
	public ResponseEntity<Object> activateUser(@PathVariable Long id) {
		User user = userService.findOne(id);
		if (user == null) {
			return new ResponseEntity<>("User not found.", HttpStatus.NOT_FOUND);
		}
		user.setStatus(UserStatus.ACTIVATED);
		AdminLogger.log(Level.INFO, "User " + user.getEmail() + " is activated.");
		return new ResponseEntity<>(userService.save(user), HttpStatus.OK);
	}
	
	@RequestMapping(value="/block-user/{id}", method=RequestMethod.PUT)
	public ResponseEntity<Object> blockUser(@PathVariable Long id) {
		User user = userService.findOne(id);
		if (user == null) {
			return new ResponseEntity<>("User not found.", HttpStatus.NOT_FOUND);
		}
		user.setStatus(UserStatus.BLOCKED);
		AdminLogger.log(Level.INFO, "User " + user.getEmail() + " is blocked.");
		return new ResponseEntity<>(userService.save(user), HttpStatus.OK);
	}
	
	@RequestMapping(value="/delete-user/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<Object> deleteUser(@PathVariable Long id) {
		User user = userService.findOne(id);
		if (user == null) {
			return new ResponseEntity<>("User not found.", HttpStatus.NOT_FOUND);
		}
		userService.delete(user);
		AdminLogger.log(Level.INFO, "User " + user.getEmail() + " is deleted.");
		return new ResponseEntity<>(user, HttpStatus.OK);
	}
}
