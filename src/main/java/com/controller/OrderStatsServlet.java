
package com.controller;

import java.io.IOException;
import com.dao.OrderDAO;
import com.dao.ProductDAO;
import com.dao.UserDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/OrderStatsServlet")
public class OrderStatsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        OrderDAO orderDao = new OrderDAO();
        ProductDAO productDao = new ProductDAO();
        UserDAO userDao = new UserDAO();

        int pending = orderDao.getPendingOrders();
        int delivered = orderDao.getDeliveredOrders();
        int cancelled = orderDao.getCancelledOrders();
        int totalOrders = orderDao.getOrderCount();
        int totalProducts = productDao.getProductCount();
        int totalUsers = userDao.getUserCount();

        String json = String.format(
            "{\"pending\": %d, \"delivered\": %d, \"cancelled\": %d, \"totalOrders\": %d, \"products\": %d, \"users\": %d}",
            pending, delivered, cancelled, totalOrders, totalProducts, totalUsers
        );

        response.getWriter().write(json);
    }
}
