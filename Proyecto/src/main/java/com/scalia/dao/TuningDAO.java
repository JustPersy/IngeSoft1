package com.scalia.dao;

import com.scalia.models.Tuning;
import com.scalia.utils.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TuningDAO {
    public static List<Tuning> getAllTunings() {
        List<Tuning> list = new ArrayList<>();
        String sql = "SELECT * FROM tunings";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(mapRow(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static List<Tuning> getTuningsByInstrument(int instrumentId) {
        List<Tuning> list = new ArrayList<>();
        String sql = "SELECT * FROM tunings WHERE instrument_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, instrumentId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    list.add(mapRow(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    private static Tuning mapRow(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String name = rs.getString("name");
        Integer instId = rs.getObject("instrument_id") != null ? rs.getInt("instrument_id") : null;
        String notesJson = rs.getString("notes");
        List<String> notes = Arrays.asList(notesJson.replace("[", "").replace("]", "").replace("\"", "").split(",\s*"));
        String description = rs.getString("description");
        return new Tuning(id, name, instId, notes, description);
    }
}
