package com.controller;

import com.dao.CartDAO;
import com.dao.ProductDAO;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/add-to-cart")
public class AddToCartServlet extends HttpServlet {

    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession(false);

        // 1. LOGIN CHECK (MOST IMPORTANT)
        if (session == null || session.getAttribute("userId") == null) {
            resp.sendRedirect(
                req.getContextPath() + "/user/login.jsp?msg=Please+login"
            );
            return;
        }

        int userId = (int) session.getAttribute("userId");
        int productId = Integer.parseInt(req.getParameter("id"));
        int qty = Integer.parseInt(req.getParameter("qty"));

        ProductDAO productDAO = new ProductDAO();
        CartDAO cartDAO = new CartDAO();

        // 2. GET TOTAL STOCK
        int stock = productDAO.getProductById(productId).getQuantity();

        //  3. GET ALREADY IN CART QTY
        int cartQty = cartDAO.getCartQty(userId, productId);

        //  4. FINAL REAL-WORLD CHECK
        if (cartQty + qty > stock) {
            resp.sendRedirect(
                req.getContextPath() +
                "/user/product-detail.jsp?id=" + productId +
                "&msg=Out+of+Stock"
            );
            return;
        }

        // 5. SAFE TO ADD
        cartDAO.addToCart(userId, productId, qty);

        resp.sendRedirect(
            req.getContextPath() + "/user/cart.jsp?msg=Added"
        );
    }
}
