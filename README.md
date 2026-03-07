# 🛒 E-Commerce Shopping Cart System

A full-stack E-Commerce Web Application developed using **Java Servlets, JSP, JDBC, and PostgreSQL**.  
The system allows users to browse products, add items to a shopping cart, and place orders.  
Admins can manage products and view orders through an admin dashboard.

The application follows **MVC Architecture**, uses **Docker for containerization**, and is deployed on **Render Cloud Platform**.

---

# 🚀 Live Demo

👉 https://YOUR-RENDER-LINK.onrender.com

---

# 📂 GitHub Repository

👉 https://github.com/Ajyp14/E-Commerce-Sc

---

# ✨ Features

## User Features
- User Registration
- User Login / Logout
- Browse Products
- Add Products to Cart
- Update Cart Items
- Remove Cart Items
- Place Orders
- View Order History

## Admin Features
- Admin Login
- Add New Products
- Update Products
- Delete Products
- View Orders
- Cancel Orders
- Order Statistics Dashboard

##Admin credentials 
Username = admin
password = admin123
---

# 🏗️ Project Architecture

This project follows the **MVC (Model View Controller)** architecture.

Model  
Java classes such as Product, User, Cart, and Order.

View  
JSP pages used to display UI.

Controller  
Java Servlets that handle HTTP requests and responses.

---

# 🛠️ Technologies Used

### Backend
- Java
- Servlets
- JSP
- JDBC

### Frontend
- HTML
- CSS
- Bootstrap
- JavaScript

### Database
- PostgreSQL

### Tools & Deployment
- Docker
- Maven
- Apache Tomcat
- Render Cloud Platform

---

# 🐳 Docker Deployment

The project uses a **multi-stage Docker build**.

Stage 1 – Build Stage  
Uses Maven image to build the WAR file.

Stage 2 – Runtime Stage  
Uses Apache Tomcat image to run the application.

Dockerfile example:
