package com.scalia.models;

public class Instrument {
    private int id;
    private String name;
    private String type;
    private String description;
    private String tuningStandard;
    private InstrumentCategory category;

    // Constructor completo actualizado
    public Instrument(int id, String name, String type, String description, String tuningStandard, InstrumentCategory category) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.description = description;
        this.tuningStandard = tuningStandard;
        this.category = category;
    }

    // Constructor vacío
    public Instrument() {
        // Opcional: inicializa valores por defecto si es necesario
    }

    // Getters (ya los tienes)
    public int getId() { return id; }
    public String getName() { return name; }
    public String getType() { return type; }
    public String getDescription() { return description; }
    public String getTuningStandard() { return tuningStandard; }
    public InstrumentCategory getCategory() { return category; }
    public void setCategory(InstrumentCategory category) { this.category = category; }

    // --- NUEVOS: Setters ---
    public void setId(int id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setType(String type) { this.type = type; }
    public void setDescription(String description) { this.description = description; }
    public void setTuningStandard(String tuningStandard) { this.tuningStandard = tuningStandard; }
}