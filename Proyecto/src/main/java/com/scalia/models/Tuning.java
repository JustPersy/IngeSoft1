package com.scalia.models;

import java.util.List;

public class Tuning {
    private int id;
    private String name;
    private Integer instrumentId;
    private List<String> notes;
    private String description;

    public Tuning() {}

    public Tuning(int id, String name, Integer instrumentId, List<String> notes, String description) {
        this.id = id;
        this.name = name;
        this.instrumentId = instrumentId;
        this.notes = notes;
        this.description = description;
    }

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

    public Integer getInstrumentId() {
        return instrumentId;
    }

    public void setInstrumentId(Integer instrumentId) {
        this.instrumentId = instrumentId;
    }

    public List<String> getNotes() {
        return notes;
    }

    public void setNotes(List<String> notes) {
        this.notes = notes;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return name;
    }
}
