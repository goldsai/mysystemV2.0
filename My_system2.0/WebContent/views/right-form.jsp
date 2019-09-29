<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>Right Management Application</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/w3.css">
</head>
<body class="w3-light-grey w3-margin">
	<div class="w3-container w3-round-xlarge w3-card-4 w3-dark-grey"
		style="padding: 1px">
		<div class="w3-container w3-light-grey w3-center"
			style="border-radius: 16px 16px 0 0">
			<h1>Rights Management</h1>
			<h4>
				<a
					class="w3-btn w3-ripple w3-blue-grey w3-round-xxlarge w3-border w3-border-white"
					href="<%=request.getContextPath()%>/right/new">Add New Right</a>
				&nbsp;&nbsp;&nbsp; <a
					class="w3-btn w3-ripple w3-blue-grey w3-round-xxlarge w3-border w3-border-white"
					href="<%=request.getContextPath()%>/right/list">List All Right</a>
			</h4>
		</div>
		<div class="w3-container w3-green w3-center w3-leftbar w3-rightbar">
			<h2>
				<c:if test="${right != null}">Edit Right</c:if>
				<c:if test="${right == null}">Add New Right</c:if>
			</h2>
		</div>
		<c:if test="${right != null}">
			<form action="update" method="post" style="margin-bottom:0">
			<input type="hidden" name="id" value="<c:out value='${right.id}' />" />
		</c:if>
		<c:if test="${right == null}"><form action="insert" method="post"></c:if>
		
		<table class="w3-table-all w3-border">
			<tr>
				<th>URI:</th>
				<td><input type="text" name="uri" size="45"
					value="<c:out value='${right.uri}' />" /></td>
			</tr>
			<tr>
				<th>Short name:</th>
				<td><input type="text" name="shortName" size="45"
					value="<c:out value='${right.shortName}' />" /></td>
			</tr>
			<tr>
				<th>Long name:</th>
				<td><input type="text" name="longName" size="45"
					value="<c:out value='${right.longName}' />" /></td>
			</tr>
			<tr>
				<th>Description:</th>
				<td><textarea name="desc" cols="45" rows="10"><c:out
							value='${right.desc}' /></textarea></td>
			</tr>
			<tr>
				<td colspan="2" class="w3-center"><input class="w3-btn w3-ripple w3-green w3-round-xxlarge w3-border w3-border-white" type="submit"
					value="Save" /></td>
			</tr>
		</table>
		</form>
		<div class="w3-container w3-light-grey w3-center"
			style="border-radius: 0 0 16px 16px; margin-top: 0">
			<h1>Rights Management</h1>
			<h4>
				<a
					class="w3-btn w3-ripple w3-blue-grey w3-round-xxlarge w3-border w3-border-white"
					href="<%=request.getContextPath()%>/right/new">Add New Right</a>
				&nbsp;&nbsp;&nbsp; <a
					class="w3-btn w3-ripple w3-blue-grey w3-round-xxlarge w3-border w3-border-white"
					href="<%=request.getContextPath()%>/right/list">List All Right</a>
			</h4>
		</div>
	</div>
</body>
</html>