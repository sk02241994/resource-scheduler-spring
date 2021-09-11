package com.office.resourcescheduler.controller;

import com.office.resourcescheduler.model.Resource;
import com.office.resourcescheduler.util.FormTransform;

public class ResourceForm implements FormTransform<Resource, Void> {

	private Long resourceId;
	private String resourceName;
	private String description;
	private Integer timeLimitHours;
	private Integer timeLimitMinutes;
	private Integer maxUsersAllowed;
	private boolean isEnabled;
	private boolean isAllowedMultiple;
	private boolean isAllowEmpOnProbation;

	public Long getResourceId() {
		return resourceId;
	}

	public void setResourceId(Long resourceId) {
		this.resourceId = resourceId;
	}

	public String getResourceName() {
		return resourceName;
	}

	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getTimeLimitHours() {
		return timeLimitHours;
	}

	public void setTimeLimitHours(Integer timeLimitHours) {
		this.timeLimitHours = timeLimitHours;
	}

	public Integer getTimeLimitMinutes() {
		return timeLimitMinutes;
	}

	public void setTimeLimitMinutes(Integer timeLimitMinutes) {
		this.timeLimitMinutes = timeLimitMinutes;
	}

	public Integer getMaxUsersAllowed() {
		return maxUsersAllowed;
	}

	public void setMaxUsersAllowed(Integer maxUsersAllowed) {
		this.maxUsersAllowed = maxUsersAllowed;
	}

	public boolean isEnabled() {
		return isEnabled;
	}

	public void setEnabled(boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

	public boolean isAllowedMultiple() {
		return isAllowedMultiple;
	}

	public void setAllowedMultiple(boolean isAllowedMultiple) {
		this.isAllowedMultiple = isAllowedMultiple;
	}

	public boolean isAllowEmpOnProbation() {
		return isAllowEmpOnProbation;
	}

	public void setAllowEmpOnProbation(boolean isAllowEmpOnProbation) {
		this.isAllowEmpOnProbation = isAllowEmpOnProbation;
	}

	public ResourceForm(Long resourceId, String resourceName, String description, Integer timeLimitHours,
			Integer timeLimitMinutes, Integer maxUsersAllowed, boolean isEnabled, boolean isAllowedMultiple,
			boolean isAllowEmpOnProbation) {
		this.resourceId = resourceId;
		this.resourceName = resourceName;
		this.description = description;
		this.timeLimitHours = timeLimitHours;
		this.timeLimitMinutes = timeLimitMinutes;
		this.maxUsersAllowed = maxUsersAllowed;
		this.isEnabled = isEnabled;
		this.isAllowedMultiple = isAllowedMultiple;
		this.isAllowEmpOnProbation = isAllowEmpOnProbation;
	}

	public ResourceForm() {
	}

	@Override
	public Resource transform(Void param) {

		Integer hoursToMinutes = timeLimitHours == null ? 0 : timeLimitHours * 60;
		Integer minutes = timeLimitMinutes == null ? 0 : timeLimitMinutes;
		Integer totalMinutes = hoursToMinutes + minutes;
		Integer timeLimit = totalMinutes == 0 ? null : totalMinutes;

		return new Resource(resourceId, resourceName, description, isEnabled, timeLimit, isAllowedMultiple,
				maxUsersAllowed, isAllowEmpOnProbation);

	}
}
