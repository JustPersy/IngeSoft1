package com.scalia.dao;

import com.scalia.models.Chord;
import com.scalia.utils.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for Chord model
 * Handles database operations for chords
 */
public class ChordDAO {
    
    /**
     * Get all chords from database
     * @return List of all chords
     */
    public List<Chord> getAllChords() {
        List<Chord> chords = new ArrayList<>();
        String sql = "SELECT * FROM chords ORDER BY name";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                Chord chord = new Chord(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("notes"),
                    rs.getString("structure"),
                    rs.getInt("instrument_id"),
                    rs.getString("chord_type")
                );
                chords.add(chord);
            }
        } catch (SQLException e) {
            System.err.println("Error getting all chords: " + e.getMessage());
        }
        
        return chords;
    }
    
    /**
     * Get chords by instrument ID
     * @param instrumentId The instrument ID
     * @return List of chords for the instrument
     */
    public List<Chord> getChordsByInstrument(int instrumentId) {
        List<Chord> chords = new ArrayList<>();
        String sql = "SELECT * FROM chords WHERE instrument_id = ? ORDER BY name";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, instrumentId);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Chord chord = new Chord(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("notes"),
                    rs.getString("structure"),
                    rs.getInt("instrument_id"),
                    rs.getString("chord_type")
                );
                chords.add(chord);
            }
        } catch (SQLException e) {
            System.err.println("Error getting chords by instrument: " + e.getMessage());
        }
        
        return chords;
    }
    
    /**
     * Get chord by ID
     * @param id The chord ID
     * @return Chord object or null if not found
     */
    public Chord getChordById(int id) {
        String sql = "SELECT * FROM chords WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return new Chord(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("notes"),
                    rs.getString("structure"),
                    rs.getInt("instrument_id"),
                    rs.getString("chord_type")
                );
            }
        } catch (SQLException e) {
            System.err.println("Error getting chord by ID: " + e.getMessage());
        }
        
        return null;
    }
    
    /**
     * Add a new chord
     * @param chord The chord to add
     * @return true if successful, false otherwise
     */
    public boolean addChord(Chord chord) {
        String sql = "INSERT INTO chords (name, notes, structure, instrument_id, chord_type) VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, chord.getName());
            stmt.setString(2, chord.getNotes());
            stmt.setString(3, chord.getStructure());
            stmt.setInt(4, chord.getInstrumentId());
            stmt.setString(5, chord.getChordType());
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error adding chord: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Update an existing chord
     * @param chord The chord to update
     * @return true if successful, false otherwise
     */
    public boolean updateChord(Chord chord) {
        String sql = "UPDATE chords SET name = ?, notes = ?, structure = ?, instrument_id = ?, chord_type = ? WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, chord.getName());
            stmt.setString(2, chord.getNotes());
            stmt.setString(3, chord.getStructure());
            stmt.setInt(4, chord.getInstrumentId());
            stmt.setString(5, chord.getChordType());
            stmt.setInt(6, chord.getId());
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error updating chord: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Delete a chord
     * @param id The chord ID to delete
     * @return true if successful, false otherwise
     */
    public boolean deleteChord(int id) {
        String sql = "DELETE FROM chords WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error deleting chord: " + e.getMessage());
            return false;
        }
    }
}
