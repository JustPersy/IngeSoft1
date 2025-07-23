package com.scalia.models;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias para la clase modelo Instrument.
 * Valida la correcta inicialización y acceso a los datos del instrumento
 * a través de sus constructores y métodos getter/setter.
 * Adaptado al esquema de DB (con type, tuningStandard, y un objeto InstrumentCategory).
 */
public class InstrumentTest {

    @Test
    @DisplayName("Test: Constructor e inicialización de Instrumento completo")
    void testInstrumentConstructorFull() {
        // Primero, crea una instancia de InstrumentCategory para usar en el Instrument
        InstrumentCategory stringCategory = new InstrumentCategory(2, "Cuerdas pulsadas"); // ID 2 para Cuerdas pulsadas según tu SQL

        // Constructor: Instrument(int id, String name, String type, String description, String tuningStandard, InstrumentCategory category)
        Instrument instrument = new Instrument(
                1,
                "Guitarra Eléctrica",
                "String", // type
                "Guitarra de 6 cuerdas con pastillas eléctricas.",
                "EADGBE", // tuningStandard
                stringCategory // Pasar el objeto InstrumentCategory
        );

        assertEquals(1, instrument.getId(), "El ID debe ser 1.");
        assertEquals("Guitarra Eléctrica", instrument.getName(), "El nombre debe ser 'Guitarra Eléctrica'.");
        assertEquals("String", instrument.getType(), "El tipo debe ser 'String'.");
        assertEquals("Guitarra de 6 cuerdas con pastillas eléctricas.", instrument.getDescription(), "La descripción debe coincidir.");
        assertEquals("EADGBE", instrument.getTuningStandard(), "La afinación debe ser 'EADGBE'.");
        assertNotNull(instrument.getCategory(), "La categoría no debe ser nula.");
        assertEquals(2, instrument.getCategory().getId(), "El ID de categoría debe ser 2.");
        assertEquals("Cuerdas pulsadas", instrument.getCategory().getName(), "El nombre de categoría debe ser 'Cuerdas pulsadas'.");
    }

    @Test
    @DisplayName("Test: Constructor e inicialización de Instrumento con campos nulos/predeterminados")
    void testInstrumentConstructorWithNulls() {
        // Instrumento sin afinación, y una categoría (Teclado)
        InstrumentCategory keyboardCategory = new InstrumentCategory(6, "Teclado"); // ID 6 para Teclado

        Instrument instrument = new Instrument(
                2,
                "Piano",
                "Keyboard", // type
                "Instrumento de teclas de 88 notas.",
                null, // tuningStandard nulo
                keyboardCategory // Pasar el objeto InstrumentCategory
        );

        assertEquals(2, instrument.getId(), "El ID debe ser 2.");
        assertEquals("Piano", instrument.getName(), "El nombre debe ser 'Piano'.");
        assertEquals("Keyboard", instrument.getType(), "El tipo debe ser 'Keyboard'.");
        assertEquals("Instrumento de teclas de 88 notas.", instrument.getDescription(), "La descripción debe coincidir.");
        assertNull(instrument.getTuningStandard(), "La afinación debe ser nula.");
        assertNotNull(instrument.getCategory(), "La categoría no debe ser nula.");
        assertEquals(6, instrument.getCategory().getId(), "El ID de categoría debe ser 6.");
        assertEquals("Teclado", instrument.getCategory().getName(), "El nombre de categoría debe ser 'Teclado'.");
    }

    @Test
    @DisplayName("Test: Métodos Setters de Instrumento")
    void testInstrumentSetters() {
        // Inicializar con un constructor base
        InstrumentCategory initialCategory = new InstrumentCategory(5, "Percusión"); // ID 5 para Percusión
        Instrument instrument = new Instrument(
                3,
                "Batería",
                "Percussion",
                "Un set completo de percusión.",
                null,
                initialCategory
        );

        // Crear una nueva categoría para setear
        InstrumentCategory newCategory = new InstrumentCategory(1, "Cuerdas frotadas"); // ID 1 para Cuerdas frotadas

        // Probar los setters
        instrument.setId(4);
        instrument.setName("Violín");
        instrument.setType("String");
        instrument.setDescription("Instrumento de cuerda frotada.");
        instrument.setTuningStandard("GDAE");
        instrument.setCategory(newCategory); // Setear el nuevo objeto InstrumentCategory

        assertEquals(4, instrument.getId(), "El ID debe ser 4 después de setearlo.");
        assertEquals("Violín", instrument.getName(), "El nombre debe ser 'Violín' después de setearlo.");
        assertEquals("String", instrument.getType(), "El tipo debe ser 'String' después de setearlo.");
        assertEquals("Instrumento de cuerda frotada.", instrument.getDescription(), "La descripción debe coincidir después de setearla.");
        assertEquals("GDAE", instrument.getTuningStandard(), "La afinación debe coincidir después de setearla.");
        assertNotNull(instrument.getCategory(), "La categoría no debe ser nula después de setearla.");
        assertEquals(1, instrument.getCategory().getId(), "El ID de la nueva categoría debe ser 1.");
        assertEquals("Cuerdas frotadas", instrument.getCategory().getName(), "El nombre de la nueva categoría debe ser 'Cuerdas frotadas'.");
    }
}