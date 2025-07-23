package com.scalia.models;

public class TheoryConcept {
    private int id;
    private String name;
    private String category;
    private String description;
    private String difficultyLevel;

    public TheoryConcept() {}

    public TheoryConcept(int id, String name, String category, String description, String difficultyLevel) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.description = description;
        this.difficultyLevel = difficultyLevel;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDifficultyLevel() {
        return difficultyLevel;
    }

    public void setDifficultyLevel(String difficultyLevel) {
        this.difficultyLevel = difficultyLevel;
    }

    @Override
    public String toString() {
        return name;
    }
}
