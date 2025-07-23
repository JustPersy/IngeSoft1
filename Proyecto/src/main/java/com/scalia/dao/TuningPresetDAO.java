package com.scalia.dao;

import com.scalia.models.TuningPreset;
import com.scalia.utils.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for TuningPreset model
 * Handles database operations for tuning presets
 */
public class TuningPresetDAO {
    
    /**
     * Get all tuning presets from database
     * @return List of all tuning presets
     */
    public List<TuningPreset> getAllTuningPresets() {
        List<TuningPreset> presets = new ArrayList<>();
        String sql = "SELECT * FROM tuning_presets ORDER BY instrument_id, is_standard DESC, name";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                TuningPreset preset = new TuningPreset(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getInt("instrument_id"),
                    rs.getString("tuning_notes"),
                    rs.getString("description"),
                    rs.getBoolean("is_standard")
                );
                presets.add(preset);
            }
        } catch (SQLException e) {
            System.err.println("Error getting all tuning presets: " + e.getMessage());
        }
        
        return presets;
    }
    
    /**
     * Get tuning presets by instrument ID
     * @param instrumentId The instrument ID
     * @return List of tuning presets for the instrument
     */
    public List<TuningPreset> getTuningPresetsByInstrument(int instrumentId) {
        List<TuningPreset> presets = new ArrayList<>();
        String sql = "SELECT * FROM tuning_presets WHERE instrument_id = ? ORDER BY is_standard DESC, name";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, instrumentId);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                TuningPreset preset = new TuningPreset(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getInt("instrument_id"),
                    rs.getString("tuning_notes"),
                    rs.getString("description"),
                    rs.getBoolean("is_standard")
                );
                presets.add(preset);
            }
        } catch (SQLException e) {
            System.err.println("Error getting tuning presets by instrument: " + e.getMessage());
        }
        
        return presets;
    }
    
    /**
     * Get tuning preset by ID
     * @param id The tuning preset ID
     * @return TuningPreset object or null if not found
     */
    public TuningPreset getTuningPresetById(int id) {
        String sql = "SELECT * FROM tuning_presets WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return new TuningPreset(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getInt("instrument_id"),
                    rs.getString("tuning_notes"),
                    rs.getString("description"),
                    rs.getBoolean("is_standard")
                );
            }
        } catch (SQLException e) {
            System.err.println("Error getting tuning preset by ID: " + e.getMessage());
        }
        
        return null;
    }
    
    /**
     * Add a new tuning preset
     * @param preset The tuning preset to add
     * @return true if successful, false otherwise
     */
    public boolean addTuningPreset(TuningPreset preset) {
        String sql = "INSERT INTO tuning_presets (name, instrument_id, tuning_notes, description, is_standard) VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, preset.getName());
            stmt.setInt(2, preset.getInstrumentId());
            stmt.setString(3, preset.getTuningNotes());
            stmt.setString(4, preset.getDescription());
            stmt.setBoolean(5, preset.isStandard());
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error adding tuning preset: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Update an existing tuning preset
     * @param preset The tuning preset to update
     * @return true if successful, false otherwise
     */
    public boolean updateTuningPreset(TuningPreset preset) {
        String sql = "UPDATE tuning_presets SET name = ?, instrument_id = ?, tuning_notes = ?, description = ?, is_standard = ? WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, preset.getName());
            stmt.setInt(2, preset.getInstrumentId());
            stmt.setString(3, preset.getTuningNotes());
            stmt.setString(4, preset.getDescription());
            stmt.setBoolean(5, preset.isStandard());
            stmt.setInt(6, preset.getId());
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error updating tuning preset: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Delete a tuning preset
     * @param id The tuning preset ID to delete
     * @return true if successful, false otherwise
     */
    public boolean deleteTuningPreset(int id) {
        String sql = "DELETE FROM tuning_presets WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error deleting tuning preset: " + e.getMessage());
            return false;
        }
    }
}
