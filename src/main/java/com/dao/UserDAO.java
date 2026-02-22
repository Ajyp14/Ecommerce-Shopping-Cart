package com.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.model.User;
import com.util.DBConnection;

public class UserDAO {

    /* ===================== ADD USER ===================== */
    public boolean addUser(User user) {
        boolean result = false;

        String sql = "INSERT INTO users "
                   + "(name, email, password, phone, address, role, status) "
                   + "VALUES (?,?,?,?,?,?,?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            // email duplicate check
            if (isEmailExists(user.getEmail())) {
                return false;
            }

            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword());
            ps.setString(4, user.getPhone());
            ps.setString(5, user.getAddress());
            ps.setString(6, user.getRole());
            ps.setString(7, user.getStatus());

            result = ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /* ===================== LOGIN ===================== */
    public User login(String email, String password) {
        User u = null;

        String sql = "SELECT * FROM users "
                   + "WHERE email=? AND password=? AND status='Active'";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, email);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                u = new User();
                u.setId(rs.getInt("id"));
                u.setName(rs.getString("name"));
                u.setEmail(rs.getString("email"));
                u.setRole(rs.getString("role"));
                u.setStatus(rs.getString("status"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return u;
    }

    /* ===================== GET ALL USERS ===================== */
    public List<User> getAllUsers() {
        List<User> list = new ArrayList<>();

        String sql = "SELECT * FROM users ORDER BY id DESC";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                User u = new User();
                u.setId(rs.getInt("id"));
                u.setName(rs.getString("name"));
                u.setEmail(rs.getString("email"));
                u.setPhone(rs.getString("phone"));
                u.setAddress(rs.getString("address"));
                u.setRole(rs.getString("role"));
                u.setStatus(rs.getString("status"));
                list.add(u);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /* ===================== GET USER BY ID ===================== */
    public User getUserById(int id) {
        User u = null;

        String sql = "SELECT * FROM users WHERE id=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                u = new User();
                u.setId(rs.getInt("id"));
                u.setName(rs.getString("name"));
                u.setEmail(rs.getString("email"));
                u.setPhone(rs.getString("phone"));
                u.setAddress(rs.getString("address"));
                u.setRole(rs.getString("role"));
                u.setStatus(rs.getString("status"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return u;
    }

    /* ===================== UPDATE USER ===================== */
    public boolean updateUser(User u) {
        String sql = "UPDATE users SET name=?, email=?, phone=?, "
                   + " role=? WHERE id=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, u.getName());
            ps.setString(2, u.getEmail());
            ps.setString(3, u.getPhone());
           // ps.setString(4, u.getAddress());
            ps.setString(4, u.getRole());
           // ps.setString(6, u.getStatus());
            ps.setInt(5, u.getId());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /* ===================== DELETE USER ===================== */
    public boolean deleteUser(int id) {
        String sql = "DELETE FROM users WHERE id=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /* ===================== UPDATE STATUS ===================== */
    public boolean updateStatus(int id, String status) {
        String sql = "UPDATE users SET status=? WHERE id=?";

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

    /* ===================== EMAIL EXISTS CHECK ===================== */
    public boolean isEmailExists(String email) {
        String sql = "SELECT id FROM users WHERE email=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            return rs.next();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /* ===================== USER COUNT ===================== */
    public int getUserCount() {
        int count = 0;
        String sql = "SELECT COUNT(*) FROM users";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                count = rs.getInt(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }

	
}