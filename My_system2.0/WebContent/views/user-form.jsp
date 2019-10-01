<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="mysystem.model.Right"%>
<%@ page import="mysystem.model.User"%>
<%@ page import="java.util.List"%>

<html>
<head>
<title>User Management Application</title>
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
			<h2>
				<c:if test="${user != null}">Edit User</c:if>
				<c:if test="${user == null}">Add New User</c:if>
			</h2>
		</div>
		<c:if test="${user != null}">
			<form action="update" method="post" style="margin-bottom: 0">
				<input type="hidden" name="id" value="<c:out value='${user.id}' />" />
		</c:if>
		<c:if test="${user == null}">
			<form action="insert" method="post">
		</c:if>

		<table class="w3-table-all w3-border">
			<tr>
				<th>Login:</th>
				<td><input type="text" name="login" size="45"
					value="<c:out value='${user.login}' />" /></td>
			</tr>
			<tr>
				<th>Password:</th>
				<td><input type="password" name="pass" size="45" value="" /></td>
			</tr>
			<tr>
				<th>Right:</th>
				<td>
					<%
						List<Right> lRight = (List<Right>) request.getAttribute("listAllRights");
						User us=(User) request.getAttribute("user");
						int i = 0;
						for (Right r : lRight) {
							out.print("<input class=\"w3-check\" type=\"checkbox\" name=\"right\" value=\"" + r.getId() + "\"");
							if (us!=null && us.getRights().indexOf(r)>=0)
								out.print(" checked=\"checked\"");
							
							out.println("> <label>" + r.getLongName() + "</label>");
							i++;
						}
						
					%>

				</td>
			</tr>

			<tr>
				<td colspan="2" class="w3-center"><input
					class="w3-btn w3-ripple w3-green w3-round-xxlarge w3-border w3-border-white"
					type="submit" value="Save" /></td>
			</tr>
		</table>
		</form>
		<div class="w3-container w3-light-grey w3-center"
			style="border-radius: 0 0 16px 16px; margin-top: 0">
			<h1>Users Management</h1>
			<h4>
				<a
					class="w3-btn w3-ripple w3-blue-grey w3-round-xxlarge w3-border w3-border-white"
					href="<%=request.getContextPath()%>/user/new">Add New User</a>
				&nbsp;&nbsp;&nbsp; <a
					class="w3-btn w3-ripple w3-blue-grey w3-round-xxlarge w3-border w3-border-white"
					href="<%=request.getContextPath()%>/user/list">List All User</a>
			</h4>
		</div>
	</div>
</body>
</html>