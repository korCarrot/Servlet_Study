<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %><html>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<head>
    <title>Title</title>
</head>
<body>
<c:if test="${param.result == 'error'}">
    <h6>아이디(로그인 전용 아이디) 또는 비밀번호를 잘못 입력했습니다.
        입력하신 내용을 다시 확인해주세요.</h6>
</c:if>

<form action="${contextPath}/login" method="post">
    <input type="text" name="mid" placeholder="아이디"><br>
    <input type="text" name="mpw" placeholder="비밀번호"><br>
    <input type="checkbox" name="auto"> 로그인 상태 유지
    <br>
    <button type="submit">로그인</button>
</form>
</body>
</html>
