<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.dao.ProductDAO, com.model.Product" %>
<%@ include file="include/header.jsp" %>

<style>

.thumb {
    width: 150px;
    height: 150px;
    object-fit: cover;
    cursor: pointer;
    border: 2px solid transparent;
    border-radius: 6px;
}


.thumb:hover {
    border-color: #0d6efd;
    transform: scale(1.05);
}

.thumb.active {
    border-color: #0d6efd;
    box-shadow: 0 0 5px rgba(13,110,253,0.5);
}
</style>
<%
    /* ---------- SAFE PARAMETER HANDLING ---------- */
    String idParam = request.getParameter("id");
    if (idParam == null) {
        response.sendRedirect("index.jsp");
        return;
    }

    int id;
    try {
        id = Integer.parseInt(idParam);
    } catch (Exception e) {
        response.sendRedirect("index.jsp");
        return;
    }

    ProductDAO dao = new ProductDAO();
    Product p = dao.getProductById(id);

    if (p == null) {
        response.sendRedirect("index.jsp");
        return;
    }

    int stock = p.getQuantity();
    boolean loggedIn = session.getAttribute("userId") != null;

    String msg = request.getParameter("msg");
    boolean outOfStockMsg = "Out of Stock".equals(msg) || stock == 0;
%>

<div class="container py-4">

<!-- BACK BUTTON -->
    <button class="btn btn-outline-secondary mb-3" onclick="history.back()">
        ← Back
    </button>

 <!-- BREADCRUMB -->
    <nav class="breadcrumb mb-4">
        <a class="breadcrumb-item" href="index.jsp">Home</a>
        <span class="breadcrumb-item"><%= p.getCategory() %></span>
        <span class="breadcrumb-item active"><%= p.getName() %></span>
    </nav>
   
   <div class="row g-4">
   
   	<!-- ================= IMAGE SECTION ================= -->
        <div class="col-md-5 text-center">
   			
   			<!-- MAIN IMAGE -->
            <img id="mainImg"
                 src="<%=p.getImage1()%>"
                 class="img-fluid rounded shadow-sm mb-2"
                 style="height:400px; width:100%; object-fit:contain;">
   			
   			 <!-- THUMBNAILS -->
            <div class="d-flex justify-content-center gap-2 mt-2">

                <% if (p.getImage1() != null) { %>
                <img src="<%=p.getImage1()%>"
                     class="thumb active"
                     onclick="changeImage(this)">
                <% } %>

                <% if (p.getImage2() != null && !p.getImage2().isEmpty()) { %>
                <img src="<%=p.getImage2()%>"
                     class="thumb"
                     onclick="changeImage(this)">
                <% } %>

                <% if (p.getImage3() != null && !p.getImage3().isEmpty()) { %>
                <img src="<%=p.getImage3()%>"
                     class="thumb"
                     onclick="changeImage(this)">
                <% } %>

            </div>
		</div>
		
		<div class="col-md-7">
			
			<h3 class="fw-bold"><%= p.getName() %></h3>
            <p class="text-muted">Brand: <%= p.getBrand() %></p>
            <h4 class="text-danger fw-bold">₹ <%= p.getPrice() %></h4>
			
			<p><%= p.getDescription() %></p>
			
			 <% if (outOfStockMsg) { %>
                <div class="alert alert-danger fw-bold">
                    ❌ Out of Stock
                </div>
            <% } %>
			
			 <!-- QUANTITY -->
            <div class="mb-3">
                <label class="fw-bold">Quantity</label>
                <input type="number" id="qty" value="1" min="1"
                       class="form-control w-25"
                       oninput="checkStock()">

                <div id="stockMsg"
                     class="text-danger fw-bold mt-1 d-none">
                    Out of Stock
                </div>
            </div>
			
			 <!-- ADD TO CART -->
            <% if (loggedIn) { %>
            <form action="<%=request.getContextPath()%>/add-to-cart"
                  method="post" onsubmit="return validateSubmit()">

                <input type="hidden" name="id" value="<%= p.getId() %>">
                <input type="hidden" name="qty" id="hiddenQty">

	 		 
	 		 
                <button type="submit"
                        id="addBtn"
                        class="btn btn-success w-100 mb-2"
                        onclick="setQty()"
                        <%= outOfStockMsg ? "disabled" : "" %>>
                    🛒 Add to Cart
                </button>
            </form>
            <% } else { %>
                <a href="login.jsp?msg=Please+Login"
                   class="btn btn-success w-100 mb-2">
                   🛒 Add to Cart
                </a>
            <% } %>
            <button class="btn btn-primary w-100"
                    id="buyBtn"
                    onclick="buyNow(<%= p.getId() %>)"
                    <%= outOfStockMsg ? "disabled" : "" %>>
                Buy Now
            </button>
            
       </div>
    </div>

    <!-- SPECIFICATIONS -->
    <div class="mt-4">
        <h5 class="fw-bold">Specifications</h5>
        <p><%= p.getSpecifications() %></p>
    </div>

</div>

<!-- ================= JAVASCRIPT ================= -->
<script>
const MAX_STOCK = <%= stock %>;

function checkStock() {
    const qty = +document.getElementById("qty").value;
    toggleButtons(qty > MAX_STOCK);
}

function toggleButtons(disable) {
    document.getElementById("stockMsg")
        .classList.toggle("d-none", !disable);

    if(document.getElementById("addBtn"))
        document.getElementById("addBtn").disabled = disable;

    document.getElementById("buyBtn").disabled = disable;
}

function setQty() {
    document.getElementById("hiddenQty").value =
        document.getElementById("qty").value;
}

function validateSubmit() {
    return document.getElementById("qty").value <= MAX_STOCK;
}

function buyNow(id) {
    <% if (!loggedIn) { %>
        window.location.href = "login.jsp?msg=Please+Login";
        return;
    <% } %>
    const qty = document.getElementById("qty").value;
    if (qty > MAX_STOCK) return;
    window.location.href = "buy-now.jsp?id=" + id + "&qty=" + qty;
}

/* ---------- IMAGE FLIP ---------- */
function changeImage(thumb) {

    document.getElementById("mainImg").src = thumb.src;
    document.querySelectorAll(".thumb").forEach(img =>
        img.classList.remove("active")
    );
    thumb.classList.add("active");
    
}
</script>

<%@ include file="include/footer.jsp" %>