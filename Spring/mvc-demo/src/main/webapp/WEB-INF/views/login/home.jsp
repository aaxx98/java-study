<%@ page contentType="text/html; charset=UTF-8" %>
<%
    String username = (String) session.getAttribute("username");
%>
<html>
<body>
<% if (username == null) { %>
    <div>로그인이 필요합니다. <a href="/login">로그인</a></div>
<% } else { %>
    <div>[<%= username %>]님 반갑습니다.</div>
    <a href="/logout">로그아웃</a>
<% } %>
</body>
</html>
