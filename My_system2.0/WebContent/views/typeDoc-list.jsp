<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>Type Documents Management Application</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/w3.css">
</head>
<body class="w3-light-grey w3-margin">
	<div class="w3-container w3-round-xlarge w3-card-4 w3-dark-grey"
		style="padding: 1px">
		<div class="w3-container w3-light-grey w3-center"
			style="border-radius: 16px 16px 0 0">
			<h1>Type Documents Management</h1>
			<h4>
				<a
					class="w3-btn w3-ripple w3-blue-grey w3-round-xxlarge w3-border w3-border-white"
					href="<%=request.getContextPath()%>/type_doc/new">Add New Type Documents</a>
				&nbsp;&nbsp;&nbsp; <a
					class="w3-btn w3-ripple w3-blue-grey w3-round-xxlarge w3-border w3-border-white"
					href="<%=request.getContextPath()%>/type_doc/list">List All Type Documents</a>
			</h4>
		</div>
		<div class="w3-container w3-green w3-center w3-leftbar w3-rightbar">
			<h2>List of Type Documents</h2>
		</div>


		<table class="w3-table-all w3-hoverable">

			<tr>
				<th>ID</th>
				<th>Dir Path</th>
				<th>Short name</th>
				<th>Long name</th>
				<th>Description</th>
				<th>Action</th>
			</tr>
			<c:forEach var="typeDoc" items="${listTypeDoc}">
				<tr>
					<td><c:out value="${typeDoc.id}" /></td>
					<td><c:out value="${typeDoc.dirPath}" /></td>
					<td><c:out value="${typeDoc.shortName}" /></td>
					<td><c:out value="${typeDoc.longName}" /></td>
					<td><c:out value="${typeDoc.desc}" /></td>
					<td><a
						class="w3-btn w3-ripple w3-green w3-round-xxlarge w3-border w3-border-white"
						href="<%=request.getContextPath() %>/type_doc/edit?id=<c:out value='${typeDoc.id}' />">Edit</a>
						&nbsp;&nbsp;&nbsp;&nbsp;<a
						class="w3-btn w3-ripple w3-green w3-round-xxlarge w3-border w3-border-white"
						href="<%=request.getContextPath() %>/type_doc/show_user?id=<c:out value='${typeDoc.id}' />">Show doc</a>
						&nbsp;&nbsp;&nbsp;&nbsp; <a
						class="w3-btn w3-ripple w3-red w3-round-xxlarge w3-border w3-border-white"
						href="<%=request.getContextPath() %>/type_doc/delete?id=<c:out value='${typeDoc.id}' />">Delete</a>
					</td>
				</tr>
			</c:forEach>
		</table>
		<div class="w3-container w3-light-grey w3-center"
			style="border-radius: 0 0 16px 16px">
			<h1>Type Documents Management</h1>
			<h4>
				<a
					class="w3-btn w3-ripple w3-blue-grey w3-round-xxlarge w3-border w3-border-white"
					href="<%=request.getContextPath()%>/type_doc/new">Add New Type Documents</a>
				&nbsp;&nbsp;&nbsp; <a
					class="w3-btn w3-ripple w3-blue-grey w3-round-xxlarge w3-border w3-border-white"
					href="<%=request.getContextPath()%>/type_doc/list">List All Type Documents</a>
			</h4>
		</div>
	</div>
</body>
</html>