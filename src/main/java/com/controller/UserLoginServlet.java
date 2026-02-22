
package com.controller;

import java.io.IOException;
import com.dao.UserDAO;
import com.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/login")
public class UserLoginServlet extends HttpServlet {

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {

        String email = req.getParameter("email");
        String password = req.getParameter("password");

        UserDAO dao = new UserDAO();
        User u = dao.login(email, password);

        if(u != null) {
            HttpSession session = req.getSession();
            session.setAttribute("userId", u.getId());
            session.setAttribute("userName", u.getName());

            resp.sendRedirect("user/index.jsp");
        } else {
            resp.sendRedirect("user/login.jsp?msg=Invalid Email or Password");
        }
    }
}
