<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.dao.UserDAO, com.model.User" %>

<%@ include file="include/header.jsp" %>
<%@ include file="include/sidebar.jsp" %>

<%
    int id = Integer.parseInt(request.getParameter("id"));
    UserDAO dao = new UserDAO();
    User u = dao.getUserById(id);
%>

<div class="content-area">
    <h3 class="fw-bold mb-4">Edit User</h3>
    
     <% 
    	String error= (String) request.getAttribute("msg");
    
    	if(error != null){
    %>
    <div class="alert alert-danger">
    	<%= error %>
    </div>
    <% } %>
    

    <form action="../UpdateUserServlet" method="post" class="card p-4 shadow-sm">
        <input type="hidden" name="id" value="<%=u.getId()%>">

        <div class="mb-3">
            <label class="form-label">Name</label>
            <input type="text" name="name" class="form-control" value="<%=u.getName()%>" required>
        </div>

        <div class="mb-3">
            <label class="form-label">Email</label>
            <input type="email" name="email" class="form-control" value="<%=u.getEmail()%>" required>
        </div>

        <div class="mb-3">
            <label class="form-label">Phone</label>
            <input type="text" name="phone" class="form-control" value="<%=u.getPhone()%>" required>
        </div>

        <div class="mb-3">
            <label class="form-label">Role</label>
            <select name="role" class="form-select">
                <option value="USER" <%= u.getRole().equals("USER")?"selected":"" %>>USER</option>
                <option value="ADMIN" <%= u.getRole().equals("ADMIN")?"selected":"" %>>ADMIN</option>
            </select>
        </div>

        <button type="submit" class="btn btn-primary">Update User</button>
        <a href="manage-users.jsp" class="btn btn-secondary">Back</a>
    </form>
</div>

<%@ include file="include/footer.jsp" %>