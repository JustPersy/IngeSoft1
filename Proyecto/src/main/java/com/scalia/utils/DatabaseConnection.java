package com.scalia.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Database connection utility for Scalia application
 * Handles MySQL database connections and basic operations
 */
public class DatabaseConnection {
    
    // Database configuration
    private static final String URL = "jdbc:mysql://localhost:3306/scalia_db?useUnicode=true&characterEncoding=UTF-8";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "1234"; // Set your MySQL password here
    
    private static Connection connection = null;
    
    /**
     * Get database connection
     * @return Connection object
     * @throws SQLException if connection fails
     */
    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            try {
                System.out.println("USANDO CONFIGURACIÓN CORRECTA DE DATABASECONNECTION");
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
                System.out.println("Database connected successfully!");
            } catch (ClassNotFoundException e) {
                throw new SQLException("MySQL JDBC Driver not found", e);
            }
        }
        return connection;
    }
    
    /**
     * Close database connection
     */
    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Database connection closed.");
            } catch (SQLException e) {
                System.err.println("Error closing database connection: " + e.getMessage());
            }
        }
    }
    
    /**
     * Test database connection
     * @return true if connection successful, false otherwise
     */
    public static boolean testConnection() {
        try {
            Connection conn = getConnection();
            return conn != null && !conn.isClosed();
        } catch (SQLException e) {
            System.err.println("Database connection test failed: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Execute a query and return ResultSet
     * @param sql SQL query to execute
     * @param params Parameters for prepared statement
     * @return ResultSet with query results
     * @throws SQLException if query execution fails
     */
    public static ResultSet executeQuery(String sql, Object... params) throws SQLException {
        Connection conn = getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql);
        
        for (int i = 0; i < params.length; i++) {
            stmt.setObject(i + 1, params[i]);
        }
        
        return stmt.executeQuery();
    }
    
    /**
     * Execute an update (INSERT, UPDATE, DELETE) statement
     * @param sql SQL statement to execute
     * @param params Parameters for prepared statement
     * @return Number of affected rows
     * @throws SQLException if statement execution fails
     */
    public static int executeUpdate(String sql, Object... params) throws SQLException {
        Connection conn = getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql);
        
        for (int i = 0; i < params.length; i++) {
            stmt.setObject(i + 1, params[i]);
        }
        
        return stmt.executeUpdate();
    }
    
    /**
     * Execute an INSERT statement and return the generated key
     * @param sql SQL INSERT statement
     * @param params Parameters for prepared statement
     * @return Generated key (usually ID)
     * @throws SQLException if statement execution fails
     */
    public static int executeInsert(String sql, Object... params) throws SQLException {
        Connection conn = getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        
        for (int i = 0; i < params.length; i++) {
            stmt.setObject(i + 1, params[i]);
        }
        
        stmt.executeUpdate();
        
        ResultSet rs = stmt.getGeneratedKeys();
        if (rs.next()) {
            return rs.getInt(1);
        }
        
        return -1;
    }
} 