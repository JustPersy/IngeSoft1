package com.scalia.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
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
    private Button guestButton;

    @FXML
    private Label errorLabel;

    @FXML
    private Hyperlink registerLink;

    @FXML
    private ImageView logoImageView;

    @FXML
    private VBox loginContainer;

    @FXML
    private VBox logoVBox;

    @FXML
    private HBox buttonHBox;

    @FXML
    private HBox registerHBox;

    private UserDAO userDAO;

    @FXML
    private void initialize() {
        userDAO = new UserDAO();
        // Cargar el logo en el login
        if (logoImageView != null) {
            logoImageView.setImage(new javafx.scene.image.Image(getClass().getResource("/assets/scalialogo.png").toExternalForm()));
        }

        // Animación suave de hover para loginButton y guestButton
        setupButtonHoverAnimation(loginButton);
        setupButtonHoverAnimation(guestButton);
        setupFieldFocusAnimation(usernameField);
        setupFieldFocusAnimation(passwordField);
        playEntryAnimation();
    }

    private void setupButtonHoverAnimation(Button button) {
        if (button == null) return;
        final double scaleTo = 1.04;
        final double opacityTo = 0.85;
        final double duration = 320; // ms

        javafx.animation.ScaleTransition scaleUp = new javafx.animation.ScaleTransition(javafx.util.Duration.millis(duration), button);
        scaleUp.setToX(scaleTo);
        scaleUp.setToY(scaleTo);
        javafx.animation.FadeTransition fadeOut = new javafx.animation.FadeTransition(javafx.util.Duration.millis(duration), button);
        fadeOut.setToValue(opacityTo);

        javafx.animation.ScaleTransition scaleDown = new javafx.animation.ScaleTransition(javafx.util.Duration.millis(duration), button);
        scaleDown.setToX(1.0);
        scaleDown.setToY(1.0);
        javafx.animation.FadeTransition fadeIn = new javafx.animation.FadeTransition(javafx.util.Duration.millis(duration), button);
        fadeIn.setToValue(1.0);

        button.setOnMouseEntered(e -> {
            scaleDown.stop(); fadeIn.stop();
            scaleUp.playFromStart(); fadeOut.playFromStart();
        });
        button.setOnMouseExited(e -> {
            scaleUp.stop(); fadeOut.stop();
            scaleDown.playFromStart(); fadeIn.playFromStart();
        });
    }

    private void setupFieldFocusAnimation(TextField field) {
        if (field == null) return;
        final double scaleTo = 1.035;
        final double duration = 180;
        javafx.animation.ScaleTransition scaleUp = new javafx.animation.ScaleTransition(javafx.util.Duration.millis(duration), field);
        scaleUp.setToX(scaleTo);
        scaleUp.setToY(scaleTo);
        javafx.animation.ScaleTransition scaleDown = new javafx.animation.ScaleTransition(javafx.util.Duration.millis(duration), field);
        scaleDown.setToX(1.0);
        scaleDown.setToY(1.0);
        field.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal) { scaleDown.stop(); scaleUp.playFromStart(); }
            else { scaleUp.stop(); scaleDown.playFromStart(); }
        });
    }

    private void playEntryAnimation() {
        javafx.animation.SequentialTransition seq = new javafx.animation.SequentialTransition();
        javafx.animation.FadeTransition fadeLogo = new javafx.animation.FadeTransition(javafx.util.Duration.millis(350), logoVBox);
        fadeLogo.setFromValue(0); fadeLogo.setToValue(1);
        fadeLogo.setDelay(javafx.util.Duration.millis(80));
        logoVBox.setOpacity(0);
        javafx.animation.TranslateTransition transLogo = new javafx.animation.TranslateTransition(javafx.util.Duration.millis(350), logoVBox);
        transLogo.setFromY(30); transLogo.setToY(0);
        javafx.animation.ParallelTransition logoAnim = new javafx.animation.ParallelTransition(fadeLogo, transLogo);

        javafx.animation.FadeTransition fadeContainer = new javafx.animation.FadeTransition(javafx.util.Duration.millis(400), loginContainer);
        fadeContainer.setFromValue(0); fadeContainer.setToValue(1);
        fadeContainer.setDelay(javafx.util.Duration.millis(180));
        loginContainer.setOpacity(0);
        javafx.animation.TranslateTransition transContainer = new javafx.animation.TranslateTransition(javafx.util.Duration.millis(400), loginContainer);
        transContainer.setFromY(40); transContainer.setToY(0);
        javafx.animation.ParallelTransition contAnim = new javafx.animation.ParallelTransition(fadeContainer, transContainer);

        javafx.animation.FadeTransition fadeBtns = new javafx.animation.FadeTransition(javafx.util.Duration.millis(350), buttonHBox);
        fadeBtns.setFromValue(0); fadeBtns.setToValue(1);
        fadeBtns.setDelay(javafx.util.Duration.millis(350));
        buttonHBox.setOpacity(0);
        javafx.animation.TranslateTransition transBtns = new javafx.animation.TranslateTransition(javafx.util.Duration.millis(350), buttonHBox);
        transBtns.setFromY(20); transBtns.setToY(0);
        javafx.animation.ParallelTransition btnAnim = new javafx.animation.ParallelTransition(fadeBtns, transBtns);

        javafx.animation.FadeTransition fadeReg = new javafx.animation.FadeTransition(javafx.util.Duration.millis(350), registerHBox);
        fadeReg.setFromValue(0); fadeReg.setToValue(1);
        fadeReg.setDelay(javafx.util.Duration.millis(400));
        registerHBox.setOpacity(0);
        javafx.animation.TranslateTransition transReg = new javafx.animation.TranslateTransition(javafx.util.Duration.millis(350), registerHBox);
        transReg.setFromY(20); transReg.setToY(0);
        javafx.animation.ParallelTransition regAnim = new javafx.animation.ParallelTransition(fadeReg, transReg);

        seq.getChildren().addAll(logoAnim, contAnim, btnAnim, regAnim);
        seq.play();
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
     * Handle guest login button click
     */
    @FXML
    private void handleGuestLogin() {
        // Crear usuario invitado
        User guestUser = new User();
        guestUser.setUsername("Invitado");
        guestUser.setGuest(true);
        // Guardar el usuario invitado en algún lugar accesible (por ejemplo, Main o un UserSession)
        Main.setCurrentUser(guestUser);
        navigateToMainView();
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
            forceFullHD(stage);
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
            forceFullHD(stage);
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

    /**
     * Fuerza la ventana a 1920x1080 (Full HD), centrada y a pantalla completa, en cualquier módulo.
     */
    private void forceFullHD(Stage stage) {
        double targetWidth = 1920;
        double targetHeight = 1080;
        javafx.geometry.Rectangle2D bounds = javafx.stage.Screen.getPrimary().getVisualBounds();
        // Centrar en la pantalla activa
        for (javafx.stage.Screen screen : javafx.stage.Screen.getScreens()) {
            if (stage.getX() >= screen.getVisualBounds().getMinX() &&
                stage.getX() < screen.getVisualBounds().getMaxX() &&
                stage.getY() >= screen.getVisualBounds().getMinY() &&
                stage.getY() < screen.getVisualBounds().getMaxY()) {
                bounds = screen.getVisualBounds();
                break;
            }
        }
        double x = bounds.getMinX() + (bounds.getWidth() - targetWidth) / 2;
        double y = bounds.getMinY() + (bounds.getHeight() - targetHeight) / 2;
        stage.setX(Math.max(x, bounds.getMinX()));
        stage.setY(Math.max(y, bounds.getMinY()));
        stage.setWidth(targetWidth);
        stage.setHeight(targetHeight);
        stage.setMinWidth(targetWidth);
        stage.setMinHeight(targetHeight);
        stage.setMaxWidth(targetWidth);
        stage.setMaxHeight(targetHeight);
        stage.setResizable(false);
        stage.setFullScreen(false);
        stage.setMaximized(false);
    }
}