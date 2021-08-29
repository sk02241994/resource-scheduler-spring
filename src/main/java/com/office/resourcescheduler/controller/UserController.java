package com.office.resourcescheduler.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.*;
import com.office.resourcescheduler.dao.UserDao;
import com.office.resourcescheduler.model.User;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserDao userDao;

	@PostMapping("/save")
	public String save() {
		userDao.saveUser(new User(null, "Shubham", "shubham.k@gmao.com", "user", true, true));
		return "save successful";
	}

	@GetMapping("/list")
	public ModelAndView getList() {
        ModelAndView mv = new ModelAndView("login");
        mv.addObject("name", "Shubham");
        System.out.println("On listing page");
        return mv;
	}
}
