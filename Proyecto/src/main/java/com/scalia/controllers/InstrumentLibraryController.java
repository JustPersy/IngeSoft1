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
import javafx.scene.layout.Priority;
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
import java.util.HashSet;
import java.util.Set;
import java.util.ArrayList;
import java.util.stream.Collectors; // Necesario para el filtrado de lista

public class InstrumentLibraryController {

    @FXML private GridPane instrumentGridPane;
    @FXML private VBox detailPanel;
    @FXML private Label detailNameLabel;
    @FXML private Label detailTypeLabel;
    @FXML private Label detailDescriptionLabel;
    @FXML private Label detailTuningStandardLabel;
    @FXML private HBox categoryButtonBar;
    @FXML private Label detailCategoryLabel; // Nuevo FXML ID para la etiqueta de la categoría del instrumento seleccionado
    @FXML private Label currentCategoryLabel; // Nuevo FXML ID para la etiqueta que muestra la categoría de filtro actual

    private ToggleGroup categoryToggleGroup = new ToggleGroup();
    private List<InstrumentCategory> categories;
    private InstrumentCategory selectedCategoryFilter; // Cambiado de 'selectedCategory' para mayor claridad
    private List<Instrument> allInstruments; // Lista de todos los instrumentos cargados inicialmente

    @FXML
    public void initialize() {
        System.out.println("DEBUG: initialize() de InstrumentLibraryController invocado.");
        clearDetailPanel();
        instrumentGridPane.getChildren().clear();

        // Cargar todos los instrumentos una sola vez al inicio
        allInstruments = InstrumentDAO.getAllInstruments();
        System.out.println("DEBUG: Instrumentos cargados en total: " + allInstruments.size());

        // Cargar y configurar botones de categorías
        categories = InstrumentCategoryDAO.getAllCategories();
        System.out.println("Categorías encontradas: " + categories.size());

        populateCategoryButtons(); // Método para crear y añadir los botones

        // Inicialmente seleccionar la categoría "Todas" o la primera si no existe una "Todas"
        // Asegúrate de que el ToggleGroup maneje el estado de los botones.
        ToggleButton firstButton = (ToggleButton) categoryButtonBar.getChildren().get(0);
        if (firstButton != null) {
            firstButton.setSelected(true);
            selectedCategoryFilter = (InstrumentCategory) firstButton.getUserData(); // Debería ser null para "Todas"
        } else {
            selectedCategoryFilter = null; // Por defecto, no hay filtro de categoría
        }

        updateInstrumentGrid(); // Actualizar la cuadrícula con el filtro inicial
        updateCurrentCategoryLabel(); // Actualizar el label de la categoría actual

        System.out.println("DEBUG: InstrumentLibraryController inicialización completa. Instrumentos cargados: " + allInstruments.size());
    }

    // En com.scalia.controllers.InstrumentLibraryController.java

    private void populateCategoryButtons() {
        categoryButtonBar.getChildren().clear();

        // Botón "Todas las Categorías"
        ToggleButton allButton = new ToggleButton("Todas las Categorías");
        allButton.setToggleGroup(categoryToggleGroup);
        allButton.setUserData(null);
        allButton.getStyleClass().add("category-toggle-button");
        addHoverAnimation(allButton);
        allButton.setOnAction(e -> {
            selectedCategoryFilter = null;
            updateInstrumentGrid();
            updateCurrentCategoryLabel();
        });
        // ¡Añadir esta línea para que el botón se estire horizontalmente!
        HBox.setHgrow(allButton, Priority.ALWAYS);
        categoryButtonBar.getChildren().add(allButton);

        // Botones para categorías específicas
        for (InstrumentCategory category : categories) {
            ToggleButton btn = new ToggleButton(category.getName());
            btn.setToggleGroup(categoryToggleGroup);
            btn.setUserData(category);
            btn.getStyleClass().add("category-toggle-button");
            addHoverAnimation(btn);
            btn.setOnAction(e -> {
                selectedCategoryFilter = category;
                updateInstrumentGrid();
                updateCurrentCategoryLabel();
            });
            // ¡Añadir esta línea para que el botón se estire horizontalmente!
            HBox.setHgrow(btn, Priority.ALWAYS);
            categoryButtonBar.getChildren().add(btn);
        }
    }

    private void addHoverAnimation(ToggleButton btn) {
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
    }

    private void updateInstrumentGrid() {
        instrumentGridPane.getChildren().clear();
        List<Instrument> instrumentsToShow;

        if (selectedCategoryFilter != null) {
            // Filtra la lista 'allInstruments' en memoria
            instrumentsToShow = allInstruments.stream()
                    .filter(instrument -> instrument.getCategory() != null && instrument.getCategory().getId() == selectedCategoryFilter.getId())
                    .collect(Collectors.toList());
        } else {
            instrumentsToShow = allInstruments; // Muestra todos los instrumentos si no hay filtro
        }

        int row = 0;
        int col = 0;
        int maxCols = 2; // Dos columnas por fila

        if (instrumentsToShow.isEmpty()) {
            Label noInstrumentsLabel = new Label("No instruments found for this category.");
            noInstrumentsLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: #555;"); // O un estilo de tu CSS
            GridPane.setHalignment(noInstrumentsLabel, HPos.CENTER);
            GridPane.setValignment(noInstrumentsLabel, VPos.CENTER);
            instrumentGridPane.add(noInstrumentsLabel, 0, 0, maxCols, 1); // Ocupa ambas columnas
            return;
        }

        for (Instrument instrument : instrumentsToShow) {
            Button instrumentButton = new Button(instrument.getName());
            instrumentButton.setPrefSize(200, 100);
            instrumentButton.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE); // Permite que el botón se expanda
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

    private void updateCurrentCategoryLabel() {
        if (selectedCategoryFilter != null) {
            currentCategoryLabel.setText("Categoría Actual: " + selectedCategoryFilter.getName());
        } else {
            currentCategoryLabel.setText("Categoría Actual: Todas");
        }
    }

    /**
     * Muestra los detalles del instrumento seleccionado en el panel de detalles.
     * @param instrument El objeto Instrument cuyos detalles se van a mostrar.
     */
    private void showInstrumentDetails(Instrument instrument) {
        if (instrument != null) {
            System.out.println("DEBUG: Mostrando detalles para: " + instrument.getName());
            detailNameLabel.setText("Name: " + (instrument.getName() != null ? instrument.getName() : "N/A"));
            detailTypeLabel.setText("Type: " + (instrument.getType() != null ? instrument.getType() : "N/A"));

            // Usar el nuevo label para la categoría
            if (instrument.getCategory() != null) {
                detailCategoryLabel.setText("Category: " + instrument.getCategory().getName());
            } else {
                detailCategoryLabel.setText("Category: N/A");
            }

            detailDescriptionLabel.setText("Description: " + (instrument.getDescription() != null ? instrument.getDescription() : "N/A"));

            if (instrument.getTuningStandard() != null && !instrument.getTuningStandard().isEmpty()) {
                detailTuningStandardLabel.setText("Tuning Standard: " + instrument.getTuningStandard());
            } else {
                detailTuningStandardLabel.setText("Tuning Standard: N/A");
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
        detailCategoryLabel.setText("Category: "); // Limpiar también el nuevo label de categoría
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
            stage.setMaximized(true);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error de Navegación", "No se pudo volver al menú principal.");
        }
    }

    private void showAlert(String title, String message) {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}