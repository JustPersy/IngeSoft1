package com.scalia.controllers;

import com.scalia.dao.InstrumentDAO;
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
import com.scalia.models.Instrument;

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

    @FXML
    public void initialize() {
        System.out.println("DEBUG: initialize() de InstrumentLibraryController invocado.");

        clearDetailPanel(); // Inicializar el panel de detalles vacío

        instrumentGridPane.getChildren().clear(); // Limpiar el GridPane antes de añadir botones

        // Obtener instrumentos únicos (asegúrate de que InstrumentDAO maneje la unicidad o que tu DB lo haga)
        List<Instrument> instruments = InstrumentDAO.getAllInstruments();
        // Si el DAO no garantiza unicidad y no quieres modificar el DAO o el modelo Instrument:
        // Set<Instrument> uniqueInstruments = new HashSet<>(instruments);
        // instruments = new ArrayList<>(uniqueInstruments);

        int row = 0;
        int col = 0;
        int maxCols = 2; // Queremos 2 columnas

        if (instruments.isEmpty()) {
            System.out.println("DEBUG: No se encontraron instrumentos en la base de datos.");
            Label noInstrumentsLabel = new Label("No instruments found.");
            GridPane.setHalignment(noInstrumentsLabel, HPos.CENTER);
            GridPane.setValignment(noInstrumentsLabel, VPos.CENTER);
            instrumentGridPane.add(noInstrumentsLabel, 0, 0, 2, 1);
            return;
        }

        for (Instrument instrument : instruments) {
            Button instrumentButton = new Button(instrument.getName());

            instrumentButton.setPrefSize(200, 100);
            instrumentButton.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
            instrumentButton.getStyleClass().add("instrument-button");

            instrumentButton.setOnAction(event -> {
                System.out.println("DEBUG: Botón '" + instrument.getName() + "' clicado.");
                showInstrumentDetails(instrument);
            });

            GridPane.setHalignment(instrumentButton, HPos.CENTER);
            GridPane.setValignment(instrumentButton, VPos.CENTER);
            GridPane.setHgrow(instrumentButton, Priority.ALWAYS);
            GridPane.setVgrow(instrumentButton, Priority.ALWAYS);

            if (col < maxCols) {
                instrumentGridPane.add(instrumentButton, col, row);
            } else {
                System.err.println("ERROR: Attempted to add button outside maxCols. Check GridPane logic.");
            }

            col++;
            if (col == maxCols) {
                col = 0;
                row++;
            }
        }

        System.out.println("DEBUG: InstrumentLibraryController inicialización completa. Instrumentos cargados: " + instruments.size());
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