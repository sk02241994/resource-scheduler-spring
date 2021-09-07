package com.office.resourcescheduler.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.office.resourcescheduler.model.ResetToken;
import com.office.resourcescheduler.repository.ResetTokenRepository;

@Service
@Transactional
public class ResetTokenImpl {

	private ResetTokenRepository repository;

	@Autowired
	public ResetTokenImpl(ResetTokenRepository repository) {
		this.repository = repository;
	}
	
	public ResetToken save(ResetToken resetToken) {
		return repository.save(resetToken);
	}
	
	public ResetToken findByTokenId(String token) {
		return repository.findByTokenId(token);
	}
	
	public void deleteByUserid(Long userId) {
		repository.deleteByUserId(userId);
	}
}
