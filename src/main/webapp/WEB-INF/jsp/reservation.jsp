<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Reservation</title>
        <meta http-equiv="Cache-Control" content="no-cache, no-store, must-revalidate" />
        <meta http-equiv="Pragma" content="no-cache" />
        <meta http-equiv="Expires" content="0" />
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.9.0/css/bootstrap-datepicker.min.css"/>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/clockpicker/0.0.7/bootstrap-clockpicker.css"/>
        <link rel="stylesheet" type="text/css" href="<c:url value='/resources/css/common.css'/>" />
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.9.0/js/bootstrap-datepicker.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/clockpicker/0.0.7/jquery-clockpicker.min.js"></script>
        <script type="text/javascript" src="<c:url value='/resources/js/manage_reservation.js'/>"></script>
        <script type="text/javascript" src="<c:url value='/resources/js/display-error.js'/>"></script>
        <script type="text/javascript" src="<c:url value='/resources/js/common.js'/>"></script>
        <script>
        executeEvent(window, 'load', function(){
            addNoticeFormModal(${error_message_modal});
            displayNoticeOnModal();
            displayData(${jsForm});
        });
        </script>
    </head>
    <body>
        <%@include file="nav-bar.jsp"%>
        <div class="container">
            <div class="col-md-12 text-center gap-3 d-grid mt-3 mb-3">
                <input type="button" data-toggle="modal" data-target="#edit-field" onclick="clearModal();setDate();" class="btn btn-primary btn-sm" value="Add"/>
            </div>
            <%@include file="notification.jsp"%>

            <table class="table">
                <thead>
                    <tr>
                        <th class="text-center" scope="col">User Name</th>
                        <th class="text-center" scope="col">Resource Name</th>
                        <th class="text-center" scope="col">Start Date</th>
                        <th class="text-center" scope="col">Start Time</th>
                        <th class="text-center" scope="col">End Date</th>
                        <th class="text-center" scope="col">End Time</th>
                        <sec:authorize access="hasAuthority('ADMIN')">
                            <th class="text-center" scope="col">Action</th>
                        </sec:authorize>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${view}" var="reservationRecord">
                        <tr>
                            <th scope="row" class="text-center align-middle"><c:out value="${reservationRecord.userName}" /></th>
                            <td class="text-center align-middle"><c:out value="${reservationRecord.resourceName}" /></td>
                            <td class="text-center align-middle"><c:out value="${reservationRecord.startDate}" /></td>
                            <td class="text-center align-middle"><c:out value="${reservationRecord.startTime}" /></td>
                            <td class="text-center align-middle"><c:out value="${reservationRecord.endDate}" /></td>
                            <td class="text-center align-middle"><c:out value="${reservationRecord.endTime}" /></td>
                            <sec:authorize access="hasAuthority('ADMIN')">
                                <td class="text-center align-middle">
                                    <input data-toggle="modal" data-target="#edit-field" class="btn btn-primary btn-sm" type="button" onclick="getId('${reservationRecord.reservationId}');" value="Edit">
                                    <input class="btn btn-primary btn-sm" type="button" onclick="getIdForDelete('${reservationRecord.reservationId}');" value="Delete">
                                </td>
                            </sec:authorize>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>

            <div class="modal fade" id="edit-field" tabindex="-1" role="dialog" aria-labelledby="formTitle" aria-hidden="true">
                <div class="modal-dialog modal-dialog-centered" role="document">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title" id="formTitle">Edit Reservation</h5>
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>
                        <%@include file="display-error.jsp"%>
                        <div class="modal-body">
                            <form:form modelAttribute="form" action="save" id="edit-form">
                            	<form:hidden path="reservationId"/>
                            	<form:hidden path="userId"/>

                                <div class="form-group">
                                    <label class="control-label" for="resourceName">Resource Name<span class="required">*</span>:</label>
                                    <form:select path="resourceId" class="form-control">
                                    	<form:options items="${dropdown}"/>
                                    </form:select>
                                </div>

                                <div class="form-group">
                                    <label class="control-label" for="name">Start Date<span class="required">*</span>:</label>
                                    <form:input path="startDate" placeholder="mm/dd/yyyy" class="form-control datepicker"/>
                                </div>
                                <div class="form-group clockpicker">
                                    <label class="control-label" for="name">Start Time<span class="required">*</span>:</label>
                                    <form:input path="startTime" class="form-control"/>
                                    <span class="input-group-addon">
                                        <span class="glyphicon glyphicon-time"></span>
                                    </span>
                                </div>

                                <div class="form-group">
                                    <label class="control-label" for="name">End Date<span class="required">*</span>:</label>
                                    <form:input path="endDate" placeholder="mm/dd/yyyy" class="form-control datepicker"/>
                                </div>
                                <div class="form-group clockpicker">
                                    <label class="control-label" for="name">End Time<span class="required">*</span>:</label>
                                    <form:input path="endTime" class="form-control"/>
                                    <span class="input-group-addon">
                                        <span class="glyphicon glyphicon-time"></span>
                                    </span>
                                </div>

                                <div class="form-group form-check form-check-inline">
                                    <input type="checkbox" id="isAllDay" name="is_all_day" class="form-check-input" onclick="enableDisableTextBox(this)"/> 
                                    <label class="form-check-label">All Day</label>
                                </div>
                            </form:form>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                            <button type="button" class="btn btn-primary" onclick="return validateEditForm();">Save changes</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <script type="text/javascript">
        $(function () {
            $('.datepicker').datepicker({
                daysOfWeekDisabled: [0, 6],
                today: 'Today',
            });
            $('.datepicker').datepicker("setDate", new Date());
            var input = $('.clockpicker').clockpicker({
                autoclose: true,
                placement: 'top',
                beforeShow: function(e){
                    if(readOnlyClock) {
                        e.stopPropagation();
                    }
                }
            });
         });
        </script>
    </body>
</html>
