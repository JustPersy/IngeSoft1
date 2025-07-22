package com.scalia.dao;

import com.scalia.models.Instrument;
import com.scalia.utils.DatabaseConnection; 
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias para la clase InstrumentDAO.
 * Valida la funcionalidad de acceso a datos para los instrumentos.
 *
 * Estas pruebas interactúan directamente con la base de datos MySQL.
 * Es crucial que la base de datos esté accesible y configurada correctamente
 * en DatabaseConnection para que estas pruebas pasen.
 * Se limpia y se insertan datos de prueba antes de cada test.
 */
public class InstrumentDAOTest {

    @BeforeEach
    void setUp() {
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement()) {

            // Eliminar datos existentes y reiniciar AUTO_INCREMENT
            stmt.execute("DELETE FROM instruments");
            stmt.execute("ALTER TABLE instruments AUTO_INCREMENT = 1");

            // Insertar solo los datos de prueba mínimos necesarios para esta suite
            stmt.execute("INSERT INTO instruments (name, type, description, tuning_standard) VALUES ('Guitarra Acústica', 'String', 'Guitarra acústica de 6 cuerdas', 'EADGBE')");
            stmt.execute("INSERT INTO instruments (name, type, description, tuning_standard) VALUES ('Piano', 'Keyboard', 'Piano de 88 teclas', NULL)");
        } catch (SQLException e) {
            // Si hay un error aquí, es un problema serio de configuración de DB o conexión
            fail("Error al preparar la base de datos para la prueba (setUp): " + e.getMessage());
        }
    }

    @AfterEach
    void tearDown() {
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute("DELETE FROM instruments");
            stmt.execute("ALTER TABLE instruments AUTO_INCREMENT = 1");
        } catch (SQLException e) {
            System.err.println("Advertencia: Error durante la limpieza post-prueba (tearDown): " + e.getMessage());
        }
    }

    @Test
    @DisplayName("Test: getAllInstruments debería devolver la lista correcta de instrumentos")
    void testGetAllInstruments() {
        System.out.println("Ejecutando testGetAllInstruments...");
        List<Instrument> instruments = InstrumentDAO.getAllInstruments();

        assertNotNull(instruments, "La lista de instrumentos no debe ser nula.");
        assertEquals(2, instruments.size(), "Debe devolver 2 instrumentos de prueba insertados en setUp.");

        // Verificar el primer instrumento (Guitarra Acústica)
        // Asumimos que el orden de inserción es el orden de recuperación o buscamos por nombre
        Instrument guitar = instruments.stream()
                .filter(i -> "Guitarra Acústica".equals(i.getName()))
                .findFirst()
                .orElse(null);
        assertNotNull(guitar, "Guitarra Acústica debe estar en la lista.");
        assertEquals("String", guitar.getType());
        assertEquals("Guitarra acústica de 6 cuerdas", guitar.getDescription());
        assertEquals("EADGBE", guitar.getTuningStandard());

        // Verificar el segundo instrumento (Piano)
        Instrument piano = instruments.stream()
                .filter(i -> "Piano".equals(i.getName()))
                .findFirst()
                .orElse(null);
        assertNotNull(piano, "Piano debe estar en la lista.");
        assertEquals("Keyboard", piano.getType());
        assertEquals("Piano de 88 teclas", piano.getDescription());
        assertNull(piano.getTuningStandard(), "El piano no debe tener un estándar de afinación.");
    }
}