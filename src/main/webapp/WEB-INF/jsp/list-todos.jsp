<%@ include file="common/header.jspf"%>
<%@ include file="common/navigation.jspf"%>

<div class="container">
	<div class="row">
		<div class="col-md-8 col-md-offset-2">

			<!-- Search form -->
			<form class="form-inline" method="GET" action="/list-todos" style="margin-bottom:10px;">
				<div class="form-group">
					<input type="text" name="search" class="form-control" placeholder="Search todos..." value="${search}">
				</div>
				<button type="submit" class="btn btn-default">Search</button>
				<a href="/list-todos" class="btn btn-default">Clear</a>
				<a class="btn btn-primary pull-right" href="/add-todo">Add Todo</a>
			</form>

						<!-- Filters -->
						<form class="form-inline" method="GET" action="/list-todos" style="margin-bottom:10px;">
							<div class="form-group" style="margin-right:10px;">
								<label>Filter by Status:</label>
								<select name="statusFilter" class="form-control" style="margin-left:5px;">
									<option value="">All</option>
									<c:forEach items="${statuses}" var="status">
										<option value="${status}" <c:if test="${statusFilter == status}">selected</c:if>>${status.label}</option>
									</c:forEach>
								</select>
							</div>
							<div class="form-group">
								<label>Filter by Priority:</label>
								<select name="priorityFilter" class="form-control" style="margin-left:5px;">
									<option value="">All</option>
									<c:forEach items="${priorities}" var="priority">
										<option value="${priority}" <c:if test="${priorityFilter == priority}">selected</c:if>>${priority.label}</option>
									</c:forEach>
								</select>
							</div>
							<button type="submit" class="btn btn-default" style="margin-left:10px;">Apply</button>
						</form>

			<div class="panel panel-primary">
				<div class="panel-heading">
					<h3>List of TODO's</h3>
				</div>
				<div class="panel-body">

					<!-- Empty state -->
					<c:if test="${empty todos}">
						<div class="alert alert-info">
							<strong>No todos yet!</strong> Click "Add Todo" to create one.
						</div>
					</c:if>

					<!-- Todos table -->
					<c:if test="${not empty todos}">
						<table class="table table-striped">
							<thead>
							<tr>
								<th width="40%">Description</th>
								<th width="30%">Target Date</th>
								<th width="30%"></th>
							</tr>
							</thead>
							<tbody>
							<c:forEach items="${todos}" var="todo">
								<tr>
									<td>${todo.description}</td>
									<td>
										<fmt:formatDate value="${todo.targetDate}" pattern="dd/MM/yyyy" />
										<c:set var="daysLeft" value="${daysLeftMap[todo.id]}" />
										<c:choose>
											<c:when test="${daysLeft == null}">
												<!-- no date -->
											</c:when>
											<c:when test="${daysLeft lt 0}">
												<small class="text-danger">&nbsp;<strong>OVERDUE</strong></small>
											</c:when>
											<c:otherwise>
												<small class="text-muted">&nbsp;(in ${daysLeft} days)</small>
											</c:otherwise>
										</c:choose>
									</td>
									<td>
										<a type="button" class="btn btn-success btn-sm" href="/update-todo?id=${todo.id}">Update</a>
										<a type="button" class="btn btn-warning btn-sm" href="/delete-todo?id=${todo.id}"
										   onclick="return confirm('Are you sure you want to delete this todo?');">Delete</a>
									</td>
								</tr>
							</c:forEach>
							</tbody>
						</table>
					</c:if>

				</div>
			</div>

		</div>
	</div>
</div>

<%@ include file="common/footer.jspf"%>