package practica.Pagini;

import java.util.Set;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import practica.ClaseCuDate.Angajat;
import practica.ClaseCuDate.Serviciu;
import practica.ClaseCuDate.StudiouYoga;

public class ServiciiPanel extends VBox {

    private TableView<Serviciu> serviciiTable;
    private TextField searchField;
    private Button addButton;
    private Button modifyButton;
    private Button deleteButton;
    private static Angajat angajatLogat;
    private static ObservableList<Serviciu> servicii = FXCollections.observableArrayList();
    private FilteredList<Serviciu> filteredServicii;
    private static StudiouYoga studio;
    private static Angajat angajat;

    public ServiciiPanel(StudiouYoga s, Angajat a) {
        this.studio = s;
        this.angajat = a;
        angajatLogat = angajat;
        setPadding(new Insets(20));
        setSpacing(10);

        // Initialize controls
        searchField = new TextField();
        searchField.setPromptText("Caută...");

        addButton = new Button("Adaugă");
        modifyButton = new Button("Modifică");
        deleteButton = new Button("Șterge");

        // Layout for buttons
        HBox buttonsBox = new HBox(10);
        buttonsBox.getChildren().addAll(addButton, modifyButton, deleteButton);

        serviciiTable = new TableView<>();
        serviciiTable.setPrefHeight(550);
        setupServiciiTable();

        // Layout setup
        getChildren().addAll(searchField, serviciiTable, buttonsBox);
        VBox.setMargin(buttonsBox, new Insets(10, 0, 0, 0)); // Top margin for buttons

        // Event handlers
        addButton.setOnAction(e -> addServiciu());
        modifyButton.setOnAction(e -> modifyServiciu());
        deleteButton.setOnAction(e -> deleteServiciu());

        // Load and display servicii
        loadServicii();

        filteredServicii = new FilteredList<>(servicii, p -> true);
        serviciiTable.setItems(filteredServicii);

        // Add search functionality
        searchField.textProperty().addListener((observable, oldValue, newValue) ->
                filteredServicii.setPredicate(serviciu -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }
                    String lowerCaseFilter = newValue.toLowerCase();
                    return serviciu.getTip().toLowerCase().contains(lowerCaseFilter) ||
                            String.valueOf(serviciu.getPretPerOra()).toLowerCase().contains(lowerCaseFilter) ||
                            serviciu.getLocatie().toLowerCase().contains(lowerCaseFilter);
                })
        );
    }

    private void setupServiciiTable() {
        TableColumn<Serviciu, String> numeColumn = new TableColumn<>("Nume");
        numeColumn.setCellValueFactory(new PropertyValueFactory<>("tip"));

        TableColumn<Serviciu, Double> pretColumn = new TableColumn<>("Pret");
        pretColumn.setCellValueFactory(new PropertyValueFactory<>("pretPerOra"));

        TableColumn<Serviciu, String> locatieColumn = new TableColumn<>("Locatie");
        locatieColumn.setCellValueFactory(new PropertyValueFactory<>("locatie"));

        TableColumn<Serviciu, String> antrenorColumn = new TableColumn<>("Antrenor");
        antrenorColumn.setCellValueFactory(new PropertyValueFactory<>("antrenor"));

        serviciiTable.getColumns().addAll(numeColumn, pretColumn, locatieColumn, antrenorColumn);
    }

    private void addServiciu() {
        ServiciuFormDialog dialog = new ServiciuFormDialog(null, angajat);
        dialog.showAndWait().ifPresent(serviciu -> {
            studio.adaugaServiciu(serviciu);
            servicii.add(serviciu);
            serviciiTable.refresh();
        });
    }

    private void modifyServiciu() {
        Serviciu selectedServiciu = serviciiTable.getSelectionModel().getSelectedItem();
        if (selectedServiciu != null) {
            ServiciuFormDialog dialog = new ServiciuFormDialog(selectedServiciu, angajat);
            dialog.showAndWait().ifPresent(updatedServiciu -> {
                selectedServiciu.setTip(updatedServiciu.getTip());
                selectedServiciu.setPretPerOra(updatedServiciu.getPretPerOra());
                selectedServiciu.setLocatie(updatedServiciu.getLocatie());
                serviciiTable.refresh();
            });
        } else {
            showAlert("Niciun serviciu selectat", "Selectează un serviciu pentru a-l modifica.");
        }
    }

    private void deleteServiciu() {
        Serviciu selectedServiciu = serviciiTable.getSelectionModel().getSelectedItem();
        if (selectedServiciu != null) {
            studio.removeServiciu(selectedServiciu);
            servicii.remove(selectedServiciu);
            serviciiTable.refresh();
        } else {
            showAlert("Niciun serviciu selectat", "Selectează un serviciu pentru a-l șterge.");
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void loadServicii() {
        servicii.clear();
        for(Serviciu s : studio.getServicii()) {
            if(s.getAntrenor().equals(angajat.getNume())) {
                servicii.add(s);
            }
        }
        
    }
}

class ServiciuFormDialog extends Dialog<Serviciu> {

    private TextField numeField;
    private TextField pretField;
    private ComboBox<String> locatieField;

    public ServiciuFormDialog(Serviciu serviciu, Angajat angajat) {
        setTitle(serviciu == null ? "Adaugă serviciu nou" : "Modifică serviciu");
        setHeaderText(serviciu == null ? "Introdu detalii despre serviciu" : "Modifică detalii despre serviciu");

        // Initialize fields
        numeField = new TextField();
        pretField = new TextField();
        locatieField = new ComboBox<>();
        locatieField.getItems().addAll("Lotus Centru", "Lotus Buiucani", "Lotus Râșcani", "Lotus Botanica");

        // Populate fields with existing data if modifying
        if (serviciu != null) {
            numeField.setText(serviciu.getTip());
            pretField.setText(String.valueOf(serviciu.getPretPerOra()));
            locatieField.setValue(serviciu.getLocatie());
        }

        // Layout setup
        getDialogPane().setContent(createGridPane());

        // Button setup
        ButtonType saveButtonType = new ButtonType("Salvează", ButtonBar.ButtonData.OK_DONE);
        getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);

        // Input validation
        Node saveButton = getDialogPane().lookupButton(saveButtonType);
        saveButton.setDisable(true);
        numeField.textProperty().addListener((observable, oldValue, newValue) -> validateInput(saveButton));
        pretField.textProperty().addListener((observable, oldValue, newValue) -> validateInput(saveButton));
        locatieField.valueProperty().addListener((observable, oldValue, newValue) -> validateInput(saveButton));

        // Result converter
        setResultConverter(dialogButton -> {
            if (dialogButton == saveButtonType) {
                return createServiciuFromFields(serviciu, angajat);
            }
            return null;
        });
    }

    private void validateInput(Node saveButton) {
        String nume = numeField.getText().trim();
        String pret = pretField.getText().trim();
        String locatie = locatieField.getValue();

        boolean isValid = !nume.isEmpty() && isNumeric(pret) && locatie != null && !locatie.isEmpty();
        saveButton.setDisable(!isValid);
    }

    private boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private GridPane createGridPane() {
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        grid.add(new Label("Nume:"), 0, 0);
        grid.add(numeField, 1, 0);
        grid.add(new Label("Pret:"), 0, 1);
        grid.add(pretField, 1, 1);
        grid        .add(new Label("Locatie:"), 0, 2);
        grid.add(locatieField, 1, 2);

        return grid;
    }

    private Serviciu createServiciuFromFields(Serviciu existingServiciu, Angajat angajat) {
        String nume = numeField.getText().trim();
        double pret = Double.parseDouble(pretField.getText().trim());
        String locatie = locatieField.getValue();

        if (existingServiciu == null) {
            return new Serviciu(nume, locatie, pret, angajat.getNume());
        } else {
            existingServiciu.setTip(nume);
            existingServiciu.setLocatie(locatie);
            existingServiciu.setPretPerOra(pret);
            existingServiciu.setAntrenor(angajat.getNume());
            return existingServiciu;
        }
    }
}

