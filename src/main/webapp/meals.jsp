<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> >
<%@ page contentType="text/html;charset=UTF-8" %>

<html lang="ru">
<head>
    <title>Meals</title>
</head>
<body>
    <h3><a href="index.html">Home</a></h3>
    <br>
    <h3><a href="users">Users</a></h3>
    <hr>
    <h2>meals</h2>

    <table style="border: 2px solid black;">
        <th style="text-align: center;border: 2px solid black;">Date</th>
        <th style="text-align: center;border: 2px solid black;">Description</th>
        <th style="text-align: center;border: 2px solid black;">Calories</th>
        <c:forEach var="a" items="${requestScope.meals}">
               <c:choose>
                <c:when test="${a.excess}">
                   <tr style="color:red;text-align: left">
                       <td style="border: 2px solid black;padding: 10px;">${a.dateTime.toLocalDate()} ${a.dateTime.toLocalTime()}</td>
                       <td style="border: 2px solid black;padding: 10px;">${a.description}</td>
                       <td style="border: 2px solid black;padding: 10px;">${a.calories}</td>
                   </tr>
               </c:when>
                <c:otherwise>
                    <tr style="color:green;text-align: left">
                        <td style="border: 2px solid black;padding: 10px;">${a.dateTime.toLocalDate()} ${a.dateTime.toLocalTime()}</td>
                        <td style="border: 2px solid black;padding: 10px;">${a.description}</td>
                        <td style="border: 2px solid black;padding: 10px;">${a.calories}</td>
                    </tr>
                </c:otherwise>
               </c:choose>
        </c:forEach>
    </table>
</body>
</html>
