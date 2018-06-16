package com.project.Rentingaccommodation.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.project.Rentingaccommodation.model.Message;
import com.project.Rentingaccommodation.repository.MessageRepository;
import com.project.Rentingaccommodation.service.MessageService;

@Transactional
@Service
public class JpaMessageService implements MessageService {

	@Autowired
	private MessageRepository repository;
	
	@Override
	public Message findOne(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Message> findAll() {
		return repository.findAll();
	}

	@Override
	public Message save(Message message) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Message> save(List<Message> messages) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Message delete(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(List<Long> ids) {
		// TODO Auto-generated method stub
		
	}

}
