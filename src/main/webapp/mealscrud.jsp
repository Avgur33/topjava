<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <link rel="stylesheet" type="text/css" href="css/mystyle.css"/>
    <title>Show All Meals</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<h3><a href="users">Users</a></h3>
<h3><a href="meals">Meals</a></h3>
<hr>
<h2>Meals</h2>
<p><a href="mealscrud?action=create">Add Meal</a></p>
<table>
    <thead>
    <tr>
        <th>Date</th>
        <th>Descriptiton</th>
        <th>Calories</th>
        <th colspan=2>Action</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="a" items="${requestScope.meals}">
        <c:choose>
            <c:when test="${a.excess}">
                <tr style="color:red;text-align: left">
                    <td>${a.dateTime.toLocalDate()} ${a.dateTime.toLocalTime()}</td>
                    <td>${a.description}</td>
                    <td>${a.calories}</td>
                    <td><a href="mealscrud?action=update&mealId=<c:out value="${a.id}"/>">Update</a></td>
                    <td><a href="mealscrud?action=delete&mealId=<c:out value="${a.id}"/>">Delete</a></td>
                </tr>
            </c:when>
            <c:otherwise>
                <tr style="color:green;text-align: left">
                    <td>${a.dateTime.toLocalDate()} ${a.dateTime.toLocalTime()}</td>
                    <td>${a.description}</td>
                    <td>${a.calories}</td>
                    <td><a href="mealscrud?action=update&mealId=<c:out value="${a.id}"/>">Update</a></td>
                    <td><a href="mealscrud?action=delete&mealId=<c:out value="${a.id}"/>">Delete</a></td>
                </tr>
            </c:otherwise>
        </c:choose>
    </c:forEach>
    </tbody>
</table>
</body>
</html>