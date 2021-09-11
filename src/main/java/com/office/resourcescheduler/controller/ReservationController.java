package com.office.resourcescheduler.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(ReservationController.RESERVATION_URI)
public class ReservationController {

	public static final String RESERVATION_URI = "/reservation";
	public static final String LIST = "/list";
	public static final String SAVE = "/save";
	
	@GetMapping(LIST)
	public void getReservationList() {
		System.out.println("From reservation");
	}
}
