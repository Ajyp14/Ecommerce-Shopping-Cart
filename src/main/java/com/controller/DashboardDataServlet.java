package com.controller;

import java.io.IOException;

import java.util.HashMap;
import java.util.Map;
import com.google.gson.Gson;
import com.dao.OrderDAO;
import com.dao.ProductDAO;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/DashboardData")
public class DashboardDataServlet extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        OrderDAO orderDao = new OrderDAO();
        ProductDAO productDao = new ProductDAO();

        Map<String, Object> data = new HashMap<>();
        data.put("ordersByMonth", orderDao.getOrdersByMonth());
        data.put("categoryData", productDao.getCategoryWiseCount());

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write(new Gson().toJson(data));
    }
}