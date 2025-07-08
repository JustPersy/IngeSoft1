package com.scalia.dao;

import com.scalia.models.User;
import com.scalia.utils.DatabaseConnection;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for User operations
 */
public class UserDAO {
    
    /**
     * Create a new user
     * @param user User object to create
     * @return true if successful, false otherwise
     */
    public boolean createUser(User user) {
        String sql = "INSERT INTO users (username, email, password, first_name, last_name, created_at, updated_at) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        try {
            LocalDateTime now = LocalDateTime.now();
            int userId = DatabaseConnection.executeInsert(sql, 
                user.getUsername(), 
                user.getEmail(), 
                user.getPassword(), 
                user.getFirstName(), 
                user.getLastName(),
                now,
                now
            );
            
            if (userId > 0) {
                user.setId(userId);
                user.setCreatedAt(now);
                user.setUpdatedAt(now);
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Error creating user: " + e.getMessage());
        }
        
        return false;
    }
    
    /**
     * Find user by username
     * @param username Username to search for
     * @return User object if found, null otherwise
     */
    public User findByUsername(String username) {
        String sql = "SELECT * FROM users WHERE username = ?";
        
        try (ResultSet rs = DatabaseConnection.executeQuery(sql, username)) {
            if (rs.next()) {
                return mapResultSetToUser(rs);
            }
        } catch (SQLException e) {
            System.err.println("Error finding user by username: " + e.getMessage());
        }
        
        return null;
    }
    
    /**
     * Find user by email
     * @param email Email to search for
     * @return User object if found, null otherwise
     */
    public User findByEmail(String email) {
        String sql = "SELECT * FROM users WHERE email = ?";
        
        try (ResultSet rs = DatabaseConnection.executeQuery(sql, email)) {
            if (rs.next()) {
                return mapResultSetToUser(rs);
            }
        } catch (SQLException e) {
            System.err.println("Error finding user by email: " + e.getMessage());
        }
        
        return null;
    }
    
    /**
     * Authenticate user with username and password
     * @param username Username
     * @param password Password
     * @return User object if authentication successful, null otherwise
     */
    public User authenticateUser(String username, String password) {
        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
        
        try (ResultSet rs = DatabaseConnection.executeQuery(sql, username, password)) {
            if (rs.next()) {
                return mapResultSetToUser(rs);
            }
        } catch (SQLException e) {
            System.err.println("Error authenticating user: " + e.getMessage());
        }
        
        return null;
    }
    
    /**
     * Check if username exists
     * @param username Username to check
     * @return true if username exists, false otherwise
     */
    public boolean usernameExists(String username) {
        return findByUsername(username) != null;
    }
    
    /**
     * Check if email exists
     * @param email Email to check
     * @return true if email exists, false otherwise
     */
    public boolean emailExists(String email) {
        return findByEmail(email) != null;
    }
    
    /**
     * Get all users
     * @return List of all users
     */
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users ORDER BY created_at DESC";
        
        try (ResultSet rs = DatabaseConnection.executeQuery(sql)) {
            while (rs.next()) {
                users.add(mapResultSetToUser(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error getting all users: " + e.getMessage());
        }
        
        return users;
    }
    
    /**
     * Update user information
     * @param user User object with updated information
     * @return true if successful, false otherwise
     */
    public boolean updateUser(User user) {
        String sql = "UPDATE users SET first_name = ?, last_name = ?, email = ?, updated_at = ? WHERE id = ?";
        
        try {
            LocalDateTime now = LocalDateTime.now();
            int rowsAffected = DatabaseConnection.executeUpdate(sql, 
                user.getFirstName(), 
                user.getLastName(), 
                user.getEmail(), 
                now,
                user.getId()
            );
            
            if (rowsAffected > 0) {
                user.setUpdatedAt(now);
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Error updating user: " + e.getMessage());
        }
        
        return false;
    }
    
    /**
     * Delete user by ID
     * @param userId User ID to delete
     * @return true if successful, false otherwise
     */
    public boolean deleteUser(int userId) {
        String sql = "DELETE FROM users WHERE id = ?";
        
        try {
            int rowsAffected = DatabaseConnection.executeUpdate(sql, userId);
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error deleting user: " + e.getMessage());
        }
        
        return false;
    }
    
    /**
     * Map ResultSet to User object
     * @param rs ResultSet containing user data
     * @return User object
     * @throws SQLException if mapping fails
     */
    private User mapResultSetToUser(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(rs.getInt("id"));
        user.setUsername(rs.getString("username"));
        user.setEmail(rs.getString("email"));
        user.setPassword(rs.getString("password"));
        user.setFirstName(rs.getString("first_name"));
        user.setLastName(rs.getString("last_name"));
        user.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        user.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
        return user;
    }
} 