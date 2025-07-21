package com.scalia.services;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import com.scalia.models.Instrument;
import java.util.List;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias para el servicio de gestión de instrumentos.
 * Valida la lógica de negocio para el manejo de instrumentos musicales.
 * 
 * Estas pruebas cubren funcionalidades centrales del catálogo de instrumentos
 * sin depender de la base de datos.
 */
public class InstrumentServiceTest {
    
    private InstrumentService instrumentService;
    private List<Instrument> testInstruments;
    
    @BeforeEach
    void setUp() {
        instrumentService = new InstrumentService();
        testInstruments = new ArrayList<>();
        
        // Crear instrumentos de prueba
        Instrument guitar = new Instrument();
        guitar.setId(1);
        guitar.setName("Guitarra");
        guitar.setType("String");
        testInstruments.add(guitar);
        
        Instrument piano = new Instrument();
        piano.setId(2);
        piano.setName("Piano");
        piano.setType("Keyboard");
        testInstruments.add(piano);
        
        Instrument violin = new Instrument();
        violin.setId(3);
        violin.setName("Violín");
        violin.setType("String");
        testInstruments.add(violin);
        
        Instrument flute = new Instrument();
        flute.setId(4);
        flute.setName("Flauta");
        flute.setType("Wind");
        testInstruments.add(flute);
        
        Instrument drums = new Instrument();
        drums.setId(5);
        drums.setName("Batería");
        drums.setType("Percussion");
        testInstruments.add(drums);
    }
    
    @Test
    @DisplayName("Test: Filtrar instrumentos por familia debería retornar lista correcta")
    void testFilterInstrumentsByFamily() {
        // Filtrar instrumentos de cuerda
        List<Instrument> stringInstruments = instrumentService.filterByFamily(testInstruments, "String");
        
        assertEquals(2, stringInstruments.size(), "Debería haber 2 instrumentos de cuerda");
        assertTrue(stringInstruments.stream().allMatch(i -> "String".equals(i.getType())), 
            "Todos los instrumentos filtrados deberían ser de la familia String");
        
        // Verificar que contiene los instrumentos correctos
        assertTrue(stringInstruments.stream().anyMatch(i -> "Guitarra".equals(i.getName())), 
            "Debería contener la guitarra");
        assertTrue(stringInstruments.stream().anyMatch(i -> "Violín".equals(i.getName())), 
            "Debería contener el violín");
        
        // Filtrar instrumentos de viento
        List<Instrument> windInstruments = instrumentService.filterByFamily(testInstruments, "Wind");
        assertEquals(1, windInstruments.size(), "Debería haber 1 instrumento de viento");
        assertEquals("Flauta", windInstruments.get(0).getName(), "Debería ser la flauta");
    }
    
    @Test
    @DisplayName("Test: Buscar instrumentos por nombre debería encontrar coincidencias")
    void testSearchInstrumentsByName() {
        // Búsqueda exacta
        List<Instrument> exactMatches = instrumentService.searchByName(testInstruments, "Guitarra");
        assertEquals(1, exactMatches.size(), "Debería encontrar 1 guitarra");
        assertEquals("Guitarra", exactMatches.get(0).getName(), "Debería ser la guitarra");
        
        // Búsqueda parcial (case insensitive)
        List<Instrument> partialMatches = instrumentService.searchByName(testInstruments, "gui");
        assertEquals(1, partialMatches.size(), "Debería encontrar guitarra con búsqueda parcial");
        
        // Búsqueda que no encuentra nada
        List<Instrument> noMatches = instrumentService.searchByName(testInstruments, "Saxofón");
        assertEquals(0, noMatches.size(), "No debería encontrar instrumentos inexistentes");
        
        // Búsqueda con string vacío
        List<Instrument> emptySearch = instrumentService.searchByName(testInstruments, "");
        assertEquals(testInstruments.size(), emptySearch.size(), 
            "Búsqueda vacía debería retornar todos los instrumentos");
    }
    
    @Test
    @DisplayName("Test: Obtener estadísticas de instrumentos debería calcular correctamente")
    void testGetInstrumentStatistics() {
        InstrumentStatistics stats = instrumentService.getStatistics(testInstruments);
        
        // Verificar estadísticas básicas
        assertEquals(5, stats.getTotalInstruments(), "Debería haber 5 instrumentos en total");
        assertEquals(2, stats.getInstrumentsByFamily("String"), "Debería haber 2 instrumentos de cuerda");
        assertEquals(1, stats.getInstrumentsByFamily("Keyboard"), "Debería haber 1 instrumento de teclado");
        assertEquals(1, stats.getInstrumentsByFamily("Wind"), "Debería haber 1 instrumento de viento");
        assertEquals(1, stats.getInstrumentsByFamily("Percussion"), "Debería haber 1 instrumento de percusión");
        
        // Verificar familias únicas
        assertEquals(4, stats.getUniqueFamilies().size(), "Debería haber 4 familias únicas");
        assertTrue(stats.getUniqueFamilies().contains("String"), "Debería incluir familia String");
        assertTrue(stats.getUniqueFamilies().contains("Keyboard"), "Debería incluir familia Keyboard");
        assertTrue(stats.getUniqueFamilies().contains("Wind"), "Debería incluir familia Wind");
        assertTrue(stats.getUniqueFamilies().contains("Percussion"), "Debería incluir familia Percussion");
    }
}

/**
 * Servicio para gestión de instrumentos musicales.
 * Contiene lógica de negocio para el manejo del catálogo de instrumentos.
 */
class InstrumentService {
    
    /**
     * Filtra instrumentos por familia.
     */
    public List<Instrument> filterByFamily(List<Instrument> instruments, String family) {
        if (instruments == null || family == null) {
            return new ArrayList<>();
        }
        
        return instruments.stream()
                .filter(instrument -> family.equals(instrument.getType()))
                .collect(java.util.stream.Collectors.toList());
    }
    
    /**
     * Busca instrumentos por nombre (case insensitive).
     */
    public List<Instrument> searchByName(List<Instrument> instruments, String searchTerm) {
        if (instruments == null) {
            return new ArrayList<>();
        }
        
        if (searchTerm == null || searchTerm.trim().isEmpty()) {
            return new ArrayList<>(instruments);
        }
        
        String lowerSearchTerm = searchTerm.toLowerCase();
        return instruments.stream()
                .filter(instrument -> instrument.getName().toLowerCase().contains(lowerSearchTerm))
                .collect(java.util.stream.Collectors.toList());
    }
    
    /**
     * Obtiene estadísticas de los instrumentos.
     */
    public InstrumentStatistics getStatistics(List<Instrument> instruments) {
        return new InstrumentStatistics(instruments);
    }
}

/**
 * Clase para almacenar estadísticas de instrumentos.
 */
class InstrumentStatistics {
    private final List<Instrument> instruments;
    
    public InstrumentStatistics(List<Instrument> instruments) {
        this.instruments = instruments != null ? instruments : new ArrayList<>();
    }
    
    public int getTotalInstruments() {
        return instruments.size();
    }
    
    public int getInstrumentsByFamily(String family) {
        return (int) instruments.stream()
                .filter(instrument -> family.equals(instrument.getType()))
                .count();
    }
    
    public java.util.Set<String> getUniqueFamilies() {
        return instruments.stream()
                .map(Instrument::getType)
                .collect(java.util.stream.Collectors.toSet());
    }
} 