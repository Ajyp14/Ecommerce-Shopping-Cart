<%@ page contentType="text/html; charset=UTF-8" %>

<%  
// Session checking  
response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
response.setHeader("Pragma", "no-cache");
response.setDateHeader("Expires", 0);


if(session.getAttribute("adminObj") == null) {
    response.sendRedirect("login.jsp?msg=Please login first!");
    return;
}
%>

<%@ include file="include/header.jsp" %>
<%@ include file="include/sidebar.jsp" %>

<!-- Custom CSS for dashboard -->
<style>
    body {
        background-color: #f4f6f8;
    }
    .dashboard-title {
        font-weight: 700;
        font-size: 28px;
        margin-bottom: 25px;
        color: #333;
        text-align: center;
    }
    .dashboard-cards {
        display: grid;
        grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
        gap: 20px;
        margin: 20px 0;
    }
    .dashboard-card {
        background: linear-gradient(145deg, #ffffff, #e6e6e6);
        border-radius: 15px;
        box-shadow: 0 8px 20px rgba(0,0,0,0.1);
        padding: 30px 20px;
        text-align: center;
        transition: transform 0.3s, box-shadow 0.3s;
    }
    .dashboard-card:hover {
        transform: translateY(-5px);
        box-shadow: 0 12px 25px rgba(0,0,0,0.2);
    }
    .dashboard-card h6 {
        font-size: 16px;
        color: #555;
        margin-bottom: 10px;
        text-transform: uppercase;
        letter-spacing: 1px;
    }
    .dashboard-card h3 {
        font-size: 32px;
        font-weight: 700;
        color: #222;
    }

    /* Gradient colors */
    .bg-products { background: linear-gradient(135deg, #6a11cb, #2575fc); color: white; }
    .bg-users { background: linear-gradient(135deg, #ff416c, #ff4b2b); color: white; }
    .bg-orders { background: linear-gradient(135deg, #00c6ff, #0072ff); color: white; }
    .bg-pending { background: linear-gradient(135deg, #f7971e, #ffd200); color: white; }
    .bg-delivered { background: linear-gradient(135deg, #11998e, #38ef7d); color: white; }
    .bg-cancelled { background: linear-gradient(135deg, #fc354c, #0abfbc); color: white; }
</style>

<h3 class="dashboard-title">Admin Dashboard</h3>

<!-- Dashboard Cards -->
<div class="dashboard-cards">
    <div class="dashboard-card bg-products">
        <h6>Total Products</h6>
        <h3 id="productCount">0</h3>
    </div>
    <div class="dashboard-card bg-users">
        <h6>Total Users</h6>
        <h3 id="userCount">0</h3>
    </div>
    <div class="dashboard-card bg-orders">
        <h6>Total Orders</h6>
        <h3 id="orderCount">0</h3>
    </div>
    <div class="dashboard-card bg-pending">
        <h6>Pending Orders</h6>
        <h3 id="pendingCount">0</h3>
    </div>
    <div class="dashboard-card bg-delivered">
        <h6>Delivered Orders</h6>
        <h3 id="deliveredCount">0</h3>
    </div>
    <div class="dashboard-card bg-cancelled">
        <h6>Cancelled Orders</h6>
        <h3 id="cancelCount">0</h3>
    </div>
</div>

<!-- Dashboard Data Script (NO GRAPH) -->
<script>
function loadDashboardData() {
    fetch('../OrderStatsServlet')
        .then(response => response.json())
        .then(data => {
            document.getElementById("productCount").innerText = data.products;
            document.getElementById("userCount").innerText = data.users;
            document.getElementById("orderCount").innerText = data.totalOrders;
            document.getElementById("pendingCount").innerText = data.pending;
            document.getElementById("deliveredCount").innerText = data.delivered;
            document.getElementById("cancelCount").innerText = data.cancelled;
        })
        .catch(err => console.error('Dashboard data error:', err));
}

loadDashboardData();
setInterval(loadDashboardData, 30000);
</script>

<%@ include file="include/footer.jsp" %>