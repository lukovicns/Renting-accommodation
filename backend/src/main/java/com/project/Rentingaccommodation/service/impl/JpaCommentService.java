package com.project.Rentingaccommodation.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.project.Rentingaccommodation.model.Comment;
import com.project.Rentingaccommodation.model.CommentStatus;
import com.project.Rentingaccommodation.model.User;
import com.project.Rentingaccommodation.repository.CommentRepository;
import com.project.Rentingaccommodation.service.CommentService;

@Transactional
@Service
public class JpaCommentService implements CommentService {

    @Autowired
    CommentRepository repository;

	@Override
	public Comment findOne(Long id) {
		for (Comment comment : repository.findAll()) {
			if (comment.getId() == id) {
				return comment;
			}
		}
		return null;
	}

	@Override
	public List<Comment> findAll() {
		return repository.findAll();
	}
	
	@Override
	public List<Comment> findApprovedComments() {
		List<Comment> approvedComments = new ArrayList<Comment>();
		for (Comment comment : findAll()) {
			if (comment.getStatus().equals(CommentStatus.APPROVED)) {
				approvedComments.add(comment);
			}
		}
		return approvedComments;
	}
	
	@Override
	public List<Comment> findApartmentApprovedComments(Long id) {
		List<Comment> approvedComments = new ArrayList<Comment>();
		for (Comment comment : findApartmentComments(id)) {
			if (comment.getStatus().equals(CommentStatus.APPROVED)) {
				approvedComments.add(comment);
			}
		}
		return approvedComments;
	}
	
	@Override
	public Comment findByUserAndApartment(Long apartmentId, Long userId) {
		for (Comment comment : findApartmentComments(apartmentId)) {
			System.out.println(comment);
			if (comment.getUser().getId() == userId) {
				return comment;
			}
		}
		return null;
	}

	@Override
	public List<Comment> findWaitingComments() {
		List<Comment> waitingComments = new ArrayList<Comment>();
		for (Comment comment : findAll()) {
			if (comment.getStatus().equals(CommentStatus.WAITING)) {
				waitingComments.add(comment);
			}
		}
		return waitingComments;
	}
	
	@Override
	public List<Comment> findUserComments(User user) {
		List<Comment> userComments = new ArrayList<Comment>();
		for (Comment comment : findAll()) {
			if (comment.getUser().getId() == user.getId()) {
				userComments.add(comment);
			}
		}
		return userComments;
	}
	
	@Override
	public List<Comment> findApartmentComments(Long id) {
		List<Comment> apartmentComments = new ArrayList<Comment>();
		for (Comment comment : findAll()) {
			if (comment.getApartment().getId() == id) {
				apartmentComments.add(comment);
			}
		}
		return apartmentComments;
	}
	
	@Override
	public Comment save(Comment comment) {
		return repository.save(comment);
	}

	@Override
	public void delete(Comment comment) {
		repository.delete(comment);
	}
}
