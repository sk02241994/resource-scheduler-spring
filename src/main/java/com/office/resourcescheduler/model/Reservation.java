package com.office.resourcescheduler.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.office.resourcescheduler.errorhandler.ValidationException;
import com.office.resourcescheduler.util.PojoDeletable;
import com.office.resourcescheduler.util.PojoSavable;

@Entity()
@Table(name = "rs_reservation")
public class Reservation implements PojoSavable<Void>, PojoDeletable<Void> {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "rs_reservation_id", length = 11)
	private Long reservationId;

	@Column(name = "rs_user_id", length = 11, nullable = false)
	private Long userId;

	@Column(name = "rs_resource_id", length = 11, nullable = false)
	private Long resourceId;

	@Column(name = "start_date")
	private LocalDateTime startDate;

	@Column(name = "end_date")
	private LocalDateTime endDate;

	public Reservation() {

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

	public LocalDateTime getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDateTime startDate) {
		this.startDate = startDate;
	}

	public LocalDateTime getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDateTime endDate) {
		this.endDate = endDate;
	}

	public Reservation(Long reservationId, Long userId, Long resourceId, LocalDateTime startDate,
			LocalDateTime endDate) {
		this.reservationId = reservationId;
		this.userId = userId;
		this.resourceId = resourceId;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	@Override
	public void validateDelete(Void variable) throws ValidationException {
		// TODO Auto-generated method stub

	}

	@Override
	public void sanitize() {
		// TODO Auto-generated method stub

	}

	@Override
	public void validate(Void variable) throws ValidationException {
		// TODO Auto-generated method stub

	}

}