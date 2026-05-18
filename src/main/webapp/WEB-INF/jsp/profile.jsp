<%@ include file="common/header.jspf"%>
<%@ include file="common/navigation.jspf"%>

<div class="container">
    <div class="panel panel-primary">
        <div class="panel-heading">Profile</div>
        <div class="panel-body">
            <c:if test="${param.passwordChanged != null}">
                <div class="alert alert-success">Your password was updated successfully.</div>
            </c:if>

            <p><strong>Username:</strong> ${username}</p>
            <p><strong>Role:</strong> ${role}</p>
            <p><strong>Status:</strong>
                <c:choose>
                    <c:when test="${enabled}">Active</c:when>
                    <c:otherwise>Disabled</c:otherwise>
                </c:choose>
            </p>
            <p><strong>Last Login:</strong>
                <c:choose>
                    <c:when test="${not empty lastLogin}">
                        <fmt:formatDate value="${lastLogin}" pattern="dd/MM/yyyy HH:mm:ss"/>
                    </c:when>
                    <c:otherwise>Never</c:otherwise>
                </c:choose>
            </p>
            <p><strong>Pending Todos:</strong> ${todoCount}</p>

            <a class="btn btn-primary" href="/change-password">Change Password</a>
            <a class="btn btn-default" href="/list-todos">My Todos</a>
            <a class="btn btn-default" href="/">Home</a>
        </div>
    </div>
</div>

<%@ include file="common/footer.jspf"%>