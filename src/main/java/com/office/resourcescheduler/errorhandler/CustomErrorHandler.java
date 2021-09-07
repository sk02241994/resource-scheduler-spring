
package com.office.resourcescheduler.errorhandler;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import com.office.resourcescheduler.service.EmailService;
import com.office.resourcescheduler.util.Constants;

@ControllerAdvice
public class CustomErrorHandler implements ErrorController {

	private static final String ERROR_PAGE = "exception-handler";
	private static final String MESSAGE = "message";

	private EmailService service;

	public CustomErrorHandler(EmailService service) {
		this.service = service;
	}

	@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Page not found")
	@ResponseBody
	public void notFound(HttpServletRequest request) {

	}

	@ExceptionHandler(Exception.class)
	public ModelAndView handleError(HttpServletRequest request, Exception ex) {
		ModelAndView notFoundModelAndView = new ModelAndView(ERROR_PAGE);
		notFoundModelAndView.addObject(MESSAGE, ex.getMessage());
		sendErrorMail(ex);
		return notFoundModelAndView;
	}

	private void sendErrorMail(Exception ex) {
		String message = ExceptionUtils.getStackTrace(ex);
		StringBuilder errorMessage = new StringBuilder();
		errorMessage.append("Following is the cause of the exception");
		errorMessage.append(message);
		service.sendMail(Constants.ERROR_RECEIVER, "From error Controller", errorMessage.toString());
	}
}
