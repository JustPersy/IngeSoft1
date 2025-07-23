package com.scalia.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import com.scalia.Main;

import java.io.IOException;

/**
 * Controller for the main application view
 * Handles navigation between different modules
 */
public class MainController {

    @FXML
    private void openTheory() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/TheoryView.fxml"));
            Parent root = loader.load();

            Stage stage = Main.getPrimaryStage();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/css/styles.css").toExternalForm());

            stage.setScene(scene);
            stage.setTitle("Scalia - Teoría Musical");
            stage.setMaximized(true);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showModuleMessage("Error", "No se pudo cargar el módulo de Teoría Musical.");
        }
    }

    @FXML
    private void openInstruments() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/InstrumentLibraryView.fxml"));
            Parent root = loader.load();

            Stage stage = Main.getPrimaryStage();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/css/styles.css").toExternalForm());

            stage.setScene(scene);
            stage.setTitle("Scalia - Biblioteca de Instrumentos");
            stage.setMaximized(true); // Abrir maximizada
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void openChords() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ChordView.fxml"));
            Parent root = loader.load();

            Stage stage = Main.getPrimaryStage();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/css/styles.css").toExternalForm());

            stage.setScene(scene);
            stage.setTitle("Scalia - Visualizador de Acordes");
            stage.setMaximized(true);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showModuleMessage("Error", "No se pudo cargar el visualizador de acordes.");
        }
    }

    @FXML
    private void openTuner() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/TunerView.fxml"));
            Parent root = loader.load();

            Stage stage = Main.getPrimaryStage();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/css/styles.css").toExternalForm());

            stage.setScene(scene);
            stage.setTitle("Scalia - Afinador");
            stage.setMaximized(true);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showModuleMessage("Error", "No se pudo cargar el afinador.");
        }
    }

    @FXML
    private void openTunings() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/TuningLibraryView.fxml"));
            Parent root = loader.load();

            Stage stage = Main.getPrimaryStage();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/css/styles.css").toExternalForm());

            stage.setScene(scene);
            stage.setTitle("Scalia - Biblioteca de Afinaciones");
            stage.setMaximized(true);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showModuleMessage("Error", "No se pudo cargar la biblioteca de afinaciones.");
        }
    }

    @FXML
    private void openUsers() {
        showModuleMessage("Usuarios", "Gestión de usuarios en desarrollo");
    }

    @FXML
    private void handleLogout() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Cerrar Sesión");
        alert.setHeaderText("¿Estás seguro de que quieres cerrar sesión?");
        alert.setContentText("Serás redirigido a la pantalla de login.");

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                navigateToLogin();
            }
        });
    }

    /**
     * Show a message for modules that are still in development
     * @param moduleName The name of the module
     * @param message The development message
     */
    private void showModuleMessage(String moduleName, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(moduleName);
        alert.setHeaderText("Módulo en Desarrollo");
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Navigate back to the login screen
     */
    private void navigateToLogin() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/LoginView.fxml"));
            Parent root = loader.load();
            
            Stage stage = Main.getPrimaryStage();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/css/styles.css").toExternalForm());
            
            stage.setScene(scene);
            stage.setTitle("Scalia - Iniciar Sesión");
            stage.setMaximized(true); // Abrir maximizada
            stage.show();
            
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error de Navegación");
            alert.setContentText("No se pudo cargar la pantalla de login: " + e.getMessage());
            alert.showAndWait();
        }
    }
}