
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>
    <head> 
        <title>User</title> 
        <meta http-equiv="Cache-Control" content="no-cache, no-store, must-revalidate" />
        <meta http-equiv="Pragma" content="no-cache" />
        <meta http-equiv="Expires" content="0" />
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
        <link rel="stylesheet" type="text/css" href="<c:url value='/resources/css/common.css'/>" />
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
        <script type="text/javascript" src="<c:url value='/resources/js/manage_user.js'/>"></script>
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
                <input type="button" data-toggle="modal" data-target="#edit-field" onclick="clearModal()" class="btn btn-primary btn-sm" value="Add" /%>
            </div>
            <%@include file="notification.jsp"%>
            <table class="table">
                <thead>
                    <tr>
                        <th class="text-center" scope="col">Name</th>
                        <th class="text-center" scope="col">Email</th>
                        <th class="text-center" scope="col">Status</th>
                        <th class="text-center" scope="col">Action</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${view}" var="userRecord">
                        <tr>
                            <th scope="row" class="text-center align-middle"><c:out value="${userRecord.userName}" /></th>
                            <td class="text-center align-middle"><c:out value="${userRecord.emailAddress}" /></td>
                            <td class="text-center align-middle"><c:out value="${userRecord.isActive() ? 'Active' : 'Inactive'}" /></td>
                            <td class="text-center align-middle"><input data-toggle="modal" data-target="#edit-field" class="btn btn-primary btn-sm" type="button" onclick="getUser('${userRecord.userId}');" value="Edit"></td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>

            <div class="modal fade" id="edit-field" tabindex="-1" role="dialog" aria-labelledby="formTitle" aria-hidden="true">
                <div class="modal-dialog modal-dialog-centered" role="document">
                    <div class="modal-content"> 
                        <div class="modal-header">
                            <h5 class="modal-title" id="formTitle">Edit User</h5>
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                              <span aria-hidden="true">&times;</span>
                            </button>
                        </div>
                        <%@include file="display-error.jsp"%>
                        <div class="modal-body"> 

                            <form:form modelAttribute="form" action="save" id="edit-form">
                            	<form:hidden path="userId"/>
                                <div class="form-group">
                                    <label class="control-label" for="name">Name<span class="required">*</span>:</label>
                                    <div>
                                    	<form:input path="name" class="form-control input-lg" maxlength="50"/>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label class="control-label" for="email">Email Address<span class="required">*</span>:</label>
                                    <div>
                                    	<form:input path="emailAddress" class="form-control input-lg" maxlength="50"/>
                                    </div>
                                </div>
                                <div class="form-group form-check form-check-inline">
                                	<form:checkbox path="active" class="form-check-input"/>
                                    <label class="form-check-label">Enable</label>
                                </div>

                                <div class="form-group form-check form-check-inline">
                                	<form:checkbox path="admin" class="form-check-input"/>
                                    <label class="form-check-label">Is Admin</label>
                                </div>

                                <div class="form-group form-check form-check-inline">
                                	<form:checkbox path="permanentEmployee" class="form-check-input"/>
                                    <label class="form-check-label">Is Permanent Employee</label>
                                </div>
                                <br/>
                                <div class="form-check form-check-inline">
                                  <form:radiobutton path="gender" value="M" class="form-check-input"/>
                                  <label class="form-check-label" for="flexRadioDefault1">
                                    Male
                                  </label>
                                </div>
                                <div class="form-check form-check-inline">
                                  <form:radiobutton path="gender" value="F" class="form-check-input"/>
                                  <label class="form-check-label" for="flexRadioDefault2">
                                    Female
                                  </label>
                                </div>

                            </form:form>

                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                            <button type="button" class="btn btn-primary" onclick="return validateForm();">Save changes</button>
                        </div>
                    </div>
                </div>
            </div>

        </div>
    </body>
</html>
