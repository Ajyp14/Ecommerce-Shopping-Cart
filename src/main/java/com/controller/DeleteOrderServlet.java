package com.controller;

import com.dao.OrderDAO;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/DeleteOrder")
public class DeleteOrderServlet extends HttpServlet {

    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        int orderId = Integer.parseInt(req.getParameter("id"));

        OrderDAO dao = new OrderDAO();
        boolean deleted = dao.deleteOrder(orderId);

        if (deleted)
            resp.sendRedirect("admin/manage-orders.jsp?msg=Order Removed");
        else
            resp.sendRedirect("admin/manage-orders.jsp?msg=Delete Failed");
    }
}