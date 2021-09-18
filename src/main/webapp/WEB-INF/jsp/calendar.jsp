<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Resources</title>
        <meta http-equiv="Cache-Control" content="no-cache, no-store, must-revalidate" />
        <meta http-equiv="Pragma" content="no-cache" />
        <meta http-equiv="Expires" content="0" />
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.3.0/font/bootstrap-icons.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.9.0/css/bootstrap-datepicker.min.css"/>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/clockpicker/0.0.7/bootstrap-clockpicker.css"/>
        <link rel="stylesheet" type="text/css" href="<c:url value='/resources/css/common.css'/>" />
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.9.0/js/bootstrap-datepicker.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/clockpicker/0.0.7/jquery-clockpicker.min.js"></script>
        <script type="text/javascript" src="<c:url value='/resources/js/display-error.js'/>"></script>
        <script type="text/javascript" src="<c:url value='/resources/js/common.js'/>"></script>
        <script>
            var schedule= ${view};
            var userId = <sec:authentication property="principal.userId"/>;
            <sec:authorize access="hasAuthority('ADMIN')">var isAdmin = true; </sec:authorize>
            <sec:authorize access="hasAuthority('USER')">var isAdmin = false; </sec:authorize>
            executeEvent(window, 'load', function(){
                addNoticeFormModal(${error_message_modal});
                displayNoticeOnModal();
                displayDataOnEdit(${reservation});
            });
        </script>
    </head>
    <body>
        <%@include file="nav-bar.jsp"%>
        <div class="container">
            <div class="row justify-content-center">
                <h3 id="month-and-year"></h3>
            </div>
            <%@include file="notification.jsp"%>
            <div class="calendar-navigation">
                <form class="form-inline justify-content-center" >
                    <div class="form-group form-inline align-middle">
                            <div class="col-form-label">Jump To:</div>
                            <div class="col-sm-2">
                                <select name="month" id="month" onchange="jump();" class="form-control">
                                    <option value=0>January</option>
                                    <option value=1>February</option>
                                    <option value=2>March</option>
                                    <option value=3>April</option>
                                    <option value=4>May</option>
                                    <option value=5>June</option>
                                    <option value=6>July</option>
                                    <option value=7>August</option>
                                    <option value=8>September</option>
                                    <option value=9>October</option>
                                    <option value=10>November</option>
                                    <option value=11>December</option>
                                </select>
                            </div>
                        </div>
                        <div class="form-group form-inline align-middle">
                            <select id="year" class="form-control" onchange="jump()"></select>
                        </div>
                </form>
            </div>
            
            <div class="row mt-3">
                <div class="col-4 text-left">
                    <div class="previous">
                        <button type="button" class="btn btn-secondary" onclick="previous()">Previous</button>
                    </div>
                </div>
                <div class="col-4">
                        <form class="form-inline justify-content-center" >
                            <div class="form-group form-inline align-middle">
                                <div class="col-form-label">Jump To:</div>
                                <div class="col-sm-2">
                                    <select name="resource_filter" class="form-control" onchange="filterByResource(this.value);">
                                        <option value="">All</option>
                                        <c:forEach items="${resources}" var="resource">
                                            <option value="${resource.rsResourceId}">${resource.resourceName}</option>
                                            </c:forEach>
                                    </select>
                                </div>
                            </div>
                        </form>
                </div>
                <div class="col-4 text-right">
                    <div class="next">
                        <button type="button" class="btn btn-secondary" onclick="next()">Next</button>
                    </div>
                </div>
            </div>

            <div class="calendar-table mt-5">
                <table class="table" style="table-layout: fixed;">
                    <thead>
                        <tr>
                            <th scope="col">Sunday</th>
                            <th scope="col">Monday</th>
                            <th scope="col">Tuesday</th>
                            <th scope="col">Wednesday</th>
                            <th scope="col">Thursday</th>
                            <th scope="col">Friday</th>
                            <th scope="col">Saturday</th>
                        </tr>
                    </thead>
                    <tbody id="calendar-body">
                        
                    </tbody>
                </table>
            </div>
        </div>

        <div id="display-all-day-data" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="displayRecordsTitle" aria-hidden="true">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="displayRecordsTitle"></h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>   
                    </div>
                    <div class="modal-body" id="displayRecordBody">
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                    </div>
                </div>
            </div>
        </div>
        
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
        <script type="text/javascript">
            $(function(){
                $('.datepicker').datepicker({
                    daysOfWeekDisabled: [0, 6]
                });
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
    <script type="text/javascript" src="<c:url value='/resources/js/get-data-for-calendar.js'/>"></script>
    <script type="text/javascript" src="<c:url value='/resources/js/calendar.js'/>"></script>
</html>
