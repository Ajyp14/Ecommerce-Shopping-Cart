
package com.controller;

import java.io.IOException;
import com.dao.UserDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.model.User;

@WebServlet("/UpdateUserServlet")
public class UpdateUserServlet extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException 
    {

        int id = Integer.parseInt(req.getParameter("id"));
        String name = req.getParameter("name");
        String email = req.getParameter("email");
        String phone = req.getParameter("phone");
        String role = req.getParameter("role");

        User u = new User(id, name, email, phone, role);

        UserDAO dao = new UserDAO();
        boolean result = dao.updateUser(u);

        if(result) {
            resp.sendRedirect("admin/manage-users.jsp?msg=updated");
        } else {
            resp.sendRedirect("admin/edit-user.jsp?id=" + id + "&msg=error");
        }
    }
}
