<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ include file="include/header.jsp" %>

<style>
.register-container {
    max-width: 500px;
    margin: 100px auto;
    background: #ffffff;
    border-radius: 10px;
    padding: 30px;
    box-shadow: 0 0 15px rgba(0,0,0,0.1);
}
</style>

<div class="register-container">

    <h4 class="text-center fw-bold mb-3">Create Account</h4>

    <!-- Display Error or Success Message -->
    <%
        String msg = request.getParameter("msg");
        if(msg != null){
    %>
        <div class="alert alert-info text-center py-2"><%= msg %></div>
    <% } %>

  <form action="${pageContext.request.contextPath}/RegisterServlet" method="post">

    <div class="mb-3">
        <label class="form-label fw-bold">Full Name</label>
        <input type="text" name="name" required class="form-control">
    </div>

    <div class="mb-3">
        <label class="form-label fw-bold">Email</label>
        <input type="email" name="email" required class="form-control">
    </div>

    <div class="mb-3">
        <label class="form-label fw-bold">Password</label>
        <input type="password" name="password" pattern="(?=.*[A-Z])(?=.*[0-9]).{8,}"
       title="Password must be at least 8 characters with 1 uppercase letter and 1 number" required class="form-control">
    </div>

    <div class="mb-3">
        <label class="form-label fw-bold">Phone</label>
        <input type="text" name="phone" required class="form-control">
    </div>

    <div class="mb-3">
        <label class="form-label fw-bold">Address (Optional)</label>
        <textarea name="address" class="form-control"></textarea>
    </div>

    <button type="submit" class="btn btn-primary w-100">Register</button>
</form>

    <p class="text-center mt-3">
        Already have an account?
        <a href="login.jsp" class="fw-bold text-success">Login Here</a>
    </p>

</div>

<%@ include file="include/footer.jsp" %>