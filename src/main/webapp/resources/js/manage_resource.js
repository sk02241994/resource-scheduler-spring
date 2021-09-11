/**
 * Method will request for editing by passing the id of resource.
 * 
 * @param resource_id
 */
function getResource(resourceId) {
	clearNotice();
	enableButton();
	$.ajax({
		url : 'edit',
		type : 'GET',
		dataType : 'json',
		data : {
			resourceId : resourceId
		},
		contentType : 'application/json',
		success : function(data) {
			displayData(data);
		}
	});

}

function displayData(data) {
	if (data) {
		$('#edit-form #resourceId').val(data.resourceId);
		$('#edit-form #resourceName').val(data.resourceName);
		$('#edit-form #description').val(data.description);
		$('#edit-form #timeLimitHours').val(data.timeLimitHours == 0 ? '' : data.timeLimitHours);
		$('#edit-form #timeLimitMinutes').val(data.timeLimitMinutes == 0 ? '' : data.timeLimitMinutes);
		$('#edit-form #maxUsersAllowed').val(data.maxUsersAllowed);
		$('#edit-form #enabled1').prop('checked', data.enabled);
		$('#edit-form #allowedMultiple1').prop('checked', data.allowedMultiple);
		$('#edit-form #allowEmpOnProbation1').prop('checked', data.allowEmpOnProbation);
	}
}

/**
 * Method will request for delete by passing resource id to be deleted.
 * 
 * @param resource_id
 */
function deleteResource(resourceId) {
	if (confirm("Do you really want to delete this ?")) {
		goToPage('delete?resourceId=' + resourceId);
	}
}

/**
 * Method will validate so that all the required resource form details are
 * filled.
 * 
 * @param formObj
 * @returns boolean
 */
function validateResource() {

	var formObj = document.getElementById('edit-form');
	clearNotice();
	if (formObj.resourceName.value.trim().length == 0) {
		addError('Please enter the resource name.')
	}

	const numberRe = /^\d+$/;
	var timeLimitHours = formObj.timeLimitHours.value.trim();
	var timeLimitMinutes = formObj.timeLimitMinutes.value.trim();
	var maxAllowed = formObj.maxUsersAllowed.value.trim();

	if (timeLimitHours.length != 0 && !numberRe.test(timeLimitHours)) {
		addError('Please enter valid hours.')
	}

	if (timeLimitMinutes.length != 0 && !numberRe.test(timeLimitMinutes)) {
		addError('Please enter valid minutes.')
	}

	if (maxAllowed.length != 0 && !numberRe.test(allowedMultiple)) {
		addError('Please enter valid number of users allowed to access the resources.')
	}

	if (hasErrorNotice()) {
		displayNotice();
		return false;
	}

	disableButton();
	formObj.submit();
	return true;
}

function onlyNumber(hoursMins) {
	return hoursMins.value.replace(/[^0-9.]/g, '')
}
