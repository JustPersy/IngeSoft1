package com.scalia.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import com.scalia.Main;
import com.scalia.dao.TuningPresetDAO;
import com.scalia.dao.InstrumentDAO;
import com.scalia.models.TuningPreset;
import com.scalia.models.Instrument;

import java.io.IOException;
import java.util.List;

/**
 * Controller for the Tuning Library View
 * Handles browsing and managing tuning presets
 */
public class TuningLibraryController {

    @FXML
    private ComboBox<Instrument> instrumentFilterComboBox;

    @FXML
    private CheckBox standardOnlyCheckBox;

    @FXML
    private ListView<TuningPreset> tuningPresetsListView;

    @FXML
    private Label tuningNameLabel;

    @FXML
    private TextArea tuningDescriptionArea;

    @FXML
    private GridPane tuningNotesGrid;

    @FXML
    private TextArea additionalInfoArea;

    @FXML
    private Button favoriteButton;

    @FXML
    private Button useInTunerButton;

    @FXML
    private Button copyNotesButton;

    @FXML
    private Button backButton;

    private TuningPresetDAO tuningPresetDAO;
    private TuningPreset selectedTuningPreset;

    /**
     * Initialize the controller
     */
    @FXML
    public void initialize() {
        tuningPresetDAO = new TuningPresetDAO();
        
        // Load instruments for filter
        loadInstruments();
        
        // Load all tuning presets initially
        loadTuningPresets();
        
        // Set up event handlers
        instrumentFilterComboBox.setOnAction(e -> filterTuningPresets());
        standardOnlyCheckBox.setOnAction(e -> filterTuningPresets());
        
        tuningPresetsListView.getSelectionModel().selectedItemProperty().addListener(
            (observable, oldValue, newValue) -> {
                if (newValue != null) {
                    selectedTuningPreset = newValue;
                    displayTuningDetails(newValue);
                }
            }
        );
        
        // Auto-select first preset if available
        if (!tuningPresetsListView.getItems().isEmpty()) {
            tuningPresetsListView.getSelectionModel().selectFirst();
        }
    }
    
    /**
     * Load instruments for the filter dropdown
     */
    private void loadInstruments() {
        List<Instrument> instruments = InstrumentDAO.getAllInstruments();
        
        // Add "All" option
        instrumentFilterComboBox.getItems().add(null); // null represents "All"
        instrumentFilterComboBox.getItems().addAll(instruments);
        
        // Set placeholder text for "All" option
        instrumentFilterComboBox.setButtonCell(new ListCell<Instrument>() {
            @Override
            protected void updateItem(Instrument item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText("Todos los instrumentos");
                } else {
                    setText(item.getName());
                }
            }
        });
        
        instrumentFilterComboBox.setCellFactory(listView -> new ListCell<Instrument>() {
            @Override
            protected void updateItem(Instrument item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText("Todos los instrumentos");
                } else {
                    setText(item.getName());
                }
            }
        });
        
        instrumentFilterComboBox.getSelectionModel().selectFirst(); // Select "All"
    }
    
    /**
     * Load all tuning presets
     */
    private void loadTuningPresets() {
        List<TuningPreset> presets = tuningPresetDAO.getAllTuningPresets();
        
        // Add sample presets if database is empty
        if (presets.isEmpty()) {
            presets = createSampleTuningPresets();
        }
        
        tuningPresetsListView.getItems().setAll(presets);
        
        // Custom cell factory for better display
        tuningPresetsListView.setCellFactory(listView -> new ListCell<TuningPreset>() {
            @Override
            protected void updateItem(TuningPreset preset, boolean empty) {
                super.updateItem(preset, empty);
                if (empty || preset == null) {
                    setText(null);
                } else {
                    String instrumentName = getInstrumentName(preset.getInstrumentId());
                    String standardText = preset.isStandard() ? " (Estándar)" : "";
                    setText(preset.getName() + " - " + instrumentName + standardText);
                }
            }
        });
    }
    
    /**
     * Filter tuning presets based on selected criteria
     */
    private void filterTuningPresets() {
        List<TuningPreset> allPresets = tuningPresetDAO.getAllTuningPresets();
        
        // Add sample presets if database is empty
        if (allPresets.isEmpty()) {
            allPresets = createSampleTuningPresets();
        }
        
        // Filter by instrument
        Instrument selectedInstrument = instrumentFilterComboBox.getSelectionModel().getSelectedItem();
        if (selectedInstrument != null) {
            allPresets.removeIf(preset -> preset.getInstrumentId() != selectedInstrument.getId());
        }
        
        // Filter by standard only
        if (standardOnlyCheckBox.isSelected()) {
            allPresets.removeIf(preset -> !preset.isStandard());
        }
        
        tuningPresetsListView.getItems().setAll(allPresets);
        
        // Auto-select first item
        if (!allPresets.isEmpty()) {
            tuningPresetsListView.getSelectionModel().selectFirst();
        }
    }
    
    /**
     * Display details of the selected tuning preset
     */
    private void displayTuningDetails(TuningPreset preset) {
        tuningNameLabel.setText(preset.getName());
        tuningDescriptionArea.setText(preset.getDescription());
        
        // Display tuning notes in grid
        displayTuningNotes(preset.getTuningNotes());
        
        // Display additional information
        displayAdditionalInfo(preset);
        
        // Update favorite button
        updateFavoriteButton(preset);
    }
    
    /**
     * Display tuning notes in a grid layout
     */
    private void displayTuningNotes(String tuningNotes) {
        tuningNotesGrid.getChildren().clear();
        
        String[] notes = tuningNotes.split("-");
        int stringNumber = 1;
        
        for (String note : notes) {
            Label stringLabel = new Label("Cuerda " + stringNumber + ":");
            stringLabel.getStyleClass().add("string-label");
            
            Label noteLabel = new Label(note.trim());
            noteLabel.getStyleClass().add("note-label");
            
            tuningNotesGrid.add(stringLabel, 0, stringNumber - 1);
            tuningNotesGrid.add(noteLabel, 1, stringNumber - 1);
            
            stringNumber++;
        }
    }
    
    /**
     * Display additional information about the tuning
     */
    private void displayAdditionalInfo(TuningPreset preset) {
        StringBuilder info = new StringBuilder();
        
        String instrumentName = getInstrumentName(preset.getInstrumentId());
        info.append("INSTRUMENTO: ").append(instrumentName).append("\n\n");
        
        if (preset.isStandard()) {
            info.append("TIPO: Afinación Estándar\n");
            info.append("Esta es la afinación más común para este instrumento.\n\n");
        } else {
            info.append("TIPO: Afinación Alternativa\n");
            info.append("Esta es una afinación especial que puede ofrecer sonidos únicos.\n\n");
        }
        
        // Add specific information based on tuning
        if (preset.getName().toLowerCase().contains("drop")) {
            info.append("CARACTERÍSTICAS:\n");
            info.append("- Facilita la ejecución de power chords\n");
            info.append("- Popular en rock y metal\n");
            info.append("- Permite riffs más graves\n");
        } else if (preset.getName().toLowerCase().contains("open")) {
            info.append("CARACTERÍSTICAS:\n");
            info.append("- Forma un acorde cuando se tocan todas las cuerdas al aire\n");
            info.append("- Ideal para slide guitar\n");
            info.append("- Facilita ciertos acordes\n");
        } else if (preset.isStandard()) {
            info.append("CARACTERÍSTICAS:\n");
            info.append("- Afinación de referencia universal\n");
            info.append("- La mayoría del material educativo usa esta afinación\n");
            info.append("- Recomendada para principiantes\n");
        }
        
        additionalInfoArea.setText(info.toString());
    }
    
    /**
     * Update the favorite button state
     */
    private void updateFavoriteButton(TuningPreset preset) {
        // In a real application, you would check if this preset is in user's favorites
        // For now, we'll just enable the button
        favoriteButton.setDisable(false);
        favoriteButton.setText("⭐ Agregar a Favoritos");
    }
    
    /**
     * Get instrument name by ID
     */
    private String getInstrumentName(int instrumentId) {
        List<Instrument> instruments = InstrumentDAO.getAllInstruments();
        return instruments.stream()
                .filter(instrument -> instrument.getId() == instrumentId)
                .map(Instrument::getName)
                .findFirst()
                .orElse("Instrumento Desconocido");
    }
    
    /**
     * Toggle favorite status of the selected tuning preset
     */
    @FXML
    private void toggleFavorite() {
        if (selectedTuningPreset == null) {
            showAlert("Información", "Selecciona una afinación para agregar a favoritos.");
            return;
        }
        
        // In a real application, this would add/remove from user's favorites
        showAlert("Favoritos", "Afinación '" + selectedTuningPreset.getName() + 
                 "' agregada a favoritos.\n(Funcionalidad de favoritos en desarrollo)");
    }
    
    /**
     * Use the selected tuning in the tuner
     */
    @FXML
    private void useInTuner() {
        if (selectedTuningPreset == null) {
            showAlert("Información", "Selecciona una afinación para usar en el afinador.");
            return;
        }
        
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/TunerView.fxml"));
            Parent root = fxmlLoader.load();
            
            // Here you could pass the selected tuning to the tuner controller
            // TunerController tunerController = fxmlLoader.getController();
            // tunerController.setSelectedTuning(selectedTuningPreset);
            
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/css/styles.css").toExternalForm());
            
            Stage stage = Main.getPrimaryStage();
            stage.setScene(scene);
        } catch (IOException e) {
            System.err.println("Error loading tuner view: " + e.getMessage());
            showAlert("Error", "No se pudo cargar el afinador.");
        }
    }
    
    /**
     * Copy tuning notes to clipboard
     */
    @FXML
    private void copyNotes() {
        if (selectedTuningPreset == null) {
            showAlert("Información", "Selecciona una afinación para copiar las notas.");
            return;
        }
        
        Clipboard clipboard = Clipboard.getSystemClipboard();
        ClipboardContent content = new ClipboardContent();
        content.putString(selectedTuningPreset.getTuningNotes());
        clipboard.setContent(content);
        
        showAlert("Copiado", "Notas de afinación copiadas al portapapeles: " + 
                 selectedTuningPreset.getTuningNotes());
    }
    
    /**
     * Create sample tuning presets for demonstration
     */
    private List<TuningPreset> createSampleTuningPresets() {
        List<TuningPreset> presets = new java.util.ArrayList<>();
        
        // Guitar tunings
        presets.add(new TuningPreset(1, "Afinación Estándar", 1, "E-A-D-G-B-E", 
                   "La afinación estándar de guitarra, usada en la mayoría de géneros musicales.", true));
        presets.add(new TuningPreset(2, "Drop D", 1, "D-A-D-G-B-E", 
                   "Afinación popular en rock y metal, facilita los power chords.", false));
        presets.add(new TuningPreset(3, "Open G", 1, "D-G-D-G-B-D", 
                   "Afinación abierta que forma un acorde de Sol mayor.", false));
        presets.add(new TuningPreset(4, "DADGAD", 1, "D-A-D-G-A-D", 
                   "Afinación popular en música celta y folk.", false));
        
        // Bass tunings
        presets.add(new TuningPreset(5, "Afinación Estándar", 2, "E-A-D-G", 
                   "Afinación estándar de bajo eléctrico de 4 cuerdas.", true));
        presets.add(new TuningPreset(6, "Drop D", 2, "D-A-D-G", 
                   "Afinación Drop D para bajo, permite riffs más graves.", false));
        
        // Ukulele tunings
        presets.add(new TuningPreset(7, "Afinación Estándar", 3, "G-C-E-A", 
                   "Afinación estándar de ukulele soprano.", true));
        presets.add(new TuningPreset(8, "Low G", 3, "G-C-E-A", 
                   "Afinación con Sol grave en lugar de Sol agudo.", false));
        
        // Violin tuning
        presets.add(new TuningPreset(9, "Afinación Estándar", 4, "G-D-A-E", 
                   "Afinación estándar de violín por quintas.", true));
        
        return presets;
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
