package com.office.resourcescheduler.controller;

public class ReservationView {

	private Long reservationId;
	private String userName;
	private String resourceName;
	private String startDate;
	private String startTime;
	private String endDate;
	private String endTime;
    private Long userId;
    private Long resourceId;

	public Long getReservationId() {
		return reservationId;
	}

	public void setReservationId(Long reservationId) {
		this.reservationId = reservationId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getResourceName() {
		return resourceName;
	}

	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
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

    public void setUserId(Long userId){
        this.userId = userId;
    }

    public Long getUserId(){
        return userId;
    }

    public void setResourceId(Long resourceId){
        this.resourceId = resourceId;
    }

    public Long getResourceId(){
        return resourceId;
    }

	public ReservationView(Long reservationId, String userName, String resourceName, String startDate, String startTime,
			String endDate, String endTime, Long userId, Long resourceId) {
		this.reservationId = reservationId;
		this.userName = userName;
		this.resourceName = resourceName;
		this.startDate = startDate;
		this.startTime = startTime;
		this.endDate = endDate;
		this.endTime = endTime;
        this.userId = userId;
        this.resourceId = resourceId;
	}

}
