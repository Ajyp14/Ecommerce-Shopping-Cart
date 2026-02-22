

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin Panel</title>

    <!-- Local Bootstrap CSS -->
<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/bootstrap.min.css">

<!-- If Local Fails then load CDN -->
<link rel="stylesheet"
      href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css"
      onerror="this.href='${pageContext.request.contextPath}/assets/css/bootstrap.min.css'">

<!-- Admin Panel CSS -->
<link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/admin.css">

<!-- Bootstrap Icons -->
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css">

    <style>
        body { overflow-x: hidden; }

        .sidebar { width: 250px; transition: 0.4s; }

        @media(max-width: 992px) {
            .sidebar {
                position: fixed;
                top: 0;
                left: -250px;
                height: 100vh;
                z-index: 999;
            }
            .sidebar.show { left: 0; }
        }
    </style>
    
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
    
</head>

<body class="bg-light">

<div class="dashboard d-flex">
