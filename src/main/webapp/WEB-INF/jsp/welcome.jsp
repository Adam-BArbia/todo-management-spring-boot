<%@ include file="common/header.jspf"%>
<%@ include file="common/navigation.jspf"%>

<style>
	.home-hero {
		background: linear-gradient(135deg, var(--theme-blue) 0%, var(--theme-blue-mid) 100%);
		color: #fff;
		border-radius: 8px;
		padding: 32px;
		box-shadow: 0 10px 30px rgba(11, 35, 64, 0.18);
		margin-bottom: 24px;
	}
	.home-hero h1 {
		margin-top: 0;
		font-weight: 700;
	}
	.home-hero p {
		color: rgba(255,255,255,0.9);
		font-size: 16px;
	}
	.home-stat-card {
		background: #fff;
		border: 1px solid var(--theme-border);
		border-radius: 8px;
		padding: 22px;
		box-shadow: 0 6px 18px rgba(22, 33, 62, 0.08);
	}
	.home-stat-number {
		font-size: 42px;
		font-weight: 700;
		line-height: 1;
		color: var(--theme-blue);
	}
	.home-stat-label {
		margin-top: 8px;
		font-size: 14px;
		color: #6b7280;
	}
	.home-action-list {
		margin-top: 18px;
	}
	.home-action-list .btn {
		margin-right: 8px;
		margin-bottom: 8px;
	}
	.home-feature {
		background: #fff;
		border: 1px solid var(--theme-border);
		border-radius: 8px;
		padding: 18px;
		height: 100%;
		box-shadow: 0 4px 14px rgba(22, 33, 62, 0.06);
	}
	.home-feature i {
		font-size: 24px;
		color: var(--theme-blue);
		margin-bottom: 10px;
	}
	.home-muted {
		color: #dbe9ff;
	}
</style>

<div class="container">
	<div class="home-hero">
		<div class="row">
			<div class="col-md-8">
				<div style="display:flex; align-items:center; gap:12px; margin-bottom:10px;">
					<i class="fa fa-tasks" style="font-size:34px;"></i>
					<div>
						<div class="home-muted" style="text-transform:uppercase; letter-spacing:1px; font-size:12px;">Todo Management</div>
						<h1 style="margin-bottom:0;">Welcome, <c:out value="${name}"/>!</h1>
					</div>
				</div>
				<p>Stay on top of your tasks, track progress, and keep everything organized from one simple dashboard.</p>
				<div class="home-action-list">
					<a href="/list-todos" class="btn btn-primary btn-lg"><i class="fa fa-list"></i> View My Todos</a>
					<a href="/add-todo" class="btn btn-info btn-lg"><i class="fa fa-plus"></i> Add Todo</a>
				</div>
			</div>
			<div class="col-md-4">
				<div class="home-stat-card text-center">
					<div class="home-stat-number"><c:out value="${todoCount}"/></div>
					<div class="home-stat-label">Pending todos</div>
					<div style="margin-top:16px; color:#6b7280; font-size:13px;">
						<c:choose>
							<c:when test="${todoCount gt 0}">You have tasks waiting for your attention.</c:when>
							<c:otherwise>You’re all caught up. Add something new to get started.</c:otherwise>
						</c:choose>
					</div>
				</div>
			</div>
		</div>
	</div>

	<div class="row">
		<div class="col-md-4" style="margin-bottom:20px;">
			<div class="home-feature text-center">
				<i class="fa fa-bolt"></i>
				<h4>Quick Actions</h4>
				<p>Jump straight into your todo list or create a new task in one click.</p>
			</div>
		</div>
		<div class="col-md-4" style="margin-bottom:20px;">
			<div class="home-feature text-center">
				<i class="fa fa-tags"></i>
				<h4>Tags & Organization</h4>
				<p>Group tasks with tags so you can filter and manage work more easily.</p>
			</div>
		</div>
		<div class="col-md-4" style="margin-bottom:20px;">
			<div class="home-feature text-center">
				<i class="fa fa-check-circle"></i>
				<h4>Track Progress</h4>
				<p>Monitor subtasks, status, and completion from a clean and simple layout.</p>
			</div>
		</div>
	</div>

</div>
<%@ include file="common/footer.jspf"%>