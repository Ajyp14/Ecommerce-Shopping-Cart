<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ include file="include/header.jsp" %>
<%@ include file="include/sidebar.jsp" %>

<div class="content-area">
    <h3 class="fw-bold mb-3 text-primary ">Add New User</h3>
    
    <% 
    	String error= (String) request.getAttribute("msg");
    
    	if(error != null){
    %>
    <div class="alert alert-danger">
    	<%= error %>
    </div>
    <% } %>

    <div class="card p-4 shadow" style="max-width:600px;">
       <form action="${pageContext.request.contextPath}/AddUserServlet" method="post">


            <div class="mb-3">
                <label class="form-label">Full Name*</label>
                <input type="text" name="name" required class="form-control" placeholder="Enter full name">
            </div>

            <div class="mb-3">
                <label class="form-label">Email*</label>
                <input type="email" name="email" required class="form-control" placeholder="Enter email">
            </div>

            <div class="mb-3">
                <label class="form-label">Password*</label>
                <input type="password" name="password" required class="form-control" placeholder="Enter password">
            </div>

            <div class="mb-3">
                <label class="form-label">Phone</label>
                <input type="text" name="phone" class="form-control" placeholder="Enter phone number">
            </div>

            <div class="mb-3">
                <label class="form-label">Role*</label>
                <select name="role" class="form-select">
                    <option value="USER">Customer</option>
                    <option value="ADMIN">Admin</option>
                </select>
            </div>

          <button class="btn btn-success w-100">Add User</button>
        </form>
    </div>
</div>

<style>
.content-area {
    margin-left: 240px;
    padding: 20px;
}
@media(max-width:768px){
    .content-area {
        margin-left: 0;
        padding: 12px;
    }
}
</style>

<%@ include file="include/footer.jsp"%>
 