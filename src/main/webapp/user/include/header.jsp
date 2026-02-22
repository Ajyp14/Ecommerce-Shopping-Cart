<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%
// ONLY for UI (no redirect here)
Integer userId = (Integer) session.getAttribute("userId");
String userName = (String) session.getAttribute("userName");
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Online Shopping</title>

<!-- Bootstrap CSS -->
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css"
	onerror="this.href='${pageContext.request.contextPath}/assets/bootstrap/css/bootstrap.min.css'">

<!-- Bootstrap Icons -->
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css">


<!-- Custom CSS -->
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/assets/css/style.css">

<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
</head>
<body class="bg-light">
	<nav
		class="navbar navbar-expand-lg navbar-dark bg-primary fixed-top shadow-sm">
		<div class="container">

			<a class="navbar-brand fw-bold"
				href="${pageContext.request.contextPath}/user/index.jsp"> <i
				class="bi bi-shop"></i> OnlineShop
			</a>

			<button class="navbar-toggler" type="button"
				data-bs-toggle="collapse" data-bs-target="#navMenu">
				<span class="navbar-toggler-icon"></span>
			</button>
			
			<div class="collapse navbar-collapse" id="navMenu">
				<ul class="navbar-nav ms-auto">

					<li class="nav-item"><a class="nav-link active"
						href="${pageContext.request.contextPath}/user/index.jsp"> Home
					</a></li>

					<%
					if (userId == null) {
					%>

					<!-- NOT LOGGED IN -->
					<li class="nav-item"><a class="nav-link"
						href="${pageContext.request.contextPath}/user/login.jsp">
							Login </a></li>

					<li class="nav-item"><a class="nav-link"
						href="${pageContext.request.contextPath}/admin/login.jsp">
							Admin Login </a></li>

					<li class="nav-item"><a class="btn btn-outline-light ms-2"
						href="${pageContext.request.contextPath}/user/register.jsp">
							Register </a></li>

					<%
					} else {
					%>
					<!-- LOGGED IN -->
            <li class="nav-item">
              <a class="nav-link"
                 href="${pageContext.request.contextPath}/user/cart.jsp">
                <i class="bi bi-cart"></i> Cart
              </a>
            </li>

            <li class="nav-item dropdown">
              <a class="nav-link dropdown-toggle"
                 data-bs-toggle="dropdown">
                <i class="bi bi-person-circle"></i> <%= userName %>
              </a>

              <ul class="dropdown-menu dropdown-menu-end">
                <li>
                  <a class="dropdown-item"
                     href="${pageContext.request.contextPath}/user/myorders">
                     My Orders
                  </a>
                </li>
                <li>
                  <a class="dropdown-item text-danger"
                     href="${pageContext.request.contextPath}/userlogout">
                     Logout
                  </a>
                </li>
              </ul>
            </li>
					<%
					}
					%>
				</ul>

			</div>



		</div>

	</nav>

<div style="margin-top:80px;"></div>