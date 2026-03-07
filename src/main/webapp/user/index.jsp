<%@page import="com.util.DBConnection"%>
<%@page import="java.sql.Connection"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="include/header.jsp" %>
<%@ page import="com.dao.ProductDAO, com.model.Product, java.util.*" %>

<%
Connection con = DBConnection.getConnection();
System.out.println("Connection object: " + con); 

    // Get selected category from request parameter
    String selectedCategory = request.getParameter("category");

    ProductDAO pdao = new ProductDAO();
    List<Product> productList;

    if (selectedCategory != null && !selectedCategory.isEmpty()) {
        // Fetch products by selected category
        productList = pdao.getProductsByCategory(selectedCategory);
    } else {
        // Fetch all products
        productList = pdao.getAllProducts();
    }

    // Fetch all categories for dropdown
    List<String> categories = pdao.getAllCategories();
%>


<div class="container">
 	
 <!-- Welcome Section -->
 	<div class="bg-white p-4 rounded shadow-sm mb-4 text-center">
        <h3 class="fw-bold">Welcome to Online Shopping 🛍</h3>
        <p class="text-muted">Best Deals • Fast Delivery • Trusted Shopping</p>
    </div>
 	
 	<!-- Category Filter Dropdown -->
    <form method="get" action="index.jsp" class="mb-4">
        <select name="category" class="form-select w-auto d-inline-block" onchange="this.form.submit()">
            <option value="">All Categories</option>
            <%
                for(String cat : categories) {
                    String selected = "";
                    if(selectedCategory != null && selectedCategory.equals(cat)) {
                    	
                        selected = "selected";
                        
                    }
            %>
                <option value="<%=cat%>" <%=selected%>><%=cat%></option>
            <%
                }
            %>
        </select>
    </form>
 	
 	  <!-- Product Listing -->
    <h4 class="fw-bold mb-3">Latest Products</h4>
    <div class="row g-4">
        <% for(Product p : productList) { %>
            <div class="col-md-3 col-sm-6 col-6">
                <div class="card shadow-sm h-100">

                    <!-- Product Image -->
                    <a href="product-detail.jsp?id=<%=p.getId()%>">
                        <img src="${pageContext.request.contextPath}/product-images/<%=p.getImage1()%>"
                             class="card-img-top p-2"
                             style="height:200px; object-fit:contain;">
                    </a>
	                <div class="card-body text-center">
                        <!-- Product Name & Price -->
                        <h6 class="fw-semibold text-truncate"><%=p.getName()%></h6>
                        <p class="fw-bold text-danger fs-5">₹ <%=p.getPrice()%></p>
		
                        <!-- Buy Now Button -->
                 <%-- /user --%>       <a href="product-detail.jsp?id=<%=p.getId()%>"
                           class="btn btn-primary w-100 btn-sm mb-2">
                            Buy Now
                        </a>
                        

                        <!-- Add to Cart Form -->
                        <form action="${pageContext.request.contextPath}/add-to-cart" method="post">
                            <input type="hidden" name="id" value="<%=p.getId()%>">
                            <input type="hidden" name="qty" value="1">
                            <button class="btn btn-success w-100 btn-sm">
                                <i class="bi bi-cart-plus"></i> Add to Cart
                            </button>
                        </form>

                    </div>
                </div>
            </div>
        <% } %>
    </div>
 	
  
</div>



<%@ include file="include/footer.jsp" %>