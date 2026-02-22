package com.controller;

import com.dao.UserDAO;
import com.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {

    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String name = req.getParameter("name");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String phone = req.getParameter("phone");
        String address = req.getParameter("address");

        // Create User object
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);
        user.setPhone(phone);
        user.setAddress(address);
        user.setRole("User");      // default role
        user.setStatus("Active");  // default status

        UserDAO dao = new UserDAO();

        boolean ok = dao.addUser(user); // use addUser() from UserDAO

        if (ok) {
            resp.sendRedirect("user/login.jsp?msg=Registration Successful! Please Login.");
        } else {
            resp.sendRedirect("user/register.jsp?msg=Email Already Exists or Registration Failed!");
        }
    }
}