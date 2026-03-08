<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.dao.ProductDAO,com.model.Product" %>
<%@ include file="include/header.jsp" %>

<%

    int id = Integer.parseInt(request.getParameter("id"));
    int qty = Integer.parseInt(request.getParameter("qty"));

    ProductDAO dao = new ProductDAO();
    Product p = dao.getProductById(id);

    if(qty > p.getQuantity()){
        response.sendRedirect("product-detail.jsp?id="+id+"&msg=Out-of-Stock");
        return;
    }

    double total = p.getPrice() * qty;
%>

<style>
.checkout-box{
    background:#fff;
    border-radius:12px;
    padding:30px;
    box-shadow:0 8px 25px rgba(0,0,0,0.1);
}
.product-img{
    width:100%;
    max-height:200px;
    object-fit:contain;
    background:#f8f9fa;
    border-radius:10px;
}
.price{
    font-size:22px;
    font-weight:700;
    color:#dc3545;
}
.summary-box{
    background:#f8f9fa;
    border-radius:10px;
    padding:20px;
}
</style>

<div class="container my-5">

    <div class="row justify-content-center">
        <div class="col-lg-8">

            <div class="checkout-box">

                <h3 class="fw-bold mb-4 text-center">Checkout</h3>

                <div class="row g-4">

                    <!-- PRODUCT INFO -->
                    <div class="col-md-4 text-center">
                        <img src="<%=p.getImage1()%>"
                             class="product-img mb-3">
                        <h6 class="fw-bold"><%=p.getName()%></h6>
                        <p class="text-muted mb-1">Brand: <%=p.getBrand()%></p>
                        <p class="price">₹ <%=p.getPrice()%></p>
                    </div>

                    <!-- ORDER SUMMARY -->
                    <div class="col-md-8">

                        <div class="summary-box">

                            <h5 class="fw-bold mb-3">Order Summary</h5>

                            <div class="d-flex justify-content-between mb-2">
                                <span>Price</span>
                                <span>₹ <%=p.getPrice()%></span>
                            </div>

                            <div class="d-flex justify-content-between mb-2">
                                <span>Quantity</span>
                                <span><%=qty%></span>
                            </div>

                            <div class="d-flex justify-content-between mb-2">
                                <span>Delivery</span>
                                <span class="text-success">FREE</span>
                            </div>

                            <hr>

                            <div class="d-flex justify-content-between fw-bold fs-5">
                                <span>Total</span>
                                <span>₹ <%=total%></span>
                            </div>

                        </div>

                        <!-- PAYMENT FORM -->
                        <form action="payment.jsp" method="post" class="mt-4">

                            <input type="hidden" name="productId" value="<%=id%>">
                            <input type="hidden" name="qty" value="<%=qty%>">
                            <input type="hidden" name="total" value="<%=total%>">

                            <button class="btn btn-primary w-100 py-2 fw-bold">
                                Continue to Payment
                            </button>
                        </form>

                        <button class="btn btn-outline-secondary w-100 mt-2"
                                onclick="history.back()">
                            ← Back
                        </button>

                    </div>
                </div>

            </div>

        </div>
    </div>

</div>

<%@ include file="include/footer.jsp" %>