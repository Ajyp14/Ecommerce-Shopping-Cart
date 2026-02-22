
package com.controller;

import java.io.IOException;
import com.dao.OrderDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/UpdateOrderStatus")   // IMPORTANT URL
public class UpdateOrderStatusServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        int id = Integer.parseInt(req.getParameter("id"));
        String status = req.getParameter("status");

        OrderDAO dao = new OrderDAO();
        boolean updated = dao.updateStatus(id, status);

        if (updated) {
            resp.sendRedirect("admin/manage-orders.jsp?msg=Status Updated");
        } else {
            resp.sendRedirect("admin/manage-orders.jsp?msg=Update Failed");
        }
    }
}
