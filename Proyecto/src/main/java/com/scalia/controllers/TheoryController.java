package com.scalia.controllers;

import com.scalia.dao.TheoryDAO;
import com.scalia.models.TheoryConcept;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import com.scalia.Main;

import java.io.IOException;
import java.util.List;

/**
 * Controller for the music theory module.
 * Displays theory concepts with descriptions.
 */
public class TheoryController {

    @FXML
    private ListView<TheoryConcept> conceptListView;
    @FXML
    private TextArea conceptTextArea;

    @FXML
    public void initialize() {
        List<TheoryConcept> concepts = TheoryDAO.getAllConcepts();
        ObservableList<TheoryConcept> obs = FXCollections.observableArrayList(concepts);
        conceptListView.setItems(obs);
        conceptListView.getSelectionModel().selectedItemProperty().addListener((o, oldV, newV) -> showConcept(newV));
    }

    private void showConcept(TheoryConcept concept) {
        if (concept == null) {
            conceptTextArea.setText("");
            return;
        }
        conceptTextArea.setText(concept.getDescription());
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
            stage.setMaximized(true);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
