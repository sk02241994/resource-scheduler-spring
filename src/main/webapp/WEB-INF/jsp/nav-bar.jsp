<c:set var="context" value="${pageContext.request.contextPath}" />
<nav class="nav navbar-expand-lg navbar-dark bg-dark">
    <ul class="nav navbar-nav">
    	<sec:authorize access="hasAuthority('ADMIN')">
                <li class="nav-item"><a class="nav-link" href="${context}/reservation/list">Home</a></li>
                <li class="nav-item"><a class="nav-link" href="${context}/user/list">Manage User</a></li>
                <li class="nav-item"><a class="nav-link" href="${context}/resource/list">Manage Resource</a></li>
                <li class="nav-item"><a class="nav-link" href="${context}/calendar/list">Calendar</a></li>
		</sec:authorize>
		<sec:authorize access="hasAuthority('USER')">
                <li class="nav-item"><a class="nav-link" href="${context}/reservation/list">Home</a></li>
                <li class="nav-item"><a class="nav-link" href="${context}/calendar/list">Calendar</a></li>
        </sec:authorize>
    </ul>
    <ul class="nav navbar-nav ml-auto">
        <li class="nav-item"><a class="nav-link" href="${context}/logout">Logout</a></li>
    </ul>
</nav>

