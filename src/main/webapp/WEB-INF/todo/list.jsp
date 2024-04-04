
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Title</title>
</head>
<h1>Todo List</h1>
<body>
<%--${todoDTOList}--%>

<ul>
    <c:forEach items="${todoDTOList}" var="dto">
        <li>
            <span><a href="">${dto.tno}</a></span>
            <span>${dto.title}</span>
            <span>${dto.dueDate}</span>
            <span>${dto.finished? "DONE": "NOT YET"}</span>
        </li>
    </c:forEach>
</ul>
</body>
</html>
