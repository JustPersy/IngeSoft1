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
        String query = "SELECT i.*, c.id as category_id, c.name as category_name FROM instruments i LEFT JOIN categories c ON i.category_id = c.id";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                InstrumentCategory category = null;
                int catId = rs.getInt("category_id");
                if (!rs.wasNull()) {
                    category = new InstrumentCategory(catId, rs.getString("category_name"));
                }
                
                // For piano (ID 2), tuning standard should be null
                // For other instruments, we can set a default or null
                String tuningStandard = null;
                if (rs.getInt("id") == 1) { // Guitar
                    tuningStandard = "E-A-D-G-B-E";  // Standard guitar tuning
                }
                // Piano and other instruments get null tuning standard
                
                instruments.add(new Instrument(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("family"),  // Use 'family' column that exists
                        rs.getString("description"),
                        tuningStandard,  // This will be null for piano (ID 2)
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
        String query = "SELECT i.*, c.id as category_id, c.name as category_name FROM instruments i LEFT JOIN categories c ON i.category_id = c.id WHERE i.category_id = ?";

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
                    
                    // For piano (ID 2), tuning standard should be null
                    // For other instruments, we can set a default or null
                    String tuningStandard = null;
                    if (rs.getInt("id") == 1) { // Guitar
                        tuningStandard = "E-A-D-G-B-E";  // Standard guitar tuning
                    }
                    // Piano and other instruments get null tuning standard
                    
                    instruments.add(new Instrument(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("family"),  // Use 'family' column that exists
                            rs.getString("description"),
                            tuningStandard,  // This will be null for piano (ID 2)
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
        String query = "INSERT INTO instruments (name, family, description, category_id) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, name);
            stmt.setString(2, type);  // This will go to 'family' column
            stmt.setString(3, description);
            stmt.setInt(4, categoryId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    /**
     * Get instrument by ID
     * @param id The instrument ID
     * @return Instrument or null if not found
     */
    public static Instrument getInstrumentById(int id) {
        String query = "SELECT i.*, c.id as category_id, c.name as category_name FROM instruments i LEFT JOIN categories c ON i.category_id = c.id WHERE i.id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                InstrumentCategory category = null;
                int catId = rs.getInt("category_id");
                if (!rs.wasNull()) {
                    category = new InstrumentCategory(catId, rs.getString("category_name"));
                }
                
                return new Instrument(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("family"),  // Use 'family' column that exists
                    rs.getString("description"),
                    "",  // No tuning_standard column in current schema
                    category
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    /**
     * Search instruments by name
     * @param searchTerm The search term
     * @return List of matching instruments
     */
    public static List<Instrument> searchInstruments(String searchTerm) {
        List<Instrument> instruments = new ArrayList<>();
        String query = "SELECT i.*, c.id as category_id, c.name as category_name FROM instruments i LEFT JOIN categories c ON i.category_id = c.id WHERE i.name LIKE ? OR i.description LIKE ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            String searchPattern = "%" + searchTerm + "%";
            stmt.setString(1, searchPattern);
            stmt.setString(2, searchPattern);
            
            ResultSet rs = stmt.executeQuery();
            
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
    
    /**
     * Get instruments by family
     * @param family The instrument family
     * @return List of instruments in the family
     */
    public static List<Instrument> getInstrumentsByFamily(String family) {
        List<Instrument> instruments = new ArrayList<>();
        String query = "SELECT i.*, c.id as category_id, c.name as category_name FROM instruments i LEFT JOIN categories c ON i.category_id = c.id WHERE i.family = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            
            stmt.setString(1, family);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                InstrumentCategory category = null;
                int catId = rs.getInt("category_id");
                if (!rs.wasNull()) {
                    category = new InstrumentCategory(catId, rs.getString("category_name"));
                }
                
                instruments.add(new Instrument(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("family"),  // Use 'family' column that exists
                    rs.getString("description"),
                    "",  // No tuning_standard column in current schema
                    category
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return instruments;
    }
}

