package com.project.Rentingaccommodation.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
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

import com.project.Rentingaccommodation.logger.MessageLogger;
import com.project.Rentingaccommodation.model.Admin;
import com.project.Rentingaccommodation.model.Agent;
import com.project.Rentingaccommodation.model.Apartment;
import com.project.Rentingaccommodation.model.Direction;
import com.project.Rentingaccommodation.model.Message;
import com.project.Rentingaccommodation.model.MessageStatus;
import com.project.Rentingaccommodation.model.SendMessage;
import com.project.Rentingaccommodation.model.User;
import com.project.Rentingaccommodation.model.UserPrivileges;
import com.project.Rentingaccommodation.model.UserRoles;
import com.project.Rentingaccommodation.security.JwtUser;
import com.project.Rentingaccommodation.security.JwtUserPermissions;
import com.project.Rentingaccommodation.security.JwtValidator;
import com.project.Rentingaccommodation.service.AdminService;
import com.project.Rentingaccommodation.service.AgentService;
import com.project.Rentingaccommodation.service.ApartmentService;
import com.project.Rentingaccommodation.service.MessageService;
import com.project.Rentingaccommodation.service.UserService;

@RestController
@RequestMapping(value="/api/messages")
public class MessageController {

	@Autowired
	private MessageService service;
	
	@Autowired
	private ApartmentService apartmentService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private AgentService agentService;
	
	@Autowired
	private AdminService adminService;
	
	@Autowired
	private JwtValidator jwtValidator;
	
	@Autowired
	private JwtUserPermissions jwtUserPermissions;
	
	@RequestMapping(value="", method=RequestMethod.GET)
	public ResponseEntity<List<Message>> getMessages() {
		return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<Object> getMessage(@PathVariable Long id) {
		Message message = service.findOne(id);
		if (message == null) {
			return new ResponseEntity<>("Message not found.", HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(message, HttpStatus.OK);
	}
	
	@RequestMapping(value="/user/{id}/sent", method=RequestMethod.GET)
	public ResponseEntity<Object> getUserSentMessages(@PathVariable Long id, @RequestHeader(value="Authorization") String token) {
		if(!jwtUserPermissions.hasRoleAndPrivilege(token, UserRoles.USER, UserPrivileges.WRITE_PRIVILEGE)) {
			return new ResponseEntity<>("Not authorized", HttpStatus.UNAUTHORIZED);
		}
		User user = userService.findOne(id);
		if (user == null) {
			return new ResponseEntity<>("User not found.", HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(service.findUserSentMessages(user), HttpStatus.OK);
	}
	
	@RequestMapping(value="/user/{id}/sent/{messageId}", method=RequestMethod.GET)
	public ResponseEntity<Object> getUserSentMessage(@PathVariable Long id, @PathVariable Long messageId, @RequestHeader(value="Authorization") String token) {
		if(!jwtUserPermissions.hasRoleAndPrivilege(token, UserRoles.USER, UserPrivileges.WRITE_PRIVILEGE)) {
			return new ResponseEntity<>("Not authorized", HttpStatus.UNAUTHORIZED);
		}
		User user = userService.findOne(id);
		if (user == null) {
			return new ResponseEntity<>("User not found.", HttpStatus.NOT_FOUND);
		}
		Message message = service.findUserSentMessage(user, messageId);
		if (message == null) {
			return new ResponseEntity<>("Message not found.", HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(message, HttpStatus.OK);
	}
	
	@RequestMapping(value="/user/{id}/received", method=RequestMethod.GET)
	public ResponseEntity<Object> getUserReceivedMessages(@PathVariable Long id, @RequestHeader(value="Authorization") String token) {
		if(!jwtUserPermissions.hasRoleAndPrivilege(token, UserRoles.USER, UserPrivileges.WRITE_PRIVILEGE)) {
			return new ResponseEntity<>("Not authorized", HttpStatus.UNAUTHORIZED);
		}
		User user = userService.findOne(id);
		if (user == null) {
			return new ResponseEntity<>("User not found.", HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(service.findUserReceivedMessages(user), HttpStatus.OK);
	}
	
	@RequestMapping(value="/user/{id}/received/{messageId}", method=RequestMethod.GET)
	public ResponseEntity<Object> getUserReceivedMessage(@PathVariable Long id, @PathVariable Long messageId, @RequestHeader("Authorization") String token) {
		if(!jwtUserPermissions.hasRoleAndPrivilege(token, UserRoles.USER, UserPrivileges.WRITE_PRIVILEGE)) {
			return new ResponseEntity<>("Not authorized", HttpStatus.UNAUTHORIZED);
		}
		User user = userService.findOne(id);
		if (user == null) {
			return new ResponseEntity<>("User not found.", HttpStatus.NOT_FOUND);
		}
		Message message = service.findUserReceivedMessage(user, messageId);
		if (message == null) {
			return new ResponseEntity<>("Message not found.", HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(message, HttpStatus.OK);
	}
	
	@RequestMapping(value="/user/{id}/received/{messageId}/mark-as-read", method=RequestMethod.PUT)
	public ResponseEntity<Object> markAsReadUserReceivedMessage(@PathVariable Long id, @PathVariable Long messageId, @RequestHeader("Authorization") String token) {
		if(!jwtUserPermissions.hasRoleAndPrivilege(token, UserRoles.USER, UserPrivileges.WRITE_PRIVILEGE)) {
			return new ResponseEntity<>("Not authorized", HttpStatus.UNAUTHORIZED);
		}
		User user = userService.findOne(id);
		if (user == null) {
			return new ResponseEntity<>("User not found.", HttpStatus.NOT_FOUND);
		}
		Message message = service.findUserReceivedMessage(user, messageId);
		if (message == null) {
			return new ResponseEntity<>("Message not found.", HttpStatus.NOT_FOUND);
		}
		message.setStatus(MessageStatus.READ);
		return new ResponseEntity<>(service.save(message), HttpStatus.OK);
	}
	
	@RequestMapping(value="/agent/{id}/sent", method=RequestMethod.GET)
	public ResponseEntity<Object> getAgentSentMessages(@PathVariable Long id, @RequestHeader("Authorization") String token) {
		if(!jwtUserPermissions.hasRoleAndPrivilege(token, UserRoles.AGENT, UserPrivileges.WRITE_PRIVILEGE)) {
			return new ResponseEntity<>("Not authorized", HttpStatus.UNAUTHORIZED);
		}
		Agent agent = agentService.findOne(id);
		if (agent == null) {
			return new ResponseEntity<>("Agent not found.", HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(service.findAgentSentMessages(agent), HttpStatus.OK);
	}
	
	@RequestMapping(value="/agent/{id}/received", method=RequestMethod.GET)
	public ResponseEntity<Object> getAgentReceivedMessages(@PathVariable Long id, @RequestHeader("Authorization") String token) {
		if(!jwtUserPermissions.hasRoleAndPrivilege(token, UserRoles.AGENT, UserPrivileges.WRITE_PRIVILEGE)) {
			return new ResponseEntity<>("Not authorized", HttpStatus.UNAUTHORIZED);
		}
		Agent agent = agentService.findOne(id);
		if (agent == null) {
			return new ResponseEntity<>("Agent not found.", HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(service.findAgentReceivedMessages(agent), HttpStatus.OK);
	}
	
	@RequestMapping(value="/user-to-agent", method=RequestMethod.POST)
	public ResponseEntity<Object> sendMessageToAgent(@RequestBody SendMessage data, @RequestHeader("Authorization") String token) throws SecurityException, IOException {
		if(!jwtUserPermissions.hasRoleAndPrivilege(token, UserRoles.USER, UserPrivileges.WRITE_PRIVILEGE)) {
			return new ResponseEntity<>("Not authorized", HttpStatus.UNAUTHORIZED);
		}
		if (data.getApartment() == null || Long.valueOf(data.getApartment()) == 0 ||
			data.getUser() == null || Long.valueOf(data.getUser()) == 0 ||
			data.getAgent() == null || Long.valueOf(data.getAgent()) == 0 ||
			data.getText() == null || data.getText() == "") {
			return new ResponseEntity<>("All fields are required(apartment id, user id, agent id, text).", HttpStatus.FORBIDDEN);
		}
		Apartment apartment = apartmentService.findOne(data.getApartment());
		User user = userService.findOne(data.getUser());
		Agent agent = agentService.findOne(data.getAgent());
		
		if (apartment == null) {
			return new ResponseEntity<>("Apartment not found.", HttpStatus.NOT_FOUND);
		}
		
		if (user == null) {
			return new ResponseEntity<>("User not found.", HttpStatus.NOT_FOUND);
		}
		if (agent == null) {
			return new ResponseEntity<>("Agent not found.", HttpStatus.NOT_FOUND);
		}
		
		SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
		SimpleDateFormat timeFormatter = new SimpleDateFormat("HH:mm:ss");
		
		String date = dateFormatter.format(new Date());
		String time = timeFormatter.format(new Date());
		
		Message userSentMessage = new Message(user, agent, apartment, date, time, data.getText(), MessageStatus.UNREAD, Direction.USER_TO_AGENT);
		MessageLogger.log(Level.INFO, "User " + user.getEmail() + " has successfully sent a message to agent " + agent.getEmail() + " .");
		return new ResponseEntity<>(service.save(userSentMessage), HttpStatus.OK);
	}
	
	@RequestMapping(value="/agent-to-user", method=RequestMethod.POST)
	public ResponseEntity<Object> sendMessageToUser(@RequestBody SendMessage data, @RequestHeader("Authorization") String token) throws SecurityException, IOException {
		if(!jwtUserPermissions.hasRoleAndPrivilege(token, UserRoles.AGENT, UserPrivileges.WRITE_PRIVILEGE)) {
			return new ResponseEntity<>("Not authorized", HttpStatus.UNAUTHORIZED);
		}
		if (data.getApartment() == null || Long.valueOf(data.getApartment()) == 0 ||
			data.getUser() == null || Long.valueOf(data.getUser()) == 0 ||
			data.getAgent() == null || Long.valueOf(data.getAgent()) == 0 ||
			data.getText() == null || data.getText() == "") {
			return new ResponseEntity<>("All fields are required(apartment id, user id, agent id, text).", HttpStatus.FORBIDDEN);
		}
		Apartment apartment = apartmentService.findOne(data.getApartment());
		User user = userService.findOne(data.getUser());
		Agent agent = agentService.findOne(data.getAgent());
		
		if (user == null) {
			return new ResponseEntity<>("User not found.", HttpStatus.NOT_FOUND);
		}
		if (agent == null) {
			return new ResponseEntity<>("Agent not found.", HttpStatus.NOT_FOUND);
		}
		if (apartment == null) {
			return new ResponseEntity<>("Apartment not found.", HttpStatus.NOT_FOUND);
		}
		
		SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
		SimpleDateFormat timeFormatter = new SimpleDateFormat("HH:mm:ss");
		
		String date = dateFormatter.format(new Date());
		String time = timeFormatter.format(new Date());
		
		Message message = new Message(user, agent, apartment, date, time, data.getText(), MessageStatus.UNREAD, Direction.AGENT_TO_USER);
		MessageLogger.log(Level.INFO, "Agent " + agent.getEmail() + " has successfully sent a message to user " + user.getEmail() + " .");
		return new ResponseEntity<>(service.save(message), HttpStatus.OK);
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<Object> deleteMessage(@PathVariable Long id, @RequestHeader("Authorization") String authHeader) throws SecurityException, IOException {
		if(!jwtUserPermissions.hasRoleAndPrivilege(authHeader, UserRoles.ADMIN, UserPrivileges.WRITE_PRIVILEGE)) {
			return new ResponseEntity<>("Not authorized", HttpStatus.UNAUTHORIZED);
		}
		try {
			String token = authHeader.split(" ")[1].trim();
			JwtUser jwtUser = jwtValidator.validate(token);
			if (jwtUser != null) {
				Message message = service.findOne(id);
				Admin admin = adminService.findByIdAndEmail(jwtUser.getId(), jwtUser.getEmail());
				if (admin == null) {
					MessageLogger.log(Level.WARNING, "User " + jwtUser.getEmail() + " tried to delete message #" + message.getId() + ", but he doesn't have permissions to do that.");
					return new ResponseEntity<>("User with this token doesn't have admin permissions.", HttpStatus.NOT_FOUND);
				}
				if (message == null) {
					return new ResponseEntity<>("Message not found.", HttpStatus.NOT_FOUND);
				}
				service.delete(message);
				return new ResponseEntity<>(message, HttpStatus.OK);
			} else {
				MessageLogger.log(Level.WARNING, "Given token is not valid. User doesn't exist.");
				return new ResponseEntity<>("User with this email doesn't exist.", HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			MessageLogger.log(Level.WARNING, "Exception occured while validating token.");
			return new ResponseEntity<>("Token not provided.", HttpStatus.FORBIDDEN);
		}
	}
	
	@RequestMapping(value="/{id}/delete-user-sent", method=RequestMethod.DELETE)
	public ResponseEntity<Object> deleteUserSentMessage(@PathVariable Long id, @RequestHeader("Authorization") String token) throws SecurityException, IOException {
		if(!jwtUserPermissions.hasRoleAndPrivilege(token, UserRoles.USER, UserPrivileges.WRITE_PRIVILEGE)) {
			return new ResponseEntity<>("Not authorized", HttpStatus.UNAUTHORIZED);
		}
		Message message = service.findOne(id);
		if (message == null) {
			return new ResponseEntity<>("Message not found.", HttpStatus.NOT_FOUND);
		}
		User user = message.getUser();
		if (user == null) {
			return new ResponseEntity<>("User not found.", HttpStatus.NOT_FOUND);
		}
		if (message.getDirection().equals(Direction.USER_TO_AGENT)) {
			message.setStatus(MessageStatus.DELETED_FOR_USER);
			MessageLogger.log(Level.INFO, "User " + user.getEmail() + " has successfully deleted sent message for himself.");
			return new ResponseEntity<>(service.save(message), HttpStatus.OK);
		} else {
			return new ResponseEntity<>("User not found.", HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping(value="/{id}/delete-user-received", method=RequestMethod.DELETE)
	public ResponseEntity<Object> deleteUserReceivedMessage(@PathVariable Long id, @RequestHeader("Authorization") String token) throws SecurityException, IOException {
		if(!jwtUserPermissions.hasRoleAndPrivilege(token, UserRoles.USER, UserPrivileges.WRITE_PRIVILEGE)) {
			return new ResponseEntity<>("Not authorized", HttpStatus.UNAUTHORIZED);
		}
		Message message = service.findOne(id);
		if (message == null) {
			return new ResponseEntity<>("Message not found.", HttpStatus.NOT_FOUND);
		}
		User user = message.getUser();
		if (user == null) {
			return new ResponseEntity<>("User not found.", HttpStatus.NOT_FOUND);
		}
		if (message.getDirection().equals(Direction.AGENT_TO_USER)) {
			message.setStatus(MessageStatus.DELETED_FOR_USER);
			MessageLogger.log(Level.INFO, "User " + user.getEmail() + " has successfully deleted received message for himself.");
			return new ResponseEntity<>(service.save(message), HttpStatus.OK);
		} else {
			return new ResponseEntity<>("User not found.", HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping(value="/{id}/delete-agent-sent", method=RequestMethod.DELETE)
	public ResponseEntity<Object> deleteAgentSentMessage(@PathVariable Long id, @RequestHeader("Authorization") String token) throws SecurityException, IOException {
		if(!jwtUserPermissions.hasRoleAndPrivilege(token, UserRoles.AGENT, UserPrivileges.WRITE_PRIVILEGE)) {
			return new ResponseEntity<>("Not authorized", HttpStatus.UNAUTHORIZED);
		}
		Message message = service.findOne(id);
		if (message == null) {
			return new ResponseEntity<>("Message not found.", HttpStatus.NOT_FOUND);
		}
		Agent agent = message.getAgent();
		if (agent == null) {
			return new ResponseEntity<>("Agent not found.", HttpStatus.NOT_FOUND);
		}
		if (message.getDirection().equals(Direction.AGENT_TO_USER)) {
			message.setStatus(MessageStatus.DELETED_FOR_AGENT);
			MessageLogger.log(Level.INFO, "Agent " + agent.getEmail() + " has successfully deleted sent message for himself.");
			return new ResponseEntity<>(service.save(message), HttpStatus.OK);
		} else {
			return new ResponseEntity<>("Agent not found.", HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping(value="/{id}/delete-agent-received", method=RequestMethod.DELETE)
	public ResponseEntity<Object> deleteAgentReceivedMessage(@PathVariable Long id, @RequestHeader("Authorization") String token) throws SecurityException, IOException {
		if(!jwtUserPermissions.hasRoleAndPrivilege(token, UserRoles.AGENT, UserPrivileges.WRITE_PRIVILEGE)) {
			return new ResponseEntity<>("Not authorized", HttpStatus.UNAUTHORIZED);
		}
		Message message = service.findOne(id);
		if (message == null) {
			return new ResponseEntity<>("Message not found.", HttpStatus.NOT_FOUND);
		}
		Agent agent = message.getAgent();
		if (agent == null) {
			return new ResponseEntity<>("Agent not found.", HttpStatus.NOT_FOUND);
		}
		if (message.getDirection().equals(Direction.USER_TO_AGENT)) {
			message.setStatus(MessageStatus.DELETED_FOR_AGENT);
			MessageLogger.log(Level.INFO, "Agent " + agent.getEmail() + " has successfully deleted received message for himself.");
			return new ResponseEntity<>(service.save(message), HttpStatus.OK);
		} else {
			return new ResponseEntity<>("Agent not found.", HttpStatus.NOT_FOUND);
		}
	}
}
