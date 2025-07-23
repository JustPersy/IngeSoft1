package com.scalia.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import com.scalia.Main;
import com.scalia.dao.TuningPresetDAO;
import com.scalia.dao.InstrumentDAO;
import com.scalia.models.TuningPreset;
import com.scalia.models.Instrument;

import java.io.IOException;
import java.util.List;
import java.util.Random;

/**
 * Controller for the Tuner View
 * Handles instrument tuning and frequency detection
 */
public class TunerController {

    @FXML
    private ComboBox<Instrument> instrumentComboBox;

    @FXML
    private ComboBox<TuningPreset> tuningPresetComboBox;

    @FXML
    private Label currentNoteLabel;

    @FXML
    private Label targetNoteLabel;

    @FXML
    private Label frequencyLabel;

    @FXML
    private ProgressBar tuningProgressBar;

    @FXML
    private Circle tuningIndicator;

    @FXML
    private Button startTuningButton;

    @FXML
    private Button stopTuningButton;

    @FXML
    private Button backButton;

    @FXML
    private VBox tuningContainer;

    @FXML
    private ListView<String> tuningNotesListView;

    @FXML
    private Label statusLabel;

    private TuningPresetDAO tuningPresetDAO;
    private boolean isTuning = false;
    private Random random; // For simulation

    /**
     * Initialize the controller
     */
    @FXML
    public void initialize() {
        tuningPresetDAO = new TuningPresetDAO();
        random = new Random();
        
        // Load instruments
        loadInstruments();
        
        // Set up event handlers
        instrumentComboBox.setOnAction(e -> loadTuningPresetsForSelectedInstrument());
        
        tuningPresetComboBox.setOnAction(e -> displayTuningNotes());
        
        // Initialize UI state
        updateTuningState(false);
        
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
     * Load tuning presets for the selected instrument
     */
    private void loadTuningPresetsForSelectedInstrument() {
        Instrument selectedInstrument = instrumentComboBox.getSelectionModel().getSelectedItem();
        
        if (selectedInstrument == null) return;
        
        List<TuningPreset> presets = tuningPresetDAO.getTuningPresetsByInstrument(selectedInstrument.getId());
        
        // Add sample presets if database is empty
        if (presets.isEmpty()) {
            presets = createSampleTuningPresets(selectedInstrument.getId());
        }
        
        tuningPresetComboBox.getItems().setAll(presets);
        
        // Auto-select first preset (usually standard tuning)
        if (!presets.isEmpty()) {
            tuningPresetComboBox.getSelectionModel().selectFirst();
        }
    }
    
    /**
     * Display tuning notes for the selected preset
     */
    private void displayTuningNotes() {
        TuningPreset selectedPreset = tuningPresetComboBox.getSelectionModel().getSelectedItem();
        
        if (selectedPreset == null) return;
        
        String[] notes = selectedPreset.getTuningNotes().split("-");
        tuningNotesListView.getItems().setAll(notes);
        
        if (notes.length > 0) {
            targetNoteLabel.setText("Objetivo: " + notes[0]);
        }
    }
    
    /**
     * Start tuning process
     */
    @FXML
    private void startTuning() {
        TuningPreset selectedPreset = tuningPresetComboBox.getSelectionModel().getSelectedItem();
        
        if (selectedPreset == null) {
            showAlert("Información", "Selecciona una afinación antes de comenzar.");
            return;
        }
        
        isTuning = true;
        updateTuningState(true);
        statusLabel.setText("Afinando... Toca la cuerda");
        
        // Start simulation (in a real app, this would connect to audio input)
        simulateTuning();
    }
    
    /**
     * Stop tuning process
     */
    @FXML
    private void stopTuning() {
        isTuning = false;
        updateTuningState(false);
        statusLabel.setText("Afinación detenida");
        resetTuningDisplay();
    }
    
    /**
     * Update UI state based on tuning status
     */
    private void updateTuningState(boolean tuning) {
        startTuningButton.setDisable(tuning);
        stopTuningButton.setDisable(!tuning);
        instrumentComboBox.setDisable(tuning);
        tuningPresetComboBox.setDisable(tuning);
    }
    
    /**
     * Reset tuning display
     */
    private void resetTuningDisplay() {
        currentNoteLabel.setText("---");
        frequencyLabel.setText("--- Hz");
        tuningProgressBar.setProgress(0.5);
        tuningIndicator.getStyleClass().removeAll("tuning-flat", "tuning-sharp", "tuning-perfect");
        tuningIndicator.getStyleClass().add("tuning-neutral");
    }
    
    /**
     * Simulate tuning process (placeholder for real audio processing)
     */
    private void simulateTuning() {
        // In a real application, this would:
        // 1. Capture audio from microphone
        // 2. Perform FFT analysis to detect fundamental frequency
        // 3. Compare with target note frequency
        // 4. Update UI accordingly
        
        new Thread(() -> {
            try {
                while (isTuning) {
                    // Simulate note detection
                    String[] notes = {"C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B"};
                    String detectedNote = notes[random.nextInt(notes.length)];
                    double frequency = 220.0 + random.nextDouble() * 220.0; // Random frequency
                    
                    // Simulate tuning accuracy
                    double accuracy = random.nextDouble();
                    
                    javafx.application.Platform.runLater(() -> {
                        updateTuningDisplay(detectedNote, frequency, accuracy);
                    });
                    
                    Thread.sleep(100); // Update every 100ms
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }).start();
    }
    
    /**
     * Update tuning display with detected values
     */
    private void updateTuningDisplay(String detectedNote, double frequency, double accuracy) {
        currentNoteLabel.setText(detectedNote);
        frequencyLabel.setText(String.format("%.1f Hz", frequency));
        
        // Update progress bar (0.5 = perfect, < 0.5 = flat, > 0.5 = sharp)
        tuningProgressBar.setProgress(accuracy);
        
        // Update visual indicator
        tuningIndicator.getStyleClass().removeAll("tuning-flat", "tuning-sharp", "tuning-perfect", "tuning-neutral");
        
        if (accuracy < 0.45) {
            tuningIndicator.getStyleClass().add("tuning-flat");
            statusLabel.setText("Muy bajo - Ajusta hacia arriba");
        } else if (accuracy > 0.55) {
            tuningIndicator.getStyleClass().add("tuning-sharp");
            statusLabel.setText("Muy alto - Ajusta hacia abajo");
        } else {
            tuningIndicator.getStyleClass().add("tuning-perfect");
            statusLabel.setText("¡Perfecto! Nota afinada correctamente");
        }
    }
    
    /**
     * Select a specific note to tune
     */
    @FXML
    private void selectNote() {
        String selectedNote = tuningNotesListView.getSelectionModel().getSelectedItem();
        if (selectedNote != null) {
            targetNoteLabel.setText("Objetivo: " + selectedNote);
        }
    }
    
    /**
     * Create sample instruments for demonstration
     */
    private List<Instrument> createSampleInstruments() {
        List<Instrument> instruments = new java.util.ArrayList<>();
        instruments.add(new Instrument(1, "Guitarra Acústica", "Cuerda", "Guitarra de seis cuerdas", "E-A-D-G-B-E", null));
        instruments.add(new Instrument(2, "Bajo Eléctrico", "Cuerda", "Bajo de cuatro cuerdas", "E-A-D-G", null));
        instruments.add(new Instrument(3, "Ukulele", "Cuerda", "Instrumento de cuatro cuerdas", "G-C-E-A", null));
        instruments.add(new Instrument(4, "Violín", "Cuerda", "Instrumento de cuerdas frotadas", "G-D-A-E", null));
        return instruments;
    }
    
    /**
     * Create sample tuning presets for demonstration
     */
    private List<TuningPreset> createSampleTuningPresets(int instrumentId) {
        List<TuningPreset> presets = new java.util.ArrayList<>();
        
        if (instrumentId == 1) { // Guitar
            presets.add(new TuningPreset(1, "Afinación Estándar", instrumentId, "E-A-D-G-B-E", "Afinación estándar de guitarra", true));
            presets.add(new TuningPreset(2, "Drop D", instrumentId, "D-A-D-G-B-E", "Afinación Drop D popular en rock", false));
            presets.add(new TuningPreset(3, "Open G", instrumentId, "D-G-D-G-B-D", "Afinación abierta en Sol", false));
        } else if (instrumentId == 2) { // Bass
            presets.add(new TuningPreset(4, "Afinación Estándar", instrumentId, "E-A-D-G", "Afinación estándar de bajo", true));
            presets.add(new TuningPreset(5, "Drop D", instrumentId, "D-A-D-G", "Afinación Drop D para bajo", false));
        } else if (instrumentId == 3) { // Ukulele
            presets.add(new TuningPreset(6, "Afinación Estándar", instrumentId, "G-C-E-A", "Afinación estándar de ukulele", true));
            presets.add(new TuningPreset(7, "Low G", instrumentId, "G-C-E-A", "Afinación con Sol grave", false));
        } else if (instrumentId == 4) { // Violin
            presets.add(new TuningPreset(8, "Afinación Estándar", instrumentId, "G-D-A-E", "Afinación estándar de violín", true));
        }
        
        return presets;
    }
    
    /**
     * Go back to main menu
     */
    @FXML
    private void goBack() {
        // Stop tuning if active
        if (isTuning) {
            stopTuning();
        }
        
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
