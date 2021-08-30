package com.office.resourcescheduler.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import com.office.resourcescheduler.model.User;
import com.office.resourcescheduler.service.UserRepository;

@Component
@ConditionalOnProperty(name = "app.db-init", havingValue = "true")
public class DbInitilizer implements CommandLineRunner {

	@Autowired
	private UserRepository userRepository;

	@Override
	public void run(String... args) throws Exception {
		userRepository.deleteAll();
		userRepository.save(new User(1, "Shubham", "sk@gmai.com", "assd12345", true, Roles.ADMIN, Gender.M));
		userRepository.save(new User(1, "Amruta", "ak@gmai.com", "assd12345", true, Roles.USER, Gender.F));
		System.out.println("DB initialized");
	}

}
