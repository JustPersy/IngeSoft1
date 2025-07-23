package com.scalia.dao;

import com.scalia.models.Chord;
import com.scalia.utils.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ChordDAO {
    public static List<Chord> getAllChords() {
        List<Chord> list = new ArrayList<>();
        String sql = "SELECT * FROM chords";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(mapRow(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    private static Chord mapRow(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String name = rs.getString("name");
        String rootNote = rs.getString("root_note");
        String chordType = rs.getString("chord_type");
        String notesJson = rs.getString("notes");
        List<String> notes = Arrays.asList(notesJson.replace("[", "").replace("]", "").replace("\"", "").split(",\s*"));
        String fingering = rs.getString("fingering");
        return new Chord(id, name, rootNote, chordType, notes, fingering);
    }
}
