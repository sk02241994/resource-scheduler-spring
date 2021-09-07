package com.office.resourcescheduler.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.office.resourcescheduler.model.User;

public interface UserRepository extends CrudRepository<User, Long>, JpaRepository<User, Long> {

	User findByEmailAddress(String emailAddress);
	
	@Query("UPDATE User u SET u.password = :password WHERE u.emailAddress = :emailAddress")
	User updatePassword(@Param("password")String password, @Param("emailAddress")String emailAddress);
}
