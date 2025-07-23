package com.scalia.dao;

import com.scalia.models.TheoryContent;
import com.scalia.utils.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for TheoryContent model
 * Handles database operations for theory content
 */
public class TheoryContentDAO {
    
    /**
     * Get all theory content from database
     * @return List of all theory content
     */
    public List<TheoryContent> getAllTheoryContent() {
        List<TheoryContent> contents = new ArrayList<>();
        String sql = "SELECT * FROM theory_content ORDER BY category, order_index, title";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                TheoryContent content = new TheoryContent(
                    rs.getInt("id"),
                    rs.getString("title"),
                    rs.getString("content"),
                    rs.getString("category"),
                    rs.getString("difficulty"),
                    rs.getString("examples"),
                    rs.getInt("order_index")
                );
                contents.add(content);
            }
        } catch (SQLException e) {
            System.err.println("Error getting all theory content: " + e.getMessage());
        }
        
        return contents;
    }
    
    /**
     * Get theory content by category
     * @param category The category to filter by
     * @return List of theory content for the category
     */
    public List<TheoryContent> getTheoryContentByCategory(String category) {
        List<TheoryContent> contents = new ArrayList<>();
        String sql = "SELECT * FROM theory_content WHERE category = ? ORDER BY order_index, title";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, category);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                TheoryContent content = new TheoryContent(
                    rs.getInt("id"),
                    rs.getString("title"),
                    rs.getString("content"),
                    rs.getString("category"),
                    rs.getString("difficulty"),
                    rs.getString("examples"),
                    rs.getInt("order_index")
                );
                contents.add(content);
            }
        } catch (SQLException e) {
            System.err.println("Error getting theory content by category: " + e.getMessage());
        }
        
        return contents;
    }
    
    /**
     * Get theory content by difficulty
     * @param difficulty The difficulty level
     * @return List of theory content for the difficulty
     */
    public List<TheoryContent> getTheoryContentByDifficulty(String difficulty) {
        List<TheoryContent> contents = new ArrayList<>();
        String sql = "SELECT * FROM theory_content WHERE difficulty = ? ORDER BY category, order_index, title";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, difficulty);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                TheoryContent content = new TheoryContent(
                    rs.getInt("id"),
                    rs.getString("title"),
                    rs.getString("content"),
                    rs.getString("category"),
                    rs.getString("difficulty"),
                    rs.getString("examples"),
                    rs.getInt("order_index")
                );
                contents.add(content);
            }
        } catch (SQLException e) {
            System.err.println("Error getting theory content by difficulty: " + e.getMessage());
        }
        
        return contents;
    }
    
    /**
     * Get theory content by ID
     * @param id The content ID
     * @return TheoryContent object or null if not found
     */
    public TheoryContent getTheoryContentById(int id) {
        String sql = "SELECT * FROM theory_content WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return new TheoryContent(
                    rs.getInt("id"),
                    rs.getString("title"),
                    rs.getString("content"),
                    rs.getString("category"),
                    rs.getString("difficulty"),
                    rs.getString("examples"),
                    rs.getInt("order_index")
                );
            }
        } catch (SQLException e) {
            System.err.println("Error getting theory content by ID: " + e.getMessage());
        }
        
        return null;
    }
    
    /**
     * Add new theory content
     * @param content The theory content to add
     * @return true if successful, false otherwise
     */
    public boolean addTheoryContent(TheoryContent content) {
        String sql = "INSERT INTO theory_content (title, content, category, difficulty, examples, order_index) VALUES (?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, content.getTitle());
            stmt.setString(2, content.getContent());
            stmt.setString(3, content.getCategory());
            stmt.setString(4, content.getDifficulty());
            stmt.setString(5, content.getExamples());
            stmt.setInt(6, content.getOrderIndex());
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error adding theory content: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Update existing theory content
     * @param content The theory content to update
     * @return true if successful, false otherwise
     */
    public boolean updateTheoryContent(TheoryContent content) {
        String sql = "UPDATE theory_content SET title = ?, content = ?, category = ?, difficulty = ?, examples = ?, order_index = ? WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, content.getTitle());
            stmt.setString(2, content.getContent());
            stmt.setString(3, content.getCategory());
            stmt.setString(4, content.getDifficulty());
            stmt.setString(5, content.getExamples());
            stmt.setInt(6, content.getOrderIndex());
            stmt.setInt(7, content.getId());
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error updating theory content: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Delete theory content
     * @param id The content ID to delete
     * @return true if successful, false otherwise
     */
    public boolean deleteTheoryContent(int id) {
        String sql = "DELETE FROM theory_content WHERE id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error deleting theory content: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Get all available categories
     * @return List of distinct categories
     */
    public List<String> getAllCategories() {
        List<String> categories = new ArrayList<>();
        String sql = "SELECT DISTINCT category FROM theory_content ORDER BY category";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                categories.add(rs.getString("category"));
            }
        } catch (SQLException e) {
            System.err.println("Error getting categories: " + e.getMessage());
        }
        
        return categories;
    }
}
