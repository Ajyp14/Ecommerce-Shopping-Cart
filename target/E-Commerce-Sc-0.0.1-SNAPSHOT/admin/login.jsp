
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<!DOCTYPE html>
<html>
<head>
    <title>Admin Login</title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"
          rel="stylesheet">

    <style>
        body {
            background: #e9ecef;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
        }
        .login-box {
            background: white;
            width: 350px;
            padding: 25px;
            border-radius: 10px;
            box-shadow: 0 0 15px rgba(0,0,0,0.2);
        }
        .btn-login {
            width: 100%;
        }
        .title {
            font-weight: bold;
            font-size: 22px;
            text-align: center;
            margin-bottom: 15px;
        }
    </style>
</head>

<body>

<div class="login-box">

    <p class="title">Admin Login</p>

    <!-- SUCCESS MESSAGE (LOGOUT) -->
    <%
        String success = request.getParameter("success");
        if (success != null) {
    %>
        <div class="alert alert-success text-center py-2">
            <%= success %>
        </div>
    <% } %>

    <!-- ERROR MESSAGE (LOGIN FAILED) -->
    <%
        String error = request.getParameter("error");
        if (error != null) {
    %>
        <div class="alert alert-danger text-center py-2">
            <%= error %>
        </div>
    <% } %>

    <form action="${pageContext.request.contextPath}/AdminLoginServlet"
          method="post">

        <label class="fw-semibold">Username</label>
        <input type="text"
               name="username"
               class="form-control mb-3"
               required>

        <label class="fw-semibold">Password</label>
        <input type="password"
               name="password"
               class="form-control mb-3"
               required>

        <button type="submit" class="btn btn-primary btn-login">
            Login
        </button>
    </form>

</div>

</body>
</html>
