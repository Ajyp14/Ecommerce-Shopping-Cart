<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="include/header.jsp" %>

<%

    String total = request.getParameter("total");
%>

<div class="container mt-4">
    <h3 class="fw-bold mb-3">Select Payment Method</h3>
    <button class="btn btn-outline-secondary mb-3"
        onclick="window.history.back();">
    ← Back
</button>

    <form action="redirect-payment.jsp" method="post">

        
        <input type="hidden" name="total" value="<%=total%>">
        <input type="hidden" name="productId" value="<%=request.getParameter("productId")%>">
		<input type="hidden" name="qty" value="<%=request.getParameter("qty")%>">
        

        <div class="card p-3 mb-3">
            <h5>Choose Payment Method</h5>

            <label class="d-block mt-2">
                <input type="radio" name="payment" value="COD" required> Cash on Delivery
            </label>


            <label class="d-block mt-2">
                <input type="radio" name="payment" value="CARD"> Credit / Debit Card
            </label>
        </div>

        <button class="btn btn-primary w-100">Continue & Proceed</button>
    </form>
</div>

<%@ include file="include/footer.jsp" %>