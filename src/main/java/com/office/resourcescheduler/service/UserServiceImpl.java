package com.office.resourcescheduler.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.office.resourcescheduler.model.User;
import com.office.resourcescheduler.repository.UserRepository;

@Service
@Transactional
public class UserServiceImpl {
	
	private UserRepository userRepository;

	@Autowired
	public UserServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	public List<User> findAllUsers(){
		return userRepository.findAll();
	}
	
	public Optional<User> findById(Long userId){
		return userRepository.findById(userId);
	}

	public User save(User user) {
		return userRepository.save(user);
	}
	
	public User findByEmailAddress(String emailAddress) {
		return userRepository.findByEmailAddress(emailAddress);
	}
	
	public User updatePassword(String password, String email) {
		return userRepository.updatePassword(password, email);
	}
}
