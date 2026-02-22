package com.controller;

import java.io.File;
import java.io.IOException;

import com.dao.ProductDAO;
import com.util.ImageConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import com.model.Product;

@WebServlet("/UpdateProductServlet")
@MultipartConfig
public class UpdateProductServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // ---------- GET PRODUCT ID ----------
        int id = Integer.parseInt(req.getParameter("id"));

        ProductDAO dao = new ProductDAO();
        Product oldProduct = dao.getProductById(id);

        if (oldProduct == null) {
            resp.sendRedirect("admin/manage-products.jsp?msg=invalid");
            return;
        }

        // ---------- FORM DATA ----------
        String name = req.getParameter("name");
        String brand = req.getParameter("brand");
        String category = req.getParameter("category");
        String subcategory = req.getParameter("subcategory");
        double price = Double.parseDouble(req.getParameter("price"));
        double discountPrice = Double.parseDouble(req.getParameter("discount_price"));
        int quantity = Integer.parseInt(req.getParameter("quantity"));
        String description = req.getParameter("description");
        String specifications = req.getParameter("specifications");

        // ---------- IMAGE UPLOAD PATH (EXTERNAL STORAGE) ----------
        if (!ImageConfig.initializeImageDirectory()) {
            resp.sendRedirect("admin/manage-products.jsp?msg=storage_error");
            return;
        }
        
        String uploadPath = ImageConfig.getImageStoragePath();

        // ---------- HANDLE IMAGES ----------
        String image1 = updateImage(req.getPart("image1"),
                oldProduct.getImage1(), uploadPath);

        String image2 = updateImage(req.getPart("image2"),
                oldProduct.getImage2(), uploadPath);

        String image3 = updateImage(req.getPart("image3"),
                oldProduct.getImage3(), uploadPath);

        // ---------- UPDATED PRODUCT ----------
        Product product = new Product();
        product.setId(id);
        product.setName(name);
        product.setBrand(brand);
        product.setCategory(category);
        product.setSubcategory(subcategory);
        product.setPrice(price);
        product.setDiscountPrice(discountPrice);
        product.setQuantity(quantity);
        product.setDescription(description);
        product.setSpecifications(specifications);
        product.setImage1(image1);
        product.setImage2(image2);
        product.setImage3(image3);

        // ---------- UPDATE DB ----------
        boolean success = dao.updateProduct(product);

        if (success) {
            resp.sendRedirect("admin/manage-products.jsp?msg=updated");
        } else {
            resp.sendRedirect("admin/edit-product.jsp?id=" + id + "&msg=error");
        }
    }

    // ---------- IMAGE UPDATE HELPER ----------
    private String updateImage(Part newImagePart,
                               String oldImageName,
                               String uploadPath) throws IOException {

        if (newImagePart != null && newImagePart.getSize() > 0) {

            // delete old image
            if (oldImageName != null) {
                File oldFile = new File(uploadPath + File.separator + oldImageName);
                if (oldFile.exists()) oldFile.delete();
            }

            // save new image
            String newFileName =
                    System.currentTimeMillis() + "_"
                  + newImagePart.getSubmittedFileName();

            newImagePart.write(uploadPath + File.separator + newFileName);
            return newFileName;
        }

        // no new image uploaded → keep old image
        return oldImageName;
    }
}