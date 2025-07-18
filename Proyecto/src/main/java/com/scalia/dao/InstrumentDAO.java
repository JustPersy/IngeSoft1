package com.scalia.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.scalia.models.Instrument;
import com.scalia.utils.DatabaseConnection;

public class InstrumentDAO {
    public static List<Instrument> getAllInstruments() {
        List<Instrument> instruments = new ArrayList<>();
        String query = "SELECT * FROM instruments";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                instruments.add(new Instrument(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("type"),
                        rs.getString("description"),
                        rs.getString("tuning_standard")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return instruments;
    }
}

