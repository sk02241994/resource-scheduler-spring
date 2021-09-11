package com.office.resourcescheduler.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.office.resourcescheduler.model.User;

public interface UserRepository extends CrudRepository<User, Long>, JpaRepository<User, Long> {

	User findByEmailAddress(String emailAddress);
	
	@Modifying(clearAutomatically = true, flushAutomatically = true)
	@Transactional
	@Query(value = "UPDATE User u SET u.password = :password WHERE u.emailAddress = :emailAddress")
	void updatePassword(@Param("password")String password, @Param("emailAddress")String emailAddress);
}
