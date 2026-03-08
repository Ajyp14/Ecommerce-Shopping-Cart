package com.controller;

import java.io.IOException;
import java.util.Map;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

import com.dao.ProductDAO;
import com.model.Product;
import config.CloudinaryConfig;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/UpdateProductServlet")
@MultipartConfig(maxFileSize = 1024 * 1024 * 5)
public class UpdateProductServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        int id = Integer.parseInt(req.getParameter("id"));

        ProductDAO dao = new ProductDAO();
        Product oldProduct = dao.getProductById(id);

        if (oldProduct == null) {
            resp.sendRedirect("admin/manage-products.jsp?msg=invalid");
            return;
        }

        // -------- FORM DATA --------
        String name = req.getParameter("name");
        String brand = req.getParameter("brand");
        String category = req.getParameter("category");
        String subcategory = req.getParameter("subcategory");

        double price = Double.parseDouble(req.getParameter("price"));
        double discountPrice = Double.parseDouble(req.getParameter("discount_price"));
        int quantity = Integer.parseInt(req.getParameter("quantity"));

        String description = req.getParameter("description");
        String specifications = req.getParameter("specifications");

        // -------- IMAGE HANDLING --------
        String image1 = uploadImage(req.getPart("image1"), oldProduct.getImage1());
        String image2 = uploadImage(req.getPart("image2"), oldProduct.getImage2());
        String image3 = uploadImage(req.getPart("image3"), oldProduct.getImage3());

        // -------- PRODUCT OBJECT --------
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

        boolean success = dao.updateProduct(product);

        if (success) {
            resp.sendRedirect("admin/manage-products.jsp?msg=updated");
        } else {
            resp.sendRedirect("admin/edit-product.jsp?id=" + id + "&msg=error");
        }
    }

    // -------- CLOUDINARY IMAGE UPLOAD --------
    private String uploadImage(Part part, String oldImage) {

        try {

            if (part != null && part.getSize() > 0) {

                Cloudinary cloudinary = CloudinaryConfig.getCloudinary();

                // convert uploaded file to byte[]
                byte[] fileBytes = part.getInputStream().readAllBytes();

                Map uploadResult = cloudinary.uploader().upload(
                        fileBytes,
                        ObjectUtils.emptyMap()
                );

                return (String) uploadResult.get("secure_url");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        // if no new image uploaded → keep old image
        return oldImage;
    }
}