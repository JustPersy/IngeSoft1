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
        showModuleMessage("Teoría Musical", "Módulo de teoría musical en desarrollo");
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
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void openChords() {
        showModuleMessage("Acordes", "Visualizador de acordes en desarrollo");
    }

    @FXML
    private void openTuner() {
        showModuleMessage("Afinador", "Afinador de instrumentos en desarrollo");
    }

    @FXML
    private void openTunings() {
        showModuleMessage("Afinaciones", "Biblioteca de afinaciones en desarrollo");
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