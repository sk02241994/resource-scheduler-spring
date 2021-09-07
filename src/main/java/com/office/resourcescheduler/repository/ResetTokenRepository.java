package com.office.resourcescheduler.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.office.resourcescheduler.model.ResetToken;

public interface ResetTokenRepository extends JpaRepository<ResetToken, Long> {

	@Query("SELECT r from ResetToken r WHERE r.token = :token")
	ResetToken findByTokenId(@Param("token") String token);
	
	void deleteByUserId(Long userId);
}
