package com.scalia.models;

/**
 * Model class for music theory lessons and content
 * Represents educational content for music theory
 */
public class TheoryContent {
    private int id;
    private String title;
    private String content;
    private String category; // scales, intervals, chords, etc.
    private String difficulty; // beginner, intermediate, advanced
    private String examples;
    private int orderIndex;
    
    // Constructors
    public TheoryContent() {}
    
    public TheoryContent(int id, String title, String content, String category, String difficulty, String examples, int orderIndex) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.category = category;
        this.difficulty = difficulty;
        this.examples = examples;
        this.orderIndex = orderIndex;
    }
    
    // Getters and Setters
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getContent() {
        return content;
    }
    
    public void setContent(String content) {
        this.content = content;
    }
    
    public String getCategory() {
        return category;
    }
    
    public void setCategory(String category) {
        this.category = category;
    }
    
    public String getDifficulty() {
        return difficulty;
    }
    
    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }
    
    public String getExamples() {
        return examples;
    }
    
    public void setExamples(String examples) {
        this.examples = examples;
    }
    
    public int getOrderIndex() {
        return orderIndex;
    }
    
    public void setOrderIndex(int orderIndex) {
        this.orderIndex = orderIndex;
    }
    
    @Override
    public String toString() {
        return "TheoryContent{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", category='" + category + '\'' +
                ", difficulty='" + difficulty + '\'' +
                ", examples='" + examples + '\'' +
                ", orderIndex=" + orderIndex +
                '}';
    }
}
