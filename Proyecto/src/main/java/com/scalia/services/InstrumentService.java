package com.scalia.services;

import com.scalia.dao.InstrumentDAO;
import com.scalia.dao.InstrumentCategoryDAO;
import com.scalia.models.Instrument;
import com.scalia.models.InstrumentCategory;
import com.scalia.models.InstrumentStatistics;

import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * Service class for instrument-related operations
 * Provides business logic for instrument management
 */
public class InstrumentService {
    
    /**
     * Get all instruments with their categories
     * @return List of instruments
     */
    public List<Instrument> getAllInstruments() {
        return InstrumentDAO.getAllInstruments();
    }
    
    /**
     * Get instruments by category
     * @param categoryId The category ID to filter by
     * @return List of instruments in the category
     */
    public List<Instrument> getInstrumentsByCategory(int categoryId) {
        return InstrumentDAO.getInstrumentsByCategory(categoryId);
    }
    
    /**
     * Get all instrument categories
     * @return List of categories
     */
    public List<InstrumentCategory> getAllCategories() {
        return InstrumentCategoryDAO.getAllCategories();
    }
    
    /**
     * Search instruments by name
     * @param searchTerm The search term
     * @return List of matching instruments
     */
    public List<Instrument> searchInstruments(String searchTerm) {
        return InstrumentDAO.searchInstruments(searchTerm);
    }
    
    /**
     * Get instrument by ID
     * @param id The instrument ID
     * @return The instrument or null if not found
     */
    public Instrument getInstrumentById(int id) {
        return InstrumentDAO.getInstrumentById(id);
    }
    
    /**
     * Check if an instrument exists
     * @param id The instrument ID
     * @return true if instrument exists, false otherwise
     */
    public boolean instrumentExists(int id) {
        return getInstrumentById(id) != null;
    }
    
    /**
     * Get instruments count by category
     * @param categoryId The category ID
     * @return Number of instruments in the category
     */
    public int getInstrumentCountByCategory(int categoryId) {
        return getInstrumentsByCategory(categoryId).size();
    }
    
    /**
     * Get total instruments count
     * @return Total number of instruments
     */
    public int getTotalInstrumentCount() {
        return getAllInstruments().size();
    }
    
    /**
     * Get category by ID
     * @param id The category ID
     * @return The category or null if not found
     */
    public InstrumentCategory getCategoryById(int id) {
        return InstrumentCategoryDAO.getCategoryById(id);
    }
    
    /**
     * Validate instrument data
     * @param instrument The instrument to validate
     * @return true if valid, false otherwise
     */
    public boolean isValidInstrument(Instrument instrument) {
        if (instrument == null) {
            return false;
        }
        
        // Check required fields
        if (instrument.getName() == null || instrument.getName().trim().isEmpty()) {
            return false;
        }
        
        if (instrument.getType() == null || instrument.getType().trim().isEmpty()) {
            return false;
        }
        
        return true;
    }
    
    /**
     * Get instruments by family
     * @param family The instrument family
     * @return List of instruments in the family
     */
    public List<Instrument> getInstrumentsByFamily(String family) {
        return InstrumentDAO.getInstrumentsByFamily(family);
    }
    
    /**
     * Filter instruments by family from a given list
     * @param instruments List of instruments to filter
     * @param family The instrument family to filter by
     * @return Filtered list of instruments
     */
    public List<Instrument> filterByFamily(List<Instrument> instruments, String family) {
        if (instruments == null || family == null) {
            return new ArrayList<>();
        }
        
        return instruments.stream()
                .filter(instrument -> family.equals(instrument.getType()))
                .collect(Collectors.toList());
    }
    
    /**
     * Search instruments by name from a given list (case insensitive)
     * @param instruments List of instruments to search
     * @param searchTerm The search term
     * @return List of matching instruments
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
                .collect(Collectors.toList());
    }
    
    /**
     * Get statistics for a list of instruments
     * @param instruments List of instruments to analyze
     * @return InstrumentStatistics object with analytical data
     */
    public InstrumentStatistics getStatistics(List<Instrument> instruments) {
        return new InstrumentStatistics(instruments);
    }
}
