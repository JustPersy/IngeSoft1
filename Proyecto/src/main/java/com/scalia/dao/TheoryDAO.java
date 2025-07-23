package com.scalia.dao;

import com.scalia.models.TheoryConcept;
import com.scalia.utils.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TheoryDAO {
    public static List<TheoryConcept> getAllConcepts() {
        List<TheoryConcept> list = new ArrayList<>();
        String sql = "SELECT * FROM theory_concepts";
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

    private static TheoryConcept mapRow(ResultSet rs) throws SQLException {
        return new TheoryConcept(
                rs.getInt("id"),
                rs.getString("name"),
                rs.getString("category"),
                rs.getString("description"),
                rs.getString("difficulty_level")
        );
    }
}
