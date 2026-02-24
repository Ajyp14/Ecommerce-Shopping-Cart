# Dockerfile for Railway Deployment
FROM tomcat:10.1-jdk17

# Remove default Tomcat applications
RUN rm -rf /usr/local/tomcat/webapps/*

# Copy WAR file (Railway will build this automatically)
COPY target/E-Commerce-Sc-0.0.1-SNAPSHOT.war /usr/local/tomcat/webapps/ROOT.war

# Create image storage directory
# Using /app/data which Railway can persist
RUN mkdir -p /app/data/product-images && \
    chmod 777 /app/data/product-images

# Set environment variable for image storage path
ENV PRODUCT_IMAGES_PATH=/app/data/product-images

# Expose port 8080
EXPOSE 8080

# Start Tomcat
CMD ["catalina.sh", "run"]