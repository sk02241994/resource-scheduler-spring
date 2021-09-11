
/**
 * Get email address for editing user details.
 * 
 * @param email_address
 */
function getUser(userId) {
    clearNotice();
    enableButton();
    $.ajax({
        url: 'edit',
        type: 'GET',
        dataType: 'json',
        data: {userId: userId},
        contentType: 'application/json',
        success: function(data){
            displayData(data);
        }
    });

}

function displayData(data){
    if(data){
        $('#edit-form #userId').val(data.userId);
        $('#edit-form #name').val(data.name);
        $('#edit-form #emailAddress').val(data.emailAddress);
        $('#edit-form #active1').prop('checked', data.active);
        $('#edit-form #admin1').prop('checked', data.admin);
        $('#edit-form #permanentEmployee1').prop('checked', data.permanentEmployee);
        $('input[name="gender"]').each(function(){
            if($(this).val() == data.gender){
                $(this).prop('checked', 'checked');
            }
        });
    }
}

/**
 * Method will validate email address according to what is input by user.
 * 
 * @param mail
 * @returns boolean
 */
function isNotValidEmail(mail) {
	return !/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/.test(mail);
}

/**
 * Method to validate users first name.
 * 
 * @param name
 * @returns boolean
 */
function isNotValidName(name) {
	return !/^[a-zA-Z]{1,60}\s?[a-zA-Z]{1,30}$/.test(name);
}

/**
 * Method to validate the form.
 * 
 * @param form
 * @returns
 */
function validateForm(form) {

    var form = document.getElementById('edit-form');
	clearNotice();

	if (form.name.value.trim().length == 0) {
		addError('Please enter name.');
	}

	if (form.emailAddress.value.trim().length == 0) {
		addError('Please enter email.');
	}

	if (form.name.value.trim().length != 0 && isNotValidName(form.name.value)) {
		addError('Please enter valid name.');
	}

	if (form.emailAddress.value.trim().length != 0 && isNotValidEmail(form.emailAddress.value)) {
		addError('Please enter valid email.');
	}

	if (hasErrorNotice()) {
		displayNotice();
		return false;
	}

	disableButton();
	form.submit();
	return true;
}
