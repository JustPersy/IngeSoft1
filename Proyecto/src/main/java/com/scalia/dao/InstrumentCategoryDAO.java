package com.scalia.dao;

import com.scalia.models.InstrumentCategory;
import com.scalia.utils.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InstrumentCategoryDAO {
    public static List<InstrumentCategory> getAllCategories() {
        List<InstrumentCategory> categories = new ArrayList<>();
        String query = "SELECT * FROM instrument_categories";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                categories.add(new InstrumentCategory(
                        rs.getInt("id"),
                        rs.getString("name")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categories;
    }

    public static InstrumentCategory getCategoryById(int id) {
        String query = "SELECT * FROM instrument_categories WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new InstrumentCategory(rs.getInt("id"), rs.getString("name"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean createCategory(String name) {
        String query = "INSERT INTO instrument_categories (name) VALUES (?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, name);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
} 