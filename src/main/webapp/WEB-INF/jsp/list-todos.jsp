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
<option value="${status.name()}" <c:if test="${statusFilter == status.toString()}">selected</c:if>>${status.label}</option>
</c:forEach>
</select>
</div>
<div class="form-group">
<label>Filter by Priority:</label>
<select name="priorityFilter" class="form-control" style="margin-left:5px;">
<option value="">All</option>
<c:forEach items="${priorities}" var="priority">
<option value="${priority.name()}" <c:if test="${priorityFilter == priority.toString()}">selected</c:if>>${priority.label}</option>
</c:forEach>
</select>
</div>
<div class="form-group" style="margin-left:10px;">
<label>Filter by Tag:</label>
<select name="tagFilter" class="form-control" style="margin-left:5px;">
<option value="">All</option>
<c:forEach items="${allTags}" var="tag">
<option value="${tag.name}" <c:if test="${tagFilter == tag.name}">selected</c:if>>${tag.name}</option>
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
<th width="25%">Description</th>
<th width="15%">Target Date</th>
<th width="12%">Priority</th>
<th width="12%">Status</th>
<th width="13%">Tags</th>
<th width="23%">Actions</th>
</tr>
</thead>
<tbody>
<c:forEach items="${todos}" var="todo">
			<!-- Main todo row -->
			<tr>
			<td>
				<!--  -->
					<a href="#" onclick="toggleSubtasks(${todo.id}); return false;" class="btn btn-link" style="padding: 0; margin-right: 5px;" title="Show/Hide Subtasks"><i class="fa fa-chevron-down" id="arrow-${todo.id}"></i></a>
				<!---->
				${todo.description}
				<c:if test="${subtaskTotalMap[todo.id] != null && subtaskTotalMap[todo.id] > 0}">
					<br/>
					<small class="text-muted">(${subtaskCompletedMap[todo.id]}/${subtaskTotalMap[todo.id]} subtasks done)</small>
				</c:if>
			</td>
<td>
<fmt:formatDate value="${todo.targetDate}" pattern="dd/MM/yyyy" />
<br>
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
<span class="label ${todo.priority.cssClass}">
${todo.priority.label}
</span>
</td>
<td>
<span class="label ${todo.status.cssClass}">
${todo.status.label}
</span>
</td>
<td>
<c:if test="${not empty todo.tags}">
<c:forEach items="${todo.tags}" var="tag">
<span class="label label-default" style="margin-right: 3px;">${tag.name}</span>
</c:forEach>
</c:if>
<c:if test="${empty todo.tags}">
<span class="text-muted">-</span>
</c:if>
</td>
			<td>
				<div style="margin-bottom: 8px;">
					<a href="/update-todo?id=${todo.id}" class="btn btn-info btn-sm" title="Edit"><i class="fa fa-pencil"></i></a>
					<a href="/delete-todo?id=${todo.id}" class="btn btn-danger btn-sm" title="Delete" onclick="return confirm('Are you sure you want to delete this todo?');"><i class="fa fa-trash"></i></a>
				</div>
<hr style="margin: 6px 0; border-color: #ddd;">
<div style="font-size: 11px; margin-bottom: 4px;">
<c:if test="${todo.status.toString() == 'TODO'}">
<a href="/update-todo-status?id=${todo.id}&status=IN_PROGRESS" class="btn btn-info btn-xs"><i class="fa fa-play"></i></a>
<a href="/update-todo-status?id=${todo.id}&status=COMPLETED" class="btn btn-success btn-xs"><i class="fa fa-check"></i></a>
</c:if>
<c:if test="${todo.status.toString() == 'IN_PROGRESS'}">
<a href="/update-todo-status?id=${todo.id}&status=COMPLETED" class="btn btn-success btn-xs"><i class="fa fa-check"></i></a>
<a href="/update-todo-status?id=${todo.id}&status=TODO" class="btn btn-default btn-xs"><i class="fa fa-undo"></i></a>
</c:if>
<c:if test="${todo.status.toString() == 'COMPLETED'}">
<a href="/update-todo-status?id=${todo.id}&status=TODO" class="btn btn-warning btn-xs"><i class="fa fa-refresh"></i></a>
</c:if>
</div>
</td>
</tr>
			<!-- Subtasks collapsible row -->
			<tr id="subtasks-row-${todo.id}" style="display:none;">
			<td colspan="6">
				<div class="subtasks-list" style="background-color: #f9f9f9; border-left: 4px solid #0b2340; padding: 15px; margin: 0; border-radius: 4px;">
					<c:if test="${not empty todo.subtasks}">
						<table class="table table-condensed" style="margin: 0; background-color: transparent;">
							<thead style="background-color: transparent;">
							<tr style="border-top: 1px solid #ddd;">
								<th style="background-color: transparent;">Name</th>
								<th style="background-color: transparent;">Due</th>
								<th style="background-color: transparent;">Status</th>
								<th style="background-color: transparent;">Action</th>
							</tr>
							</thead>
							<tbody>
							<c:forEach items="${todo.subtasks}" var="st">
								<tr style="background-color: white;">
									<td>${st.name}</td>
									<td>
										<c:if test="${not empty st.dueFrom}">
											<fmt:formatDate value="${st.dueFrom}" pattern="dd/MM/yyyy" />
										</c:if>
									</td>
									<td>
										<span class="label ${st.status.cssClass}">${st.status.label}</span>
									</td>
									<td>
										<div style="font-size: 11px;">
											<c:choose>
												<c:when test="${st.status.toString() == 'COMPLETED'}">
													<a href="/subtask/update-status?id=${st.id}&status=TODO" class="btn btn-warning btn-xs"><i class="fa fa-undo"></i></a>
												</c:when>
												<c:when test="${st.status.toString() == 'TODO'}">
													<a href="/subtask/update-status?id=${st.id}&status=IN_PROGRESS" class="btn btn-info btn-xs"><i class="fa fa-play"></i></a>
													<a href="/subtask/update-status?id=${st.id}&status=COMPLETED" class="btn btn-success btn-xs"><i class="fa fa-check"></i></a>
												</c:when>
												<c:when test="${st.status.toString() == 'IN_PROGRESS'}">
													<a href="/subtask/update-status?id=${st.id}&status=COMPLETED" class="btn btn-success btn-xs"><i class="fa fa-check"></i></a>
													<a href="/subtask/update-status?id=${st.id}&status=TODO" class="btn btn-default btn-xs"><i class="fa fa-undo"></i></a>
												</c:when>
											</c:choose>
										</div>
									</td>
								</tr>
							</c:forEach>
							</tbody>
						</table>
					</c:if>
					<c:if test="${empty todo.subtasks}">
						<div class="text-muted">No subtasks yet</div>
					</c:if>
					<hr style="margin: 10px 0;">
					<div class="panel panel-default" style="margin:10px 0; border: 1px solid #e3e3e3;">
						<div class="panel-heading" style="padding: 8px 15px; background-color: #f5f5f5;">
							<h5 style="margin: 0; font-size: 12px;"><strong>Add New Subtask</strong></h5>
						</div>
						<div class="panel-body" style="padding: 10px;">
							<form method="post" action="/subtask/create" class="form-inline" style="margin-bottom:0;">
								<input type="hidden" name="todoId" value="${todo.id}" />
								<div class="form-group" style="margin-right: 10px;">
									<input type="text" name="name" class="form-control" placeholder="Subtask name" required style="width:200px;" />
								</div>
								<div class="form-group" style="margin-right: 10px;">
									<input type="text" name="dueFrom" class="form-control" placeholder="Due date (dd/MM/yyyy)" style="width:150px;" />
								</div>
								<button type="submit" class="btn btn-success btn-sm">Add Subtask</button>
							</form>
						</div>
					</div>
				</div>
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
<script type="text/javascript">
function toggleSubtasks(id) {
	var row = document.getElementById('subtasks-row-' + id);
	var arrow = document.getElementById('arrow-' + id);
	if (!row) return;
	if (row.style.display === 'none' || row.style.display === '') {
		row.style.display = 'table-row';
		if (arrow) arrow.style.transform = 'rotate(180deg)';
	} else {
		row.style.display = 'none';
		if (arrow) arrow.style.transform = 'rotate(0deg)';
	}
}
</script>
