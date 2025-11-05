<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>Hello JSP</title>
    <style>
        body {
            font-family: "Segoe UI", Arial, sans-serif;
            background-color: #f8f9fa;
            color: #333;
            margin: 40px;
        }
        h1 {
            color: #007bff;
        }
        .box {
            background: white;
            border-radius: 8px;
            padding: 20px;
            box-shadow: 0 2px 6px rgba(0,0,0,0.1);
            width: fit-content;
        }
    </style>
</head>
<body>
    <div class="box">
        <h1>My HOME</h1>
        <p>이 페이지는 <strong>Spring Boot</strong>에서 렌더링된 JSP입니다.</p>
        <p>현재 시각: <%= new java.util.Date() %></p>
    </div>
</body>
</html>