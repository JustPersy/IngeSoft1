package com.scalia.models;

public class Instrument {
    private int id;
    private String name;
    private String type;
    private String description;
    private String tuningStandard;

    // Constructor completo (ya lo tienes)
    public Instrument(int id, String name, String type, String description, String tuningStandard) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.description = description;
        this.tuningStandard = tuningStandard;
    }

    // --- NUEVO: Constructor vacío (sin argumentos) ---
    public Instrument() {
        // Opcional: inicializa valores por defecto si es necesario
    }

    // Getters (ya los tienes)
    public int getId() { return id; }
    public String getName() { return name; }
    public String getType() { return type; }
    public String getDescription() { return description; }
    public String getTuningStandard() { return tuningStandard; }

    // --- NUEVOS: Setters ---
    public void setId(int id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setType(String type) { this.type = type; }
    public void setDescription(String description) { this.description = description; }
    public void setTuningStandard(String tuningStandard) { this.tuningStandard = tuningStandard; }
}