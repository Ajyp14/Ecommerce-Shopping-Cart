
package com.controller;

import java.io.File;
import java.io.IOException;
import com.dao.ProductDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.model.Product;

@WebServlet("/DeleteProduct")
public class DeleteProductServlet extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        int id = Integer.parseInt(req.getParameter("id"));

        ProductDAO dao = new ProductDAO();
        Product p = dao.getProductById(id);

        if (p != null) {
            // Delete images from folder
            String uploadPath = req.getServletContext().getRealPath("") + "product-images" + File.separator;

            deleteFile(uploadPath + p.getImage1());
            deleteFile(uploadPath + p.getImage2());
            deleteFile(uploadPath + p.getImage3());

            boolean deleted = dao.deleteProduct(id);

            if (deleted) {
                resp.sendRedirect("admin/manage-products.jsp?msg=deleted");
            } else {
                resp.sendRedirect("admin/manage-products.jsp?msg=error");
            }
        }
    }

    private void deleteFile(String filePath) {
        if (filePath != null) {
            File file = new File(filePath);
            if (file.exists()) file.delete();
        }
    }
}
