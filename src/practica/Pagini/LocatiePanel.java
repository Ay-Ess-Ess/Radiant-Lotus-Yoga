package practica.Pagini;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import practica.ClaseCuDate.Angajat;
import practica.ClaseCuDate.Client;
import practica.ClaseCuDate.Locatie;
import practica.ClaseCuDate.StudiouYoga;

public class LocatiePanel extends VBox {

    private TableView<Locatie> locatieTable;
    private TextField searchField;
    private Button addButton;
    private Button modifyButton;
    private Button deleteButton;
    private static StudiouYoga studio;
    private FilteredList<Locatie> filteredLocatii;
    private static ObservableList<Locatie> locatii = FXCollections.observableArrayList();

    public LocatiePanel(StudiouYoga studio) {
        this.studio = studio;
        setPadding(new Insets(20));
        setSpacing(10);

        // Initialize controls
        searchField = new TextField();
        searchField.setPromptText("Search...");

        addButton = new Button("Add");
        modifyButton = new Button("Modify");
        deleteButton = new Button("Delete");

        locatieTable = new TableView<>();
        locatieTable.setPrefHeight(550);
        setupLocatieTable();

        // Layout setup
        GridPane buttonPane = new GridPane();
        buttonPane.setHgap(10);
        buttonPane.add(addButton, 0, 0);
        buttonPane.add(modifyButton, 1, 0);
        buttonPane.add(deleteButton, 2, 0);

        getChildren().addAll(searchField, locatieTable, buttonPane);

        // Event handlers
        addButton.setOnAction(e -> addLocatie());
        modifyButton.setOnAction(e -> modifyLocatie());
        deleteButton.setOnAction(e -> deleteLocatie());

        // Load and display locatii
        loadLocatii();

        filteredLocatii = new FilteredList<>(locatii, p -> true);
        locatieTable.setItems(filteredLocatii);

        // Add search functionality
        searchField.textProperty().addListener((observable, oldValue, newValue) ->
            filteredLocatii.setPredicate(locatie -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();
                return locatie.getNume().toLowerCase().contains(lowerCaseFilter) ||
                       locatie.getAdresa().toLowerCase().contains(lowerCaseFilter) ||
                       locatie.getTelefon().toLowerCase().contains(lowerCaseFilter) ||
                       locatie.getPersoanaContact().toLowerCase().contains(lowerCaseFilter);
            })
        );
    }

    private void setupLocatieTable() {
        TableColumn<Locatie, Boolean> selectColumn = new TableColumn<>("Select");
        selectColumn.setCellValueFactory(cellData -> cellData.getValue().selectedProperty());
        selectColumn.setCellFactory(column -> {
            CheckBoxTableCell<Locatie, Boolean> cell = new CheckBoxTableCell<>();
            cell.setEditable(true); // Make sure the cell is editable
            return cell;
        });
        
        selectColumn.setCellFactory(column -> {
        TableCell<Locatie, Boolean> cell = new TableCell<>() {
            private final CheckBox checkBox = new CheckBox();

            {
                checkBox.setOnAction(event -> {
                    Locatie locatie = getTableView().getItems().get(getIndex());
                    locatie.setSelected(checkBox.isSelected());
                });
            }

            @Override
            protected void updateItem(Boolean item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    checkBox.setSelected(item);
                    setGraphic(checkBox);
                }
            }
        };

        cell.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        return cell;
    });

        TableColumn<Locatie, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getNume()));
        nameColumn.setCellFactory(column -> createClickableCell());

        TableColumn<Locatie, String> addressColumn = new TableColumn<>("Address");
        addressColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getAdresa()));

        TableColumn<Locatie, String> phoneColumn = new TableColumn<>("Phone");
        phoneColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getTelefon()));

        TableColumn<Locatie, String> persoanaContactColumn = new TableColumn<>("Persoana Contact");
        persoanaContactColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getPersoanaContact()));

        locatieTable.getColumns().addAll(selectColumn, nameColumn, addressColumn, phoneColumn, persoanaContactColumn);
    }

    private TableCell<Locatie, String> createClickableCell() {
        TableCell<Locatie, String> cell = new TableCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? null : item);
                setGraphic(null);
            }
        };

        cell.setOnMouseClicked(event -> {
            if (!cell.isEmpty() && event.getButton() == MouseButton.PRIMARY) {
                String cellValue = cell.getItem();
                System.out.println("Cell clicked: " + cellValue);
                showLocatieDetails(cell.getTableRow().getItem());
            }
        });

        return cell;
    }

    private void showLocatieDetails(Locatie locatie) {
        if (locatie != null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Locatie Details");
            alert.setHeaderText("Details about " + locatie.getNume());
            alert.setContentText("Name: " + locatie.getNume() + "\n" +
                                 "Address: " + locatie.getAdresa() + "\n" +
                                 "Phone: " + locatie.getTelefon() + "\n" +
                                 "Persoana Contact: " + locatie.getPersoanaContact());
            alert.showAndWait();
        }
    }

    private void addLocatie() {
        LocatieFormDialog dialog = new LocatieFormDialog(null, studio);
        dialog.showAndWait().ifPresent(locatie -> {
            studio.addLocatie(locatie);
            locatii.add(locatie);
        });
        studio.salveazaDate("src/practica/Date/locatii.txt", "src/practica/Date/users.txt", "src/practica/Date/servicii.txt", "src/practica/Date/contracte.txt", "src/practica/Date/accesorii.txt");
    }

    private void modifyLocatie() {
        Locatie selectedLocatie = locatieTable.getSelectionModel().getSelectedItem();
        if (selectedLocatie != null) {
            LocatieFormDialog dialog = new LocatieFormDialog(selectedLocatie, studio);
            dialog.showAndWait().ifPresent(updatedLocatie -> {
                selectedLocatie.setNume(updatedLocatie.getNume());
                selectedLocatie.setAdresa(updatedLocatie.getAdresa());
                selectedLocatie.setTelefon(updatedLocatie.getTelefon());
                selectedLocatie.setPersoanaContact(updatedLocatie.getPersoanaContact());
                locatieTable.refresh();
            });
            studio.salveazaDate("src/practica/Date/locatii.txt", "src/practica/Date/users.txt", "src/practica/Date/servicii.txt", "src/practica/Date/contracte.txt", "src/practica/Date/accesorii.txt");
        } else {
            showAlert("No locatie selected", "Please select a locatie to modify.");
        }
    }

    private void deleteLocatie() {
        ObservableList<Locatie> selectedLocatii = FXCollections.observableArrayList();
        for (Locatie locatie : locatii) {
            if (locatie.isSelected()) {
                selectedLocatii.add(locatie);
            }
        }

        if (!selectedLocatii.isEmpty()) {
            locatii.removeAll(selectedLocatii);
            for (Locatie locatie : selectedLocatii) {
                studio.removeLocatie(locatie);
            }
            studio.salveazaDate("src/practica/Date/locatii.txt", "src/practica/Date/users.txt", "src/practica/Date/servicii.txt", "src/practica/Date/contracte.txt", "src/practica/Date/accesorii.txt");
        } else {
            showAlert("No locatii selected", "Please select at least one locatie to delete.");
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void loadLocatii() {
        locatii.clear();
        locatii.addAll(studio.getLocatii());
    }
}

class LocatieFormDialog extends Dialog<Locatie> {

    private TextField nameField;
    private TextField addressField;
    private TextField phoneField;
    private ComboBox<String> persoanaContactField;

    public LocatieFormDialog(Locatie locatie, StudiouYoga studio) {
        App a = new App();
        
        setTitle(locatie == null ? "Add Locatie" : "Modify Locatie");
        setHeaderText(locatie == null ? "Enter new locatie details" : "Modify locatie details");

        // Create form fields
        nameField = new TextField();
        addressField = new TextField();
        phoneField = new TextField();
        persoanaContactField = new ComboBox<>();
        for (Angajat angajat : studio.getAngajati()) {
            if(angajat.getPower().equalsIgnoreCase("manager")) {
                persoanaContactField.getItems().add(angajat.getNume());
            }
        }
        if (locatie != null) {
            nameField.setText(locatie.getNume());
            addressField.setText(locatie.getAdresa());
            phoneField.setText(locatie.getTelefon());
            persoanaContactField.setValue(locatie.getPersoanaContact());
        }

        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.add(new Label("Name:"), 0, 0);
        gridPane.add(nameField, 1, 0);
        gridPane.add(new Label("Address:"), 0, 1);
        gridPane.add(addressField, 1, 1);
        gridPane.add(new Label("Phone:"), 0, 2);
        gridPane.add(phoneField, 1, 2);
        gridPane.add(new Label("Persoana Contact:"), 0, 3);
        gridPane.add(persoanaContactField, 1, 3);

        getDialogPane().setContent(gridPane);

        ButtonType saveButtonType = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);
        
        Button saveButton = (Button) getDialogPane().lookupButton(saveButtonType);
        saveButton.addEventFilter(ActionEvent.ACTION, event -> {
            if (!validateInput(a, studio, locatie)) {
                event.consume();
            }
        });

        setResultConverter(dialogButton -> {
            if (dialogButton == saveButtonType) {
                return new Locatie(
                        nameField.getText(),
                        addressField.getText(),
                        phoneField.getText(),
                        persoanaContactField.getSelectionModel().getSelectedItem()
                );
            }
            return null;
        });
    }
    private boolean validateInput(App a, StudiouYoga studio, Locatie locatie) {
        
        if (nameField == null || addressField == null || phoneField == null || persoanaContactField == null) {
            a.showAlert("Registration Error", "Nu sunt introduse toate datele.");
            return false;
        } else if (nameField.getText().matches(".*\\d.*") || nameField.getText().length() < 6) {
            a.showAlert("Registration Error", "Numele contine cifre sau e prea scurt.");
            return false;
        } else if (addressField.getText().length() <= 5) {
            a.showAlert("Registration Error", "Adresa invalida.");
            return false;
        } else if (phoneField.getText().length() != 9 || (!phoneField.getText().startsWith("06") && !phoneField.getText().startsWith("07"))) {
            a.showAlert("Registration Error", "Numar de telefon invalid.");
            return false;
        } 
        return true;
    }
}
