<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
 <title>Right Management Application</title>
</head>
<body>
 <center>
  <h1>Rights Management</h1>
        <h2>
         <a href="new">Add New Right</a>
         &nbsp;&nbsp;&nbsp;
         <a href="list">List All Rights</a>
         
        </h2>
 </center>
    <div align="center">
  <c:if test="${right != null}">
   <form action="update" method="post">
        </c:if>
        <c:if test="${right == null}">
   <form action="insert" method="post">
        </c:if>
        <table border="1" cellpadding="5">
            <caption>
             <h2>
              <c:if test="${right != null}">
               Edit Right
              </c:if>
              <c:if test="${right == null}">
               Add New Right
              </c:if>
             </h2>
            </caption>
          <c:if test="${right != null}">
           <input type="hidden" name="id" value="<c:out value='${right.id}' />" />
          </c:if>            
            <tr>
                <th>URI: </th>
                <td>
                 <input type="text" name="name" size="45"
                   value="<c:out value='${right.uri}' />"
                  />
                </td>
            </tr>
            <tr>
                <th>Short name: </th>
                <td>
                 <input type="text" name="email" size="45"
                   value="<c:out value='${right.shortName}' />"
                 />
                </td>
            </tr>
            <tr>
                <th>Long name: </th>
                <td>
                 <input type="text" name="country" size="15"
                   value="<c:out value='${right.longName}' />"
                 />
                </td>
            </tr>
            <tr>
                <th>Description: </th>
                <td>
                 <input type="text" name="country" size="15"
                   value="<c:out value='${right.desc}' />"
                 />
                </td>
            </tr>
            <tr>
             <td colspan="2" align="center">
              <input type="submit" value="Save" />
             </td>
            </tr>
        </table>
        </form>
    </div> 
</body>
</html>