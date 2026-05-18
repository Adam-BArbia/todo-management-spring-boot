<%@ include file="common/header.jspf"%>

<div class="container">
    <div class="row">
        <div class="col-md-6 col-md-offset-3">
            <div class="panel panel-primary">
                <div class="panel-heading">Login</div>
                <div class="panel-body">

                    <c:if test="${param.error != null}">
                        <div class="alert alert-danger">
                            Invalid username or password.
                        </div>
                    </c:if>

                    <c:if test="${param.logout != null}">
                        <div class="alert alert-success">
                            You have been logged out.
                        </div>
                    </c:if>

                    <c:if test="${param.registered != null}">
                        <div class="alert alert-success">
                            Account created successfully. Please log in.
                        </div>
                    </c:if>

                    <c:if test="${param.passwordChanged != null}">
                        <div class="alert alert-success">
                            Your password was changed successfully.
                        </div>
                    </c:if>

                    <form method="post" action="/login">
                        <fieldset class="form-group">
                            <label for="username">Username</label>
                            <input type="text" id="username" name="username" class="form-control" required="required" autocomplete="username" />
                        </fieldset>

                        <fieldset class="form-group">
                            <label for="password">Password</label>
                            <input type="password" id="password" name="password" class="form-control" required="required" autocomplete="current-password" />
                        </fieldset>

                        <button type="submit" class="btn btn-success">Login</button>
                        <a href="/register" class="btn btn-default">Register</a>
                    </form>

                </div>
            </div>
        </div>
    </div>
</div>

<%@ include file="common/footer.jspf"%>