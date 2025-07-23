package com.scalia.controllers;

import com.scalia.dao.InstrumentDAO;
import com.scalia.dao.TuningDAO;
import com.scalia.models.Instrument;
import com.scalia.models.Tuning;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import com.scalia.Main;

import java.io.IOException;
import java.util.List;

/**
 * Controller for the tuning library module.
 * Allows selecting an instrument and viewing its tunings.
 */
public class TuningLibraryController {

    @FXML
    private ComboBox<Instrument> instrumentComboBox;
    @FXML
    private ListView<Tuning> tuningListView;

    @FXML
    public void initialize() {
        List<Instrument> instruments = InstrumentDAO.getAllInstruments();
        instrumentComboBox.setItems(FXCollections.observableArrayList(instruments));
        instrumentComboBox.getSelectionModel().selectedItemProperty().addListener((obs, oldV, newV) -> loadTunings(newV));
    }

    private void loadTunings(Instrument instrument) {
        if (instrument == null) {
            tuningListView.setItems(FXCollections.emptyObservableList());
            return;
        }
        List<Tuning> tunings = TuningDAO.getTuningsByInstrument(instrument.getId());
        tuningListView.setItems(FXCollections.observableArrayList(tunings));
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
