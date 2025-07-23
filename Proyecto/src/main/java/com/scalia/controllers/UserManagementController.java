package com.scalia.controllers;

import com.scalia.dao.UserDAO;
import com.scalia.models.User;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import com.scalia.Main;

import java.io.IOException;

/**
 * Controller for the user management module.
 * Displays registered users and allows deletion.
 */
public class UserManagementController {

    @FXML
    private TableView<User> userTable;
    @FXML
    private TableColumn<User, String> usernameColumn;
    @FXML
    private TableColumn<User, String> emailColumn;
    @FXML
    private Button deleteButton;

    private final UserDAO userDAO = new UserDAO();

    @FXML
    public void initialize() {
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        refreshUsers();
    }

    private void refreshUsers() {
        userTable.setItems(FXCollections.observableArrayList(userDAO.getAllUsers()));
    }

    @FXML
    private void handleDeleteUser() {
        User selected = userTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            userDAO.deleteUser(selected.getId());
            refreshUsers();
        }
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
