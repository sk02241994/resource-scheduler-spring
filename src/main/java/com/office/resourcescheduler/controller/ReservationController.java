package com.office.resourcescheduler.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(ReservationController.RESOURCE_URI)
public class ReservationController {

	public static final String RESOURCE_URI = "/resource";
	public static final String LIST = "/list";
	public static final String SAVE = "/save";
	
	@GetMapping(LIST)
	public void getReservationList() {
		System.out.println("From reservation");
	}
}
