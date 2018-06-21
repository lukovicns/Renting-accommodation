package com.project.Rentingaccommodation.repository;

import com.project.Rentingaccommodation.model.Comment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
	
}
