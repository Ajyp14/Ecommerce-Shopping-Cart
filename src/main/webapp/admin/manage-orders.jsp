<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.dao.OrderDAO" %>
<%@ page import="com.model.Order" %>

<%@ include file="include/header.jsp" %>
<%@ include file="include/sidebar.jsp" %>

<%
    OrderDAO dao = new OrderDAO();
    List<Order> orders = dao.getAllOrders();
%>

<div class="content-area">
    <h3 class="fw-bold mb-3">Manage Orders</h3>

    <!-- SHOW MESSAGE -->
    <%
        String msg = request.getParameter("msg");
        if(msg != null){
    %>
        <div class="alert alert-info"><%=msg%></div>
    <% } %>

    <!-- ===================== DESKTOP TABLE ===================== -->
    <div class="table-responsive shadow-sm d-none d-md-block">
        <table class="table table-bordered align-middle text-center">
            <thead class="table-dark">
                <tr>
                    <th>Order ID</th>
                    <th>User</th>
                    <th>Items</th>
                    <th>Total (₹)</th>
                    <th>Date</th>
                    <th>Status</th>
                    <th>Action</th>
                </tr>
            </thead>

            <tbody>
            <% if(!orders.isEmpty()) {
                for(Order o : orders) { %>
                <tr>
                    <td>#<%=o.getId()%></td>
                    <td><%=o.getUserName()%></td>
                    <td><%=o.getItemCount()%></td>
                    <td class="fw-bold text-primary">₹ <%=o.getTotalAmount()%></td>
                    <td><%=o.getOrderDate()%></td>

                    <!-- STATUS UPDATE -->
                    <td>
                        <form action="../UpdateOrderStatus" method="post" class="d-flex">
                            <input type="hidden" name="id" value="<%=o.getId()%>">
                            <select name="status" class="form-select me-2">
                                <option <%=o.getStatus().equals("Pending")?"selected":""%>>Pending</option>
                                <option <%=o.getStatus().equals("Packing")?"selected":""%>>Packing</option>
                                <option <%=o.getStatus().equals("Shipped")?"selected":""%>>Shipped</option>
                                <option <%=o.getStatus().equals("Delivered")?"selected":""%>>Delivered</option>
                                <option <%=o.getStatus().equals("Cancelled")?"selected":""%>>Cancelled</option>
                            </select>
                            <button class="btn btn-success btn-sm">
                                <i class="bi bi-check-lg"></i>
                            </button>
                        </form>
                    </td>

                    <!-- DELETE ORDER -->
                    <td>
                        <% if(!o.getStatus().equals("Delivered")) { %>
                        <form action="../DeleteOrder" method="post"
                              onsubmit="return confirm('Are you sure you want to delete this order permanently?')">
                            <input type="hidden" name="id" value="<%=o.getId()%>">
                            <button class="btn btn-danger btn-sm">
                                <i class="bi bi-trash"></i>
                            </button>
                        </form>
                        <% } else { %>
                            <span class="badge bg-secondary">Locked</span>
                        <% } %>
                    </td>
                </tr>
            <% } } else { %>
                <tr>
                    <td colspan="7" class="text-muted">No Orders Found</td>
                </tr>
            <% } %>
            </tbody>
        </table>
    </div>

    <!-- ===================== MOBILE VIEW ===================== -->
    <div class="d-md-none">
        <% if(!orders.isEmpty()) {
            for(Order o : orders) { %>
        <div class="card mb-3 shadow-sm">
            <div class="card-body">

                <h6 class="fw-bold">Order #<%=o.getId()%> — <%=o.getUserName()%></h6>
                <p class="small text-muted">
                    Items: <%=o.getItemCount()%> • Date: <%=o.getOrderDate()%>
                </p>
                <p class="fw-bold text-primary">₹ <%=o.getTotalAmount()%></p>

                <!-- STATUS UPDATE -->
                <form method="post" action="${pageContext.request.contextPath}/UpdateOrderStatus">
                    <input type="hidden" name="id" value="<%=o.getId()%>">
                    <select name="status" class="form-select form-select-sm">
                        <option <%=o.getStatus().equals("Pending")?"selected":""%>>Pending</option>
                        <option <%=o.getStatus().equals("Delivered")?"selected":""%>>Delivered</option>
                        <option <%=o.getStatus().equals("Cancelled")?"selected":""%>>Cancelled</option>
                    </select>
                    <button class="btn btn-primary btn-sm mt-2 w-100">Update Status</button>
                </form>

                <!-- DELETE ORDER -->
                <% if(!o.getStatus().equals("Delivered")) { %>
                <form method="post"
                      action="${pageContext.request.contextPath}/DeleteOrder"
                      onsubmit="return confirm('Delete this order permanently?')">
                    <input type="hidden" name="id" value="<%=o.getId()%>">
                    <button class="btn btn-danger btn-sm mt-2 w-100">
                        🗑 Remove Order
                    </button>
                </form>
                <% } else { %>
                    <span class="badge bg-secondary w-100 mt-2 d-block text-center">
                       Not Delivered – Cannot Remove
                    </span>
                <% } %>

            </div>
        </div>
        <% } } else { %>
        <p class="text-center text-muted">No Orders Available</p>
        <% } %>
    </div>

</div>

<style>
.content-area {
    margin-left: 240px;
    padding: 20px;
    min-height: 100vh;
}
@media (max-width: 768px) {
    .content-area {
        margin-left: 0 !important;
        padding: 12px;
    }
}
</style>

<%@ include file="include/footer.jsp"%>