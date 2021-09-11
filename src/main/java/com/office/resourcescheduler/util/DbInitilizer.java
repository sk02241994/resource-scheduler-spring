package com.office.resourcescheduler.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.office.resourcescheduler.model.User;
import com.office.resourcescheduler.repository.UserRepository;

@Component
@ConditionalOnProperty(name = "app.db-init", havingValue = "true")
public class DbInitilizer implements CommandLineRunner {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public void run(String... args) throws Exception {
		userRepository.deleteAll();
		userRepository.save(new User(1L, "John Doe", "skaradkar57@gmail.com", passwordEncoder.encode("assd12345"), true, true, Roles.ADMIN, Gender.M));
		userRepository.save(new User(2L, "Jane Doe", "ak@gmai.com", passwordEncoder.encode("assd12345"), true, true, Roles.USER, Gender.F));
		System.out.println("DB initialized");
	}

}
