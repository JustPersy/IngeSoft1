package com.scalia.models;

/**
 * Model class for musical chords
 * Represents a chord with its notes and structure
 */
public class Chord {
    private int id;
    private String name;
    private String notes;
    private String structure;
    private int instrumentId;
    private String chordType;
    
    // Constructors
    public Chord() {}
    
    public Chord(int id, String name, String notes, String structure, int instrumentId, String chordType) {
        this.id = id;
        this.name = name;
        this.notes = notes;
        this.structure = structure;
        this.instrumentId = instrumentId;
        this.chordType = chordType;
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
    
    public String getNotes() {
        return notes;
    }
    
    public void setNotes(String notes) {
        this.notes = notes;
    }
    
    public String getStructure() {
        return structure;
    }
    
    public void setStructure(String structure) {
        this.structure = structure;
    }
    
    public int getInstrumentId() {
        return instrumentId;
    }
    
    public void setInstrumentId(int instrumentId) {
        this.instrumentId = instrumentId;
    }
    
    public String getChordType() {
        return chordType;
    }
    
    public void setChordType(String chordType) {
        this.chordType = chordType;
    }
    
    @Override
    public String toString() {
        return "Chord{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", notes='" + notes + '\'' +
                ", structure='" + structure + '\'' +
                ", instrumentId=" + instrumentId +
                ", chordType='" + chordType + '\'' +
                '}';
    }
}
