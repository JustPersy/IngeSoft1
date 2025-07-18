package com.scalia.controllers;
import com.scalia.dao.InstrumentDAO;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import com.scalia.models.Instrument;


public class InstrumentLibraryController {

    @FXML private TableView<Instrument> instrumentTable;
    @FXML private TableColumn<Instrument, String> nameColumn;
    @FXML private TableColumn<Instrument, String> typeColumn;
    @FXML private TableColumn<Instrument, String> descriptionColumn;
    @FXML private TableColumn<Instrument, String> tuningColumn;

    private boolean initialized = false;

    @FXML
    public void initialize() {
        if (initialized) return; // 🔒 Evita recargar múltiples veces

        // Configurar columnas
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        tuningColumn.setCellValueFactory(new PropertyValueFactory<>("tuningStandard"));

        // Limpiar y cargar datos
        instrumentTable.setItems(javafx.collections.FXCollections.observableArrayList(
                InstrumentDAO.getAllInstruments()
        ));


        initialized = true;
    }
}
