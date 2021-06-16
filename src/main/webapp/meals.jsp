<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://topjava.javawebinar.ru/functions" %>
<%--<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>--%>
<html>
<head>
    <title>Meal list</title>
    <style>
        .normal {
            color: green;
        }

        .excess {
            color: red;
        }
    </style>
</head>
<body>
<section>
    <h3><a href="index.html">Home</a></h3>
    <hr/>
    <h2>Meals</h2>
<section>
    <table class="datetime-tool">
        <tr>
            <td><label>От даты (включая)</label></td>
            <td><label>До даты (включая)</label></td>
            <td></td>
            <td><label>От времени (включая)</label></td>
            <td><label>До времени (исключая)</label></td>
        </tr>
        <tr>
            <td><input type="date" name="from-date" value="2014-06-12"/></td>
            <td><input type="date" name="to-date" value="2014-06-12"/></td>
            <td></td>
            <td><input type="time" name="from-time" value="08:30"/></td>
            <td><input type="time" name="to-time" value="08:30"/></td>
        </tr>
        <tr>
            <td></td>
            <td></td>
            <td></td>
            <td>
                <!--<button onclick="window.history.back()" type="button">reload</button> -->
            </td>
            <td>
                <button type="submit">Отфильтровать</button>
            </td>
        </tr>
    </table>
</section>
    <a href="meals?action=create">Add Meal</a>
    <br><br>
    <table border="1" cellpadding="8" cellspacing="0">
        <thead>
        <tr>
            <th>Date</th>
            <th>Description</th>
            <th>Calories</th>
            <th></th>
            <th></th>
        </tr>
        </thead>
        <c:forEach items="${meals}" var="meal">
            <jsp:useBean id="meal" type="ru.javawebinar.topjava.to.MealTo"/>
            <tr class="${meal.excess ? 'excess' : 'normal'}">
                <td>
                        <%--${meal.dateTime.toLocalDate()} ${meal.dateTime.toLocalTime()}--%>
                        <%--<%=TimeUtil.toString(meal.getDateTime())%>--%>
                        <%--${fn:replace(meal.dateTime, 'T', ' ')}--%>
                        ${fn:formatDateTime(meal.dateTime)}
                </td>
                <td>${meal.description}</td>
                <td>${meal.calories}</td>
                <td><a href="meals?action=update&id=${meal.id}">Update</a></td>
                <td><a href="meals?action=delete&id=${meal.id}">Delete</a></td>
            </tr>
        </c:forEach>
    </table>
</section>
</body>
</html>