
package com.controller;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.dao.ProductDAO;
import com.dao.UserDAO;
import com.dao.OrderDAO;

@WebServlet("/AdminDashboard")
public class AdminDashboardServlet extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        ProductDAO pdao = new ProductDAO();
        UserDAO udao = new UserDAO();
        OrderDAO odao = new OrderDAO();

        // Fetch counts
        req.setAttribute("productCount", pdao.getProductCount());
        req.setAttribute("userCount", udao.getUserCount());
        req.setAttribute("orderCount", odao.getOrderCount());
        req.setAttribute("pendingCount", odao.getPendingCount());
        req.setAttribute("deliveredCount", odao.getDeliveredCount());
        req.setAttribute("cancelCount", odao.getCancelledCount());

        // Forward to Dashboard JSP
        req.getRequestDispatcher("admin/dashboard.jsp").forward(req, resp);
    }
}
