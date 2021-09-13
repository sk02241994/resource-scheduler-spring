package com.office.resourcescheduler.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.mail.MessagingException;

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
import com.office.resourcescheduler.util.Roles;

@Controller
@RequestMapping(ReservationController.RESERVATION_URI)
public class ReservationController implements NoticeInterface {

	private static final String HH_MM = "HH:mm";
	private static final String MM_DD_YYYY = "MM/dd/yyyy";
	public static final String RESERVATION_URI = "/reservation";
	public static final String LIST = "/list";
	public static final String SAVE = "/save";
	public static final String EDIT = "/edit";
	public static final String DELETE = "/delete";
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
		Map<Long, String> resources = resourceImpl.findAll().stream().filter(Resource::isActive)
				.collect(Collectors.toMap(Resource::getResourceId, Resource::getResourceName));

		List<ReservationView> reservationsView = reservations.stream().map(e -> getView(e, users, resources))
				.collect(Collectors.toList());
		modelAndView.addObject(VIEW, reservationsView);
		modelAndView.addObject("dropdown", resources);
		modelAndView.addObject(FORM, new ReservationForm());
		return modelAndView;
	}

	private ReservationView getView(Reservation reservation, Map<Long, String> users, Map<Long, String> resources) {
		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(MM_DD_YYYY);
		DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern(HH_MM);
		return new ReservationView(reservation.getReservationId(), users.get(reservation.getUserId()),
				resources.get(reservation.getResourceId()),
				reservation.getStartDate().toLocalDate().format(dateFormatter),
				reservation.getStartDate().toLocalTime().format(timeFormatter),
				reservation.getEndDate().toLocalDate().format(dateFormatter),
				reservation.getEndDate().toLocalTime().format(timeFormatter));
	}

	@GetMapping(EDIT)
	@ResponseBody
	public ReservationForm getById(@RequestParam(name = "reservationId") Long resourceId) {
		Reservation reservation = reservationImpl.findById(resourceId).orElseThrow();

		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(MM_DD_YYYY);
		DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern(HH_MM);

		return new ReservationForm(reservation.getReservationId(), reservation.getUserId(), reservation.getResourceId(),
				reservation.getStartDate().toLocalDate().format(dateFormatter),
				reservation.getStartDate().toLocalTime().format(timeFormatter),
				reservation.getEndDate().toLocalDate().format(dateFormatter),
				reservation.getEndDate().toLocalTime().format(timeFormatter));

	}

	@PostMapping(SAVE)
	public ModelAndView save(@ModelAttribute(FORM) ReservationForm form, RedirectAttributes redirectAttributes)
			throws MessagingException, IOException {
		clearNotices();
		Long userId = null;
		UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (form.getReservationId() != null) {
			Reservation reservation = reservationImpl.findById(form.getReservationId()).orElseThrow();
			userId = reservation.getUserId();
		} else {
			userId = user.getUserId();
		}

		form.setUserId(userId);
		Reservation reservation = form.transform(null);

		try {
			reservation.sanitizeAndValidate();
			Reservation savedReservation = reservationImpl.save(reservation);
			sendMail(savedReservation);
			addSuccessNotice("The resource has been successfully booked.");
			displayNotice(redirectAttributes);
			return new ModelAndView("redirect:" + RESERVATION_URI + LIST);
		} catch (ValidationException e) {
            addModalErrorNotice(e.getError());
			ModelAndView modelAndView = new ModelAndView(RESERVATION_PAGE);
			List<Reservation> reservations = reservationImpl.findAll();
			Map<Long, String> users = userServiceImpl.findAllUsers().stream()
					.collect(Collectors.toMap(User::getUserId, User::getUserName));
			Map<Long, String> resources = resourceImpl.findAll().stream().filter(Resource::isActive)
					.collect(Collectors.toMap(Resource::getResourceId, Resource::getResourceName));

			List<ReservationView> reservationsView = reservations.stream().map(e1 -> getView(e1, users, resources))
					.collect(Collectors.toList());
			modelAndView.addObject(VIEW, reservationsView);
			modelAndView.addObject("dropdown", resources);
			Gson gson = new Gson();
			modelAndView.addObject(JS_FORM, gson.toJson(form));
			displayModalNotice(modelAndView);
			return modelAndView;
		}

	}

	private void sendMail(Reservation savedReservation) throws MessagingException, IOException {

		User user = userServiceImpl.findById(savedReservation.getUserId()).orElseThrow();
		Resource resource = resourceImpl.findById(savedReservation.getResourceId()).orElseThrow();
		
		String[] emails = userServiceImpl.findAllUsers().stream()
				.filter(e -> e.getRole() == Roles.ADMIN)
				.filter(User::isActive)
				.map(User::getEmailAddress)
				.collect(Collectors.toList())
				.stream()
				.toArray(String[]::new);

		emailService.sendMail(emails, "Resource Booked", getMessage(savedReservation, user, resource));
		emailService.sendIcsAttachment(user.getEmailAddress(), "Resource used notification",
				"You have booked " + resource.getResourceName(), savedReservation.getReservationId());

	}

	private String getMessage(Reservation savedReservation, User user, Resource resource) {
		StringBuilder message = new StringBuilder();
		message.append("Dear Admin,\n\n");
		message.append("This is to notify that the user " + user.getUserName() + " has booked "
				+ resource.getResourceName() + "\n");
		message.append("From: " + formatDate(savedReservation.getStartDate().toLocalDate()) + " "
				+ formatTime(savedReservation.getStartDate().toLocalTime()) + "\n");
		message.append("To: " + formatDate(savedReservation.getEndDate().toLocalDate()) + " "
				+ formatTime(savedReservation.getEndDate().toLocalTime()));
		return message.toString();
	}

	private String formatDate(LocalDate date) {
		return date.format(DateTimeFormatter.ofPattern(MM_DD_YYYY));
	}

	private String formatTime(LocalTime time) {
		return time.format(DateTimeFormatter.ofPattern(HH_MM));
	}

	@GetMapping(DELETE)
	public ModelAndView delete(@RequestParam(name = "reservationId") Long reservationId,
			RedirectAttributes redirectAttributes) {
		clearNotices();
		ModelAndView modelAndView = new ModelAndView("redirect:" + RESERVATION_URI + LIST);
		Reservation reservation = reservationImpl.findById(reservationId).orElseThrow();
		try {
			reservation.validateDelete(null);
			reservationImpl.delete(reservation.getReservationId());
			addSuccessNotice("Resource has been deleted successfully");
		} catch (ValidationException e) {
			addErrorNotice(e.getError());
		}
		displayNotice(redirectAttributes);
		return modelAndView;
	}
}
