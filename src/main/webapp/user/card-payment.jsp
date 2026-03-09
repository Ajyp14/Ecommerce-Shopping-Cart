<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>


<%
    // Receive data from redirect-payment.jsp or Servlet
    String fullname = request.getParameter("fullname");
    String phone = request.getParameter("phone");
    String address = request.getParameter("address");
    String total = request.getParameter("total");
    String msg = request.getParameter("msg");
    
    String productId = request.getParameter("productId");
    String qty = request.getParameter("qty");
    String payment = request.getParameter("payment");


    if (total == null) total = "0";
    
%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>Card Details Form</title>

<style>
    body {
        font-family: Arial, sans-serif;
        background: #f2f2f2;
        display: flex;
        justify-content: center;
        margin-top: 50px;
    }
    .card-container {
        background: #fff;
        padding: 25px;
        width: 350px;
        border-radius: 10px;
        box-shadow: 0 0 10px rgba(0,0,0,0.1);
    }
    .card-container h2 {
        text-align: center;
        margin-bottom: 20px;
    }
    .input-group { margin-bottom: 15px; }
    label { font-weight: bold; }

    input, select {
        width: 100%;
        padding: 10px;
        margin-top: 5px;
        border-radius: 5px;
        border: 1px solid #ccc;
        font-size: 14px;
    }

    .row {
        display: flex;
        gap: 10px;
    }

    button {
        width: 100%;
        padding: 12px;
        background: #28a745;
        color: #fff;
        border: none;
        font-size: 16px;
        border-radius: 5px;
        cursor: pointer;
    }
    button:hover { background: #218838; }

    .error-box {
        background: #ffe5e5;
        padding: 10px;
        border-left: 5px solid red;
        color: #a00000;
        margin-bottom: 15px;
        border-radius: 4px;
        font-size: 14px;
    }
</style>

</head>
<body>

<div class="card-container">

    <h2>Card Details</h2>
	<button class="btn btn-outline-secondary mb-3"
        onclick="window.history.back();">
    ← Back
</button>
    <!-- ERROR MESSAGE (if any) -->
    <% if (msg != null) { %>
        <div class="error-box"><%= msg %></div>
    <% } %>

    <!-- Card Payment Form → goes to CardPaymentServlet -->
   <form action="<%=request.getContextPath()%>/CardPaymentServlet" method="post">


        <!-- Hidden values -->
        <input type="hidden" name="fullname" value="<%= fullname %>">
        <input type="hidden" name="phone" value="<%= phone %>">
        <input type="hidden" name="address" value="<%= address %>">
        <input type="hidden" name="total" value="<%= total %>">
        
        <input type="hidden" name="productId" value="<%= productId %>">
	    <input type="hidden" name="qty" value="<%= qty %>">
        <input type="hidden" name="payment" value="<%= payment %>">
        

        <div class="input-group">
            <label>Card Number</label>
            <input type="text" name="cardNumber" maxlength="16" required>
        </div>

        <div class="input-group">
            <label>Card Holder Name</label>
            <input type="text" name="holderName" required>
        </div>

        <div class="row">
            <div class="input-group">
                <label>Expiry Month</label>
                <select name="expMonth" required>
                    <option value="">Month</option>
                    <option>01</option><option>02</option><option>03</option>
                    <option>04</option><option>05</option><option>06</option>
                    <option>07</option><option>08</option><option>09</option>
                    <option>10</option><option>11</option><option>12</option>
                </select>
            </div>

            <div class="input-group">
                <label>Expiry Year</label>
                <select name="expYear" required>
                    <option value="">Year</option>
                    <option>2026</option><option>2027</option>
                    <option>2028</option><option>2029</option><option>2030</option>
                </select>
            </div>
        </div>

        <div class="input-group">
            <label>CVV</label>
            <input type="password" name="cvv" maxlength="3" required>
        </div>

        <button type="submit">
            Pay ₹<%= total %>
        </button>

    </form>
</div>

</body>
</html>