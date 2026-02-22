<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="include/header.jsp" %>
<%@ page import="com.dao.CartDAO, com.model.Product, java.util.*" %>

<%
 

    CartDAO cartDao = new CartDAO();
    List<Product> cart = cartDao.getCartProducts(userId);

    double grandTotal = 0;
    for (Product p : cart) {
        grandTotal += p.getPrice() * p.getQuantity();
    }
%>

<div class="container mt-4">
    <h3 class="fw-bold mb-3">Checkout</h3>
<button class="btn btn-outline-secondary mb-3"
        onclick="window.history.back();">
    ← Back
</button>

    <form action="<%=request.getContextPath()%>/user/payment.jsp" method="post" class="row g-3">

        <div class="col-md-6">
            <label class="fw-bold">Full Name</label>
            <input type="text" name="fullname" class="form-control" required>
        </div>

        <div class="col-md-6">
            <label class="fw-bold">Phone Number</label>
            <input type="text" name="phone" class="form-control" required>
        </div>

        <div class="col-12">
            <label class="fw-bold">Address</label>
            <textarea name="address" class="form-control" required></textarea>
        </div>

        <h4 class="mt-3">Total Amount: ₹<%=grandTotal%></h4>

        <input type="hidden" name="total" value="<%=grandTotal%>">

        <button type="submit" class="btn btn-primary w-100">Place Order</button>
    </form>
</div>

<%@ include file="include/footer.jsp" %>