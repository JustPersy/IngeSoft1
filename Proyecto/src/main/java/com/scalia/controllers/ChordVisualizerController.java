package com.scalia.controllers;

import com.scalia.dao.ChordDAO;
import com.scalia.models.Chord;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import com.scalia.Main;

import java.io.IOException;
import java.util.List;

/**
 * Simple controller for the chord visualizer module.
 * Loads chords from the database and displays basic details.
 */
public class ChordVisualizerController {

    @FXML
    private ListView<Chord> chordListView;
    @FXML
    private Label chordInfoLabel;

    @FXML
    public void initialize() {
        List<Chord> chords = ChordDAO.getAllChords();
        ObservableList<Chord> observableChords = FXCollections.observableArrayList(chords);
        chordListView.setItems(observableChords);
        chordListView.getSelectionModel().selectedItemProperty().addListener((obs, oldV, newV) -> showChordInfo(newV));
    }

    private void showChordInfo(Chord chord) {
        if (chord == null) {
            chordInfoLabel.setText("");
            return;
        }
        chordInfoLabel.setText(
                chord.getName() + " - " + chord.getChordType() + "\nNotas: " + String.join(", ", chord.getNotes())
        );
    }

    @FXML
    private void handleBackToMainMenu() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MainView.fxml"));
            Parent root = loader.load();
            Stage stage = Main.getPrimaryStage();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/css/styles.css").toExternalForm());
            stage.setScene(scene);
            stage.setTitle("Scalia - Menú Principal");
            stage.setMaximized(true);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
