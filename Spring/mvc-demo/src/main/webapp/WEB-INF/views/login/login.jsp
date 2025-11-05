<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    String error = request.getParameter("error");
%>
<html>
<body>
<h2>로그인</h2>

<% if (error != null) { %>
    <div style="color:red;">아이디 또는 비밀번호가 잘못되었습니다.</div>
<% } %>

<form action="login" method="post">
    아이디: <input type="text" name="userName" /><br/>
    비밀번호: <input type="password" name="password" /><br/>
    <button type="submit">로그인</button>
</form>
</body>
</html>
