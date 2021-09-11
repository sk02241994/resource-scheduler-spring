package com.office.resourcescheduler.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.office.resourcescheduler.errorhandler.ValidationException;
import com.office.resourcescheduler.model.ResetToken;
import com.office.resourcescheduler.model.User;
import com.office.resourcescheduler.service.EmailService;
import com.office.resourcescheduler.service.ResetTokenImpl;
import com.office.resourcescheduler.service.UserServiceImpl;
import com.office.resourcescheduler.util.Constants;
import com.office.resourcescheduler.util.NoticeInterface;
import com.office.resourcescheduler.util.PasswordValidate;

@Controller
@RequestMapping(ResetPasswordController.RESET_URI)
public class ResetPasswordController implements NoticeInterface {

	private static final String REDIRECT = "redirect:";
	private static final String RESET_FORM = "reset_form";
	public static final String RESET_URI = "/reset";
	private static final String FORGOT_PASSWORD = "/forgotPassword";
	private static final String FORGOT_PASSWORD_SEND = "/forgotPasswordSend";
	private static final String VERIFY_TOKEN = "/verifyToken";
	private static final String VERIFIED_TOKEN = "/verifiedToken";
	private static final String CHANGE_PASSWORD = "/changePassword";
	private static final String SAVE_CHANGED_PASSWORD = "/saveChangePassword";
	private static final String RESET_PASSOWORD = "reset-password";
	private static final String VERIFY_TOKEN_PAGE = "verify-token";
	private static final String CHANGE_PASSWORD_PAGE = "change-password";

	private static final String FORM = "form";

	private EmailService emailService;
	private UserServiceImpl userServiceImpl;
	private ResetTokenImpl resetPasswordImpl;
	private PasswordEncoder passwordEncoder;

	@Autowired
	public ResetPasswordController(EmailService emailService, UserServiceImpl userServiceImpl,
			ResetTokenImpl resetPasswordImpl, PasswordEncoder passwordEncoder) {
		this.emailService = emailService;
		this.userServiceImpl = userServiceImpl;
		this.resetPasswordImpl = resetPasswordImpl;
		this.passwordEncoder = passwordEncoder;
	}

	@GetMapping(FORGOT_PASSWORD)
	public ModelAndView forgotPassword() {
		ModelAndView mv = new ModelAndView(RESET_PASSOWORD);
		mv.addObject(FORM, new ResetForm());
		return mv;
	}

	@PostMapping(FORGOT_PASSWORD_SEND)
	public ModelAndView sendToken(@ModelAttribute(FORM) ResetForm resetForm, RedirectAttributes redirectAttributes) {
		clearNotices();
		ModelAndView mv = new ModelAndView(REDIRECT + RESET_URI + VERIFY_TOKEN);
		User user = userServiceImpl.findByEmailAddress(resetForm.getEmailAddress());
		if (user == null) {
			throw new UsernameNotFoundException("User not found.");
		}
		String token = UUID.randomUUID().toString();
		resetPasswordImpl.deleteByUserid(user.getUserId());
		ResetToken resetToken = resetPasswordImpl.save(new ResetToken(token, user));
		emailService.sendMail(user.getEmailAddress(), "Password reset", mailContent(resetToken));
		addSuccessNotice("Token has been sent on you email. And will expire in 5 minutes.");
		redirectAttributes.addFlashAttribute(RESET_FORM, resetForm);
		displayNotice(redirectAttributes);
		return mv;
	}

	@GetMapping(VERIFY_TOKEN)
	public ModelAndView verifyToken(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView(VERIFY_TOKEN_PAGE);
		Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(request);
		ResetForm resetForm = null;
		if (inputFlashMap != null) {
			resetForm = (ResetForm) inputFlashMap.get(RESET_FORM);
		}
		mv.addObject(FORM, resetForm);
		return mv;
	}

	@PostMapping(VERIFIED_TOKEN)
	public ModelAndView submitToken(@ModelAttribute(FORM) ResetForm resetForm, RedirectAttributes redirectAttributes) {
		clearNotices();
		ModelAndView mv = new ModelAndView();
		ResetToken resetToken = resetPasswordImpl.findByTokenId(resetForm.getToken());

		if (isValidToken(resetToken, resetForm)) {
			displayNotice(redirectAttributes);
			redirectAttributes.addFlashAttribute(RESET_FORM, resetForm);
			mv.setViewName(REDIRECT + RESET_URI + VERIFY_TOKEN);
			return mv;
		}
		redirectAttributes.addFlashAttribute(RESET_FORM, resetForm);
		mv.setViewName(REDIRECT + RESET_URI + CHANGE_PASSWORD);
		return mv;
	}

	@GetMapping(CHANGE_PASSWORD)
	public ModelAndView changePassword(HttpServletRequest request) {
		clearNotices();
		ModelAndView mv = new ModelAndView(CHANGE_PASSWORD_PAGE);

		Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(request);
		ResetForm resetForm = null;
		if (inputFlashMap != null) {
			resetForm = (ResetForm) inputFlashMap.get(RESET_FORM);
		}
		mv.addObject(FORM, resetForm);

		return mv;
	}

	@PostMapping(SAVE_CHANGED_PASSWORD)
	public ModelAndView goToLogin(@ModelAttribute(FORM) ResetForm resetForm, RedirectAttributes redirectAttributes) {
		clearNotices();
		ModelAndView mv = new ModelAndView(REDIRECT + Constants.LOGIN);
		try {
			PasswordValidate.validatePassword(resetForm.getPassword(), resetForm.getConfirmPassword());
			userServiceImpl.updatePassword(passwordEncoder.encode(resetForm.getPassword()), resetForm.getEmailAddress());
		} catch (ValidationException e) {
			addErrorNotice(e.getError());
			displayNotice(redirectAttributes);
			mv.setViewName(REDIRECT + RESET_URI + CHANGE_PASSWORD);
			return mv;
		}
		mv.setViewName(REDIRECT + Constants.LOGIN);
		return mv;
	}

	public String mailContent(ResetToken resetToken) {
		StringBuilder builder = new StringBuilder();
		builder.append("Your token is: " + resetToken.getToken());
		builder.append(" and will expire on : "
				+ resetToken.getExpiryDate().format(DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss")));
		return builder.toString();
	}

	private boolean isValidToken(ResetToken resetToken, ResetForm resetForm) {
		boolean hasTokenExpired = false;
		if (resetToken == null) {
			hasTokenExpired = true;
			addErrorNotice("Please enter valid token");
		}
		if (resetToken != null && LocalDateTime.now().isAfter(resetToken.getExpiryDate())) {
			hasTokenExpired = true;
			addErrorNotice("The token has expired.");
		}
		return hasTokenExpired;
	}
}
