<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
    <h3 class="fw-bold mb-3">My Cart</h3>
<button class="btn btn-outline-secondary mb-3"
        onclick="window.history.back();">
    ← Back
</button>
    <% String msg = request.getParameter("msg");
       if (msg != null) { %>
       <div class="alert alert-info"><%= msg.replace("+", " ") %></div>
    <% } %>

    <% if (cart.isEmpty()) { %>
        <p class="text-muted text-center">Your cart is empty.</p>
    <% } else { %>

    <table class="table table-bordered text-center align-middle">
        <tr class="table-primary">
            <th>Image</th>
            <th>Name</th>
            <th>Price</th>
            <th>Quantity</th>
            <th>Total</th>
            <th>Action</th>
        </tr>

        <% for (Product p : cart) {
               double t = p.getPrice() * p.getQuantity();
        %>
        <tr>
            <td style="width:80px;">
                <img src="<%=request.getContextPath()%>/product-images/<%=p.getImage1()%>" width="60" alt="">
            </td>
            <td><%= p.getName() %></td>
            <td>₹<%= p.getPrice() %></td>

            <!-- update qty form -->
            <td>
                <form action="<%=request.getContextPath()%>/update-cart" method="post" class="d-inline">
                    <input type="hidden" name="cartId" value="<%= p.getCartId() %>">
                    <input type="number" name="qty" min="1" value="<%= p.getQuantity() %>"
                           class="form-control form-control-sm w-50 d-inline">
                    <button type="submit" class="btn btn-sm btn-outline-success">✔</button>
                </form>
            </td>

            <td>₹<%= t %></td>

            <td>
                <a href="<%=request.getContextPath()%>/remove-cart-item?cid=<%=p.getCartId()%>"
                   class="btn btn-sm btn-danger">🗑 Remove</a>
            </td>
        </tr>
        <% } %>

    </table>

    <h4 class="text-end fw-bold">Grand Total: ₹<%= grandTotal %></h4>

    <a href="<%=request.getContextPath()%>/user/checkout.jsp" class="btn btn-primary w-100">Proceed to Checkout</a>
    <% } %>
</div>

<%@ include file="include/footer.jsp" %>