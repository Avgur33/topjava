<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html lang="ru">
<head>
    <link rel="stylesheet" type="text/css" href="js/jquery.datetimepicker.min.css"/>
    <script src="js/jquery.js"></script>
    <script src="js/jquery.datetimepicker.full.js"></script>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Create or Update meal</title>

</head>
<body>

<h3><a href="index.html">Home</a></h3>
<hr>
<h1>Edit meal</h1>
<form method="POST" action='mealscrud' name="frmAddMeal" style="line-height:10px;">
    <input type="text" readonly="readonly" name="id" style="visibility: hidden"
           value="<c:out value="${requestScope.meal.id}" />"/>
    <table>
        <tr>
            <td>DateTime</td>
            <td>
                <input type="datetime-local" name="dateTime" value="<c:out value="${requestScope.meal.dateTime}" />"/>
            </td>
            <%--<td><input type="datetime-local" id="datetimepicker"/></td>--%>
        </tr>
        <tr>
            <td>Description:</td>
            <td>
                <input type="text" name="description" value="<c:out value="${requestScope.meal.description}" />"/>
            </td>
        </tr>
        <tr>
            <td>Calories:</td>
            <td><input type="text" name="calories" value="<c:out value="${requestScope.meal.calories}" />"/></td>
        </tr>
        <%--DateTime: <input type="text" name="dob" value=&lt;%&ndash;
                         value="<fmt:formatDate pattern="MM/dd/yyyy" value="${meal.dob}" />"/> <br/>&ndash;%&gt;
        --%>
    </table>
    <input type="submit" value="Save"/> <input type="reset" value="Cancel"/>
</form>
<script>

    $(function () {
        $('#datetimepicker').datetimepicker({
            format: 'd.m.Y H:i'
        });
    });
</script>
</body>
</html>