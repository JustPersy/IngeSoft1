package com.scalia.models;

import java.util.List;

public class Chord {
    private int id;
    private String name;
    private String rootNote;
    private String chordType;
    private List<String> notes;
    private String fingering;

    public Chord() {}

    public Chord(int id, String name, String rootNote, String chordType, List<String> notes, String fingering) {
        this.id = id;
        this.name = name;
        this.rootNote = rootNote;
        this.chordType = chordType;
        this.notes = notes;
        this.fingering = fingering;
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

    public String getRootNote() {
        return rootNote;
    }

    public void setRootNote(String rootNote) {
        this.rootNote = rootNote;
    }

    public String getChordType() {
        return chordType;
    }

    public void setChordType(String chordType) {
        this.chordType = chordType;
    }

    public List<String> getNotes() {
        return notes;
    }

    public void setNotes(List<String> notes) {
        this.notes = notes;
    }

    public String getFingering() {
        return fingering;
    }

    public void setFingering(String fingering) {
        this.fingering = fingering;
    }

    @Override
    public String toString() {
        return name;
    }
}
