<aside class="sidebar bg-dark vh-100" id="sidebar">
    <div class="text-center py-4 bg-primary fw-bold fs-5">Admin Panel</div>
    <nav class="nav flex-column px-3 mt-3">

    <!-- Dashboard -->
    <a href="dashboard.jsp" class="nav-link text-light">
        <i class="bi bi-speedometer2"></i> Dashboard
    </a>

    <!-- Product Section -->
    <h6 class="text-secondary mt-3">Products</h6>
    <a href="add-product.jsp" class="nav-link text-light">
        <i class="bi bi-plus-circle"></i> Add Product
    </a>
    <a href="manage-products.jsp" class="nav-link text-light">
        <i class="bi bi-box-seam"></i> Manage Products
    </a>

    <!-- User Section -->
    <h6 class="text-secondary mt-3">Users</h6>
    <a href="add-user.jsp" class="nav-link text-light">
        <i class="bi bi-person-plus"></i> Add User
    </a>
    <a href="manage-users.jsp" class="nav-link text-light">
        <i class="bi bi-people"></i> Manage Users
    </a>

    <!-- Order Section -->
    <h6 class="text-secondary mt-3">Orders</h6>
    <a href="manage-orders.jsp" class="nav-link text-light">
        <i class="bi bi-cart-check"></i> Manage Orders
    </a>

<a href="<%= request.getContextPath() %>/user/index" 
   class="nav-link text-warning mt-3">
    <i class="bi bi-box-arrow-right"></i> Home
</a>


    <!-- Logout -->
 <a href="<%= request.getContextPath() %>/logout" 
   class="nav-link text-warning mt-3">
    <i class="bi bi-box-arrow-right"></i> Logout
</a>

</nav>

</aside>

<main class="flex-grow-1" id="content-area">
    <nav class="navbar bg-white shadow-sm d-lg-none p-2">
        <button class="btn btn-outline-primary" onclick="toggleSidebar()">
            <i class="bi bi-list"></i> Menu
        </button>
        <span class="fw-bold ms-auto">Welcome Admin</span>
    </nav>

    <div class="container py-4">