package com.scalia.controllers;

import com.scalia.dao.InstrumentDAO;
import com.scalia.dao.InstrumentCategoryDAO;
import com.scalia.models.Instrument;
import com.scalia.models.InstrumentCategory;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.control.Alert.AlertType;
import javafx.geometry.VPos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Priority; // Importar Priority
import javafx.stage.Stage;
import com.scalia.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.HBox;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.animation.ScaleTransition;
import javafx.util.Duration;

import java.io.IOException;
import java.util.List;
import java.util.HashSet; // Para la unicidad en el DAO
import java.util.Set;     // Para la unicidad en el DAO
import java.util.ArrayList; // Para la unicidad en el DAO

public class InstrumentLibraryController {

    @FXML private GridPane instrumentGridPane;
    @FXML private VBox detailPanel;
    @FXML private Label detailNameLabel;
    @FXML private Label detailTypeLabel;
    @FXML private Label detailDescriptionLabel;
    @FXML private Label detailTuningStandardLabel;
    @FXML private HBox categoryButtonBar;
    private ToggleGroup categoryToggleGroup = new ToggleGroup();
    private List<InstrumentCategory> categories;
    private InstrumentCategory selectedCategory;

    private List<Instrument> allInstruments;

    @FXML
    public void initialize() {
        System.out.println("DEBUG: initialize() de InstrumentLibraryController invocado.");
        clearDetailPanel();
        instrumentGridPane.getChildren().clear();

        // Cargar categorías
        categories = InstrumentCategoryDAO.getAllCategories();
        System.out.println("Categorías encontradas: " + categories.size());
        for (InstrumentCategory cat : categories) {
            System.out.println("Categoría: " + cat.getName());
        }
        categoryButtonBar.getChildren().clear();
        for (InstrumentCategory category : categories) {
            ToggleButton btn = new ToggleButton(category.getName());
            btn.setToggleGroup(categoryToggleGroup);
            btn.setUserData(category);
            btn.getStyleClass().add("category-toggle-button"); // Aplica el estilo personalizado
            // Animación de zoom suave al hacer hover
            ScaleTransition stEnter = new ScaleTransition(Duration.millis(180), btn);
            stEnter.setToX(1.10);
            stEnter.setToY(1.10);
            ScaleTransition stExit = new ScaleTransition(Duration.millis(180), btn);
            stExit.setToX(1.0);
            stExit.setToY(1.0);
            btn.setOnMouseEntered(e -> {
                stExit.stop();
                stEnter.playFromStart();
            });
            btn.setOnMouseExited(e -> {
                stEnter.stop();
                stExit.playFromStart();
            });
            btn.setOnAction(e -> {
                selectedCategory = category;
                updateInstrumentGrid();
            });
            categoryButtonBar.getChildren().add(btn);
        }
        if (!categories.isEmpty()) {
            ((ToggleButton)categoryButtonBar.getChildren().get(0)).setSelected(true);
            selectedCategory = categories.get(0);
        }

        // Cargar todos los instrumentos
        allInstruments = InstrumentDAO.getAllInstruments();
        updateInstrumentGrid();
        System.out.println("DEBUG: InstrumentLibraryController inicialización completa. Instrumentos cargados: " + allInstruments.size());
    }

    private void updateInstrumentGrid() {
        instrumentGridPane.getChildren().clear();
        List<Instrument> instrumentsToShow;
        if (selectedCategory != null) {
            instrumentsToShow = InstrumentDAO.getInstrumentsByCategory(selectedCategory.getId());
        } else {
            instrumentsToShow = allInstruments;
        }
        int row = 0;
        int col = 0;
        int maxCols = 2;
        if (instrumentsToShow.isEmpty()) {
            Label noInstrumentsLabel = new Label("No instruments found for this category.");
            GridPane.setHalignment(noInstrumentsLabel, HPos.CENTER);
            GridPane.setValignment(noInstrumentsLabel, VPos.CENTER);
            instrumentGridPane.add(noInstrumentsLabel, 0, 0, 2, 1);
            return;
        }
        for (Instrument instrument : instrumentsToShow) {
            Button instrumentButton = new Button(instrument.getName());
            instrumentButton.setPrefSize(200, 100);
            instrumentButton.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
            instrumentButton.getStyleClass().add("instrument-button");
            instrumentButton.setOnAction(event -> showInstrumentDetails(instrument));
            GridPane.setHalignment(instrumentButton, HPos.CENTER);
            GridPane.setValignment(instrumentButton, VPos.CENTER);
            GridPane.setHgrow(instrumentButton, Priority.ALWAYS);
            GridPane.setVgrow(instrumentButton, Priority.ALWAYS);
            if (col < maxCols) {
                instrumentGridPane.add(instrumentButton, col, row);
            }
            col++;
            if (col == maxCols) {
                col = 0;
                row++;
            }
        }
    }

    /**
     * Muestra los detalles del instrumento seleccionado en el panel de detalles.
     * @param instrument El objeto Instrument cuyos detalles se van a mostrar.
     */
    private void showInstrumentDetails(Instrument instrument) {
        if (instrument != null) {
            System.out.println("DEBUG: Mostrando detalles para: " + instrument.getName());
            // Ahora incluimos el título "Name:" directamente en el texto del Label
            detailNameLabel.setText("Name: " + (instrument.getName() != null ? instrument.getName() : "N/A"));
            detailTypeLabel.setText("Type: " + (instrument.getType() != null ? instrument.getType() : "N/A"));
            detailDescriptionLabel.setText("Description: " + (instrument.getDescription() != null ? instrument.getDescription() : "N/A"));

            if (instrument.getTuningStandard() != null && !instrument.getTuningStandard().isEmpty()) {
                detailTuningStandardLabel.setText("Tuning Standard: " + instrument.getTuningStandard());
            } else {
                detailTuningStandardLabel.setText("Tuning Standard: N/A");
            }
            // Mostrar la categoría
            if (instrument.getCategory() != null) {
                detailTypeLabel.setText(detailTypeLabel.getText() + " | Categoría: " + instrument.getCategory().getName());
            }
        } else {
            System.err.println("DEBUG: showInstrumentDetails llamado con instrumento nulo.");
            clearDetailPanel();
        }
    }

    /**
     * Limpia el contenido del panel de detalles.
     */
    private void clearDetailPanel() {
        detailNameLabel.setText("Name: ");
        detailTypeLabel.setText("Type: ");
        detailDescriptionLabel.setText("Description: ");
        detailTuningStandardLabel.setText("Tuning Standard: ");
        System.out.println("DEBUG: Panel de detalles limpiado.");
    }

    @FXML
    private void handleBackToMainMenu() {
        try {
            System.out.println("DEBUG: Volviendo al menú principal.");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MainView.fxml"));
            Parent root = loader.load();

            Stage stage = Main.getPrimaryStage();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/css/styles.css").toExternalForm());

            stage.setScene(scene);
            stage.setTitle("Scalia - Menú Principal");
            stage.setMaximized(true); // Abrir maximizada
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error de Navegación", "No se pudo volver al menú principal.");
        }
    }

    private void showAlert(String title, String message) {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(Alert.AlertType.INFORMATION); // <--- DEBE SER ASÍ
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}