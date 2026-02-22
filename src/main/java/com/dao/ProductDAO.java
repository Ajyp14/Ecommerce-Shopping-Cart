
package com.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.model.Product;
import com.util.DBConnection;

public class ProductDAO {

    // Add new product
    public boolean addProduct(Product p) {
        String sql = "INSERT INTO products(name,brand,category,subcategory,price,discount_price,quantity,sku,description,specifications,image1,image2,image3) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, p.getName());
            ps.setString(2, p.getBrand());
            ps.setString(3, p.getCategory());
            ps.setString(4, p.getSubcategory());
            ps.setDouble(5, p.getPrice());
            ps.setDouble(6, p.getDiscountPrice());
            ps.setInt(7, p.getQuantity());
            ps.setString(8, p.getSku());
            ps.setString(9, p.getDescription());
            ps.setString(10, p.getSpecifications());
            ps.setString(11, p.getImage1());
            ps.setString(12, p.getImage2());
            ps.setString(13, p.getImage3());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // Get all products
    public List<Product> getAllProducts() {
        List<Product> list = new ArrayList<>();
        String sql = "SELECT * FROM products ORDER BY id DESC";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Product p = new Product();
                p.setId(rs.getInt("id"));
                p.setName(rs.getString("name"));
                p.setBrand(rs.getString("brand"));
                p.setPrice(rs.getDouble("price"));
                p.setQuantity(rs.getInt("quantity"));
                p.setCategory(rs.getString("category"));
                p.setImage1(rs.getString("image1"));
                list.add(p);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    
    // Get all products sorted by price
    public List<Product> getAllProductsSorted(String sort) {

        List<Product> list = new ArrayList<>();

        String sql = "SELECT * FROM products";

        if ("low".equals(sort)) {
            sql += " ORDER BY price ASC";
        } else if ("high".equals(sort)) {
            sql += " ORDER BY price DESC";
        } else {
            sql += " ORDER BY id DESC";
        }

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Product p = new Product();
                p.setId(rs.getInt("id"));
                p.setName(rs.getString("name"));
                p.setBrand(rs.getString("brand"));
                p.setPrice(rs.getDouble("price"));
                p.setQuantity(rs.getInt("quantity"));
                p.setCategory(rs.getString("category"));
                p.setImage1(rs.getString("image1"));
                list.add(p);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

 // Search products with price sorting
    public List<Product> searchProductsSorted(String keyword, String sort) {

        List<Product> list = new ArrayList<>();

        String sql = "SELECT * FROM products WHERE name LIKE ? OR brand LIKE ? OR category LIKE ?";

        if ("low".equals(sort)) {
            sql += " ORDER BY price ASC";
        } else if ("high".equals(sort)) {
            sql += " ORDER BY price DESC";
        }

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            String searchKey = "%" + keyword + "%";
            ps.setString(1, searchKey);
            ps.setString(2, searchKey);
            ps.setString(3, searchKey);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Product p = new Product();
                p.setId(rs.getInt("id"));
                p.setName(rs.getString("name"));
                p.setBrand(rs.getString("brand"));
                p.setPrice(rs.getDouble("price"));
                p.setQuantity(rs.getInt("quantity"));
                p.setCategory(rs.getString("category"));
                p.setImage1(rs.getString("image1"));
                list.add(p);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }


    // Get products by category (for dropdown filter)
    public List<Product> getProductsByCategory(String category) {
        List<Product> list = new ArrayList<>();
        String sql = "SELECT * FROM products WHERE category=? ORDER BY id DESC";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, category);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Product p = new Product();
                p.setId(rs.getInt("id"));
                p.setName(rs.getString("name"));
                p.setBrand(rs.getString("brand"));
                p.setPrice(rs.getDouble("price"));
                p.setQuantity(rs.getInt("quantity"));
                p.setCategory(rs.getString("category"));
                p.setImage1(rs.getString("image1"));
                list.add(p);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    // Get related products by category excluding a specific product
    public List<Product> getProductsByCategory(String category, int excludeId) {
        List<Product> list = new ArrayList<>();
        String sql = "SELECT * FROM products WHERE category=? AND id!=? LIMIT 4";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, category);
            ps.setInt(2, excludeId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Product p = new Product();
                p.setId(rs.getInt("id"));
                p.setName(rs.getString("name"));
                p.setPrice(rs.getDouble("price"));
                p.setImage1(rs.getString("image1"));
                list.add(p);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    // Get all distinct categories (for dropdown)
    public List<String> getAllCategories() {
        List<String> list = new ArrayList<>();
        String sql = "SELECT DISTINCT category FROM products WHERE category IS NOT NULL";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(rs.getString("category"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    // Search products by keyword
    public List<Product> searchProducts(String keyword) {
        List<Product> list = new ArrayList<>();
        String sql = "SELECT * FROM products WHERE name LIKE ? OR brand LIKE ? OR category LIKE ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            String searchKey = "%" + keyword + "%";
            ps.setString(1, searchKey);
            ps.setString(2, searchKey);
            ps.setString(3, searchKey);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Product p = new Product();
                p.setId(rs.getInt("id"));
                p.setName(rs.getString("name"));
                p.setBrand(rs.getString("brand"));
                p.setPrice(rs.getDouble("price"));
                p.setQuantity(rs.getInt("quantity"));
                p.setCategory(rs.getString("category"));
                p.setImage1(rs.getString("image1"));
                list.add(p);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // Update product
    public boolean updateProduct(Product p) {
        String sql = "UPDATE products SET name=?, brand=?, category=?, subcategory=?, price=?, discount_price=?, quantity=?, description=?, specifications=?, image1=?, image2=?, image3=? WHERE id=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, p.getName());
            ps.setString(2, p.getBrand());
            ps.setString(3, p.getCategory());
            ps.setString(4, p.getSubcategory());
            ps.setDouble(5, p.getPrice());
            ps.setDouble(6, p.getDiscountPrice());
            ps.setInt(7, p.getQuantity());
            ps.setString(8, p.getDescription());
            ps.setString(9, p.getSpecifications());
            ps.setString(10, p.getImage1());
            ps.setString(11, p.getImage2());
            ps.setString(12, p.getImage3());
            ps.setInt(13, p.getId());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // Get product by ID
    public Product getProductById(int id) {
        Product p = null;
        String sql = "SELECT * FROM products WHERE id=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                p = new Product();
                p.setId(rs.getInt("id"));
                p.setName(rs.getString("name"));
                p.setBrand(rs.getString("brand"));
                p.setCategory(rs.getString("category"));
                p.setSubcategory(rs.getString("subcategory"));
                p.setPrice(rs.getDouble("price"));
                p.setDiscountPrice(rs.getDouble("discount_price"));
                p.setQuantity(rs.getInt("quantity"));
                p.setDescription(rs.getString("description"));
                p.setSpecifications(rs.getString("specifications"));
                p.setSku(rs.getString("sku"));
                p.setImage1(rs.getString("image1"));
                p.setImage2(rs.getString("image2"));
                p.setImage3(rs.getString("image3"));
                p.setStatus(rs.getString("status"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return p;
    }

    // Delete product
    public boolean deleteProduct(int id) {
        String sql = "DELETE FROM products WHERE id=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // Get product count
    public int getProductCount() {
        int count = 0;
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement("SELECT COUNT(*) FROM products");
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) count = rs.getInt(1);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }

    // Category-wise count for charts
    public Map<String, Integer> getCategoryWiseCount() {
        Map<String, Integer> map = new LinkedHashMap<>();
        String sql = "SELECT category, COUNT(*) AS total FROM products GROUP BY category";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                map.put(rs.getString("category"), rs.getInt("total"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

}
