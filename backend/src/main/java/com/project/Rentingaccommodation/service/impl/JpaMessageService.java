package com.project.Rentingaccommodation.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.Rentingaccommodation.model.Agent;
import com.project.Rentingaccommodation.model.Apartment;
import com.project.Rentingaccommodation.model.Direction;
import com.project.Rentingaccommodation.model.Message;
import com.project.Rentingaccommodation.model.MessageStatus;
import com.project.Rentingaccommodation.model.User;
import com.project.Rentingaccommodation.repository.MessageRepository;
import com.project.Rentingaccommodation.service.MessageService;

@Transactional
@Service
public class JpaMessageService implements MessageService {

	@Autowired
	private MessageRepository repository;
	
	@Override
	public Message findOne(Long id) {
		for (Message message : findAll()) {
			if (message.getId() == id) {
				return message;
			}
		}
		return null;
	}

	@Override
	public List<Message> findAll() {
		return repository.findAll();
	}
	
	@Override
	public Message save(Message message) {
		return repository.save(message);
	}
	
	@Override
	public void delete(Message message) {
		repository.delete(message);
	}

	@Override
	public List<Message> findUserSentMessages(User user) {
		List<Message> messages = new ArrayList<Message>();
		for (Message message : findAll()) {
			if (message.getUser().getId() == user.getId() &&
				message.getDirection().equals(Direction.USER_TO_AGENT) &&
				!message.getStatus().equals(MessageStatus.DELETED_FOR_USER)) {
				messages.add(message);
			}
		}
		return messages;
	}

	@Override
	public List<Message> findAgentSentMessages(Agent agent) {
		List<Message> messages = new ArrayList<Message>();
		for (Message message : findAll()) {
			if (message.getAgent().getId() == agent.getId() &&
				message.getDirection().equals(Direction.AGENT_TO_USER) &&
				!message.getStatus().equals(MessageStatus.DELETED_FOR_AGENT)) {
				messages.add(message);
			}
		}
		return messages;
	}
	
	@Override
	public List<Message> findUserReceivedMessages(User user) {
		List<Message> messages = new ArrayList<Message>();
		for (Message message : findAll()) {
			if (message.getUser().getId() == user.getId() &&
				message.getDirection().equals(Direction.AGENT_TO_USER) &&
				!message.getStatus().equals(MessageStatus.DELETED_FOR_USER)) {
				messages.add(message);
			}
		}
		return messages;
	}

	@Override
	public List<Message> findAgentReceivedMessages(Agent agent) {
		List<Message> messages = new ArrayList<Message>();
		for (Message message : findAll()) {
			if (message.getAgent().getId() == agent.getId() &&
				message.getDirection().equals(Direction.USER_TO_AGENT) &&
				!message.getStatus().equals(MessageStatus.DELETED_FOR_AGENT)) {
				messages.add(message);
			}
		}
		return messages;
	}

	@Override
	public List<Message> findUserReadMessages(User user) {
		List<Message> messages = new ArrayList<Message>();
		for (Message message : findAll()) {
			if (message.getUser().getId() == user.getId() &&
				message.getStatus().equals(MessageStatus.READ)) {
				messages.add(message);
			}
		}
		return messages;
	}

	@Override
	public List<Message> findAgentReadMessages(Agent agent) {
		List<Message> messages = new ArrayList<Message>();
		for (Message message : findAll()) {
			if (message.getAgent().getId() == agent.getId() &&
				message.getStatus().equals(MessageStatus.READ)) {
				messages.add(message);
			}
		}
		return messages;
	}

	@Override
	public List<Message> findUserUnreadMessages(User user) {
		List<Message> messages = new ArrayList<Message>();
		for (Message message : findAll()) {
			if (message.getUser().getId() == user.getId() &&
				message.getStatus().equals(MessageStatus.UNREAD)) {
				messages.add(message);
			}
		}
		return messages;
	}

	@Override
	public List<Message> findAgentUnreadMessages(Agent agent) {
		List<Message> messages = new ArrayList<Message>();
		for (Message message : findAll()) {
			if (message.getAgent().getId() == agent.getId() &&
				message.getStatus().equals(MessageStatus.UNREAD)) {
				messages.add(message);
			}
		}
		return messages;
	}

	@Override
	public Message findUserSentMessage(User user, Long id) {
		for (Message message : findUserSentMessages(user)) {
			if (message.getId() == id) {
				return message;
			}
		}
		return null;
	}

	@Override
	public Message findUserReceivedMessage(User user, Long id) {
		for (Message message : findUserReceivedMessages(user)) {
			if (message.getId() == id) {
				return message;
			}
		}
		return null;
	}

	@Override
	public Message findAgentSentMessage(Agent agent, Long id) {
		for (Message message : findAgentSentMessages(agent)) {
			if (message.getId() == id) {
				return message;
			}
		}
		return null;
	}

	@Override
	public Message findAgentReceivedMessage(Agent agent, Long id) {
		for (Message message : findAgentReceivedMessages(agent)) {
			if (message.getId() == id) {
				return message;
			}
		}
		return null;
	}

	@Override
	public Message sendMessageToUser(User user, Agent agent, Apartment apartment, String date, String time, String text) {
		Message message = new Message(user, agent, apartment, date, time, text, MessageStatus.UNREAD, Direction.AGENT_TO_USER);
		return repository.save(message);
	}

	@Override
	public Message sendMessageToAgent(User user, Agent agent, Apartment apartment, String date, String time, String text) {
		Message message = new Message(user, agent, apartment, date, time, text, MessageStatus.UNREAD, Direction.USER_TO_AGENT);
		return repository.save(message);
	}
	
	@Override
	public Message markAsRead(Message msg) {
		for (Message message : findAll()) {
			if (message.getId() == msg.getId() && msg.getStatus().equals(MessageStatus.UNREAD)) {
				msg.setStatus(MessageStatus.READ);
				return save(msg);
			}
		}
		return null;
	}

	@Override
	public Message markAsUnread(Message msg) {
		for (Message message : findAll()) {
			if (message.getId() == msg.getId() && msg.getStatus().equals(MessageStatus.READ)) {
				msg.setStatus(MessageStatus.UNREAD);
				return save(msg);
			}
		}
		return null;
	}

	@Override
	public void deleteUserSentMessage(User user, Message message) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteAgentSentMessage(Agent agent, Message message) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteUserReceivedMessage(User user, Message message) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteAgentReceivedMessage(Agent agent, Message message) {
		// TODO Auto-generated method stub
		
	}
}
