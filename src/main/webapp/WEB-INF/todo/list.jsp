
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>나의 할 일</title>
</head>
<h1>Todo List</h1>
<body>
<%--${todoDTOList}--%>
${loginInfo.mid}님 방가방가
<ul>
    <c:forEach items="${todoDTOList}" var="dto">
        <li>
            <span><a href="${contextPath}/todo/read?tno=${dto.tno}">${dto.tno}</a></span>
            <span>${dto.title}</span>
            <span>${dto.dueDate}</span>
            <span>${dto.finished? "DONE": "NOT YET"}</span>
        </li>
    </c:forEach>
</ul>

<form action="/jdbcex/logout" method="post">
    <button>LOGOUT</button>
</form>

</body>
</html>
