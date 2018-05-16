package com.project.Rentingaccommodation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.Rentingaccommodation.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
