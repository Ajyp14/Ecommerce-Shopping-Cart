package com.controller;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.net.URLEncoder;

import com.dao.OrderDAO;
import com.util.DBConnection;

@WebServlet("/CardPaymentServlet")
public class CardPaymentServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession();
        
        Integer userId = (Integer) session.getAttribute("userId");

        if (userId == null) {
            resp.sendRedirect(req.getContextPath() + "/user/login.jsp?msg=Login Required");
            return;
        }

        // 1️ Get User Details
        String fullname = req.getParameter("fullname");
        String phone = req.getParameter("phone");
        String address = req.getParameter("address");
        String totalStr = req.getParameter("total");
        
        if (fullname != null) session.setAttribute("fullname", fullname);
        if (phone != null) session.setAttribute("phone", phone);
        if (address != null) session.setAttribute("address", address);
        
        if (totalStr == null || totalStr.isEmpty()) totalStr = "0";
        double total = Double.parseDouble(totalStr);

        // 2️ Get Card Details & trim spaces
        String card = req.getParameter("cardNumber").trim();
        String holder = req.getParameter("holderName").trim();
        String month = req.getParameter("expMonth").trim();
        String year = req.getParameter("expYear").trim();
        String cvv = req.getParameter("cvv").trim();

        DBConnection db = new DBConnection();

        // 3️ Validate Card
        System.out.println("CARD = [" + card + "]");
        System.out.println("HOLDER = [" + holder + "]");
        System.out.println("MONTH = [" + month + "]");
        System.out.println("YEAR = [" + year + "]");
        System.out.println("CVV = [" + cvv + "]");

        boolean validCard = db.validateCard(card, holder, month, year, cvv);
        System.out.println("Card validation result: " + validCard); // debug

        if (!validCard) {
            // Redirect with proper URL encoding
            String redirectUrl = req.getContextPath() + "/user/card-payment.jsp?msg="
                    + URLEncoder.encode("Invalid Card Details", "UTF-8")
                    + "&fullname=" + URLEncoder.encode(fullname, "UTF-8")
                    + "&phone=" + URLEncoder.encode(phone, "UTF-8")
                    + "&address=" + URLEncoder.encode(address, "UTF-8")
                    + "&total=" + URLEncoder.encode(totalStr, "UTF-8");

            resp.sendRedirect(redirectUrl);
            return;
        }

        // 4️ Check Balance
        int balance = db.getCardBalance(card);
        System.out.println("Card balance: " + balance); // debug

        if (balance < total) {
            String redirectUrl = req.getContextPath() + "/user/card-payment.jsp?msg="
                    + URLEncoder.encode("Insufficient Balance", "UTF-8")
                    + "&fullname=" + URLEncoder.encode(fullname, "UTF-8")
                    + "&phone=" + URLEncoder.encode(phone, "UTF-8")
                    + "&address=" + URLEncoder.encode(address, "UTF-8")
                    + "&total=" + URLEncoder.encode(totalStr, "UTF-8");

            resp.sendRedirect(redirectUrl);
            return;
        }

        // 5️ Deduct Balance
        db.updateCardBalance(card, balance - (int) total);

        // 6️ Place Order
        OrderDAO orderDao = new OrderDAO();
        boolean ok = orderDao.placeOrder(
                userId, fullname, phone, address,
                "CARD", total, null, card
        );

        if (!ok) {
            resp.sendRedirect(req.getContextPath() + "/user/payment.jsp?msg=" + URLEncoder.encode("Order Failed", "UTF-8"));
            return;
        }

        // 7️ Success Page
        req.setAttribute("name", fullname);
        req.setAttribute("payment", "CARD");
        req.setAttribute("total", totalStr);
        req.getRequestDispatcher("/user/order-success.jsp").forward(req, resp);

        System.out.println("Card payment successful for user: " + fullname);
    }
}