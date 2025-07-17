package com.scalia.models;
public class Instrument {
    private int id;
    private String name;
    private String type;
    private String description;
    private String tuningStandard;

    public Instrument(int id, String name, String type, String description, String tuningStandard) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.description = description;
        this.tuningStandard = tuningStandard;
    }

    // Getters
    public int getId() { return id; }
    public String getName() { return name; }
    public String getType() { return type; }
    public String getDescription() { return description; }
    public String getTuningStandard() { return tuningStandard; }
}
