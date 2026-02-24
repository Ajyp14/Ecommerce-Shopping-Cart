<%@page import="com.util.DBConnection"%>
<%@page import="java.sql.Connection"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="jakarta.servlet.http.HttpSession" %>
<%@ include file="include/header.jsp" %>

<% Connection con = DBConnection.getConnection();
System.out.println("Connection object: " + con); %>

<style>
.login-container {
    max-width: 450px;
    margin: 100px auto;
    padding: 30px;
    background: white;
    border-radius: 10px;
    box-shadow: 0 0 15px rgba(0,0,0,0.1);
}
</style>

<div class="login-container">

    <h4 class="text-center fw-bold mb-4">User Login</h4>

    <!-- Show Login Message -->
    <%
        String msg = request.getParameter("msg");
        if(msg != null){
    %>
        <div class="alert alert-danger py-2 text-center"><%= msg %></div>
    <% } %>

<form action="${pageContext.request.contextPath}/login" method="post" class="p-4 shadow-sm bg-white rounded">


        <div class="mb-3">
            <label class="form-label fw-bold">Email</label>
            <input type="email" name="email" required class="form-control" placeholder="Enter your email">
        </div>

        <div class="mb-3">
            <label class="form-label fw-bold">Password</label>
            <input type="password" name="password" required class="form-control" placeholder="Enter your password">
        </div>

        <button type="submit" class="btn btn-success w-100">Login</button>
    </form>

    <p class="text-center mt-3">
        Don’t have an account?
        <a href="register.jsp" class="text-primary fw-bold">Register Here</a>
    </p>

</div>

<%@ include file="include/footer.jsp" %>
