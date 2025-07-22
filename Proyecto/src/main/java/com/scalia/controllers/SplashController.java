package com.scalia.controllers;

import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.ParallelTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;
import com.scalia.Main;

public class SplashController {
    @FXML private ImageView logoImageView;

    @FXML
    public void initialize() {
        // Cargar el logo
        Image logo = new Image(getClass().getResource("/assets/scalialogo.png").toExternalForm());
        logoImageView.setImage(logo);

        // Animación de fade-in y scale
        FadeTransition fadeIn = new FadeTransition(Duration.millis(1200), logoImageView);
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0);
        ScaleTransition scaleIn = new ScaleTransition(Duration.millis(1200), logoImageView);
        scaleIn.setFromX(0.8);
        scaleIn.setFromY(0.8);
        scaleIn.setToX(1.0);
        scaleIn.setToY(1.0);
        ParallelTransition parallelTransition = new ParallelTransition(fadeIn, scaleIn);
        parallelTransition.play();

        // Esperar 3 segundos y luego ir al login
        parallelTransition.setOnFinished(e -> {
            new Thread(() -> {
                try {
                    Thread.sleep(1800); // 1.2s animación + 1.8s espera = 3s total
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
                javafx.application.Platform.runLater(this::goToLogin);
            }).start();
        });
    }

    private void goToLogin() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/LoginView.fxml"));
            Parent root = loader.load();
            Stage stage = Main.getPrimaryStage();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/css/styles.css").toExternalForm());
            stage.setScene(scene);
            stage.setTitle("Scalia - Teoría Musical");
            stage.setMaximized(true); // Abrir maximizada
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}