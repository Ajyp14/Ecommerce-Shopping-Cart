
<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ include file="include/header.jsp" %>

<div class="container mt-4 text-center">
    <h2 class="text-success fw-bold">🎉 Order Placed Successfully!</h2>

    <p class="mt-3">Thank you, <b><%= request.getAttribute("name") %></b></p>

    <h4>Payment Method: <%= request.getAttribute("payment") %></h4>
    <h3>Total Paid: ₹<%= request.getAttribute("total") %></h3>

    <a href="user/index.jsp" class="btn btn-primary mt-3">View New items </a>
</div>

<%@ include file="include/footer.jsp" %>
