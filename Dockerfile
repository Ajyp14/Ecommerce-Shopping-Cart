FROM tomcat:10.1-jdk17

# Remove default apps
RUN rm -rf /usr/local/tomcat/webapps/*

# Copy WAR file
COPY target/E-Commerce-Sc-0.0.1-SNAPSHOT.war /usr/local/tomcat/webapps/ROOT.war

# Create image storage
RUN mkdir -p /app/data/product-images && \
    chmod 777 /app/data/product-images

ENV PRODUCT_IMAGES_PATH=/app/data/product-images

EXPOSE 10000

CMD ["catalina.sh", "run"]