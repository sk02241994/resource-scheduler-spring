package com.office.resourcescheduler.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.*;
import com.office.resourcescheduler.dao.UserDao;
import com.office.resourcescheduler.model.User;
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
}
