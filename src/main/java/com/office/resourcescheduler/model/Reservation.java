package com.office.resourcescheduler.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.office.resourcescheduler.errorhandler.ValidationException;
import com.office.resourcescheduler.service.ReservationImpl;
import com.office.resourcescheduler.service.ResourceImpl;
import com.office.resourcescheduler.service.UserServiceImpl;
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

	private List<String> validateDateTime() {
		List<String> errors = new ArrayList<>();
		if (getStartDate() == null) {
			errors.add("Please select a valid start date and time.");
		}

		if (getEndDate() == null) {
			errors.add("Please select a valid end date and time.");
		}
		return errors;
    }

	@Override
	public void validate(Void variable) throws ValidationException {
	    List<String> errors = new ArrayList<>();

	    errors.addAll(validateDateTime());
	    
	    if(errors.isEmpty()) {
	    	if(getEndDate().isBefore(getStartDate())) {
	    		errors.add("End date and time cannot be before start date and time.");
	    	}

			if (getStartDate().until(getEndDate(), ChronoUnit.MINUTES) < 10
					&& getStartDate().toLocalTime().until(getEndDate().toLocalTime(), ChronoUnit.MINUTES) < 10) {
				errors.add("Difference between end date and start date cannot be less than 10 minutes");
			}

			Resource resource = ResourceImpl.getInstance().findById(getResourceId()).orElseThrow();
			User user = UserServiceImpl.getInstance().findById(getUserId()).orElseThrow();

			if (isAllowedToSaveForProbation(user, resource)) {
				errors.add("Employees in probation cannot book " + resource.getResourceName() + ".");
			}
			
			if (isReservationUnderLimit(getStartDate(), getEndDate(), resource)) {
				errors.add(errorMessage(resource));
			}

			List<Reservation> reservations = ReservationImpl.getInstance().findAllByResourceId(getResourceId());
			if (isAllowedToSaveMultipleRecordsInADay(resource, getStartDate(), getEndDate(), reservations)) {
				errors.add("Cannot reserve the resource more than once");
			}
			
			if (resource.getMaxUserBookings() == null && isReserved(getStartDate(), getEndDate(), reservations)) {
				errors.add("The resource is already booked for selected date and time.");
			} else if (hasMoreUsers(reservations, getStartDate(), getEndDate(), resource)) {
				errors.add("The resource cannot be booked by more than " + resource.getMaxUserBookings()
						+ " users at the same time.");
			}
	    }
	    if(!errors.isEmpty()) {
	    	throw new ValidationException(errors);
	    }
	}

	private boolean isAllowedToSaveForProbation(User user, Resource resource) {
		return !user.isPermanent() && !resource.isPermanentEmployee();
	}

	private boolean isReservationUnderLimit(LocalDateTime startTime, LocalDateTime endTime, Resource resource) {

		return resource.getTimeLimit() != null && resource.getTimeLimit() != 0
				&& endTime.toLocalTime().get(ChronoField.MINUTE_OF_DAY)
						- startTime.toLocalTime().get(ChronoField.MINUTE_OF_DAY) > resource.getTimeLimit();
	}
	
	private String errorMessage(Resource resource) {
		int hours = (int) resource.getTimeLimit() / 60;
		int minutes = (int) resource.getTimeLimit() % 60;

		StringBuilder errorMessage = new StringBuilder();
		errorMessage.append("Resource cannot be booked beyond ");

		if (hours != 0) {
			errorMessage.append(hours + " hours ");
		}
		if (minutes != 0) {
			errorMessage.append(minutes + " minutes");
		}
		return errorMessage.toString();
	}

	  private boolean isAllowedToSaveMultipleRecordsInADay(Resource resource,
			LocalDateTime startDateTime, LocalDateTime endDateTime, List<Reservation> reservations) {

		return !resource.isAllowedMultiple() && reservations
				.stream()
				.filter(e -> e.getUserId().equals(getUserId()))
				.anyMatch(e -> {
					boolean isAllowedMultiple = ((startDateTime.isAfter(e.getStartDate().toLocalDate().atStartOfDay())
							&& startDateTime.isBefore(e.getEndDate().toLocalDate().atTime(LocalTime.MAX)))
							|| (endDateTime.isAfter(e.getStartDate().toLocalDate().atStartOfDay())
									&& endDateTime.isBefore(e.getEndDate().toLocalDate().atTime(LocalTime.MAX))))
							&& getResourceId() == e.getResourceId();
					if (getReservationId() != null) {
						isAllowedMultiple = isAllowedMultiple && !e.getReservationId().equals(getReservationId());
					}
					return isAllowedMultiple;
				});
	}

	private boolean isReserved(LocalDateTime startDate2, LocalDateTime endDate2, List<Reservation> reservations) {
		return reservations.stream()
				.anyMatch(e -> {

            boolean isBooked = (isInRange(startDate2, e.getStartDate(), e.getEndDate()) || 
                isInRange(endDate2, e.getStartDate(), e.getEndDate()) ||
                isInRange(e.getStartDate(), startDate2, endDate2) ||
                isInRange(e.getEndDate(), startDate2, endDate2)) && (e.getResourceId().equals(getResourceId()));

			if (getReservationId() != null) {
				isBooked = isBooked && !(getReservationId().equals(e.getReservationId()));
			}
			return isBooked;
		});
	}
	
    private boolean isInRange(LocalDateTime dateTime, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        LocalDate date = dateTime.toLocalDate();
        LocalTime time = dateTime.toLocalTime();

        LocalDate startDate = startDateTime.toLocalDate();
        LocalTime startTime = startDateTime.toLocalTime();

        LocalDate endDate = endDateTime.toLocalDate();
        LocalTime endTime = endDateTime.toLocalTime();

        return (date.equals(startDate) || date.isAfter(startDate)) && (date.equals(endDate) || date.isBefore(endDate))
            && ((time.isAfter(startTime) || time.equals(startTime)) 
            && (time.isBefore(endTime) || time.equals(endTime)));
    }

	private boolean hasMoreUsers(List<Reservation> reservations, LocalDateTime startDateTime, LocalDateTime endDateTime,
			Resource resource) {
		long count = reservations.stream()
				.filter(e -> {

					LocalDate startDate = startDateTime.toLocalDate();
					LocalTime startTime = startDateTime.toLocalTime();
					LocalDate endDate = endDateTime.toLocalDate();
					LocalTime endTime = endDateTime.toLocalTime();
		
					LocalDate resourceStartDate = e.getStartDate().toLocalDate();
					LocalTime resourceStartTime = e.getStartDate().toLocalTime();
					LocalDate resourceEndDate = e.getEndDate().toLocalDate();
					LocalTime resourceEndTime = e.getEndDate().toLocalTime();
		
                    boolean isBooked = (isInRange(startDateTime, e.getStartDate(), e.getEndDate()) || 
                        isInRange(endDateTime, e.getStartDate(), e.getEndDate()) ||
                        isInRange(e.getStartDate(), startDateTime, endDateTime) ||
                        isInRange(e.getEndDate(), startDateTime, endDateTime)) 
                        && (e.getResourceId().equals(getResourceId()));
		
					if (getReservationId() != null) {
						isBooked = isBooked && !(getReservationId().equals(e.getReservationId()));
					}
					return isBooked;
				}).count();
		return resource.getMaxUserBookings() != null && count >= resource.getMaxUserBookings();
	}
}
