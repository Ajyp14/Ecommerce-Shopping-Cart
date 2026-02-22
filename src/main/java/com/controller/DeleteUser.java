
package com.controller;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import com.dao.UserDAO;

@WebServlet("/DeleteUser")
public class DeleteUser extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        boolean deleted = new UserDAO().deleteUser(id);
        resp.sendRedirect("admin/manage-users.jsp?msg=" + (deleted ? "success" : "error"));
    }
}
