
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.dao.ProductDAO, com.model.Product" %>

<%
    int id = Integer.parseInt(request.getParameter("id"));
    ProductDAO dao = new ProductDAO();
    Product p = dao.getProductById(id);
%>

<%@ include file="include/header.jsp" %>
<%@ include file="include/sidebar.jsp" %>

<div class="content-area">
    <h3 class="page-title mb-4">Edit Product</h3>

    <form action="${pageContext.request.contextPath}/UpdateProductServlet"
          method="post"
          enctype="multipart/form-data">

        <input type="hidden" name="id" value="<%= p.getId() %>">

        <div class="card shadow p-4">

            <!-- Product Name -->
            <div class="mb-3">
                <label class="form-label fw-bold">Product Name</label>
                <input type="text" class="form-control"
                       name="name" value="<%= p.getName() %>" required>
            </div>

            <!-- Brand -->
            <div class="mb-3">
                <label class="form-label fw-bold">Brand</label>
                <input type="text" class="form-control"
                       name="brand" value="<%= p.getBrand() %>" required>
            </div>

            <!-- Category & Subcategory -->
            <div class="row mb-3">
                <div class="col-md-6">
                    <label class="form-label fw-bold">Category</label>
                    <input type="text" class="form-control"
                           name="category" value="<%= p.getCategory() %>" required>
                </div>

                <div class="col-md-6">
                    <label class="form-label fw-bold">Subcategory</label>
                    <input type="text" class="form-control"
                           name="subcategory" value="<%= p.getSubcategory() %>">
                </div>
            </div>

            <!-- Price, Discount, Quantity -->
            <div class="row mb-3">
                <div class="col-md-4">
                    <label class="form-label fw-bold">Price (₹)</label>
                    <input type="number" class="form-control"
                           name="price" value="<%= p.getPrice() %>" required>
                </div>

                <div class="col-md-4">
                    <label class="form-label fw-bold">Discount Price (₹)</label>
                    <input type="number" class="form-control"
                           name="discount_price" value="<%= p.getDiscountPrice() %>">
                </div>

                <div class="col-md-4">
                    <label class="form-label fw-bold">Quantity</label>
                    <input type="number" class="form-control"
                           name="quantity" value="<%= p.getQuantity() %>" required>
                </div>
            </div>

            <!-- Description -->
            <div class="mb-3">
                <label class="form-label fw-bold">Description</label>
                <textarea class="form-control"
                          name="description" rows="3"><%= p.getDescription() %></textarea>
            </div>

            <!-- Specifications -->
            <div class="mb-3">
                <label class="form-label fw-bold">Specifications</label>
                <textarea class="form-control"
                          name="specifications" rows="3"><%= p.getSpecifications() %></textarea>
            </div>

            <!-- Images -->
            <div class="row mb-4 text-center">
                <div class="col-md-4">
                    <label class="fw-bold">Main Image</label><br>
                    <img src="<%= p.getImage1() %>" width="80" class="mb-2"><br>
                    <input type="file" name="image1" class="form-control">
                </div>

                <div class="col-md-4">
                    <label class="fw-bold">Second Image</label><br>
                    <img src="<%= p.getImage2() %>" width="80" class="mb-2"><br>
                    <input type="file" name="image2" class="form-control">
                </div>

                <div class="col-md-4">
                    <label class="fw-bold">Third Image</label><br>
                    <img src="<%= p.getImage3() %>" width="80" class="mb-2"><br>
                    <input type="file" name="image3" class="form-control">
                </div>
            </div>

            <!-- Submit Button -->
            <button type="submit" class="btn btn-primary w-100">
                Update Product
            </button>

        </div>
    </form>
</div>

<%@ include file="include/footer.jsp" %>
