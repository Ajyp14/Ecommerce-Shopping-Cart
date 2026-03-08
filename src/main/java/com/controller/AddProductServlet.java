package com.controller;

import java.io.File;
import java.io.IOException;

import com.dao.ProductDAO;
import com.util.ImageConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import com.model.Product;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import config.CloudinaryConfig;
import java.util.Map;

@WebServlet("/AddProductServlet")
@MultipartConfig(maxFileSize = 1024 * 1024 * 5)
public class AddProductServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // ---------- FORM DATA ----------
    	System.out.println("name : "+req.getParameter("name"));
        String name = req.getParameter("name");
        
        System.out.println("Brand "+req.getParameter("brand"));
        String brand = req.getParameter("brand");
        
        System.out.println("Category : "+req.getParameter("category"));
        String category = req.getParameter("category");
        
        System.out.println("subcategor y : "+req.getParameter("subcategory"));
        String subcategory = req.getParameter("subcategory");
        
        System.out.println("Price "+req.getParameter("price"));
        double price = Double.parseDouble(req.getParameter("price"));
        
        System.out.println("discount_price : "+req.getParameter("discount_price"));
       
        // double discountPrice = Double.parseDouble(req.getParameter("discount_price"));
        
        String dp = req.getParameter("discount_price");
        double discountPrice = (dp == null || dp.isEmpty()) ? 0 : Double.parseDouble(dp);

        
        System.out.println("quantity : "+req.getParameter("quantity"));
        int quantity = Integer.parseInt(req.getParameter("quantity"));
        
        System.out.println("description : "+req.getParameter("description"));
        String description = req.getParameter("description");
        
        System.out.println("specifications : "+req.getParameter("specifications"));
        String specifications = req.getParameter("specifications");

        // ---------- SKU ----------
        
        
        
        String sku = brand.substring(0, 2).toUpperCase() + "-"
                   + category.substring(0, 3).toUpperCase() + "-"
                   + (System.currentTimeMillis() % 10000);

        //updated sku
//        String safeBrand = (brand != null && brand.length() >= 2)
//                ? brand.substring(0, 2).toUpperCase()
//                : "XX";
//
//        String safeCategory = (category != null && category.length() >= 3)
//                ? category.substring(0, 3).toUpperCase()
//                : "GEN";
//
//        String sku = safeBrand + "-" + safeCategory + "-"
//                   + (System.currentTimeMillis() % 10000);

        
        
        // ---------- IMAGE UPLOAD PATH (EXTERNAL STORAGE) ----------
        // Initialize and get external storage path
//        if (!ImageConfig.initializeImageDirectory()) {
//            resp.sendRedirect("admin/add-product.jsp?msg=storage_error");
//            return;
//        }
//        
//        String uploadPath = ImageConfig.getImageStoragePath();
//        
//        System.out.println("✅ Images will be saved to: " + uploadPath);
//        System.out.println("✅ This is OUTSIDE deployment folder - images will persist!");


        // ---------- SAVE IMAGES ----------
        String image1 = saveImage(req.getPart("image1"));
        String image2 = saveImage(req.getPart("image2"));
        String image3 = saveImage(req.getPart("image3"));
        
        System.out.println(image1+" -----  "+image2+" ----- "+image3);

        // ---------- PRODUCT OBJECT ----------
        Product product = new Product();
        product.setName(name);
        product.setBrand(brand);
        product.setCategory(category);
        product.setSubcategory(subcategory);
        product.setPrice(price);
        product.setDiscountPrice(discountPrice);
        product.setQuantity(quantity);
        product.setDescription(description);
        product.setSpecifications(specifications);
        product.setSku(sku);
        product.setImage1(image1);
        product.setImage2(image2);
        product.setImage3(image3);
        product.setStatus("Active");

        // ---------- SAVE TO DB ----------
        System.out.println("Product Dao Starts");
        
        ProductDAO dao = new ProductDAO();
        boolean success = dao.addProduct(product);
        
        System.out.println("Success"+success);
        
        if (success) {
            resp.sendRedirect("admin/manage-products.jsp?msg=success");
        } else {
            resp.sendRedirect("admin/add-product.jsp?msg=error");
        }
    }

    // ---------- IMAGE SAVE HELPER ----------
    private String saveImage(Part filePart) throws IOException {

        if (filePart != null && filePart.getSize() > 0) {

            Cloudinary cloudinary = CloudinaryConfig.getCloudinary();

            Map uploadResult = cloudinary.uploader().upload(
                    filePart.getInputStream(),
                    ObjectUtils.emptyMap()
            );

            String imageUrl = (String) uploadResult.get("secure_url");

            System.out.println("Uploaded Image URL: " + imageUrl);

            return imageUrl;
        }

        return null;
    }
}