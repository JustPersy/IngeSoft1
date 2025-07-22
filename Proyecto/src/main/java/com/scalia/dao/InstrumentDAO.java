package com.scalia.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.scalia.models.Instrument;
import com.scalia.utils.DatabaseConnection;
import com.scalia.models.InstrumentCategory;

public class InstrumentDAO {
    public static List<Instrument> getAllInstruments() {
        List<Instrument> instruments = new ArrayList<>();
        String query = "SELECT i.*, c.id as category_id, c.name as category_name FROM instruments i LEFT JOIN instrument_categories c ON i.category_id = c.id";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                InstrumentCategory category = null;
                int catId = rs.getInt("category_id");
                if (!rs.wasNull()) {
                    category = new InstrumentCategory(catId, rs.getString("category_name"));
                }
                instruments.add(new Instrument(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("type"),
                        rs.getString("description"),
                        rs.getString("tuning_standard"),
                        category
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return instruments;
    }

    public static List<Instrument> getInstrumentsByCategory(int categoryId) {
        List<Instrument> instruments = new ArrayList<>();
        String query = "SELECT i.*, c.id as category_id, c.name as category_name FROM instruments i LEFT JOIN instrument_categories c ON i.category_id = c.id WHERE i.category_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, categoryId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    InstrumentCategory category = null;
                    int catId = rs.getInt("category_id");
                    if (!rs.wasNull()) {
                        category = new InstrumentCategory(catId, rs.getString("category_name"));
                    }
                    instruments.add(new Instrument(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("type"),
                            rs.getString("description"),
                            rs.getString("tuning_standard"),
                            category
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return instruments;
    }

    public static boolean createInstrument(String name, String type, String description, String tuningStandard, int categoryId) {
        String query = "INSERT INTO instruments (name, type, description, tuning_standard, category_id) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, name);
            stmt.setString(2, type);
            stmt.setString(3, description);
            stmt.setString(4, tuningStandard);
            stmt.setInt(5, categoryId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}

