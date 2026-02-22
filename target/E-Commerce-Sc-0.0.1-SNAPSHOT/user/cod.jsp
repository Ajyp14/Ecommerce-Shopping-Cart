
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
response.sendRedirect("../place-order?fullname=" + request.getParameter("fullname")
+ "&phone=" + request.getParameter("phone")
+ "&address=" + request.getParameter("address")
+ "&payment=COD"
);
%>
