<%@ include file="common/header.jspf"%>
<%@ include file="common/navigation.jspf"%>

<div class="container">
    <div class="row">
        <div class="col-md-6 col-md-offset-3">
            <div class="panel panel-primary">
                <div class="panel-heading">Register</div>
                <div class="panel-body">

                    <c:if test="${not empty message}">
                        <div class="alert alert-success">${message}</div>
                    </c:if>

                    <c:if test="${not empty error}">
                        <div class="alert alert-danger">${error}</div>
                    </c:if>

                    <form method="post" action="/register">
                        <fieldset class="form-group">
                            <label for="username">Username</label>
                            <input type="text" id="username" name="username"
                                   class="form-control" required="required"
                                   minlength="3" maxlength="20"
                                   pattern="[A-Za-z0-9_.-]+"
                                   autocomplete="username"
                                   placeholder="letters, numbers, underscore, dot, hyphen" />
                            <small class="text-muted">3-20 characters: letters, numbers, underscore, dot, or hyphen.</small>
                        </fieldset>

                        <fieldset class="form-group">
                            <label for="password">Password</label>
                            <input type="password" id="password" name="password"
                                   class="form-control" required="required"
                                   minlength="6"
                                   autocomplete="new-password" />
                            <small class="text-muted">Minimum 6 characters recommended.</small>
                        </fieldset>

                        <fieldset class="form-group">
                            <label for="confirmPassword">Confirm Password</label>
                            <input type="password" id="confirmPassword" name="confirmPassword"
                                   class="form-control" required="required"
                                   autocomplete="new-password" />
                        </fieldset>

                        <button type="submit" id="registerSubmit" class="btn btn-primary">Create Account</button>
                        <a href="/login" class="btn btn-default">Back to Login</a>
                    </form>

                    <!-- Password strength meter -->
                    <div style="margin-top:10px;">
                        <div class="progress" style="height:8px;">
                            <div id="passwordStrengthBar" class="progress-bar" role="progressbar" style="width:0%"></div>
                        </div>
                        <small id="passwordStrengthText" class="text-muted">Password strength: </small>
                        <small id="passwordMatch" class="text-danger" style="margin-left:10px; display:none;">Passwords do not match</small>
                    </div>

                </div>
            </div>
        </div>
    </div>
</div>

<%@ include file="common/footer.jspf"%>