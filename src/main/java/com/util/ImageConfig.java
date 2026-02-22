package com.util;

import java.io.File;

/**
 * Centralized Image Storage Configuration
 * 
 * IMPORTANT: This uses an EXTERNAL folder outside the deployment directory
 * so images persist even after redeployment or server restarts.
 * 
 * For Production: Set PRODUCT_IMAGES_PATH environment variable
 * For Development: Uses default path based on OS
 */
public class ImageConfig {
    
    // ===== CONFIGURATION OPTIONS =====
    
    /**
     * Option 1: Use Environment Variable (RECOMMENDED for Production)
     * Set this in your server startup script or IDE run configuration:
     * 
     * Windows: set PRODUCT_IMAGES_PATH=C:\ProductImages
     * Linux:   export PRODUCT_IMAGES_PATH=/var/www/product-images
     * Tomcat:  Add to catalina.sh or setenv.sh
     */
    private static final String ENV_PATH = System.getenv("PRODUCT_IMAGES_PATH");
    
    /**
     * Option 2: Fixed Development Paths (OS-specific defaults)
     */
    private static final String WINDOWS_DEFAULT = "C:\\ProductImages";
    private static final String LINUX_DEFAULT = "/var/www/product-images";
    private static final String MAC_DEFAULT = System.getProperty("user.home") + "/ProductImages";
    
    /**
     * Get the image storage path
     * Priority: Environment Variable > OS Default
     */
    public static String getImageStoragePath() {
        // First, check environment variable
        if (ENV_PATH != null && !ENV_PATH.trim().isEmpty()) {
            return ENV_PATH;
        }
        
        // Otherwise, use OS-specific default
        String os = System.getProperty("os.name").toLowerCase();
        
        if (os.contains("win")) {
            return WINDOWS_DEFAULT;
        } else if (os.contains("mac")) {
            return MAC_DEFAULT;
        } else {
            return LINUX_DEFAULT;
        }
    }
    
    /**
     * Initialize the image storage directory
     * Creates the directory if it doesn't exist
     * 
     * @return true if directory exists or was created successfully
     */
    public static boolean initializeImageDirectory() {
        String path = getImageStoragePath();
        File directory = new File(path);
        
        if (!directory.exists()) {
            boolean created = directory.mkdirs();
            if (created) {
                System.out.println("✅ Created image storage directory: " + path);
            } else {
                System.err.println("❌ Failed to create image storage directory: " + path);
                return false;
            }
        } else {
            System.out.println("✅ Image storage directory exists: " + path);
        }
        
        return true;
    }
    
    /**
     * Get full path for a specific image file
     * 
     * @param filename The image filename
     * @return Full path to the image file
     */
    public static String getImageFilePath(String filename) {
        if (filename == null || filename.trim().isEmpty()) {
            return null;
        }
        return getImageStoragePath() + File.separator + filename;
    }
    
    /**
     * Check if image storage is properly configured
     * 
     * @return Configuration status message
     */
    public static String getConfigurationStatus() {
        StringBuilder status = new StringBuilder();
        status.append("Image Storage Configuration:\n");
        status.append("  Path: ").append(getImageStoragePath()).append("\n");
        status.append("  Source: ").append(ENV_PATH != null ? "Environment Variable" : "Default (OS-based)").append("\n");
        status.append("  Directory exists: ").append(new File(getImageStoragePath()).exists()).append("\n");
        status.append("  Directory writable: ").append(new File(getImageStoragePath()).canWrite()).append("\n");
        return status.toString();
    }
    
    /**
     * Print configuration on startup
     */
    static {
        System.out.println("=".repeat(60));
        System.out.println(getConfigurationStatus());
        System.out.println("=".repeat(60));
    }
}
