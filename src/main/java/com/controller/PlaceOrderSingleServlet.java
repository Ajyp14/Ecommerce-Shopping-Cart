
package com.controller;

import java.io.IOException;
import com.dao.OrderDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/place-order-single")
public class PlaceOrderSingleServlet extends HttpServlet {

    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession();
        Integer userId = (Integer) session.getAttribute("userId");

        if (userId == null) {
            resp.sendRedirect("user/login.jsp?msg=Login Required");
            return;
        }

        String fullname = req.getParameter("fullname");
        String phone = req.getParameter("phone");
        String address = req.getParameter("address");
        int productId = Integer.parseInt(req.getParameter("productId"));
        int qty = Integer.parseInt(req.getParameter("qty"));
        String payment = req.getParameter("payment"); // COD / CARD / UPI

        OrderDAO dao = new OrderDAO();
        boolean ok = dao.placeSingleOrder(userId, productId, qty,
                                          fullname, phone, address);

        if (ok)
            resp.sendRedirect("user/order-success.jsp?msg=Order Placed Successfully");
        else
            resp.sendRedirect("user/buy-now.jsp?msg=Failed");
    }
}
