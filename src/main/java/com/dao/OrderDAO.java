
package com.dao;

import java.sql.*;
import java.util.*;


import com.model.Order;
import com.model.OrderItem;
import com.util.DBConnection;

public class OrderDAO {

    public List<Order> getAllOrders() {
        List<Order> list = new ArrayList<>();
        String sql = "SELECT c.id AS cart_id, c.quantity, \r\n"
        		+ "                   p.id AS product_id, p.name, p.price, p.image1 \r\n"
        		+ "            FROM cart c \r\n"
        		+ "            JOIN products p ON c.product_id = p.id\r\n"
        		+ "            WHERE c.user_id = ?";

        try(Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()) {

            while(rs.next()){
                Order ord = new Order();
                ord.setId(rs.getInt("id"));
                ord.setUserName(rs.getString("user_name"));
                ord.setTotalAmount(rs.getDouble("total_amount"));
                ord.setStatus(rs.getString("status"));
                ord.setOrderDate(rs.getTimestamp("order_date"));
                ord.setItemCount(rs.getInt("item_count"));
                list.add(ord);
            }

        } catch(Exception e) { e.printStackTrace(); }

        return list;
    }



    public boolean updateStatus(int id, String status) {
        String sql = "UPDATE orders SET status=? WHERE id=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, status);
            ps.setInt(2, id);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    public int getOrderCount() {
        int count = 0;
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement("SELECT COUNT(*) FROM orders");
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) count = rs.getInt(1);

        } catch (Exception e) { e.printStackTrace(); }
        return count;
    }
    public List<Integer> getOrdersByMonth() {
        List<Integer> list = new ArrayList<>();
        String sql = "SELECT MONTH(order_date) AS m, COUNT(*) AS total "
                   + "FROM orders GROUP BY MONTH(order_date) ORDER BY m";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) list.add(rs.getInt("total"));

        } catch (Exception e) { e.printStackTrace(); }

        return list;
    }

    public int getPendingCount() {
        return getStatusCount("Pending");
    }

    public int getDeliveredCount() {
        return getStatusCount("Delivered");
    }

    public int getCancelledCount() {
        return getStatusCount("Cancelled");
    }

    private int getStatusCount(String status) {
        int count = 0;
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement("SELECT COUNT(*) FROM orders WHERE status=?")) {
            
            ps.setString(1, status);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) count = rs.getInt(1);

        } catch (Exception e) { e.printStackTrace(); }
        return count;
    }
 // Count Pending Orders
    public int getPendingOrders() {
        int count = 0;
        String sql = "SELECT COUNT(*) FROM orders WHERE status='Pending'";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) count = rs.getInt(1);
        } catch (Exception e) { e.printStackTrace(); }
        return count;
    }

    // Count Delivered Orders
    public int getDeliveredOrders() {
        int count = 0;
        String sql = "SELECT COUNT(*) FROM orders WHERE status='Delivered'";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) count = rs.getInt(1);

        } catch (Exception e) { e.printStackTrace(); }
        return count;
    }


    // Count Cancelled Orders
    public int getCancelledOrders() {
        int count = 0;
        String sql = "SELECT COUNT(*) FROM orders WHERE status='Cancelled'";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) count = rs.getInt(1);
        } catch (Exception e) { e.printStackTrace(); }
        return count;
    }



    public boolean placeSingleOrder(int userId, int productId, int qty,
            String name, String phone, String address) {

try(Connection conn = DBConnection.getConnection()) {

String orderSql = "INSERT INTO orders(user_id, fullname, phone, address, status) VALUES(?,?,?,?,?)";
PreparedStatement pst = conn.prepareStatement(orderSql, PreparedStatement.RETURN_GENERATED_KEYS);
pst.setInt(1, userId);
pst.setString(2, name);
pst.setString(3, phone);
pst.setString(4, address);
pst.setString(5, "Pending");
pst.executeUpdate();

ResultSet rs = pst.getGeneratedKeys();
rs.next();
int orderId = rs.getInt(1);

// Insert a single product
String itemSql = "INSERT INTO order_items(order_id, product_id, quantity) VALUES(?,?,?)";
PreparedStatement pst2 = conn.prepareStatement(itemSql);
pst2.setInt(1, orderId);
pst2.setInt(2, productId);
pst2.setInt(3, qty);
pst2.executeUpdate();

return true;

} catch (Exception ex) {
ex.printStackTrace();
}
return false;
}


    public boolean placeOrder(int userId, String fullname, String phone, String address,
            String paymentMethod, double totalAmount,
            String upiId, String cardNumber) {

String sql = "INSERT INTO orders (user_id, fullname, phone, address, payment_method, " +
"total_amount, upi_id, card_number, status) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

try (Connection con = DBConnection.getConnection();
PreparedStatement pst = con.prepareStatement(sql)) {

pst.setInt(1, userId);
pst.setString(2, fullname);
pst.setString(3, phone);
pst.setString(4, address);
pst.setString(5, paymentMethod);

pst.setDouble(6, totalAmount);

// Fix: NULL must be inserted as SQL NULL, not Java null string
if (upiId == null || upiId.trim().isEmpty()) pst.setNull(7, Types.VARCHAR);
else pst.setString(7, upiId);

if (cardNumber == null || cardNumber.trim().isEmpty()) pst.setNull(8, Types.VARCHAR);
else pst.setString(8, cardNumber);

pst.setString(9, "Pending");

return pst.executeUpdate() > 0;

} catch (Exception e) {
e.printStackTrace();
return false;
}
}
    public boolean deleteOrder(int orderId) {

        String deleteItems = "DELETE FROM order_items WHERE order_id=?";
        String deleteOrder = "DELETE FROM orders WHERE id=?";

        try (Connection con = DBConnection.getConnection()) {

            // delete order items first
            PreparedStatement ps1 = con.prepareStatement(deleteItems);
            ps1.setInt(1, orderId);
            ps1.executeUpdate();

            // delete order
            PreparedStatement ps2 = con.prepareStatement(deleteOrder);
            ps2.setInt(1, orderId);

            return ps2.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Order> getOrdersByUser(int userId) {

        List<Order> list = new ArrayList<>();

        try {
            Connection con = DBConnection.getConnection();

            String sql = "SELECT * FROM orders WHERE user_id=? ORDER BY order_date DESC";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, userId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Order o = new Order();
                o.setId(rs.getInt("id"));
                o.setUserId(rs.getInt("user_id"));
                o.setTotalAmount(rs.getDouble("total_amount"));
                o.setStatus(rs.getString("status"));
                o.setOrderDate(rs.getTimestamp("order_date"));
                o.setFullname(rs.getString("fullname"));
                o.setPhone(rs.getString("phone"));
                o.setAddress(rs.getString("address"));
                o.setPaymentMethod(rs.getString("payment_method"));

                list.add(o);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
