package com.scalia.models;

/**
 * Model class for tuning presets
 * Represents different tuning configurations for instruments
 */
public class TuningPreset {
    private int id;
    private String name;
    private int instrumentId;
    private String tuningNotes;
    private String description;
    private boolean isStandard;
    
    // Constructors
    public TuningPreset() {}
    
    public TuningPreset(int id, String name, int instrumentId, String tuningNotes, String description, boolean isStandard) {
        this.id = id;
        this.name = name;
        this.instrumentId = instrumentId;
        this.tuningNotes = tuningNotes;
        this.description = description;
        this.isStandard = isStandard;
    }
    
    // Getters and Setters
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public int getInstrumentId() {
        return instrumentId;
    }
    
    public void setInstrumentId(int instrumentId) {
        this.instrumentId = instrumentId;
    }
    
    public String getTuningNotes() {
        return tuningNotes;
    }
    
    public void setTuningNotes(String tuningNotes) {
        this.tuningNotes = tuningNotes;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public boolean isStandard() {
        return isStandard;
    }
    
    public void setStandard(boolean standard) {
        isStandard = standard;
    }
    
    @Override
    public String toString() {
        return "TuningPreset{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", instrumentId=" + instrumentId +
                ", tuningNotes='" + tuningNotes + '\'' +
                ", description='" + description + '\'' +
                ", isStandard=" + isStandard +
                '}';
    }
}
