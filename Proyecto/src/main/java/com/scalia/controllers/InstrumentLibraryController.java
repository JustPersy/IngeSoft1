package com.scalia.controllers;

import com.scalia.dao.InstrumentDAO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos; // <-- Importar HPos
import javafx.geometry.VPos; // <-- Importar VPos
// import javafx.geometry.Pos; // <-- Ya no necesitas Pos, puedes comentarla o borrarla
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Alert;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import com.scalia.Main;
import com.scalia.models.Instrument;

import java.io.IOException;
import java.util.List;

public class InstrumentLibraryController {

    @FXML private GridPane instrumentGridPane;

    @FXML
    public void initialize() {
        System.out.println("DEBUG: initialize() de InstrumentLibraryController invocado.");

        instrumentGridPane.getChildren().clear();

        List<Instrument> instruments = InstrumentDAO.getAllInstruments();

        int row = 0;
        int col = 0;
        int maxCols = 2; // Queremos 2 columnas

        for (Instrument instrument : instruments) {
            Button instrumentButton = new Button(instrument.getName());

            instrumentButton.setPrefSize(200, 100);
            instrumentButton.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
            instrumentButton.getStyleClass().add("instrument-button");

            // ¡CORRECCIÓN AQUÍ! Usar HPos.CENTER y VPos.CENTER
            GridPane.setHalignment(instrumentButton, HPos.CENTER); // <-- CORREGIDO
            GridPane.setValignment(instrumentButton, VPos.CENTER); // <-- CORREGIDO

            instrumentGridPane.add(instrumentButton, col, row);

            col++;
            if (col == maxCols) {
                col = 0;
                row++;
            }
        }

        System.out.println("DEBUG: InstrumentLibraryController inicialización completa. Instrumentos cargados: " + instruments.size());
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
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error de Navegación", "No se pudo volver al menú principal.");
        }
    }

    private void showAlert(String title, String message) {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}