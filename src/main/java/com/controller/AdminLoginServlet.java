
package com.controller;

import java.io.IOException;
import java.sql.Connection;

import com.model.Admin;
import com.util.DBConnection;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/AdminLoginServlet")
public class AdminLoginServlet extends HttpServlet {

    private static final String ADMIN_USERNAME = "admin";
    private static final String ADMIN_PASSWORD = "admin123";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String username = req.getParameter("username");
        String password = req.getParameter("password");
        
        Connection con = DBConnection.getConnection();
        System.out.println("Connection object: " + con);

        if (ADMIN_USERNAME.equals(username) && ADMIN_PASSWORD.equals(password)) 
        {

            HttpSession session = req.getSession();
            System.out.println(" admin session start");
            Admin admin = new Admin();   // optional model use
            admin.setUsername(username);

            session.setAttribute("adminObj", admin);

            resp.sendRedirect(req.getContextPath() + "/admin/dashboard.jsp");

        } 
        else 
        {
            resp.sendRedirect(req.getContextPath()
                    + "/admin/login.jsp?msg=Invalid Admin Credentials");
        }
    }
}
