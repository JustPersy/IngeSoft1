package com.scalia.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import com.scalia.Main;
import com.scalia.dao.TheoryContentDAO;
import com.scalia.models.TheoryContent;

import java.io.IOException;
import java.util.List;

/**
 * Controller for the Theory Module View
 * Handles music theory lessons and content
 */
public class TheoryController {

    @FXML
    private ListView<String> categoryListView;

    @FXML
    private ListView<TheoryContent> contentListView;

    @FXML
    private TextArea contentTextArea;

    @FXML
    private Label titleLabel;

    @FXML
    private ComboBox<String> difficultyComboBox;

    @FXML
    private Button backButton;

    @FXML
    private VBox contentContainer;

    @FXML
    private ScrollPane contentScrollPane;

    private TheoryContentDAO theoryContentDAO;

    /**
     * Initialize the controller
     */
    @FXML
    public void initialize() {
        theoryContentDAO = new TheoryContentDAO();
        
        // Initialize difficulty filter
        difficultyComboBox.getItems().addAll("Todos", "Principiante", "Intermedio", "Avanzado");
        difficultyComboBox.setValue("Todos");
        
        // Load categories
        loadCategories();
        
        // Set up event handlers
        categoryListView.getSelectionModel().selectedItemProperty().addListener(
            (observable, oldValue, newValue) -> {
                if (newValue != null) {
                    loadContentByCategory(newValue);
                }
            }
        );
        
        contentListView.getSelectionModel().selectedItemProperty().addListener(
            (observable, oldValue, newValue) -> {
                if (newValue != null) {
                    displayContent(newValue);
                }
            }
        );
        
        difficultyComboBox.setOnAction(e -> filterByDifficulty());
        
        // Initialize with first category if available
        if (!categoryListView.getItems().isEmpty()) {
            categoryListView.getSelectionModel().selectFirst();
        }
    }
    
    /**
     * Load all available categories
     */
    private void loadCategories() {
        List<String> categories = theoryContentDAO.getAllCategories();
        
        // Add default categories if database is empty
        if (categories.isEmpty()) {
            categories.add("Escalas");
            categories.add("Acordes");
            categories.add("Intervalos");
            categories.add("Ritmo");
            categories.add("Armonía");
        }
        
        categoryListView.getItems().setAll(categories);
    }
    
    /**
     * Load content by selected category
     */
    private void loadContentByCategory(String category) {
        List<TheoryContent> contents = theoryContentDAO.getTheoryContentByCategory(category);
        
        // Add sample content if database is empty
        if (contents.isEmpty()) {
            contents = createSampleContent(category);
        }
        
        contentListView.getItems().setAll(contents);
        
        // Auto-select first item
        if (!contents.isEmpty()) {
            contentListView.getSelectionModel().selectFirst();
        }
    }
    
    /**
     * Filter content by difficulty
     */
    private void filterByDifficulty() {
        String selectedDifficulty = difficultyComboBox.getValue();
        String selectedCategory = categoryListView.getSelectionModel().getSelectedItem();
        
        if (selectedCategory == null) return;
        
        List<TheoryContent> contents;
        if ("Todos".equals(selectedDifficulty)) {
            contents = theoryContentDAO.getTheoryContentByCategory(selectedCategory);
        } else {
            contents = theoryContentDAO.getTheoryContentByDifficulty(selectedDifficulty);
            // Filter by category as well
            contents.removeIf(content -> !selectedCategory.equals(content.getCategory()));
        }
        
        // Add sample content if database is empty
        if (contents.isEmpty()) {
            contents = createSampleContent(selectedCategory);
        }
        
        contentListView.getItems().setAll(contents);
    }
    
    /**
     * Display selected content
     */
    private void displayContent(TheoryContent content) {
        titleLabel.setText(content.getTitle());
        contentTextArea.setText(formatContent(content));
    }
    
    /**
     * Format content for display
     */
    private String formatContent(TheoryContent content) {
        StringBuilder formatted = new StringBuilder();
        formatted.append("CATEGORÍA: ").append(content.getCategory()).append("\n");
        formatted.append("DIFICULTAD: ").append(content.getDifficulty()).append("\n\n");
        formatted.append("CONTENIDO:\n").append(content.getContent()).append("\n\n");
        
        if (content.getExamples() != null && !content.getExamples().isEmpty()) {
            formatted.append("EJEMPLOS:\n").append(content.getExamples());
        }
        
        return formatted.toString();
    }
    
    /**
     * Create sample content for demonstration
     */
    private List<TheoryContent> createSampleContent(String category) {
        List<TheoryContent> sampleContent = new java.util.ArrayList<>();
        
        switch (category) {
            case "Escalas":
                sampleContent.add(new TheoryContent(1, "Escala Mayor", 
                    "La escala mayor es una secuencia de siete notas que sigue el patrón: Tono-Tono-Semitono-Tono-Tono-Tono-Semitono.\n\n" +
                    "Características:\n" +
                    "- Es la base de la música occidental\n" +
                    "- Tiene un sonido alegre y brillante\n" +
                    "- Se usa en muchos géneros musicales", 
                    "Escalas", "Principiante", 
                    "Do Mayor: Do-Re-Mi-Fa-Sol-La-Si-Do\nSol Mayor: Sol-La-Si-Do-Re-Mi-Fa#-Sol", 1));
                    
                sampleContent.add(new TheoryContent(2, "Escala Menor Natural", 
                    "La escala menor natural sigue el patrón: Tono-Semitono-Tono-Tono-Semitono-Tono-Tono.\n\n" +
                    "Características:\n" +
                    "- Sonido más melancólico que la escala mayor\n" +
                    "- Se forma bajando el 3er, 6to y 7mo grado de la escala mayor\n" +
                    "- Muy usada en música clásica y popular", 
                    "Escalas", "Principiante", 
                    "La menor: La-Si-Do-Re-Mi-Fa-Sol-La\nMi menor: Mi-Fa#-Sol-La-Si-Do-Re-Mi", 2));
                break;
                
            case "Acordes":
                sampleContent.add(new TheoryContent(3, "Acordes Triadas", 
                    "Un acorde triada está formado por tres notas: la fundamental, la tercera y la quinta.\n\n" +
                    "Tipos de triadas:\n" +
                    "- Mayor: 3era mayor y 5ta justa\n" +
                    "- Menor: 3era menor y 5ta justa\n" +
                    "- Disminuido: 3era menor y 5ta disminuida\n" +
                    "- Aumentado: 3era mayor y 5ta aumentada", 
                    "Acordes", "Principiante", 
                    "Do Mayor: Do-Mi-Sol\nLa menor: La-Do-Mi\nSi disminuido: Si-Re-Fa", 1));
                break;
                
            case "Intervalos":
                sampleContent.add(new TheoryContent(4, "Intervalos Básicos", 
                    "Un intervalo es la distancia entre dos notas musicales.\n\n" +
                    "Tipos de intervalos:\n" +
                    "- Unísono (0 semitonos)\n" +
                    "- Segunda menor (1 semitono)\n" +
                    "- Segunda mayor (2 semitonos)\n" +
                    "- Tercera menor (3 semitonos)\n" +
                    "- Tercera mayor (4 semitonos)", 
                    "Intervalos", "Principiante", 
                    "Do a Re = Segunda mayor\nDo a Mi = Tercera mayor\nDo a Fa = Cuarta justa", 1));
                break;
                
            default:
                sampleContent.add(new TheoryContent(5, "Introducción a " + category, 
                    "Este es el contenido introductorio para " + category + ".\n\n" +
                    "Aquí aprenderás los conceptos fundamentales y cómo aplicarlos en la práctica musical.", 
                    category, "Principiante", "Ejemplos prácticos vendrán aquí.", 1));
        }
        
        return sampleContent;
    }
    
    /**
     * Go back to main menu
     */
    @FXML
    private void goBack() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/MainView.fxml"));
            Parent root = fxmlLoader.load();
            
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/css/styles.css").toExternalForm());
            
            Stage stage = Main.getPrimaryStage();
            stage.setScene(scene);
        } catch (IOException e) {
            System.err.println("Error loading main view: " + e.getMessage());
            showAlert("Error", "No se pudo cargar la vista principal.");
        }
    }
    
    /**
     * Show alert dialog
     */
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
