package com.office.resourcescheduler.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang3.StringUtils;

import com.office.resourcescheduler.errorhandler.ValidationException;
import com.office.resourcescheduler.util.PojoDeletable;
import com.office.resourcescheduler.util.PojoSavable;

@Entity
@Table(name = "rs_resource")
public class Resource implements PojoSavable<Void>, PojoDeletable<Void> {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "rs_resource_id", length = 11)
	private Long resourceId;

	@Column(name = "resource_name", length = 50, nullable = false)
	private String resourceName;
	
	@Column(name = "description")
	private String description;

	@Column(name = "is_active")
	private boolean isActive;

	@Column(name = "time_limit")
	private Integer timeLimit;

	@Column(name = "is_allowed_multiple")
	private boolean isAllowedMultiple;

	@Column(name = "max_user_bookings")
	private Integer maxUserBookings;

	@Column(name = "is_permaneent_employee")
	private boolean isPermanentEmployee;

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
		this.resourceName= resourceName ;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public Integer getTimeLimit() {
		return timeLimit;
	}

	public void setTimeLimit(Integer timeLimit) {
		this.timeLimit = timeLimit;
	}

	public boolean isAllowedMultiple() {
		return isAllowedMultiple;
	}

	public void setAllowedMultiple(boolean isAllowedMultiple) {
		this.isAllowedMultiple = isAllowedMultiple;
	}

	public Integer getMaxUserBookings() {
		return maxUserBookings;
	}

	public void setMaxUserBookings(Integer maxUserBookings) {
		this.maxUserBookings = maxUserBookings;
	}

	public boolean isPermanentEmployee() {
		return isPermanentEmployee;
	}

	public void setPermanentEmployee(boolean isPermanentEmployee) {
		this.isPermanentEmployee = isPermanentEmployee;
	}


	public Resource(Long resourceId, String resourceName, String description, boolean isActive, Integer timeLimit,
			boolean isAllowedMultiple, Integer maxUserBookings, boolean isPermanentEmployee) {
		this.resourceId = resourceId;
		this.resourceName = resourceName;
		this.description = description;
		this.isActive = isActive;
		this.timeLimit = timeLimit;
		this.isAllowedMultiple = isAllowedMultiple;
		this.maxUserBookings = maxUserBookings;
		this.isPermanentEmployee = isPermanentEmployee;
	}

	public Resource() {
	}

	@Override
	public void sanitize() {
		setResourceName(StringUtils.trimToNull(getResourceName()));
		setDescription(StringUtils.trimToNull(getDescription()));
	}

	@Override
	public void validate(Void variable) throws ValidationException {
		List<String> errors = new ArrayList<>();

		if (StringUtils.isBlank(getResourceName())) {
			errors.add("Resource name cannot be empty");
		}

		if (!errors.isEmpty()) {
			throw new ValidationException(errors);
		}
	}

	@Override
	public void validateDelete(Void variable) throws ValidationException {

	}

}
