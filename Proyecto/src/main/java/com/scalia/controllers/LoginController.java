package com.scalia.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import com.scalia.Main;
import com.scalia.dao.UserDAO;
import com.scalia.models.User;

import java.io.IOException;

/**
 * Controller for the login view
 * Handles user authentication and navigation
 */
public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginButton;

    @FXML
    private Label errorLabel;

    @FXML
    private Hyperlink registerLink;

    private UserDAO userDAO;

    @FXML
    private void initialize() {
        userDAO = new UserDAO();
    }

    /**
     * Handle login button click
     */
    @FXML
    private void handleLogin() {
        clearError();
        
        String username = usernameField.getText().trim();
        String password = passwordField.getText();

        // Basic validation
        if (username.isEmpty() || password.isEmpty()) {
            showError("Por favor complete todos los campos");
            return;
        }

        // Authenticate user with database
        User user = userDAO.authenticateUser(username, password);
        if (user != null) {
            navigateToMainView();
        } else {
            showError("Usuario o contraseña incorrectos");
        }
    }

    /**
     * Handle register link click
     */
    @FXML
    private void handleRegister() {
        navigateToRegisterView();
    }

    /**
     * Navigate to the main application view
     */
    private void navigateToMainView() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MainView.fxml"));
            Parent root = loader.load();
            
            Stage stage = Main.getPrimaryStage();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/css/styles.css").toExternalForm());
            
            stage.setScene(scene);
            stage.setTitle("Scalia - Teoría Musical");
            stage.show();
            
        } catch (IOException e) {
            showError("Error al cargar la vista principal: " + e.getMessage());
        }
    }

    /**
     * Navigate to the registration view
     */
    private void navigateToRegisterView() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/RegisterView.fxml"));
            Parent root = loader.load();
            
            Stage stage = Main.getPrimaryStage();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/css/styles.css").toExternalForm());
            
            stage.setScene(scene);
            stage.setTitle("Scalia - Registro de Usuario");
            stage.show();
            
        } catch (IOException e) {
            showError("Error al cargar la vista de registro: " + e.getMessage());
        }
    }

    /**
     * Show error message to user
     * @param message The error message
     */
    private void showError(String message) {
        errorLabel.setText(message);
        errorLabel.setVisible(true);
    }

    /**
     * Clear error message
     */
    private void clearError() {
        errorLabel.setText("");
        errorLabel.setVisible(false);
    }
} 