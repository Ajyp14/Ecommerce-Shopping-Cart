<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="include/header.jsp" %>

<div class="container mt-4">
    <h3>UPI Payment</h3>

    <form action="../place-order" method="post">

        <input type="hidden" name="fullname" value="<%=request.getParameter("fullname")%>">
        <input type="hidden" name="phone" value="<%=request.getParameter("phone")%>">
        <input type="hidden" name="address" value="<%=request.getParameter("address")%>">
        <input type="hidden" name="payment" value="UPI">

        <label>Enter UPI ID</label>
        <input type="text" name="upiId" class="form-control" required>

        <button class="btn btn-success w-100 mt-3">Pay & Place Order</button>
    </form>
</div>

<%@ include file="include/footer.jsp" %>