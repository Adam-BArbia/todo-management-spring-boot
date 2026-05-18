<%@ include file="common/header.jspf"%>
<%@ include file="common/navigation.jspf"%>

<div class="container">

	<div class="panel panel-primary">
		<div class="panel-heading">Home Page</div>
		<div class="panel-body">
			<h4>Welcome <strong><c:out value="${name}"/></strong>!</h4>

			<p>
				You have
				<span class="badge">
          <c:choose>
			  <c:when test="${not empty todoCount}">
				  <c:out value="${todoCount}"/>
			  </c:when>
			  <c:otherwise>
				  0
			  </c:otherwise>
		  </c:choose>
        </span>
				pending todos.
			</p>

			<a href="/list-todos" class="btn btn-primary">Manage Your Todos</a>
		</div>
	</div>

</div>
<%@ include file="common/footer.jspf"%>