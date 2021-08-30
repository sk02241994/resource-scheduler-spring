package com.office.resourcescheduler.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/user")
public class UserController {


	@PostMapping("/save")
	public String save() {
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
