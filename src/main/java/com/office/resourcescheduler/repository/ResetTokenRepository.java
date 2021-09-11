package com.office.resourcescheduler.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.office.resourcescheduler.model.ResetToken;

public interface ResetTokenRepository extends JpaRepository<ResetToken, Long> {

	@Query("SELECT r from ResetToken r WHERE r.token = :token")
	ResetToken findByTokenId(@Param("token") String token);
	
    @Modifying
    @Query(value = "DELETE FROM password_reset_token where user_id=:userId", nativeQuery = true)
	void deleteByUserId(@Param("userId")Long userId);
}
