
package com.controller;

import java.io.IOException;
import com.dao.CartDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/remove-cart-item")
public class RemoveCartItemServlet extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        try {
            String cid = req.getParameter("cid");
            if (cid == null || cid.trim().isEmpty()) {
                resp.sendRedirect(req.getContextPath() + "/user/cart.jsp?msg=Invalid+input");
                return;
            }
            int cartId = Integer.parseInt(cid);
            CartDAO dao = new CartDAO();
            boolean ok = dao.removeItem(cartId);
            if (ok) {
                resp.sendRedirect(req.getContextPath() + "/user/cart.jsp?msg=item_removed");
            } else {
                resp.sendRedirect(req.getContextPath() + "/user/cart.jsp?msg=Remove+failed");
            }
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendRedirect(req.getContextPath() + "/user/cart.jsp?msg=Server+error");
        }
    }
}
