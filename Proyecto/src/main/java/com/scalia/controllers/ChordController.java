package com.scalia.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import com.scalia.Main;
import com.scalia.dao.ChordDAO;
import com.scalia.dao.InstrumentDAO;
import com.scalia.models.Chord;
import com.scalia.models.Instrument;

import java.io.IOException;
import java.util.List;

/**
 * Controller for the Chord Visualizer View
 * Handles chord visualization and selection
 */
public class ChordController {

    @FXML
    private ComboBox<Instrument> instrumentComboBox;

    @FXML
    private ListView<Chord> chordListView;

    @FXML
    private TextArea chordDetailsArea;

    @FXML
    private GridPane chordVisualizationPane;

    @FXML
    private Button playChordButton;

    @FXML
    private Button backButton;

    @FXML
    private Label chordNameLabel;

    @FXML
    private VBox chordContainer;

    private ChordDAO chordDAO;
    private Chord selectedChord;

    /**
     * Initialize the controller
     */
    @FXML
    public void initialize() {
        chordDAO = new ChordDAO();
        
        // Load instruments
        loadInstruments();
        
        // Set up event handlers
        instrumentComboBox.setOnAction(e -> loadChordsForSelectedInstrument());
        
        chordListView.getSelectionModel().selectedItemProperty().addListener(
            (observable, oldValue, newValue) -> {
                if (newValue != null) {
                    selectedChord = newValue;
                    displayChordDetails(newValue);
                    visualizeChord(newValue);
                }
            }
        );
        
        // Initialize with first instrument if available
        if (!instrumentComboBox.getItems().isEmpty()) {
            instrumentComboBox.getSelectionModel().selectFirst();
        }
    }
    
    /**
     * Load all available instruments
     */
    private void loadInstruments() {
        List<Instrument> instruments = InstrumentDAO.getAllInstruments();
        
        // Add sample instruments if database is empty
        if (instruments.isEmpty()) {
            instruments = createSampleInstruments();
        }
        
        instrumentComboBox.getItems().setAll(instruments);
    }
    
    /**
     * Load chords for the selected instrument
     */
    private void loadChordsForSelectedInstrument() {
        Instrument selectedInstrument = instrumentComboBox.getSelectionModel().getSelectedItem();
        
        if (selectedInstrument == null) return;
        
        List<Chord> chords = chordDAO.getChordsByInstrument(selectedInstrument.getId());
        
        // Add sample chords if database is empty
        if (chords.isEmpty()) {
            chords = createSampleChords(selectedInstrument.getId());
        }
        
        chordListView.getItems().setAll(chords);
        
        // Auto-select first chord
        if (!chords.isEmpty()) {
            chordListView.getSelectionModel().selectFirst();
        }
    }
    
    /**
     * Display chord details in the text area
     */
    private void displayChordDetails(Chord chord) {
        chordNameLabel.setText(chord.getName());
        
        StringBuilder details = new StringBuilder();
        details.append("ACORDE: ").append(chord.getName()).append("\n\n");
        details.append("TIPO: ").append(chord.getChordType()).append("\n\n");
        details.append("NOTAS: ").append(chord.getNotes()).append("\n\n");
        details.append("ESTRUCTURA: ").append(chord.getStructure()).append("\n\n");
        
        // Add additional info based on chord type
        if (chord.getChordType().toLowerCase().contains("mayor")) {
            details.append("CARACTERÍSTICAS:\n");
            details.append("- Sonido brillante y alegre\n");
            details.append("- Formado por tercera mayor y quinta justa\n");
            details.append("- Fundamental en música occidental\n");
        } else if (chord.getChordType().toLowerCase().contains("menor")) {
            details.append("CARACTERÍSTICAS:\n");
            details.append("- Sonido melancólico y suave\n");
            details.append("- Formado por tercera menor y quinta justa\n");
            details.append("- Muy expresivo en baladas\n");
        }
        
        chordDetailsArea.setText(details.toString());
    }
    
    /**
     * Visualize chord on the instrument
     */
    private void visualizeChord(Chord chord) {
        // Clear previous visualization
        chordVisualizationPane.getChildren().clear();
        
        Instrument instrument = instrumentComboBox.getSelectionModel().getSelectedItem();
        
        if (instrument == null) return;
        
        // Create visual representation based on instrument type
        if (instrument.getName().toLowerCase().contains("guitarra") || 
            instrument.getName().toLowerCase().contains("guitar")) {
            createGuitarChordVisualization(chord);
        } else if (instrument.getName().toLowerCase().contains("piano")) {
            createPianoChordVisualization(chord);
        } else {
            createGenericChordVisualization(chord);
        }
    }
    
    /**
     * Create guitar chord visualization
     */
    private void createGuitarChordVisualization(Chord chord) {
        // Create a simple guitar fretboard representation
        for (int string = 0; string < 6; string++) {
            for (int fret = 0; fret < 5; fret++) {
                Button fretButton = new Button();
                fretButton.setPrefSize(40, 30);
                fretButton.getStyleClass().add("fret-button");
                
                // Highlight chord notes (simplified example)
                if (isChordNote(string, fret, chord)) {
                    fretButton.getStyleClass().add("chord-note");
                    fretButton.setText("●");
                }
                
                chordVisualizationPane.add(fretButton, fret, string);
            }
        }
        
        // Add string labels
        String[] stringNames = {"E", "A", "D", "G", "B", "E"};
        for (int i = 0; i < stringNames.length; i++) {
            Label stringLabel = new Label(stringNames[i]);
            stringLabel.getStyleClass().add("string-label");
            chordVisualizationPane.add(stringLabel, 5, i);
        }
    }
    
    /**
     * Create piano chord visualization
     */
    private void createPianoChordVisualization(Chord chord) {
        // Create piano keys representation
        String[] noteNames = {"C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B"};
        
        for (int i = 0; i < 12; i++) {
            Button keyButton = new Button(noteNames[i]);
            keyButton.setPrefSize(50, 100);
            
            // Style black and white keys
            if (noteNames[i].contains("#")) {
                keyButton.getStyleClass().add("black-key");
            } else {
                keyButton.getStyleClass().add("white-key");
            }
            
            // Highlight chord notes
            if (chord.getNotes().contains(noteNames[i])) {
                keyButton.getStyleClass().add("chord-note");
            }
            
            chordVisualizationPane.add(keyButton, i, 0);
        }
    }
    
    /**
     * Create generic chord visualization
     */
    private void createGenericChordVisualization(Chord chord) {
        Label visualizationLabel = new Label("Visualización de acordes para " + 
            instrumentComboBox.getSelectionModel().getSelectedItem().getName());
        visualizationLabel.getStyleClass().add("visualization-placeholder");
        chordVisualizationPane.add(visualizationLabel, 0, 0);
    }
    
    /**
     * Check if a position represents a chord note (simplified)
     */
    private boolean isChordNote(int string, int fret, Chord chord) {
        // Simplified logic - in a real app, this would use proper music theory
        return (string + fret) % 3 == 0 && fret > 0;
    }
    
    /**
     * Play the selected chord (placeholder)
     */
    @FXML
    private void playChord() {
        if (selectedChord == null) {
            showAlert("Información", "Selecciona un acorde para reproducir.");
            return;
        }
        
        // Placeholder for audio playback
        showAlert("Reproduciendo", "Reproduciendo acorde: " + selectedChord.getName() + 
                 "\n(Funcionalidad de audio en desarrollo)");
    }
    
    /**
     * Create sample instruments for demonstration
     */
    private List<Instrument> createSampleInstruments() {
        List<Instrument> instruments = new java.util.ArrayList<>();
        instruments.add(new Instrument(1, "Guitarra Acústica", "Cuerda", "Guitarra de seis cuerdas", "E-A-D-G-B-E", null));
        instruments.add(new Instrument(2, "Piano", "Teclado", "Piano de 88 teclas", "Temperamento igual", null));
        instruments.add(new Instrument(3, "Ukulele", "Cuerda", "Instrumento de cuatro cuerdas", "G-C-E-A", null));
        return instruments;
    }
    
    /**
     * Create sample chords for demonstration
     */
    private List<Chord> createSampleChords(int instrumentId) {
        List<Chord> chords = new java.util.ArrayList<>();
        
        if (instrumentId == 1) { // Guitar
            chords.add(new Chord(1, "Do Mayor", "C-E-G", "1-3-5", instrumentId, "Mayor"));
            chords.add(new Chord(2, "Re menor", "D-F-A", "1-♭3-5", instrumentId, "Menor"));
            chords.add(new Chord(3, "Sol Mayor", "G-B-D", "1-3-5", instrumentId, "Mayor"));
            chords.add(new Chord(4, "La menor", "A-C-E", "1-♭3-5", instrumentId, "Menor"));
            chords.add(new Chord(5, "Mi Mayor", "E-G#-B", "1-3-5", instrumentId, "Mayor"));
        } else if (instrumentId == 2) { // Piano
            chords.add(new Chord(6, "Do Mayor", "C-E-G", "1-3-5", instrumentId, "Mayor"));
            chords.add(new Chord(7, "Fa Mayor", "F-A-C", "1-3-5", instrumentId, "Mayor"));
            chords.add(new Chord(8, "Sol Mayor", "G-B-D", "1-3-5", instrumentId, "Mayor"));
            chords.add(new Chord(9, "La menor", "A-C-E", "1-♭3-5", instrumentId, "Menor"));
        }
        
        return chords;
    }
    
    /**
     * Go back to main menu
     */
    @FXML
    private void goBack() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/MainView.fxml"));
            Parent root = fxmlLoader.load();
            
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/css/styles.css").toExternalForm());
            
            Stage stage = Main.getPrimaryStage();
            stage.setScene(scene);
        } catch (IOException e) {
            System.err.println("Error loading main view: " + e.getMessage());
            showAlert("Error", "No se pudo cargar la vista principal.");
        }
    }
    
    /**
     * Show alert dialog
     */
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
