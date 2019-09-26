<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
 <title>Rights Management Application</title>
</head>
<body>
 <center>
  <h1>Rights Management</h1>
        <h2>
         <a href="right/new">Add New Right</a>
         &nbsp;&nbsp;&nbsp;
         <a href="right/list">List All Right</a>
         
        </h2>
 </center>
    <div align="center">
        <table border="1" cellpadding="5">
            <caption><h2>List of Users</h2></caption>
            <tr>
                <th>ID</th>
                <th>URI</th>
                <th>Short name</th>
                <th>Long name</th>
                <th>Description</th>
                <th>Action</th>
            </tr>
            <c:forEach var="right" items="${listRight}">
                <tr>
                    <td><c:out value="${right.id}" /></td>
                    <td><c:out value="${right.uri}" /></td>
                    <td><c:out value="${right.shortName}" /></td>
                    <td><c:out value="${right.longName}" /></td>
                    <td><c:out value="${right.desc}" /></td>
                    <td>
                     <a href="edit?id=<c:out value='${right.id}' />">Edit</a>
                     &nbsp;&nbsp;&nbsp;&nbsp;
                     <a href="delete?id=<c:out value='${right.id}' />">Delete</a>                     
                    </td>
                </tr>
            </c:forEach>
        </table>
    </div> 
</body>
</html>