
package com.dao;

import java.sql.*;
import java.util.*;
import com.model.CartItem;
import com.model.Product;
import com.util.DBConnection;

public class CartDAO {

    // Fetch cart products
    public List<Product> getCartProducts(int userId) {
        List<Product> list = new ArrayList<>();
        String sql = """
            SELECT c.id AS cart_id, c.quantity, 
                   p.id AS product_id, p.name, p.price, p.image1 
            FROM cart c 
            JOIN products p ON c.product_id = p.id
            WHERE c.user_id = ?
        """;

        try(Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setInt(1, userId);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                Product p = new Product();
                p.setId(rs.getInt("product_id"));
                p.setName(rs.getString("name"));
                p.setPrice(rs.getDouble("price"));
                p.setImage1(rs.getString("image1"));
                p.setQuantity(rs.getInt("quantity"));
                p.setCartId(rs.getInt("cart_id"));   // IMPORTANT
                list.add(p);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }


    // Update quantity
    public boolean updateQuantity(int cartId, int qty) {
        String sql = "UPDATE cart SET quantity=? WHERE id=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setInt(1, qty);
            pst.setInt(2, cartId);

            return pst.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    // Remove cart item
    public boolean removeItem(int cartId) {
        String sql = "DELETE FROM cart WHERE id=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setInt(1, cartId);
            return pst.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }




    public boolean addToCart(CartItem item) {
        boolean f = false;
        try (Connection conn = DBConnection.getConnection()) {
            
            String checkSql = "SELECT * FROM cart WHERE user_id=? AND product_id=?";
            PreparedStatement pst = conn.prepareStatement(checkSql);
            pst.setInt(1, item.getUserId());
            pst.setInt(2, item.getProductId());
            ResultSet rs = pst.executeQuery();

            if(rs.next()) {
                String updateSql = "UPDATE cart SET quantity = quantity + ? WHERE user_id=? AND product_id=?";
                PreparedStatement pst2 = conn.prepareStatement(updateSql);
                pst2.setInt(1, item.getQuantity());
                pst2.setInt(2, item.getUserId());
                pst2.setInt(3, item.getProductId());
                f = pst2.executeUpdate() > 0;
            } else {
                String sql = "INSERT INTO cart(user_id, product_id, quantity) VALUES (?,?,?)";
                PreparedStatement pst3 = conn.prepareStatement(sql);
                pst3.setInt(1, item.getUserId());
                pst3.setInt(2, item.getProductId());
                pst3.setInt(3, item.getQuantity());
                f = pst3.executeUpdate() > 0;
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return f;
    }
   
    
    public boolean updateQty(int cartId, int qty) {
        boolean f = false;
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "UPDATE cart SET quantity=? WHERE id=?";
            
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, qty);
            pst.setInt(2, cartId);
            
            f = pst.executeUpdate() > 0;
        } catch(Exception e) {
            e.printStackTrace();
        }
        return f;
    }


   
    public boolean addToCart(int userId, int productId, int qty) {
        try (Connection conn = DBConnection.getConnection()) {

            // 1️ Get product stock ..here we check how many products stock we have
            PreparedStatement ps1 =
                conn.prepareStatement("SELECT quantity FROM products WHERE id=?");
            ps1.setInt(1, productId);
            ResultSet rs1 = ps1.executeQuery();

            if (!rs1.next()) return false;

            int stock = rs1.getInt("quantity");

            // 2️ Check existing cart qty
            PreparedStatement ps2 =
                conn.prepareStatement("SELECT quantity FROM cart WHERE user_id=? AND product_id=?");
            ps2.setInt(1, userId);
            ps2.setInt(2, productId);
            ResultSet rs2 = ps2.executeQuery();

            int existingQty = 0;
            if (rs2.next()) {
                existingQty = rs2.getInt("quantity");
            }

            // 3️ FINAL STOCK VALIDATION
            if (existingQty + qty > stock) {
                return false; //  Out of stock
            }

            // 4️ Insert or Update cart
            if (existingQty > 0) {
                PreparedStatement ps3 =
                    conn.prepareStatement(
                        "UPDATE cart SET quantity=? WHERE user_id=? AND product_id=?");
                ps3.setInt(1, existingQty + qty);
                ps3.setInt(2, userId);
                ps3.setInt(3, productId);
                ps3.executeUpdate();
            } else {
                PreparedStatement ps4 =
                    conn.prepareStatement(
                        "INSERT INTO cart(user_id, product_id, quantity) VALUES (?,?,?)");
                ps4.setInt(1, userId);
                ps4.setInt(2, productId);
                ps4.setInt(3, qty);
                ps4.executeUpdate();
            }

            return true;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

 // get quantity already in cart
    public int getCartQty(int userId, int productId) {
        String sql = "SELECT quantity FROM cart WHERE user_id=? AND product_id=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setInt(1, userId);
            pst.setInt(2, productId);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                return rs.getInt("quantity");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

   

    public boolean updateQuantity(int userId, int productId, int quantity) {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "UPDATE cart SET quantity=? WHERE user_id=? AND product_id=?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, quantity);
            pst.setInt(2, userId);
            pst.setInt(3, productId);
            return pst.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean removeItem(int userId, int productId) {
        try (Connection conn = DBConnection.getConnection()) {
            PreparedStatement pst = conn.prepareStatement("DELETE FROM cart WHERE user_id=? AND product_id=?");
            pst.setInt(1, userId);
            pst.setInt(2, productId);
            return pst.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    public boolean updateQuantityForUser(int productId, int userId, int qty) {
        String sql = "UPDATE cart SET quantity = ? WHERE product_id = ? AND user_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setInt(1, qty);
            pst.setInt(2, productId);
            pst.setInt(3, userId);
            int updated = pst.executeUpdate();
            return updated > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
 // get product id using cart id
    public int getProductIdByCartId(int cartId) {
        String sql = "SELECT product_id FROM cart WHERE id=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setInt(1, cartId);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                return rs.getInt("product_id");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
 // get stock quantity from products table
    public int getProductStock(int productId) {
        String sql = "SELECT quantity FROM products WHERE id=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setInt(1, productId);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                return rs.getInt("quantity");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

}
