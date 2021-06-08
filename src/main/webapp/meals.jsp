<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<html lang="ru">
<head>
    <link rel="stylesheet" type="text/css" href="css/mystyle.css"/>
    <title>Meals</title>
</head>
<body>
    <h3><a href="index.html">Home</a></h3>
    <h3><a href="users">Users</a></h3>
    <hr>
    <h2>meals</h2>

    <table>
        <th>Date</th>
        <th>Description</th>
        <th>Calories</th>
        <c:forEach var="a" items="${requestScope.meals}">
               <c:choose>
                <c:when test="${a.excess}">
                   <tr style="color:red;">
                       <td>${a.dateTime.toLocalDate()} ${a.dateTime.toLocalTime()}</td>
                       <td>${a.description}</td>
                       <td>${a.calories}</td>
                   </tr>
               </c:when>
                <c:otherwise>
                    <tr style="color:green;">
                        <td>${a.dateTime.toLocalDate()} ${a.dateTime.toLocalTime()}</td>
                        <td>${a.description}</td>
                        <td>${a.calories}</td>
                    </tr>
                </c:otherwise>
               </c:choose>
        </c:forEach>
    </table>
</body>
</html>
