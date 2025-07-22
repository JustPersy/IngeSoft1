package com.scalia.models;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias para la clase modelo Instrument.
 * Valida la correcta inicialización y acceso a los datos del instrumento
 * a través de sus constructores y métodos getter/setter.
 */
public class InstrumentTest {

    @Test
    @DisplayName("Test: Constructor e inicialización de Instrumento con afinación")
    void testInstrumentConstructorWithTuning() {
        Instrument instrument = new Instrument(1, "Guitarra Eléctrica", "String", "Guitarra de 6 cuerdas con pastillas", "EADGBE");

        assertEquals(1, instrument.getId(), "El ID debe ser 1.");
        assertEquals("Guitarra Eléctrica", instrument.getName(), "El nombre debe ser 'Guitarra Eléctrica'.");
        assertEquals("String", instrument.getType(), "El tipo debe ser 'String'.");
        assertEquals("Guitarra de 6 cuerdas con pastillas", instrument.getDescription(), "La descripción debe coincidir.");
        assertEquals("EADGBE", instrument.getTuningStandard(), "La afinación debe ser 'EADGBE'.");
    }

    @Test
    @DisplayName("Test: Constructor e inicialización de Instrumento sin afinación (NULL)")
    void testInstrumentConstructorWithoutTuning() {
        Instrument instrument = new Instrument(2, "Piano", "Keyboard", "Instrumento de teclas de 88 notas", null);

        assertEquals(2, instrument.getId(), "El ID debe ser 2.");
        assertEquals("Piano", instrument.getName(), "El nombre debe ser 'Piano'.");
        assertEquals("Keyboard", instrument.getType(), "El tipo debe ser 'Keyboard'.");
        assertEquals("Instrumento de teclas de 88 notas", instrument.getDescription(), "La descripción debe coincidir.");
        assertNull(instrument.getTuningStandard(), "La afinación debe ser nula.");
    }

    @Test
    @DisplayName("Test: Métodos Setters de Instrumento")
    void testInstrumentSetters() {
        // Asumiendo que Instrument tiene un constructor vacío para este caso de prueba
        Instrument instrument = new Instrument();
        assertNotNull(instrument, "El objeto Instrument no debe ser nulo después del constructor vacío.");

        instrument.setId(3);
        instrument.setName("Batería");
        instrument.setType("Percussion");
        instrument.setDescription("Un set completo de percusión");
        instrument.setTuningStandard("Standard Drum Kit"); // O null, si no aplica

        assertEquals(3, instrument.getId(), "El ID debe ser 3 después de setearlo.");
        assertEquals("Batería", instrument.getName(), "El nombre debe ser 'Batería' después de setearlo.");
        assertEquals("Percussion", instrument.getType(), "El tipo debe ser 'Percussion' después de setearlo.");
        assertEquals("Un set completo de percusión", instrument.getDescription(), "La descripción debe coincidir después de setearla.");
        assertEquals("Standard Drum Kit", instrument.getTuningStandard(), "La afinación debe coincidir después de setearla.");
    }
}