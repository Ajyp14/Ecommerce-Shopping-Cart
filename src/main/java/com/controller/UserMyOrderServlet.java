package com.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

import com.dao.OrderDAO;
import com.model.Order;

@WebServlet("/user/myorders")
public class UserMyOrderServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        // Login check
        if (session == null || session.getAttribute("userId") == null) {
            response.sendRedirect(request.getContextPath() + "/user/login.jsp");
            return;
        }

        int userId = (int) session.getAttribute("userId");

        OrderDAO dao = new OrderDAO();
        List<Order> orders = dao.getOrdersByUser(userId);

        request.setAttribute("orders", orders);

        //  FORWARD TO myorder.jsp
        request.getRequestDispatcher("/user/myorder.jsp")
               .forward(request, response);
    }
}