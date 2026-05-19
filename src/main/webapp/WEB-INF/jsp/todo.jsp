<%@ include file="common/header.jspf"%>
<%@ include file="common/navigation.jspf"%>
<div class="container">
	<div class="row">
		<div class="col-md-6 col-md-offset-3 ">
			<div class="panel panel-primary">
				<div class="panel-heading">Add TODO</div>
				<div class="panel-body">
					<form:form method="post" modelAttribute="todo">
						<form:hidden path="id" />
						<fieldset class="form-group">
							<form:label path="description">Description</form:label>
							<form:input path="description" type="text" class="form-control"
								required="required" />
							<form:errors path="description" cssClass="text-warning" />
						</fieldset>

						<fieldset class="form-group">
							<form:label path="targetDate">Target Date</form:label>

							<div class="input-date-wrapper" style="position:relative;">
								<form:input path="targetDate" id="targetDate" cssClass="form-control real-date-input"
											placeholder="dd/mm/yyyy" required="required" />
								<span id="targetDateOverlay" class="date-overlay"></span>
							</div>

							<form:errors path="targetDate" cssClass="text-warning" />
						</fieldset>

						<fieldset class="form-group">
							<form:label path="priority">Priority</form:label>
							<form:select path="priority" class="form-control">
								<form:option value="">Select Priority</form:option>
								<form:options items="${priorities}" itemLabel="label" />
							</form:select>
						</fieldset>

						<fieldset class="form-group">
							<form:label path="status">Status</form:label>
							<form:select path="status" class="form-control">
								<form:option value="">Select Status</form:option>
								<form:options items="${statuses}" itemLabel="label" />
							</form:select>
						</fieldset>

						<fieldset class="form-group">
							<label for="tagNames">Tags (comma-separated)</label>
							<input list="tagSuggestions" name="tagNames" id="tagNames" class="form-control" value="${tagNames}" placeholder="e.g. urgent, work" />
							<datalist id="tagSuggestions">
								<c:forEach items="${allTags}" var="t">
									<option value="${t.name}"></option>
								</c:forEach>
							</datalist>
						</fieldset>

						<button type="submit" class="btn btn-primary">Save</button>
					</form:form>

				</div>
			</div>
		</div>
	</div>
</div>
<%@ include file="common/footer.jspf"%>