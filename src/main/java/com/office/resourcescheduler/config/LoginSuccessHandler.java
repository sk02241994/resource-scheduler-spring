package com.office.resourcescheduler.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.office.resourcescheduler.controller.ReservationController;
import com.office.resourcescheduler.controller.UserController;
import com.office.resourcescheduler.login.UserPrincipal;
import com.office.resourcescheduler.util.Roles;

@Component
public class LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authenticate) throws IOException, ServletException {
		UserPrincipal user = (UserPrincipal) authenticate.getPrincipal();
		String targetUrl = user.getAuthorities().contains(new SimpleGrantedAuthority(Roles.ADMIN.toString()))
				? UserController.USER_URI + UserController.LIST
				: ReservationController.RESERVATION_URI + ReservationController.LIST;
		clearAuthenticationAttributes(request);
		getRedirectStrategy().sendRedirect(request, response, targetUrl);
	}

}