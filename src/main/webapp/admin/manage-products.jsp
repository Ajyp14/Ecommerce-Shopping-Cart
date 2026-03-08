
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.dao.ProductDAO" %>
<%@ page import="com.model.Product" %>

<%@ include file="include/header.jsp" %>
<%@ include file="include/sidebar.jsp" %>

<%
    ProductDAO dao = new ProductDAO();

    String search = request.getParameter("search");
    String sort   = request.getParameter("sort"); // low / high

    List<Product> products;

    if (search != null && !search.trim().isEmpty()) {
        products = dao.searchProductsSorted(search.trim(), sort);
    } else {
        products = dao.getAllProductsSorted(sort);
    }
%>

<div class="content-area">

    <!-- Header + Search + Sort -->
    <div class="d-flex justify-content-between align-items-center mb-3 flex-wrap">
        <h3 class="fw-bold mb-2">Manage Products</h3>

        <form method="get" class="d-flex search-box align-items-center">

            <!-- Search -->
            <input type="text"
                   name="search"
                   value="<%= (search == null ? "" : search) %>"
                   class="form-control me-2"
                   placeholder="Search...">

            <!-- Price Sort Dropdown -->
            <select name="sort" class="form-select me-2">
                <option value="">Sort by Price</option>
                <option value="low"  <%= "low".equals(sort) ? "selected" : "" %>>
                    Low to High
                </option>
                <option value="high" <%= "high".equals(sort) ? "selected" : "" %>>
                    High to Low
                </option>
            </select>

            <button class="btn btn-primary">
                <i class="bi bi-search"></i>
            </button>
        </form>
    </div>

    <!-- Desktop Table View -->
    <div class="table-responsive shadow-sm d-none d-md-block">
        <table class="table table-bordered table-striped text-center align-middle">
            <thead class="table-dark">
                <tr>
                    <th>Image</th>
                    <th>Name</th>
                    <th>Brand</th>
                    <th>Price (₹)</th>
                    <th>Qty</th>
                    <th>Action</th>
                </tr>
            </thead>
            <tbody>
            <%
                if (!products.isEmpty()) {
                    for (Product p : products) {
            %>
                <tr>
                    <td>
                        <img src="<%= p.getImage1() %>"
                             width="60" height="60" class="rounded">
                    </td>
                    <td><%= p.getName() %></td>
                    <td><%= p.getBrand() %></td>
                    <td class="fw-bold text-primary">₹ <%= p.getPrice() %></td>
                    <td><%= p.getQuantity() %></td>
                    <td>
                        <a href="edit-product.jsp?id=<%= p.getId() %>"
                           class="btn btn-warning btn-sm me-1">
                            <i class="bi bi-pencil"></i>
                        </a>
                        <a href="../DeleteProduct?id=<%= p.getId() %>"
                           class="btn btn-danger btn-sm"
                           onclick="return confirm('Delete product?')">
                            <i class="bi bi-trash"></i>
                        </a>
                    </td>
                </tr>
            <%
                    }
                } else {
            %>
                <tr>
                    <td colspan="6" class="text-muted">No Products Found</td>
                </tr>
            <%
                }
            %>
            </tbody>
        </table>
    </div>

    <!-- Mobile Card View -->
    <div class="d-md-none">
        <%
            for (Product p : products) {
        %>
        <div class="card mb-3 shadow-sm">
            <div class="row g-0 align-items-center">
                <div class="col-4 text-center">
                    <img src="<%= p.getImage1() %>"
                         class="img-fluid rounded">
                </div>
                <div class="col-8">
                    <div class="card-body p-2">
                        <h6 class="fw-bold mb-1"><%= p.getName() %></h6>
                        <div class="small text-muted"><%= p.getBrand() %></div>
                        <div class="text-primary fw-bold">₹ <%= p.getPrice() %></div>

                        <div class="d-flex mt-2">
                            <a href="edit-product.jsp?id=<%= p.getId() %>"
                               class="btn btn-warning btn-sm me-1">
                                <i class="bi bi-pencil"></i>
                            </a>
                            <a href="../DeleteProduct?id=<%= p.getId() %>"
                               onclick="return confirm('Delete product?')"
                               class="btn btn-danger btn-sm">
                                <i class="bi bi-trash"></i>
                            </a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <%
            }
            if (products.isEmpty()) {
        %>
        <p class="text-center text-muted">No Products Found</p>
        <%
            }
        %>
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
    .search-box {
        width: 100%;
    }
}
</style>

<%@ include file="include/footer.jsp" %>
