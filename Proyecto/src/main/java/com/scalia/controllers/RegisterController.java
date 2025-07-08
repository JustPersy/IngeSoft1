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
 * Controller for the registration view
 * Handles user registration and navigation
 */
public class RegisterController {

    @FXML
    private TextField usernameField;

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private PasswordField confirmPasswordField;

    @FXML
    private TextField firstNameField;

    @FXML
    private TextField lastNameField;

    @FXML
    private Button registerButton;

    @FXML
    private Label errorLabel;

    @FXML
    private Hyperlink loginLink;

    private UserDAO userDAO;

    @FXML
    private void initialize() {
        userDAO = new UserDAO();
    }

    /**
     * Handle register button click
     */
    @FXML
    private void handleRegister() {
        // Clear previous errors
        clearError();

        // Get form data
        String username = usernameField.getText().trim();
        String email = emailField.getText().trim();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();
        String firstName = firstNameField.getText().trim();
        String lastName = lastNameField.getText().trim();

        // Validate form data
        if (!validateForm(username, email, password, confirmPassword, firstName, lastName)) {
            return;
        }

        // Check if username already exists
        if (userDAO.usernameExists(username)) {
            showError("El nombre de usuario ya existe");
            return;
        }

        // Check if email already exists
        if (userDAO.emailExists(email)) {
            showError("El correo electrónico ya está registrado");
            return;
        }

        // Create new user
        User newUser = new User(username, email, password, firstName, lastName);
        
        if (userDAO.createUser(newUser)) {
            showSuccess("Usuario registrado exitosamente");
            // Navigate to login after a short delay
            new Thread(() -> {
                try {
                    Thread.sleep(2000);
                    javafx.application.Platform.runLater(this::navigateToLogin);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }).start();
        } else {
            showError("Error al registrar usuario. Intente nuevamente.");
        }
    }

    /**
     * Handle login link click
     */
    @FXML
    private void handleLogin() {
        navigateToLogin();
    }

    /**
     * Validate form data
     */
    private boolean validateForm(String username, String email, String password, String confirmPassword, String firstName, String lastName) {
        // Check for empty fields
        if (username.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() || firstName.isEmpty() || lastName.isEmpty()) {
            showError("Por favor complete todos los campos");
            return false;
        }

        // Validate username length
        if (username.length() < 3) {
            showError("El nombre de usuario debe tener al menos 3 caracteres");
            return false;
        }

        // Validate email format
        if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            showError("Por favor ingrese un correo electrónico válido");
            return false;
        }

        // Validate password length
        if (password.length() < 6) {
            showError("La contraseña debe tener al menos 6 caracteres");
            return false;
        }

        // Validate password confirmation
        if (!password.equals(confirmPassword)) {
            showError("Las contraseñas no coinciden");
            return false;
        }

        return true;
    }

    /**
     * Navigate to login view
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
            showError("Error al cargar la vista de login: " + e.getMessage());
        }
    }

    /**
     * Show error message to user
     */
    private void showError(String message) {
        errorLabel.setText(message);
        errorLabel.setVisible(true);
        errorLabel.setStyle("-fx-text-fill: red;");
    }

    /**
     * Show success message to user
     */
    private void showSuccess(String message) {
        errorLabel.setText(message);
        errorLabel.setVisible(true);
        errorLabel.setStyle("-fx-text-fill: green;");
    }

    /**
     * Clear error message
     */
    private void clearError() {
        errorLabel.setText("");
        errorLabel.setVisible(false);
    }
} 