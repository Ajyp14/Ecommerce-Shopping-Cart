
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ include file="include/header.jsp" %>
<%@ include file="include/sidebar.jsp" %>

<h3 class="mb-4 fw-bold">Add New Product</h3>

<form action="${pageContext.request.contextPath}/AddProductServlet"
      method="post"
      enctype="multipart/form-data"
      class="card p-4 shadow-lg">

    <div class="row g-3">

        <div class="col-md-6">
            <label class="form-label">Product Name</label>
            <input type="text" name="name" class="form-control" required>
        </div>

        <div class="col-md-6">
            <label class="form-label">Brand</label>
            <input type="text" name="brand" class="form-control">
        </div>

        <div class="col-md-4">
            <label class="form-label">Category</label>
            <select name="category" class="form-select" required>
                <option disabled selected>-- Select Category --</option>
                <option>Mobiles</option>
                <option>Laptops</option>
                <option>Tablets</option>
                <option>Smart Watches</option>
                <option>Headphones & Earbuds</option>
                <option>Televisions</option>
                <option>Camera & Photography</option>
                <option>Home & Kitchen</option>
                <option>Electronics</option>
            </select>
        </div>

        <div class="col-md-4">
            <label class="form-label">Subcategory</label>
            <input type="text" name="subcategory" class="form-control">
        </div>

        <div class="col-md-4">
            <label class="form-label">Price (₹)</label>
            <input type="number" step="0.01" name="price" class="form-control" min="0" required>
        </div>

        <div class="col-md-4">
            <label class="form-label">Discount Price (₹)</label>
            <input type="number" step="0.01" name="discount_price" min="0" class="form-control" required>
        </div>

        <div class="col-md-4">
            <label class="form-label">Quantity</label>
            <input type="number" name="quantity" class="form-control" min="1" required>
        </div>

        <div class="col-12">
            <label class="form-label">Product Description</label>
            <textarea name="description" class="form-control" rows="3" required></textarea>
        </div>

        <div class="col-12">
            <label class="form-label">Specifications</label>
            <textarea name="specifications" class="form-control" rows="3"></textarea>
        </div>

        <div class="col-md-4">
            <label class="form-label">Main Image</label>
            <input type="file" name="image1" class="form-control" required>
        </div>

        <div class="col-md-4">
            <label class="form-label">Second Image</label>
            <input type="file" name="image2" class="form-control">
        </div>

        <div class="col-md-4">
            <label class="form-label">Third Image</label>
            <input type="file" name="image3" class="form-control">
        </div>

        <div class="col-12 text-end mt-3">
            <button type="submit" class="btn btn-primary px-4">
                Save Product
            </button>
        </div>

    </div>
</form>

<%@ include file="include/footer.jsp" %>
