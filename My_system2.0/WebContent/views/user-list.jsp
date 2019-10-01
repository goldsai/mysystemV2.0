<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>Users Management Application</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/w3.css">
</head>
<body class="w3-light-grey w3-margin">
	<div class="w3-container w3-round-xlarge w3-card-4 w3-dark-grey"
		style="padding: 1px">
		<div class="w3-container w3-light-grey w3-center"
			style="border-radius: 16px 16px 0 0">
			<h1>Users Management</h1>
			<h4>
				<a
					class="w3-btn w3-ripple w3-blue-grey w3-round-xxlarge w3-border w3-border-white"
					href="<%=request.getContextPath()%>/user/new">Add New User</a>
				&nbsp;&nbsp;&nbsp; <a
					class="w3-btn w3-ripple w3-blue-grey w3-round-xxlarge w3-border w3-border-white"
					href="<%=request.getContextPath()%>/user/list">List All Users</a>
			</h4>
		</div>
		<div class="w3-container w3-green w3-center w3-leftbar w3-rightbar">
			<h2>List of User</h2>
		</div>


		<table class="w3-table-all w3-hoverable">

			<tr>
				<th>ID</th>
				<th>Login</th>
				<th>Right</th>

				<th>Action</th>
			</tr>
			<c:forEach var="user" items="${listUser}">
				<tr>
					<td><c:out value="${user.id}" /></td>
					<td><c:out value="${user.login}" /></td>
					<td>&nbsp;<c:forEach var="right" items="${user.rights}">
							<div class="w3-tag w3-round w3-green" style="padding: 3px">
								<div class="w3-tag w3-round w3-green w3-border w3-border-white">
									<c:out value="${right.shortName}" />
								</div>
							</div>
						</c:forEach>
					</td>
					<td><a
						class="w3-btn w3-ripple w3-green w3-round-xxlarge w3-border w3-border-white"
						href="<%=request.getContextPath() %>/user/edit?id=<c:out value='${user.id}' />">Edit</a>
						&nbsp;&nbsp;&nbsp;&nbsp;<a
						class="w3-btn w3-ripple w3-green w3-round-xxlarge w3-border w3-border-white"
						href="<%=request.getContextPath() %>/user/show_user?id=<c:out value='${user.id}' />">Show
							user</a> &nbsp;&nbsp;&nbsp;&nbsp; <a
						class="w3-btn w3-ripple w3-red w3-round-xxlarge w3-border w3-border-white"
						href="<%=request.getContextPath() %>/user/delete?id=<c:out value='${user.id}' />">Delete</a>
					</td>
				</tr>
			</c:forEach>
		</table>
		<div class="w3-container w3-light-grey w3-center"
			style="border-radius: 0 0 16px 16px">
			<h1>Users Management</h1>
			<h4>
				<a
					class="w3-btn w3-ripple w3-blue-grey w3-round-xxlarge w3-border w3-border-white"
					href="<%=request.getContextPath()%>/user/new">Add New User</a>
				&nbsp;&nbsp;&nbsp; <a
					class="w3-btn w3-ripple w3-blue-grey w3-round-xxlarge w3-border w3-border-white"
					href="<%=request.getContextPath()%>/user/list">List All Users</a>
			</h4>
		</div>
	</div>
</body>
</html>