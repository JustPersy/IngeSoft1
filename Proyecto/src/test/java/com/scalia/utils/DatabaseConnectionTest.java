package com.scalia.utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias para la clase DatabaseConnection.
 * Valida la capacidad de la aplicación para establecer y cerrar conexiones a la base de datos,
 * así como ejecutar consultas básicas.
 *
 * Estas pruebas dependen de que la configuración de la base de datos en DatabaseConnection.java
 * sea correcta y que la base de datos MySQL esté en ejecución y accesible.
 */
public class DatabaseConnectionTest {

    @AfterEach
    void tearDown() {
        // Cerrar la conexión después de cada prueba
        DatabaseConnection.closeConnection();
    }

    @Test
    @DisplayName("Test: getConnection debería devolver una conexión válida y activa")
    void testGetConnectionValid() {
        Connection connection = null;
        try {
            connection = DatabaseConnection.getConnection();
            assertNotNull(connection, "La conexión a la base de datos no debe ser nula.");

            // Prueba que la conexión es válida durante al menos 5 segundos
            assertTrue(connection.isValid(5), "La conexión a la base de datos debe ser válida y activa.");
        } catch (SQLException e) {
            // Si hay una SQLException, la prueba falla. Esto indica un problema de conexión real.
            fail("Falló la obtención de una conexión válida a la base de datos: " + e.getMessage());
        } finally {
            // Asegurarse de cerrar la conexión para liberar recursos
            if (connection != null) {
                try {
                    connection.close();
                    assertTrue(connection.isClosed(), "La conexión debería cerrarse después de su uso.");
                } catch (SQLException e) {
                    // Si falla al cerrar, también es un error
                    fail("Error al intentar cerrar la conexión a la base de datos: " + e.getMessage());
                }
            }
        }
    }

    @Test
    @DisplayName("Test: testConnection debería devolver true para una conexión válida")
    void testTestConnection() {
        boolean connectionResult = DatabaseConnection.testConnection();
        assertTrue(connectionResult, "testConnection debería devolver true para una conexión válida");
    }

    @Test
    @DisplayName("Test: executeQuery debería ejecutar consultas SELECT correctamente")
    void testExecuteQuery() {
        try {
            // Ejecutar una consulta simple para verificar que funciona
            ResultSet rs = DatabaseConnection.executeQuery("SELECT 1 as test_value");
            
            assertNotNull(rs, "El ResultSet no debe ser nulo");
            assertTrue(rs.next(), "El ResultSet debe tener al menos una fila");
            assertEquals(1, rs.getInt("test_value"), "El valor de la consulta debe ser 1");
            
        } catch (SQLException e) {
            fail("Error al ejecutar consulta de prueba: " + e.getMessage());
        }
    }

    @Test
    @DisplayName("Test: executeQuery con parámetros debería funcionar correctamente")
    void testExecuteQueryWithParameters() {
        try {
            // Crear una tabla temporal para la prueba
            Connection conn = DatabaseConnection.getConnection();
            Statement stmt = conn.createStatement();
            stmt.execute("CREATE TEMPORARY TABLE test_table (id INT, name VARCHAR(50))");
            stmt.execute("INSERT INTO test_table VALUES (1, 'Test1'), (2, 'Test2')");
            
            // Ejecutar consulta con parámetros
            ResultSet rs = DatabaseConnection.executeQuery("SELECT * FROM test_table WHERE id = ?", 1);
            
            assertNotNull(rs, "El ResultSet no debe ser nulo");
            assertTrue(rs.next(), "El ResultSet debe tener una fila");
            assertEquals(1, rs.getInt("id"), "El ID debe ser 1");
            assertEquals("Test1", rs.getString("name"), "El nombre debe ser 'Test1'");
            assertFalse(rs.next(), "No debe haber más filas");
            
            // Limpiar
            stmt.execute("DROP TEMPORARY TABLE test_table");
            
        } catch (SQLException e) {
            fail("Error al ejecutar consulta con parámetros: " + e.getMessage());
        }
    }

    @Test
    @DisplayName("Test: executeUpdate debería ejecutar operaciones INSERT/UPDATE/DELETE")
    void testExecuteUpdate() {
        try {
            // Crear una tabla temporal para la prueba
            Connection conn = DatabaseConnection.getConnection();
            Statement stmt = conn.createStatement();
            stmt.execute("CREATE TEMPORARY TABLE test_update (id INT, name VARCHAR(50))");
            
            // Probar INSERT
            int insertResult = DatabaseConnection.executeUpdate(
                "INSERT INTO test_update (id, name) VALUES (?, ?)", 1, "TestName"
            );
            assertEquals(1, insertResult, "INSERT debería afectar 1 fila");
            
            // Probar UPDATE
            int updateResult = DatabaseConnection.executeUpdate(
                "UPDATE test_update SET name = ? WHERE id = ?", "UpdatedName", 1
            );
            assertEquals(1, updateResult, "UPDATE debería afectar 1 fila");
            
            // Verificar que el UPDATE funcionó
            ResultSet rs = DatabaseConnection.executeQuery("SELECT name FROM test_update WHERE id = ?", 1);
            assertTrue(rs.next(), "Debe haber una fila después del UPDATE");
            assertEquals("UpdatedName", rs.getString("name"), "El nombre debe haberse actualizado");
            
            // Probar DELETE
            int deleteResult = DatabaseConnection.executeUpdate("DELETE FROM test_update WHERE id = ?", 1);
            assertEquals(1, deleteResult, "DELETE debería afectar 1 fila");
            
            // Verificar que el DELETE funcionó
            rs = DatabaseConnection.executeQuery("SELECT COUNT(*) as count FROM test_update");
            assertTrue(rs.next(), "Debe haber una fila de resultado");
            assertEquals(0, rs.getInt("count"), "La tabla debe estar vacía después del DELETE");
            
            // Limpiar
            stmt.execute("DROP TEMPORARY TABLE test_update");
            
        } catch (SQLException e) {
            fail("Error al ejecutar operaciones de actualización: " + e.getMessage());
        }
    }

    @Test
    @DisplayName("Test: executeInsert debería devolver el ID generado")
    void testExecuteInsert() {
        try {
            // Crear una tabla temporal con AUTO_INCREMENT
            Connection conn = DatabaseConnection.getConnection();
            Statement stmt = conn.createStatement();
            stmt.execute("CREATE TEMPORARY TABLE test_insert (id INT AUTO_INCREMENT PRIMARY KEY, name VARCHAR(50))");
            
            // Probar INSERT con ID generado
            int generatedId = DatabaseConnection.executeInsert(
                "INSERT INTO test_insert (name) VALUES (?)", "TestInsert"
            );
            
            assertTrue(generatedId > 0, "El ID generado debe ser mayor que 0");
            
            // Verificar que el registro se insertó correctamente
            ResultSet rs = DatabaseConnection.executeQuery("SELECT * FROM test_insert WHERE id = ?", generatedId);
            assertTrue(rs.next(), "Debe haber una fila con el ID generado");
            assertEquals("TestInsert", rs.getString("name"), "El nombre debe coincidir");
            
            // Limpiar
            stmt.execute("DROP TEMPORARY TABLE test_insert");
            
        } catch (SQLException e) {
            fail("Error al ejecutar INSERT con ID generado: " + e.getMessage());
        }
    }

    @Test
    @DisplayName("Test: closeConnection debería cerrar la conexión correctamente")
    void testCloseConnection() {
        try {
            // Obtener una conexión
            Connection connection = DatabaseConnection.getConnection();
            assertNotNull(connection, "La conexión debe existir");
            assertFalse(connection.isClosed(), "La conexión no debe estar cerrada inicialmente");
            
            // Cerrar la conexión
            DatabaseConnection.closeConnection();
            
            // Verificar que la conexión se cerró
            assertTrue(connection.isClosed(), "La conexión debe estar cerrada después de closeConnection");
            
        } catch (SQLException e) {
            fail("Error al probar el cierre de conexión: " + e.getMessage());
        }
    }
}