package com.office.resourcescheduler.controller;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.gson.Gson;
import com.office.resourcescheduler.errorhandler.ValidationException;
import com.office.resourcescheduler.login.UserPrincipal;
import com.office.resourcescheduler.model.Reservation;
import com.office.resourcescheduler.model.Resource;
import com.office.resourcescheduler.model.User;
import com.office.resourcescheduler.service.EmailService;
import com.office.resourcescheduler.service.ReservationImpl;
import com.office.resourcescheduler.service.ResourceImpl;
import com.office.resourcescheduler.service.UserServiceImpl;
import com.office.resourcescheduler.util.NoticeInterface;

@Controller
@RequestMapping(ReservationController.RESERVATION_URI)
public class ReservationController implements NoticeInterface{

	public static final String RESERVATION_URI = "/reservation";
	public static final String LIST = "/list";
	public static final String SAVE = "/save";
	public static final String EDIT = "/edit";
	public static final String VIEW = "view";
	public static final String FORM = "form";
	public static final String JS_FORM = "jsForm";
	public static final String RESERVATION_PAGE = "reservation";

	private ReservationImpl reservationImpl;
	private UserServiceImpl userServiceImpl;
	private ResourceImpl resourceImpl;
	private EmailService emailService;

	@Autowired
	public ReservationController(ReservationImpl reservationImpl, UserServiceImpl userServiceImpl,
			ResourceImpl resourceImpl, EmailService emailService) {
		this.reservationImpl = reservationImpl;
		this.userServiceImpl = userServiceImpl;
		this.resourceImpl = resourceImpl;
		this.emailService = emailService;
	}

	@GetMapping(LIST)
	public ModelAndView getReservationList() {
		ModelAndView modelAndView = new ModelAndView(RESERVATION_PAGE);
		List<Reservation> reservations = reservationImpl.findAll();
		Map<Long, String> users = userServiceImpl.findAllUsers().stream()
				.collect(Collectors.toMap(User::getUserId, User::getUserName));
		Map<Long, String> resources = resourceImpl.findAll().stream()
				.filter(Resource::isActive)
				.collect(Collectors.toMap(Resource::getResourceId, Resource::getResourceName));

		List<ReservationView> reservationsView = reservations.stream()
				.map(e -> getView(e, users, resources))
				.collect(Collectors.toList());
		modelAndView.addObject(VIEW, reservationsView);
		modelAndView.addObject("dropdown", resources);
		modelAndView.addObject(FORM, new ReservationForm());
		return modelAndView;
	}

	private ReservationView getView(Reservation reservation, Map<Long, String> users, Map<Long, String> resources) {
		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
		DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
		return new ReservationView(reservation.getReservationId(), users.get(reservation.getUserId()),
				resources.get(reservation.getResourceId()),
				reservation.getStartDate().toLocalDate().format(dateFormatter),
				reservation.getStartDate().toLocalTime().format(timeFormatter),
				reservation.getEndDate().toLocalDate().format(dateFormatter),
				reservation.getEndDate().toLocalTime().format(timeFormatter));
	}

	@GetMapping(EDIT)
	@ResponseBody
	public ReservationForm getById(@RequestParam(name = "resouceId") Long resourceId) {
		return null;

	}

	@PostMapping(SAVE)
	public ModelAndView save(@ModelAttribute(FORM) ReservationForm form, RedirectAttributes redirectAttributes) {
		clearNotices();
		Reservation reservation = form.transform(null);
		UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		reservation.setUserId(user.getUserId());
		try {
			reservation.sanitizeAndValidate();
			reservationImpl.save(reservation);
			addSuccessNotice("The resource has been successfully booked.");
			displayNotice(redirectAttributes);
			return new ModelAndView("redirect:" + RESERVATION_URI + LIST);
		} catch (ValidationException e) {
			ModelAndView modelAndView = new ModelAndView(RESERVATION_PAGE);
			List<Reservation> reservations = reservationImpl.findAll();
			Map<Long, String> users = userServiceImpl.findAllUsers().stream()
					.collect(Collectors.toMap(User::getUserId, User::getUserName));
			Map<Long, String> resources = resourceImpl.findAll().stream()
					.filter(Resource::isActive)
					.collect(Collectors.toMap(Resource::getResourceId, Resource::getResourceName));

			List<ReservationView> reservationsView = reservations.stream()
					.map(e1 -> getView(e1, users, resources))
					.collect(Collectors.toList());
			modelAndView.addObject(VIEW, reservationsView);
			modelAndView.addObject("dropdown", resources);
			Gson gson = new Gson();
			modelAndView.addObject(JS_FORM, gson.toJson(form));
			displayModalNotice(modelAndView);
			return modelAndView;
		}
		
	}

}
