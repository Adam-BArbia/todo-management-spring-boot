<%@ include file="../common/header.jspf"%>
<%@ include file="../common/navigation.jspf"%>

<div class="container">
    <div class="row">
        <div class="col-md-6 col-md-offset-3">
            <div class="panel panel-primary">
                <div class="panel-heading">
                    <h3 class="panel-title">Edit User</h3>
                </div>
                <div class="panel-body">

                    <form method="post" action="/admin/update-user">
                        <input type="hidden" name="id" value="${user.id}" />

                        <fieldset class="form-group">
                            <label>Username</label>
                            <div class="form-control-static">${user.username}</div>
                        </fieldset>

                        <fieldset class="form-group">
                            <label for="role">Role</label>
                            <select id="role" name="role" class="form-control">
                                <option value="USER" <c:if test="${user.role == 'USER'}">selected</c:if>>USER</option>
                                <option value="ADMIN" <c:if test="${user.role == 'ADMIN'}">selected</c:if>>ADMIN</option>
                            </select>
                        </fieldset>

                        <fieldset class="form-group">
                            <label for="enabled">Enabled</label>
                            <div class="checkbox">
                                <label>
                                    <input type="checkbox" id="enabled" name="enabled" <c:if test="${user.enabled}">checked</c:if> /> Enabled
                                </label>
                            </div>
                        </fieldset>

                        <div class="form-group">
                            <button type="submit" class="btn btn-primary">Save</button>
                            <a href="/admin/users" class="btn btn-default">Cancel</a>
                        </div>
                    </form>

                </div>
            </div>
        </div>
    </div>
</div>

<%@ include file="../common/footer.jspf"%>

