package com.scalia.models;

import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Class for storing instrument statistics.
 * Provides analytical data about instrument collections.
 */
public class InstrumentStatistics {
    private final List<Instrument> instruments;
    
    /**
     * Constructor for InstrumentStatistics
     * @param instruments List of instruments to analyze
     */
    public InstrumentStatistics(List<Instrument> instruments) {
        this.instruments = instruments != null ? instruments : new ArrayList<>();
    }
    
    /**
     * Get the total number of instruments
     * @return Total count of instruments
     */
    public int getTotalInstruments() {
        return instruments.size();
    }
    
    /**
     * Get the count of instruments by family
     * @param family The instrument family to count
     * @return Number of instruments in the specified family
     */
    public int getInstrumentsByFamily(String family) {
        return (int) instruments.stream()
                .filter(instrument -> family.equals(instrument.getType()))
                .count();
    }
    
    /**
     * Get all unique instrument families
     * @return Set of unique family names
     */
    public Set<String> getUniqueFamilies() {
        return instruments.stream()
                .map(Instrument::getType)
                .collect(Collectors.toSet());
    }
}
