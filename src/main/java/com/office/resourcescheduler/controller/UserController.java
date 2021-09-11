package com.office.resourcescheduler.controller;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.office.resourcescheduler.model.User;
import com.office.resourcescheduler.service.UserServiceImpl;
import com.office.resourcescheduler.util.NoticeInterface;
import com.office.resourcescheduler.util.Roles;

@Controller
@RequestMapping(UserController.USER_URI)
public class UserController implements NoticeInterface {

	private static final String JS_FORM = "jsForm";
	public static final String USER_URI = "/user";
	public static final String SAVE = "/save";
	public static final String LIST = "/list";
	public static final String EDIT = "/edit";
	public static final String USER_PAGE = "user";
	public static final String VIEW = "view";
	public static final String FORM = "form";

	private UserServiceImpl userServiceImpl;

	@Autowired
	public UserController(UserServiceImpl userServiceImpl) {
		this.userServiceImpl = userServiceImpl;
	}

	@PostMapping(SAVE)
	public ModelAndView save(@ModelAttribute(FORM) UserForm form, RedirectAttributes redirectAttributes) {
        clearNotices();
		User user = form.transform(null);
		try {
			user.sanitizeAndValidate();
			userServiceImpl.save(user);
			addSuccessNotice("User details have been saved.");
			displayNotice(redirectAttributes);
			return new ModelAndView("redirect:" + USER_URI + LIST);
		} catch (ValidationException e) {
			addModalErrorNotice(e.getError());
			ModelAndView mv = new ModelAndView(USER_PAGE);
			mv.addObject(VIEW, userServiceImpl.findAllUsers());
			Gson gson = new Gson();
			mv.addObject(JS_FORM, gson.toJson(form));
			mv.addObject(FORM, form);
            displayModalNotice(mv);
			return mv;
		}

	}

	@GetMapping(LIST)
	public ModelAndView getList() throws Exception {
		ModelAndView mv = new ModelAndView(USER_PAGE);
		mv.addObject(VIEW, userServiceImpl.findAllUsers());
		mv.addObject(FORM, new UserForm());
		return mv;
	}

	@GetMapping(EDIT)
	@ResponseBody
	public UserForm getById(@RequestParam(name = "userId") Long userId) throws Exception {
		User user = userServiceImpl.findById(userId)
				.orElseThrow(Exception::new);
        return new UserForm(user.getUserId(), user.getUserName(), user.getEmailAddress(), user.isActive(),
                user.getRole() == Roles.ADMIN, user.isPermanent(), user.getGender().toString());
	}
}
