package com.project.Rentingaccommodation.service;

import java.util.List;
import com.project.Rentingaccommodation.model.Comment;
import com.project.Rentingaccommodation.model.User;

public interface CommentService {

	Comment findOne(Long id);
	List<Comment> findAll();
	List<Comment> findApprovedComments();
	List<Comment> findWaitingComments();
	List<Comment> findUserComments(User user);
	List<Comment> findApartmentComments(Long id);
	Comment save(Comment comment);
	void delete(Comment comment);
	List<Comment> findApartmentApprovedComments(Long id);
	Comment findByUserAndApartment(Long id, Long id2);
}
