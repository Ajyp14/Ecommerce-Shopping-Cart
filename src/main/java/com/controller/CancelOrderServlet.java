package com.controller;

import java.io.IOException;
import com.dao.OrderDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/CancelOrderServlet")
public class CancelOrderServlet extends HttpServlet {

    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        int orderId = Integer.parseInt(req.getParameter("orderId"));

        OrderDAO dao = new OrderDAO();
        dao.updateStatus(orderId, "cancelled");

        resp.sendRedirect(req.getContextPath() + "/user/myorders?msg=Order cancelled successfully");
    }
}