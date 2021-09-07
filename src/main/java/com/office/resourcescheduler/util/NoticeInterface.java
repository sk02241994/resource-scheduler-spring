package com.office.resourcescheduler.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.gson.Gson;

public interface NoticeInterface {

	List<String> errors = new ArrayList<>();
	List<String> success = new ArrayList<>();

	List<String> modalErrors = new ArrayList<>();
	List<String> modalSuccess = new ArrayList<>();

	default void addErrorNotice(String message) {
		errors.add(message);
	}

	default void addErrorNotice(Collection<String> messages) {
		errors.addAll(messages);
	}

	default void addSuccessNotice(String message) {
		success.add(message);
	}

	default void clearNotices() {
		errors.clear();
		success.clear();
		modalErrors.clear();
		modalSuccess.clear();
	}

	default void displayNotice(ModelAndView modelAndView) {
		modelAndView.addObject("error_message", errors);
		modelAndView.addObject("success_message", success);
		displayModalNotice(modelAndView);
	}

	default void addModalErrorNotice(String message) {
		modalErrors.add(message);
	}

	default void addModalErrorNotice(Collection<String> messages) {
		modalErrors.addAll(messages);
	}

	default void addModalSuccessNotice(String message) {
		modalSuccess.add(message);
	}

	default void displayModalNotice(ModelAndView modelAndView) {
		Gson errorGson = new Gson();
		Gson successGson = new Gson();
		modelAndView.addObject("error_message_modal", errorGson.toJson(modalErrors));
		modelAndView.addObject("successs_message_modal", successGson.toJson(modalSuccess));
	}

	default void displayNotice(RedirectAttributes redirectAttributes) {
		redirectAttributes.addFlashAttribute("success_message", success);
		redirectAttributes.addFlashAttribute("error_message", errors);
	}
	
	default void displayModalNotice(RedirectAttributes redirectAttributes) {
		Gson errorGson = new Gson();
		Gson successGson = new Gson();
		redirectAttributes.addFlashAttribute("successs_message_modal", successGson.toJson(modalSuccess));
		redirectAttributes.addFlashAttribute("error_message_modal", errorGson.toJson(modalErrors));
	}
	
}
