package com.project.Rentingaccommodation.service;

import java.util.List;

import com.project.Rentingaccommodation.model.Agent;
import com.project.Rentingaccommodation.model.Apartment;
import com.project.Rentingaccommodation.model.Message;
import com.project.Rentingaccommodation.model.User;

public interface MessageService {
	
	List<Message> findAll();
	Message findOne(Long id);
	Message save(Message message);
	void delete(Message message);
	
	List<Message> findUserSentMessages(User user);
	List<Message> findAgentSentMessages(Agent agent);
	
	List<Message> findUserReceivedMessages(User user);
	List<Message> findAgentReceivedMessages(Agent agent);
	
	List<Message> findUserReadMessages(User user);
	List<Message> findAgentReadMessages(Agent agent);
	
	List<Message> findUserUnreadMessages(User user);
	List<Message> findAgentUnreadMessages(Agent agent);
	
	Message findUserSentMessage(User user, Long id);
	Message findUserReceivedMessage(User user, Long id);
	
	Message findAgentSentMessage(Agent agent, Long id);
	Message findAgentReceivedMessage(Agent agent, Long id);
	
	Message sendMessageToUser(User user, Agent agent, Apartment apartment, String date, String time, String text);
	Message sendMessageToAgent(User user, Agent agent, Apartment apartment, String date, String time, String text);
	
	Message markAsRead(Message message);
	Message markAsUnread(Message message);
	
	void deleteUserSentMessage(User user, Message message);
	void deleteAgentSentMessage(Agent agent, Message message);
	
	void deleteUserReceivedMessage(User user, Message message);
	void deleteAgentReceivedMessage(Agent agent, Message message);
}
