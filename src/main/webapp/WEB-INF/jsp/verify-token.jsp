<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>  
<!DOCTYPE html>
<html>
<head>
<title>Login</title>
<meta http-equiv="Cache-Control" content="no-cache, no-store, must-revalidate" />
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Expires" content="0" />
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="<c:url value='/resources/css/login.css'/>" />
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
<script type="text/javascript" src="<c:url value='/resources/js/common.js'/>"></script>
<script>

var timer2 = "5:01";
var interval = setInterval(function() {
    var timer = timer2.split(':');
    var minutes = parseInt(timer[0], 10);
    var seconds = parseInt(timer[1], 10);
    --seconds;
    minutes = (seconds < 0) ? --minutes : minutes;
    seconds = (seconds < 0) ? 59 : seconds;
    seconds = (seconds < 10) ? '0' + seconds : seconds;
    //minutes = (minutes < 10) ?  minutes : minutes;
    $('#timer').html(minutes + ':' + seconds);
    if (minutes < 0) clearInterval(interval);
    if((seconds <= 0) && (minutes <= 0)) { 
        clearInterval(interval);
        $('#timer-cell').hide();
        $('#resend-cell').show();
        
    }
    timer2 = minutes + ':' + seconds;
}, 1000);

var resendToken = function(){
    $('#form').attr('action', 'forgotPasswordSend');
    $('#form').submit();
}

executeEvent(window, 'load', function(){
    $('#form').attr('action', 'verifiedToken');
    $('#resend-cell').hide();
});
</script>
</head>
<body>
    <section class="form my-4 mx-5">
        <div class="container">
            <div class="row no-gutters">
                <div class="col-lg-5">
                    <img src="https://images.unsplash.com/photo-1587316205943-b15dc52a12e0?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=634&q=80" class="img-fluid" alt="">
                </div>
                <div class="col-lg-7 px-5 py-5">
                    <h1 class="font-weight-bold py-3">Resource Scheduler</h1>
                    <h4>Verify token</h4>
                    <%@include file="notification.jsp" %>
                    <%@include file="display-error.jsp"%>
                    <form:form action="verifiedToken" method="post" modelAttribute="form">
                        <div class="form-row">
                            <div class="col-lg-7">
                                <form:hidden path="emailAddress" class="form-control my-3 p-4" placeholder="Email Address" maxlength="50"/>
                            </div>
                        </div>
                        <div class="form-row">
                            <div class="col-lg-7">
                                <form:input path="token" class="form-control my-3 p-4" placeholder="Token"/>
                            </div>
                        </div>
                        <div class="form-row">
                            <div class="col-lg-7">
                                <button type="submit" class="btn1 btn-primary">Submit</button>
                            </div>
                        </div>
                    </form:form>
                    <div class="row" id="timer-cell"> 
                        <div class="col-xs-4 ml-3">Token Expires in </div>&nbsp;
                        <div class="col-xs-4" id="timer"></div>.
                    </div>
                    <div class="row" id="resend-cell" style="display:none;">
                       <div class="col-xs-4 ml-3"><a href="#" onclick="resendToken();">Resend Token</a></div>
                    </div>
                </div>
            </div>
        </div>
    </section>
</body>
</html>
