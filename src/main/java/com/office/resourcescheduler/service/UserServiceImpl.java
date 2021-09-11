package com.office.resourcescheduler.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.office.resourcescheduler.model.User;
import com.office.resourcescheduler.repository.UserRepository;
import com.office.resourcescheduler.config.SpringContext;

@Service
@Transactional
public class UserServiceImpl {
	
	private UserRepository userRepository;

    public static UserServiceImpl getInstance(){
        return SpringContext.getBean(UserServiceImpl.class);
    }

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
	
	public void updatePassword(String password, String emailAddress) {
		userRepository.updatePassword(password, emailAddress);
	}
}
