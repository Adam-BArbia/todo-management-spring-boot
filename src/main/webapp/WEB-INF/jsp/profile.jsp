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
                  <a class="btn btn-info" href="/list-todos">My Todos</a>
                  <a class="btn btn-info" href="/">Home</a>
        </div>
    </div>
</div>

<div class="container">
      <div class="panel panel-primary">
        <div class="panel-heading">Manage Tags</div>
        <div class="panel-body">
            <form method="post" action="/tags/create" class="form-inline" style="margin-bottom:10px;">
                <div class="form-group">
                    <input type="text" name="name" class="form-control" placeholder="New tag name" required />
                </div>
                <button class="btn btn-primary" type="submit">Create Tag</button>
            </form>

            <c:if test="${not empty tags}">
                <table class="table table-condensed">
                    <thead>
                        <tr><th>Name</th><th>Actions</th></tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${tags}" var="t">
                            <tr>
                                <td>${t.name}</td>
                                <td>
                                    <a href="/tags/delete?id=${t.id}" class="btn btn-xs btn-danger" onclick="return confirm('Delete tag?\nThis will remove the tag association from todos, but not delete the todos.');">Delete</a>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </c:if>
        </div>
    </div>
</div>

<%@ include file="common/footer.jspf"%>