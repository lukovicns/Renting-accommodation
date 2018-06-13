package com.project.Rentingaccommodation.service;

import java.util.List;

import com.project.Rentingaccommodation.model.Country;
import com.project.Rentingaccommodation.model.Message;

public interface MessageService {
	
	Message findOne(Long id);
	List<Message> findAll();
	Message save(Message message);
	List<Message> save(List<Message> messages);
	Country delete(Long id);
	void delete(List<Long> ids);
}
