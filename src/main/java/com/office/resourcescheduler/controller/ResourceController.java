package com.office.resourcescheduler.controller;

import java.util.List;

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
import com.office.resourcescheduler.model.Resource;
import com.office.resourcescheduler.service.ResourceImpl;
import com.office.resourcescheduler.util.NoticeInterface;

@Controller
@RequestMapping(ResourceController.RESOURCE_URI)
public class ResourceController implements NoticeInterface {

	public static final String RESOURCE_URI = "/resource";
	public static final String SAVE = "/save";
	public static final String LIST = "/list";
	public static final String EDIT = "/edit";
	public static final String DELETE = "/delete";
	public static final String RESOURCE_PAGE = "resource";
	public static final String VIEW = "view";
	public static final String FORM = "form";
	public static final String JS_FORM = "jsForm";

	private ResourceImpl resourceImpl;

	@Autowired
	public ResourceController(ResourceImpl resourceImpl) {
		this.resourceImpl = resourceImpl;
	}

	@GetMapping(LIST)
	public ModelAndView getList() {
		ModelAndView modelAndView = new ModelAndView(RESOURCE_PAGE);
		List<Resource> resources = resourceImpl.findAll();
		modelAndView.addObject(VIEW, resources);
		modelAndView.addObject(FORM, new ResourceForm());
		return modelAndView;
	}

	@GetMapping(EDIT)
	@ResponseBody
	public ResourceForm getById(@RequestParam(name = "resourceId") Long resourceId) throws Exception {
		Resource resource = resourceImpl.findById(resourceId).orElseThrow(Exception::new);
		Integer timeLimit = resource.getTimeLimit();
		Integer timeLimitHours = timeLimit != null ? Math.floorDiv(resource.getTimeLimit(), 60) : null;
		Integer timeLimitMinutes = timeLimit != null ? Math.floorMod(resource.getTimeLimit(), 60) : null;
		return new ResourceForm(resource.getResourceId(), resource.getResourceName(), resource.getDescription(),
				timeLimitHours, timeLimitMinutes, resource.getMaxUserBookings(), resource.isActive(),
				resource.isAllowedMultiple(), resource.isPermanentEmployee());
	}

	@PostMapping(SAVE)
	public ModelAndView saveResource(@ModelAttribute(FORM) ResourceForm form, RedirectAttributes redirectAttributes) {
		clearNotices();
		Resource resource = form.transform(null);
		try {
			resource.sanitizeAndValidate();
			resourceImpl.save(resource);
			addSuccessNotice("Resource has been saved successfully");
			displayNotice(redirectAttributes);
			return new ModelAndView("redirect:" + RESOURCE_URI + LIST);
		} catch (ValidationException e) {
			addModalErrorNotice(e.getError());
			ModelAndView mv = new ModelAndView(RESOURCE_PAGE);
			mv.addObject(VIEW, resourceImpl.findAll());
			Gson gson = new Gson();
			mv.addObject(JS_FORM, gson.toJson(form));
			displayModalNotice(mv);
			return mv;
		}
	}

	@GetMapping(DELETE)
	public ModelAndView delete(@RequestParam(name = "resourceId") Long resourceId,
			RedirectAttributes redirectAttributes) {
		clearNotices();
		ModelAndView modelAndView = new ModelAndView("redirect:" + RESOURCE_URI + LIST);
		Resource resource = resourceImpl.findById(resourceId)
				.orElseThrow();
		try {
			resource.validateDelete(null);
			resourceImpl.deleteById(resource.getResourceId());
			addSuccessNotice("Resource has been deleted successfully");
		} catch (ValidationException e) {
			addErrorNotice(e.getError());
		}
		displayNotice(redirectAttributes);
		return modelAndView;
	}
}
