package com.office.resourcescheduler.controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.office.resourcescheduler.dao.UserDao;
import com.office.resourcescheduler.model.User;

@Controller
public class UserController {

	@Autowired
	private UserDao userDao;

	@PostMapping("/save")
	public String save() {
		userDao.saveUser(new User(null, "Shubham", "shubham.k@gmao.com", "user", true, true));
		return "save successful";
	}

	@GetMapping("/list")
	public void getList() {
		userDao.saveUser(new User(null, "Shubham", "shubham.k@gmao.com", "user", true, true));
		System.out.println(userDao.getUsers().toString());
	}
}
