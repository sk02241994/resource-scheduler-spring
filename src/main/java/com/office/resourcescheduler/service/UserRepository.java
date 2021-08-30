package com.office.resourcescheduler.service;

import org.springframework.data.jpa.repository.JpaRepository;

import com.office.resourcescheduler.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

	User findByEmailAddress(String emailAddress);
}
