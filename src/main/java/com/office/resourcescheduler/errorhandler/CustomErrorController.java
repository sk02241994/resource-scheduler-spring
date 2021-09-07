package com.office.resourcescheduler.errorhandler;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.office.resourcescheduler.service.EmailService;
import com.office.resourcescheduler.util.Constants;

@Controller
public class CustomErrorController implements ErrorController {

	private EmailService service;

	public CustomErrorController(EmailService service) {
		this.service = service;
	}

	@GetMapping("/error")
	public ModelAndView handleError(HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView("error");
		modelAndView.addObject("status", request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE));
		sendErrorMail(request);
		return modelAndView;
	}

	private void sendErrorMail(HttpServletRequest request) {
		String message = (String) request.getAttribute(RequestDispatcher.ERROR_MESSAGE);
		String url = request.getRequestURL().toString();
		String status =  ((Integer)request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE)).toString();
		StringBuilder errorMessage = new StringBuilder();
		errorMessage.append("Following is the cause of ");
		errorMessage.append(status + "\n");
		errorMessage.append(message + "\n");
		errorMessage.append(url);
		service.sendMail(Constants.ERROR_RECEIVER, "From error Controller", errorMessage.toString());
	}
}
