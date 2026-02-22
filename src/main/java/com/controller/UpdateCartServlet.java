package com.controller;

import java.io.IOException;
import com.dao.CartDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/update-cart")

	public class UpdateCartServlet extends HttpServlet {

	    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
	            throws ServletException, IOException {

	        int cartId = Integer.parseInt(req.getParameter("cartId"));
	        int qty = Integer.parseInt(req.getParameter("qty"));

	        if (qty <= 0) {
	            resp.sendRedirect(req.getContextPath() + "/user/cart.jsp?msg=Invalid+quantity");
	            return;
	        }

	        CartDAO dao = new CartDAO();

	        //  STEP 1: get product id from cart
	        int productId = dao.getProductIdByCartId(cartId);

	        //  STEP 2: get stock from products table
	        int stockQty = dao.getProductStock(productId);

	        // STEP 3 : compare
	        if (qty > stockQty) {
	            resp.sendRedirect(
	                req.getContextPath() + "/user/cart.jsp?msg=Out+of+Stock+(Only+" + stockQty + "+available)"
	            );
	            return;
	        }

	        // STEP 4: update cart only if valid
	        boolean ok = dao.updateQuantity(cartId, qty);

	        if (ok) {
	            resp.sendRedirect(req.getContextPath() + "/user/cart.jsp?msg=Updated");
	        } else {
	            resp.sendRedirect(req.getContextPath() + "/user/cart.jsp?msg=Update+failed");
	        }
	    }
	}