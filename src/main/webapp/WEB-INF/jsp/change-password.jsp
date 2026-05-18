<%@ include file="common/header.jspf"%>
<%@ include file="common/navigation.jspf"%>

<div class="container">
    <div class="row">
        <div class="col-md-6 col-md-offset-3">
            <div class="panel panel-primary">
                <div class="panel-heading">Change Password</div>
                <div class="panel-body">

                    <c:if test="${not empty message}">
                        <div class="alert alert-success">${message}</div>
                    </c:if>

                    <c:if test="${not empty error}">
                        <div class="alert alert-danger">${error}</div>
                    </c:if>

                    <form method="post" action="/change-password">
                        <fieldset class="form-group">
                            <label for="currentPassword">Current Password</label>
                            <input type="password" id="currentPassword" name="currentPassword"
                                   class="form-control" required="required"
                                   autocomplete="current-password" />
                        </fieldset>

                        <fieldset class="form-group">
                            <label for="newPassword">New Password</label>
                            <input type="password" id="newPassword" name="newPassword"
                                   class="form-control" required="required"
                                   minlength="6" autocomplete="new-password" />
                            <small class="text-muted">Minimum 6 characters recommended.</small>
                        </fieldset>

                        <fieldset class="form-group">
                            <label for="confirmPassword">Confirm Password</label>
                            <input type="password" id="confirmPassword" name="confirmPassword"
                                   class="form-control" required="required"
                                   autocomplete="new-password" />
                        </fieldset>

                        <button type="submit" id="changeSubmit" class="btn btn-success">Save</button>
                        <a href="/profile" class="btn btn-default">Cancel</a>
                    </form>

                    <!-- Password strength meter -->
                    <div style="margin-top:10px;">
                        <div class="progress" style="height:8px;">
                            <div id="newPasswordStrengthBar" class="progress-bar" role="progressbar" style="width:0%"></div>
                        </div>
                        <small id="newPasswordStrengthText" class="text-muted">Password strength: </small>
                        <small id="newPasswordMatch" class="text-danger" style="margin-left:10px; display:none;">Passwords do not match</small>
                    </div>

                </div>
            </div>
        </div>
    </div>
</div>

<%@ include file="common/footer.jspf"%>