# -------- Build Stage --------
FROM maven:3.9.6-eclipse-temurin-17 AS build

WORKDIR /app

# Copy everything
COPY . .

# Build WAR file
RUN mvn clean package -DskipTests

# -------- Runtime Stage --------
FROM tomcat:10.1-jdk17

# Remove default apps
RUN rm -rf /usr/local/tomcat/webapps/*

# Copy WAR from build stage
COPY --from=build /app/target/E-Commerce-Sc-0.0.1-SNAPSHOT.war /usr/local/tomcat/webapps/ROOT.war

# Image folder
#RUN mkdir -p /app/data/product-images && chmod 777 /app/data/product-images
#ENV PRODUCT_IMAGES_PATH=/app/data/product-images

EXPOSE 10000

CMD ["catalina.sh", "run"]