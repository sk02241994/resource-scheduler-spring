/**
 * Method for enabling and disabling the time input boxes if all day event is
 * selected.
 * 
 * @param checkAllDay
 */
function enableDisableTextBox(checkAllDay){
	if (checkAllDay.checked == true){
	    $('#startTime').val('09:00');
	    $('#endTime').val('17:00');
	}
	else{
	    $('#startTime').val('');
        $('#endTime').val('');
	}
}

/**
 * Will request the servlet for editing the details of perticular id.
 * 
 * @param reservation_id
 */
function getId(reservationId){
	clearNotice();
    enableButton();
    $.ajax({
        url: 'edit',
        type: 'GET',
        dataType: 'json',
        data: {reservationId: reservationId},
        contentType: 'application/json',
        success: function(data){
            displayData(data);
        }
    });
	
}

function displayData(data) {
    if(data){
        $('#edit-form #reservationId').val(data.reservationId);
        $('#edit-form #userId').val(data.userId);
        $('#edit-form #resourceName option[value='+ data.resourceId +']').attr("selected", "selected");
        $('#edit-form #startDate').val(data.startDate);
        $('#startDate').datepicker("setDate", data.startDate);
        $('#edit-form #startTime').val(data.startTime);
        $('#edit-form #endDate').val(data.endDate);
        $('#endDate').datepicker("setDate", data.endDate);
        $('#edit-form #endTime').val(data.endTime);
    }
}

/**
 * Will request the servlet for deleting a particular id.
 * 
 * @param resource_id
 */
function getIdForDelete(resourceId){
	if(confirm('Do you really want to delete this ?'))
	  window.location='delete?reservationId='+resourceId;
}

//Method to check if the time is in valid format.
function isValidTime(time){
    return !/^(0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]$/.test(time)
}

// Method to check if the date is entered in correct format.
function isValidDate(date){
    return !/^(0[1-9]|1[0-2])\/(0[1-9]|1\d|2\d|3[01])\/(19|20)\d{2}$/.test(date);
}

/**
 * Method for validating the edit form and giving the error if the necessary
 * requirements are not met.
 * 
 * @param formObj
 * @returns boolean
 */
function validateEditForm() {
    var formObj = document.getElementById('edit-form');
    clearNotice();
    var resourceName = formObj.resourceId.value
    var startDate = formObj.startDate.value;
    var startTime = formObj.startTime.value;
    var endDate = formObj.endDate.value;
    var endTime = formObj.endTime.value;

    if(resourceName.length == 0) {
        addError('Please select a resource.');
    }

    if(startDate.trim().length == 0) {
        addError('Please enter start date.');
    }
    
    if(startTime.trim().length == 0) {
        addError('Please enter start time.');
    }
    
    if(endDate.trim().length == 0) {
        addError('Please enter end date.');
    }
    
    if(endTime.trim().length == 0) {
        addError('Please enter end time.');
    }

    if(startDate.trim().length != 0 && isValidDate(startDate)) {
        addError('Please enter valid start date.');
    }
    if(startTime.trim().length != 0 && isValidTime(startTime)) {
        addError('Please enter valid start time.');
    }
    if(endDate.trim().length != 0 && isValidDate(endDate)) {
        addError('Please enter valid end date.');
    }
    if(endTime.trim().length != 0 && isValidTime(endTime)) {
        addError('Please enter valid end time.');
    }

    if (Date.parse(endDate + " " + endTime) < Date.parse(startDate + " " + startTime)) {
        addError('End Date and time cannot be before start date and time.');
    }
    
    if(((Math.round(Date.parse(endDate + " " + endTime) - Date.parse(startDate + " " + startTime))/1000)/60 < 10 )){
        addError('Time difference must be greater than 10 minutes.');
    }

    if (hasErrorNotice()) {
        displayNotice();
        return false;
    }
    disableButton();
    formObj.submit();
    return true;
}

function setDate(){
    $('.datepicker').datepicker("setDate", new Date());
}
