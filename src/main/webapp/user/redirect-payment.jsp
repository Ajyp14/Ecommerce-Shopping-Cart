<%@page import="com.model.User"%>
<%@page import="com.dao.UserDAO"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%
    String payment = request.getParameter("payment");
    String total = request.getParameter("total");
    String productId = request.getParameter("productId");
    String qty      = request.getParameter("qty");

    int userId = (Integer) session.getAttribute("userId");
    UserDAO udao= new UserDAO();
    
    User user =udao.getUserById(userId);
    
%>

<%-- COD Payment (POST required) --%>
<%
    if ("COD".equals(payment)){
%>
<form id="codForm" action="../place-order" method="post">

    <input type="hidden" name="payment" value="Cash on Delivery">
    <input type="hidden" name="total" value="<%= total %>">
    <input type="hidden" name="productId" value="<%=productId%>">
	<input type="hidden" name="qty" value="<%=qty%>">
    
</form>

<script>
    document.getElementById("codForm").submit();
</script>

<%
    return;
}
%>

<%-- CARD Payment --%>
<%
    if ("CARD".equals(payment)) {
        response.sendRedirect("card-payment.jsp?fullname=" + user.getName() +
                "&phone=" + user.getPhone() +
                "&address=" + user.getAddress() +
                "&total=" + total);
        return;
    }
%>

<%-- UPI Payment --%>
<%
    if ("UPI".equals(payment)) {
        response.sendRedirect("upi-payment.jsp?fullname=" + user.getName() +
                "&phone=" + user.getPhone() +
                "&address=" + user.getAddress()+
                "&total=" + total);
        return;
    }
%>