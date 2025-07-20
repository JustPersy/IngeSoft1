package com.scalia.utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.sql.Connection;
import java.sql.SQLException;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias para la clase DatabaseConnection.
 * Valida la capacidad de la aplicación para establecer y cerrar conexiones a la base de datos.
 *
 * Estas pruebas dependen de que la configuración de la base de datos en DatabaseConnection.java
 * sea correcta y que la base de datos MySQL esté en ejecución y accesible.
 */
public class DatabaseConnectionTest {

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

}