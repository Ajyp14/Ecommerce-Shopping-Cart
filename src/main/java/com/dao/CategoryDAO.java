package com.dao;

import java.sql.*;
import java.util.*;
import com.util.DBConnection;

public class CategoryDAO {
    public List<String> getAllCategories() {
        List<String> list = new ArrayList<>();
        String sql = "SELECT name FROM category";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(rs.getString("name"));
            }
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }
}