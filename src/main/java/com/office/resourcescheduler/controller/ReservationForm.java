package com.office.resourcescheduler.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import com.office.resourcescheduler.model.Reservation;
import com.office.resourcescheduler.util.FormTransform;

public class ReservationForm implements FormTransform<Reservation, Void> {

	private Long reservationId;
	private Long userId;
	private Long resourceId;
	private String startDate;
	private String startTime;
	private String endDate;
	private String endTime;

	public ReservationForm() {
	}

	public ReservationForm(Long reservationId, Long userId, Long resourceId, String startDate, String startTime,
			String endDate, String endTime) {
		this.reservationId = reservationId;
		this.userId = userId;
		this.resourceId = resourceId;
		this.startDate = startDate;
		this.startTime = startTime;
		this.endDate = endDate;
		this.endTime = endTime;
	}

	public Long getReservationId() {
		return reservationId;
	}

	public void setReservationId(Long reservationId) {
		this.reservationId = reservationId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getResourceId() {
		return resourceId;
	}

	public void setResourceId(Long resourceId) {
		this.resourceId = resourceId;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	@Override
	public Reservation transform(Void param) {
		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
		DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
		LocalDateTime startDateTime = LocalDateTime.of(LocalDate.parse(startDate, dateFormatter),
				LocalTime.parse(startTime, timeFormatter));
		LocalDateTime endDateTime = LocalDateTime.of(LocalDate.parse(endDate, dateFormatter),
				LocalTime.parse(endTime, timeFormatter));

		return new Reservation(reservationId, userId, resourceId, startDateTime, endDateTime);
	}

}
