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
public class JpaReviewService implements CommentService {

    @Autowired
    CommentRepository repository;

	@Override
	public Comment findOne(Long id) {
		for (Comment review : repository.findAll()) {
			if (review.getId() == id) {
				return review;
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
	public List<Comment> findDeclinedComments() {
		List<Comment> declinedComments = new ArrayList<Comment>();
		for (Comment comment : findAll()) {
			if (comment.getStatus().equals(CommentStatus.DECLINED)) {
				declinedComments.add(comment);
			}
		}
		return declinedComments;
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
