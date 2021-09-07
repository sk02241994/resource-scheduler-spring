package com.office.resourcescheduler.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.office.resourcescheduler.util.Constants;
import com.office.resourcescheduler.util.Roles;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private LoginSuccessHandler successHandler;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		auth.authenticationProvider(getAuthProvider());
	}

	@Bean
	public AuthenticationProvider getAuthProvider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setUserDetailsService(userDetailsService);
		provider.setPasswordEncoder(NoOpPasswordEncoder.getInstance());
		return provider;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.csrf().disable()
		.authorizeRequests()
		.antMatchers(Constants.LOGIN, Constants.RESOURCES, Constants.RESET).permitAll()
		.antMatchers(Constants.RESOURCES_URI, Constants.USERS).hasAuthority(Roles.ADMIN.toString())
		.antMatchers(Constants.RESERVATIONS, Constants.CALENDAR).hasAnyAuthority(Roles.ADMIN.toString(), Roles.USER.toString())
		.anyRequest()
		.authenticated()
		.and()
		.exceptionHandling().accessDeniedPage(Constants.ACCESS_DENIED)
		.and()
		.formLogin()
		.loginPage("/login")
		.successHandler(successHandler)
		.permitAll()
		.and()
		.logout()
		.invalidateHttpSession(true)
		.clearAuthentication(true)
		.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
		.logoutSuccessUrl("/")
		.permitAll();
		
	}

}
