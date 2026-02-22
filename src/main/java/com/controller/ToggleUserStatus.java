
package com.controller;

import com.dao.UserDAO;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;

@WebServlet("/ToggleUserStatus")
public class ToggleUserStatus extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        
        int id = Integer.parseInt(req.getParameter("id"));
        String status = req.getParameter("status");

        System.out.println("id::::"+id);
        System.out.println("Status : : : "+status);
        
        UserDAO dao = new UserDAO();
        dao.updateStatus(id, status);

        resp.sendRedirect("admin/manage-users.jsp?msg=updated");
    }
}
