package com.scalia;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
// import javafx.stage.StageStyle;

import java.io.IOException;
import com.scalia.models.User;

/**
 * Main application class for Scalia - Music Theory Desktop Application
 * 
 * @author Juan Manuel Cristancho Álvarez
 * @version 1.0
 */
public class Main extends Application {

    private static Stage primaryStage;
    private static User currentUser;

    @Override
    public void start(Stage stage) throws IOException {
        primaryStage = stage;
        
        // Load the main FXML file
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/fxml/LoginView.fxml"));
        Parent root = fxmlLoader.load();
        
        // Create and configure the scene
        Scene scene = new Scene(root, 800, 600);
        scene.getStylesheets().add(getClass().getResource("/css/styles.css").toExternalForm());
        
        // Configure the primary stage
        stage.setTitle("Scalia - Teoría Musical");
        stage.setScene(scene);
        stage.setMinWidth(800);
        stage.setMinHeight(600);
        stage.setResizable(true);
        
        // Show the application
        stage.show();
    }

    /**
     * Get the primary stage for navigation between views
     * @return The primary stage
     */
    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    /**
     * Get the current user
     * @return The current user
     */
    public static User getCurrentUser() {
        return currentUser;
    }

    /**
     * Set the current user
     * @param user The user to set as current
     */
    public static void setCurrentUser(User user) {
        currentUser = user;
    }

    /**
     * Main method to launch the application
     * @param args Command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
