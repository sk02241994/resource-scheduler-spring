package com.office.resourcescheduler.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.office.resourcescheduler.util.Constants;
import com.office.resourcescheduler.login.UserPrincipal;
import com.office.resourcescheduler.util.Roles;
import com.office.resourcescheduler.controller.UserController;
import com.office.resourcescheduler.controller.ReservationController;

@Controller
public class LoginController{

    @GetMapping("/")
    public ModelAndView goToLoginPage(){
        String redirectUri = "redirect:";
		Object object =  SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (object instanceof UserPrincipal) {
            UserPrincipal user = (UserPrincipal) object;
             redirectUri += user.getAuthorities().contains(new SimpleGrantedAuthority(Roles.ADMIN.toString()))
                ? UserController.USER_URI + UserController.LIST
                : ReservationController.RESERVATION_URI + ReservationController.LIST;
        } else {
            redirectUri += Constants.LOGIN;
        }
            return new ModelAndView(redirectUri);
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
