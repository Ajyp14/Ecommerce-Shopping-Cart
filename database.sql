-- E-Commerce Database Schema for MySQL
-- Compatible with Railway MySQL

-- Drop existing tables (if any)
DROP TABLE IF EXISTS order_items;
DROP TABLE IF EXISTS orders;
DROP TABLE IF EXISTS cart;
DROP TABLE IF EXISTS products;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS admin;
DROP TABLE IF EXISTS card_details;
DROP TABLE IF EXISTS bank_upi;

-- ==========================================
-- ADMIN TABLE
-- ==========================================
CREATE TABLE admin (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Insert default admin
INSERT INTO admin (username, password) VALUES ('admin', 'admin123');

-- ==========================================
-- USERS TABLE
-- ==========================================
CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    phone VARCHAR(15),
    address TEXT,
    role VARCHAR(20) DEFAULT 'Customer',
    status VARCHAR(20) DEFAULT 'Active',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Insert sample users
INSERT INTO users (name, email, password, phone, address) VALUES
('Ajay pawar', 'Ap@example.com', 'password123', '1234567890', '123 Main St, City'),
('raj kamble', 'rk@example.com', 'password123', '0987654321', '456 Oak Ave, Town'),
('mahesh pande', 'mp@example.com', 'password123', '5555555555', '789 Pine Rd, Village');

-- ==========================================
-- PRODUCTS TABLE
-- ==========================================
CREATE TABLE products (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(200) NOT NULL,
    brand VARCHAR(100),
    category VARCHAR(50),
    subcategory VARCHAR(50),
    sku VARCHAR(50) UNIQUE,
    price DECIMAL(10, 2) NOT NULL,
    discount_price DECIMAL(10, 2),
    quantity INT DEFAULT 0,
    description TEXT,
    specifications TEXT,
    image1 VARCHAR(255),
    image2 VARCHAR(255),
    image3 VARCHAR(255),
    status VARCHAR(20) DEFAULT 'Active',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Insert sample products
INSERT INTO products (name, brand, category, subcategory, sku, price, discount_price, quantity, description, specifications, status) VALUES
('iPhone 15 Pro', 'Apple', 'Electronics', 'Smartphones', 'AP-ELE-0001', 129999.00, 119999.00, 50, 'Latest iPhone with A17 Pro chip', 'Display: 6.1 inch, Storage: 128GB, Camera: 48MP', 'Active'),
('Samsung Galaxy S24', 'Samsung', 'Electronics', 'Smartphones', 'SA-ELE-0002', 99999.00, 89999.00, 40, 'Flagship Samsung smartphone', 'Display: 6.2 inch, Storage: 256GB, Camera: 50MP', 'Active'),
('MacBook Pro 16"', 'Apple', 'Electronics', 'Laptops', 'AP-ELE-0003', 249999.00, 229999.00, 25, 'Powerful MacBook Pro', 'Processor: M3 Pro, RAM: 16GB, Storage: 512GB SSD', 'Active'),
('Dell XPS 15', 'Dell', 'Electronics', 'Laptops', 'DE-ELE-0004', 179999.00, 159999.00, 30, 'Premium Dell laptop', 'Processor: Intel i7, RAM: 16GB, Storage: 1TB SSD', 'Active'),
('Sony WH-1000XM5', 'Sony', 'Electronics', 'Headphones', 'SO-ELE-0005', 39999.00, 34999.00, 60, 'Noise cancellation headphones', 'Battery: 30 hours, Bluetooth 5.2, ANC', 'Active'),
('AirPods Pro 2', 'Apple', 'Electronics', 'Headphones', 'AP-ELE-0006', 24999.00, 22999.00, 70, 'Apple wireless earbuds', 'Battery: 6 hours, ANC, Transparency mode', 'Active'),
('Nike Air Max', 'Nike', 'Fashion', 'Shoes', 'NI-FAS-0007', 12999.00, 9999.00, 100, 'Running shoes', 'Size: 8-12, Material: Mesh, Cushioning: Air Max', 'Active'),
('Adidas Ultraboost', 'Adidas', 'Fashion', 'Shoes', 'AD-FAS-0008', 14999.00, 11999.00, 80, 'Performance running shoes', 'Size: 7-11, Material: Primeknit, Boost technology', 'Active'),
('Levis 501 Jeans', 'Levis', 'Fashion', 'Clothing', 'LE-FAS-0009', 3999.00, 2999.00, 150, 'Classic denim jeans', 'Size: 28-38, Fit: Regular, Color: Blue', 'Active'),
('Samsung Smart TV 55"', 'Samsung', 'Electronics', 'TV', 'SA-ELE-0010', 59999.00, 49999.00, 20, '4K Smart TV', 'Display: 55 inch 4K, Smart: Tizen OS, HDR10+', 'Active');

-- ==========================================
-- CART TABLE
-- ==========================================
CREATE TABLE cart (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    product_id INT NOT NULL,
    quantity INT DEFAULT 1,
    added_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE CASCADE
);

-- ==========================================
-- ORDERS TABLE
-- ==========================================
CREATE TABLE orders (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    order_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    total_amount DECIMAL(10, 2) NOT NULL,
    payment_method VARCHAR(50),
    payment_status VARCHAR(50) DEFAULT 'Pending',
    shipping_address TEXT,
    status VARCHAR(50) DEFAULT 'Processing',
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- Insert sample orders
INSERT INTO orders (user_id, total_amount, payment_method, payment_status, shipping_address, status) VALUES
(1, 119999.00, 'Card', 'Completed', '123 Main St, City', 'Delivered'),
(2, 89999.00, 'UPI', 'Completed', '456 Oak Ave, Town', 'Shipped'),
(3, 34999.00, 'COD', 'Pending', '789 Pine Rd, Village', 'Processing');

-- ==========================================
-- ORDER ITEMS TABLE
-- ==========================================
CREATE TABLE order_items (
    id INT AUTO_INCREMENT PRIMARY KEY,
    order_id INT NOT NULL,
    product_id INT NOT NULL,
    quantity INT NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (order_id) REFERENCES orders(id) ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES products(id)
);

-- Insert sample order items
INSERT INTO order_items (order_id, product_id, quantity, price) VALUES
(1, 1, 1, 119999.00),
(2, 2, 1, 89999.00),
(3, 5, 1, 34999.00);

-- ==========================================
-- PAYMENT TABLES (For Simulation)
-- ==========================================

-- Card Details Table
CREATE TABLE card_details (
    id INT AUTO_INCREMENT PRIMARY KEY,
    card_number VARCHAR(16) UNIQUE NOT NULL,
    holder_name VARCHAR(100) NOT NULL,
    exp_month VARCHAR(2) NOT NULL,
    exp_year VARCHAR(4) NOT NULL,
    cvv VARCHAR(3) NOT NULL,
    balance DECIMAL(10, 2) DEFAULT 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Insert test card details
INSERT INTO card_details (card_number, holder_name, exp_month, exp_year, cvv, balance) VALUES
('4111111111111111', 'Test User', '12', '2028', '123', 500000.00),
('5555555555554444', 'John Doe', '06', '2027', '456', 250000.00),
('378282246310005', 'Jane Smith', '09', '2026', '789', 100000.00);

-- Bank UPI Table
CREATE TABLE bank_upi (
    id INT AUTO_INCREMENT PRIMARY KEY,
    upi_id VARCHAR(100) UNIQUE NOT NULL,
    balance DECIMAL(10, 2) DEFAULT 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Insert test UPI IDs
INSERT INTO bank_upi (upi_id, balance) VALUES
('testuser@paytm', 250000.00),
('Ap@oksbi', 150000.00),
('rk@okaxis', 300000.00),
('mk@ybl', 500000.00);

-- ==========================================
-- VERIFICATION QUERIES
-- ==========================================

-- Show all tables
SELECT 'Tables created successfully!' as Status;

-- Count records in each table
SELECT 
    (SELECT COUNT(*) FROM admin) as admin_count,
    (SELECT COUNT(*) FROM users) as users_count,
    (SELECT COUNT(*) FROM products) as products_count,
    (SELECT COUNT(*) FROM orders) as orders_count,
    (SELECT COUNT(*) FROM order_items) as order_items_count,
    (SELECT COUNT(*) FROM card_details) as cards_count,
    (SELECT COUNT(*) FROM bank_upi) as upi_count;

-- ==========================================
-- DEFAULT CREDENTIALS
-- ==========================================
-- Admin Login:
--   Username: admin
--   Password: admin123
--
-- Test User Login:
--   Email: Ap@example.com
--   Password: password123
--
-- Test Card:
--   Card Number: 4111111111111111
--   Holder: Test User
--   Expiry: 12/2028
--   CVV: 123
--   Balance: ₹5,00,000
--
-- Test UPI:
--   UPI ID: testuser@paytm
--   Balance: ₹2,50,000
-- ==========================================