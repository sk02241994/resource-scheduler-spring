package com.office.resourcescheduler.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.office.resourcescheduler.util.Constants;

@Controller
public class LoginController{

    @GetMapping("/")
    public ModelAndView goToLoginPage(){
        return new ModelAndView("redirect:" + Constants.LOGIN);
    }

    @GetMapping(Constants.LOGIN)
    public ModelAndView login(){
        return new ModelAndView("login");
    }
    
    @GetMapping(Constants.LOGOUT)
    public ModelAndView logout() {
    	return new ModelAndView("login");
    }
    
    @GetMapping(Constants.ACCESS_DENIED)
    public ModelAndView accessDenied() {
    	return new ModelAndView("access-denied");
    }
}
