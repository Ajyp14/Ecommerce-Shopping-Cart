
package com.controller;

import com.util.ImageConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.*;
import java.nio.file.Files;

@WebServlet("/product-images/*")
public class ImageServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        // Extract filename from URL path
        // Example: /product-images/123456_phone.jpg → "123456_phone.jpg"
        String imageName = req.getPathInfo().substring(1);
        
        // Get the image from external storage (NOT deployment folder!)
        String imagePath = ImageConfig.getImageFilePath(imageName);
        
        if (imagePath == null) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid image name");
            return;
        }
        
        File imageFile = new File(imagePath);

        // Check if file exists in external storage
        if (!imageFile.exists()) {
            System.err.println("❌ Image not found: " + imagePath);
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        // Set content type
        String mimeType = getServletContext().getMimeType(imageFile.getName());
        if (mimeType == null) {
            mimeType = "application/octet-stream";
        }
        resp.setContentType(mimeType);
        
        // Set cache headers for better performance
        resp.setHeader("Cache-Control", "public, max-age=31536000"); // 1 year
        
        // Send image to browser
        try (InputStream in = new FileInputStream(imageFile);
             OutputStream out = resp.getOutputStream()) {
            
            byte[] buffer = new byte[8192];
            int bytesRead;
            while ((bytesRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
        }
    }
}
