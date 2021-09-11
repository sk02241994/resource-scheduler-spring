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
        <link rel="stylesheet" type="text/css" href="<c:url value='/resources/css/common.css'/>" />
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
        <script type="text/javascript" src="<c:url value='/resources/js/manage_resource.js'/>"></script>
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
                <input type="button" data-toggle="modal" data-target="#edit-field" onclick="clearModal()" class="btn btn-primary btn-sm" value="Add"/>
            </div>
            <%@include file="notification.jsp"%>
            <table class="table">
                <thead>
                    <tr>
                        <th class="text-center" scope="col">Name</th>
                        <th class="text-center" scope="col">Description</th>
                        <th class="text-center" scope="col">Status</th>
                        <th class="text-center" scope="col">Action</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${view}" var="resource">
                        <tr>
                            <th scope="row" class="text-center align-middle"><c:out value="${resource.resourceName}" /></th>
                            <td class="text-center align-middle"><c:out value="${resource.description}" /></td>
                            <td class="text-center align-middle"><c:out value="${resource.active ? 'Active' : 'Inactive'}" /></td>
                            <td class="text-center align-middle">
                                    <input data-toggle="modal" data-target="#edit-field" class="btn btn-primary btn-sm" type="button" onclick="getResource('${resource.resourceId}');" value="Edit">
                                    <input data-toggle="modal" class="btn btn-primary btn-sm" type="button" onclick="deleteResource('${resource.resourceId}');" value="Delete">
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>

            <div class="modal fade" id="edit-field" tabindex="-1" role="dialog" aria-labelledby="formTitle" aria-hidden="true">
                <div class="modal-dialog modal-dialog-centered" role="document">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title" id="formTitle">Edit Resources</h5>
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>
                        <%@include file="display-error.jsp"%>
                        <div class="modal-body">
                            <form:form modelAttribute="form" action="save" id="edit-form">
                            	<form:hidden path="resourceId"/>
                                    <div class="form-group">
                                        <label class="control-label" for="name">Resource Name<span class="required">*</span>:</label>
                                        <div>
                                        	<form:input path="resourceName" class="form-control input-lg" maxlength="50"/>
                                        </div>
                                    </div>

                                    <div class="form-group">
                                        <label for="description" class="control-label">Description:</label>
                                        <div>
                                        	<form:textarea path="description" class="form-control input-lg" rows="3"/>
                                        </div>
                                    </div>

                                    <div class="form-group">
                                        <label class="control-label">Time Limit:</label>
                                        <div class="row">
                                            <div class="col-md-5">
                                            	<form:input path="timeLimitHours" placeholder="Hours" onkeyup="this.value=onlyNumber(this);" class="form-control"/>
                                            </div>
                                            <div class="input-group-prepend">
                                                <span class="input-group-text" id="basic-addon1">-</span>
                                            </div>
                                            <div class="col-md-5">
                                            	<form:input path="timeLimitMinutes" placeholder="Minutes" onkeyup="this.value=onlyNumber(this);" class="form-control"/>
                                            </div>
                                        </div>
                                    </div>

                                    <div class="form-group">
                                        <label class="control-label" for="name">Max User Bookings Allowed:</label>
                                        <div>
                                        	<form:input path="maxUsersAllowed" class="form-control input-lg"/>
                                        </div>
                                    </div>

                                    <div class="form-group form-check form-check-inline">
                                    	<form:checkbox path="enabled" class="form-check-input"/>
                                        <label class="form-check-label">Enable</label>
                                    </div>

                                    <div class="form-group form-check form-check-inline">
                                    	<form:checkbox path="allowedMultiple" class="form-check-input"/>
                                        <label class="form-check-label">Is Allowed Multiple Reservations</label>
                                    </div>

                                    <div class="form-group form-check form-check-inline">
                                    	<form:checkbox path="allowEmpOnProbation" class="form-check-input"/>
                                        <label class="form-check-label">Is Allow Employees On Probation</label>
                                    </div>
                            </form:form>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                            <button type="button" class="btn btn-primary" onclick="return validateResource();">Save changes</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
