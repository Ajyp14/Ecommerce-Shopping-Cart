
package com.controller;

import java.io.IOException;
import com.dao.OrderDAO;
import com.dao.ProductDAO;
import com.dao.UserDAO;
import com.model.Product;
import com.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/place-order")
public class PlaceOrderServlet extends HttpServlet {

    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession();
        Integer userId = (Integer) session.getAttribute("userId");

        if (userId == null) {
            resp.sendRedirect("user/login.jsp?msg=Login Required");
            return;
        }
        
        System.out.println("user Id :::"+userId);

//        String fullname = req.getParameter("fullname");
//        String phone = req.getParameter("phone");
//        String address = req.getParameter("address");
        String payment = req.getParameter("payment");
        String totalStr = req.getParameter("total");
        
        int product_id= Integer.parseInt(req.getParameter("productId"));
        int quntity= Integer.parseInt(req.getParameter("qty"));
        
        double total = Double.parseDouble(totalStr);

        String upiId = req.getParameter("upiId");
        String cardNumber = req.getParameter("cardNumber");

        UserDAO udao= new UserDAO();
        User user=udao.getUserById(userId);
        
//        ProductDAO pdao = new ProductDAO();
//        Product product = pdao.getProductById(product_id);
        
        OrderDAO dao = new OrderDAO();

        boolean ok = dao.placeOrder(
                userId, user.getName(), user.getPhone(), user.getAddress(),
                payment, total, upiId, cardNumber, product_id,quntity
        );
        
        

        if (ok) {
            req.setAttribute("name", user.getName());
            req.setAttribute("payment", payment);
            req.setAttribute("total", totalStr);

            req.getRequestDispatcher("user/order-success.jsp")
               .forward(req, resp);

        } else {
            resp.sendRedirect("user/payment.jsp?msg=Order Failed");
        }
    

    }
}
