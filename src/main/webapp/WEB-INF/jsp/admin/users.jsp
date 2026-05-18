<%@ include file="../common/header.jspf"%>
<%@ include file="../common/navigation.jspf"%>

<div class="container">
    <div class="row">
        <div class="col-md-8 col-md-offset-2">
            <div class="panel panel-primary">
                <div class="panel-heading">
                    <h3 class="panel-title">User Management</h3>
                </div>
                <div class="panel-body">

                    <div class="table-responsive">
                        <table class="table table-striped table-hover">
                            <thead>
                            <tr>
                                <th>ID</th>
                                <th>Username</th>
                                <th>Role</th>
                                <th>Enabled</th>
                                <th>Last Login</th>
                                <th class="text-right">Actions</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${users}" var="u">
                                <tr>
                                    <td>${u.id}</td>
                                    <td>${u.username}</td>
                                    <td>${u.role}</td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${u.enabled}">Yes</c:when>
                                            <c:otherwise>No</c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${not empty u.lastLogin}"><fmt:formatDate value="${u.lastLogin}" pattern="dd/MM/yyyy HH:mm"/></c:when>
                                            <c:otherwise>-</c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td class="text-right">
                                        <a class="btn btn-sm btn-info" href="/admin/edit-user?id=${u.id}">Edit</a>
                                        <a class="btn btn-sm btn-danger" href="/admin/delete-user?id=${u.id}" onclick="return confirm('Delete user ${u.username}?');">Delete</a>
                                    </td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>

                </div>
            </div>
        </div>
    </div>
</div>

<%@ include file="../common/footer.jspf"%>

