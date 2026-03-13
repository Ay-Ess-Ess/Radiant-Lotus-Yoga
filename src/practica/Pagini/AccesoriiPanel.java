package practica.Pagini;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import practica.ClaseCuDate.Accesoriu;
import practica.ClaseCuDate.Angajat;
import practica.ClaseCuDate.Locatie;
import practica.ClaseCuDate.Serviciu;
import practica.ClaseCuDate.StudiouYoga;

public class AccesoriiPanel extends VBox {

    private TableView<Accesoriu> accesoriiTable;
    private TextField searchField;
    private Button addButton;
    private Button modifyButton;
    private Button deleteButton;
    private static StudiouYoga studio;
    private ComboBox<String> locatia;
    private TextField cheltuieli = new TextField();
    private FilteredList<Accesoriu> filteredAccesorii;
    private static ObservableList<Accesoriu> accesorii = FXCollections.observableArrayList();

    public AccesoriiPanel(StudiouYoga studio) {
        this.studio = studio;
        setPadding(new Insets(20));
        setSpacing(10);

        // Initialize controls
        searchField = new TextField();
        searchField.setPromptText("Search...");

        addButton = new Button("Add");
        modifyButton = new Button("Modify");
        deleteButton = new Button("Delete");
        
        locatia = new ComboBox<>();
        locatia.setValue("Locatie");
        for(Locatie l : studio.getLocatii()) 
            locatia.getItems().add(l.getNume());
        
        locatia.setOnAction(event -> updateCost(studio));
        cheltuieli.setEditable(false);
        cheltuieli.setText("0.00");

        accesoriiTable = new TableView<>();
        accesoriiTable.setPrefHeight(550);
        setupAccesoriiTable();

        // Layout setup
        GridPane buttonPane = new GridPane();
        buttonPane.setHgap(10);
        buttonPane.add(addButton, 0, 0);
        buttonPane.add(modifyButton, 1, 0);
        buttonPane.add(deleteButton, 2, 0);
        buttonPane.add(locatia, 7, 0);
        buttonPane.add(cheltuieli, 8, 0);

        getChildren().addAll(searchField, accesoriiTable, buttonPane);

        // Event handlers
        addButton.setOnAction(e -> addAccesorii());
        modifyButton.setOnAction(e -> modifyAccesorii());
        deleteButton.setOnAction(e -> deleteAccesorii());

        // Load and display accesorii
        loadAccesorii();

        filteredAccesorii = new FilteredList<>(accesorii, p -> true);
        accesoriiTable.setItems(filteredAccesorii);

        // Add search functionality
        searchField.textProperty().addListener((observable, oldValue, newValue) ->
                filteredAccesorii.setPredicate(accesoriu -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }
                    String lowerCaseFilter = newValue.toLowerCase();
                    return accesoriu.getNume().toLowerCase().contains(lowerCaseFilter) ||
                            String.valueOf(accesoriu.getCantitate()).toLowerCase().contains(lowerCaseFilter) ||
                            String.valueOf(accesoriu.getPret()).toLowerCase().contains(lowerCaseFilter) ||
                            accesoriu.getLocatie().toLowerCase().contains(lowerCaseFilter);
                })
        );
    }
    
    private void updateCost(StudiouYoga studio) {
        String selectedLocatie = locatia.getValue();
        cheltuieli.setText("");
        double result=0;

        for(Locatie l : studio.getLocatii()) {
            if(l.getNume().equalsIgnoreCase(selectedLocatie)) {
                for(Accesoriu a : l.getAccesorii())
                    result+= a.getPret() * a.getCantitate();
            }
        }
            
        cheltuieli.setText(String.valueOf(result) + "lei");
    }

    private void setupAccesoriiTable() {
        TableColumn<Accesoriu, Boolean> selectColumn = new TableColumn<>("Select");
        selectColumn.setCellValueFactory(cellData -> cellData.getValue().selectedProperty());
        selectColumn.setCellFactory(column -> {
            CheckBoxTableCell<Accesoriu, Boolean> cell = new CheckBoxTableCell<>();
            cell.setEditable(true); // Make sure the cell is editable
            return cell;
        });
        
        selectColumn.setCellFactory(column -> {
        TableCell<Accesoriu, Boolean> cell = new TableCell<>() {
            private final CheckBox checkBox = new CheckBox();

            {
                checkBox.setOnAction(event -> {
                    Accesoriu accesoriu = getTableView().getItems().get(getIndex());
                    accesoriu.setSelected(checkBox.isSelected());
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

        TableColumn<Accesoriu, String> numeColumn = new TableColumn<>("Nume");
        numeColumn.setCellValueFactory(new PropertyValueFactory<>("nume"));
        numeColumn.setCellFactory(column -> createClickableCell());

        TableColumn<Accesoriu, Integer> cantitateColumn = new TableColumn<>("Cantitate");
        cantitateColumn.setCellValueFactory(new PropertyValueFactory<>("cantitate"));

        TableColumn<Accesoriu, Double> pretColumn = new TableColumn<>("Pret");
        pretColumn.setCellValueFactory(new PropertyValueFactory<>("pret"));

        TableColumn<Accesoriu, String> locatieColumn = new TableColumn<>("Locatie");
        locatieColumn.setCellValueFactory(new PropertyValueFactory<>("locatie"));

        accesoriiTable.getColumns().addAll(selectColumn, numeColumn, cantitateColumn, pretColumn, locatieColumn);
    }

    private TableCell<Accesoriu, String> createClickableCell() {
        TableCell<Accesoriu, String> cell = new TableCell<>() {
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
                showAccesoriuDetails(cell.getTableRow().getItem());
            }
        });

        return cell;
    }

    private void showAccesoriuDetails(Accesoriu accesoriu) {
        if (accesoriu != null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Accesoriu Details");
            alert.setHeaderText("Details about " + accesoriu.getNume());
            alert.setContentText("Nume: " + accesoriu.getNume() + "\n" +
                    "Cantitate: " + accesoriu.getCantitate() + "\n" +
                    "Pret: " + accesoriu.getPret() + " lei\n" +
                    "Locatie: " + accesoriu.getLocatie());
            alert.showAndWait();
        }
    }

    private void addAccesorii() {
        AccesoriuFormDialog dialog = new AccesoriuFormDialog(null, studio);
        dialog.showAndWait().ifPresent(accesoriu -> {
            for (Locatie l : studio.getLocatii()) {
                if (l.getNume().equalsIgnoreCase(accesoriu.getLocatie())) {
                    l.addAccesoriu(accesoriu);
                    accesorii.add(accesoriu);
                }
            }
            accesoriiTable.refresh();
            studio.salveazaDate("src/practica/Date/locatii.txt", "src/practica/Date/users.txt", "src/practica/Date/servicii.txt", "src/practica/Date/contracte.txt", "src/practica/Date/accesorii.txt");
        });
    }

    private void modifyAccesorii() {
        Accesoriu selectedAccesorii = accesoriiTable.getSelectionModel().getSelectedItem();
        if (selectedAccesorii != null) {
            AccesoriuFormDialog dialog = new AccesoriuFormDialog(selectedAccesorii, studio);
            dialog.showAndWait().ifPresent(updatedAccesorii -> {
                selectedAccesorii.setNume(updatedAccesorii.getNume());
                selectedAccesorii.setCantitate(updatedAccesorii.getCantitate());
                selectedAccesorii.setPret(updatedAccesorii.getPret());
                selectedAccesorii.setLocatie(updatedAccesorii.getLocatie());
                accesoriiTable.refresh();
                studio.salveazaDate("src/practica/Date/locatii.txt", "src/practica/Date/users.txt", "src/practica/Date/servicii.txt", "src/practica/Date/contracte.txt", "src/practica/Date/accesorii.txt");
            });
        } else {
            showAlert("No accesoriu selected", "Please select an accesoriu to modify.");
        }
    }

    private void deleteAccesorii() {
        ObservableList<Accesoriu> selectedAccesorii = FXCollections.observableArrayList();
        for (Accesoriu accesoriu : accesorii) {
            if (accesoriu.isSelected()) {
                selectedAccesorii.add(accesoriu);
            }
        }

        if (!selectedAccesorii.isEmpty()) {
            accesorii.removeAll(selectedAccesorii);
            for (Accesoriu accesoriu : selectedAccesorii) {
                studio.getLocatii().forEach(locatie -> locatie.removeAccesoriu(accesoriu));
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

    private void loadAccesorii() {
        accesorii.clear();
        studio.getLocatii().forEach(locatie -> accesorii.addAll(locatie.getAccesorii()));
    }
}

class AccesoriuFormDialog extends Dialog<Accesoriu> {

    private TextField numeField;
    private Spinner<Integer> cantitateSpinner;
    private TextField pretField;
    private ComboBox<String> locatieField;

    private final StudiouYoga studio;

    public AccesoriuFormDialog(Accesoriu accesoriu, StudiouYoga studio) {
        this.studio = studio;
        setTitle(accesoriu == null ? "Add Accesoriu" : "Modify Accesoriu");
        setHeaderText(accesoriu == null ? "Enter new accesoriu details" : "Modify accesoriu details");

        // Initialize fields
        numeField = new TextField();
        cantitateSpinner = new Spinner<>(1, 100, 1); // Example range from 1 to 100
        pretField = new TextField();
        locatieField = new ComboBox<>();
        studio.getLocatii().forEach(locatie -> locatieField.getItems().add(locatie.getNume()));

        // Populate fields with existing data if modifying
        if (accesoriu != null) {
            numeField.setText(accesoriu.getNume());
            cantitateSpinner.getValueFactory().setValue(accesoriu.getCantitate());
            pretField.setText(String.valueOf(accesoriu.getPret()));
            locatieField.setValue(accesoriu.getLocatie());
        }

        // Layout setup
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.add(new Label("Nume:"), 0, 0);
        gridPane.add(numeField, 1, 0);
        gridPane.add(new Label("Cantitate:"), 0, 1);
        gridPane.add(cantitateSpinner, 1, 1);
        gridPane.add(new Label("Pret:"), 0, 2);
        gridPane.add(pretField, 1, 2);
        gridPane.add(new Label("Locatie:"), 0, 3);
        gridPane.add(locatieField, 1, 3);

        getDialogPane().setContent(gridPane);

        // Button setup
        ButtonType saveButtonType = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
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
                return createAccesoriuFromFields(accesoriu);
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
        if (str == null || str.isEmpty()) {
            return false;
        }
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private Accesoriu createAccesoriuFromFields(Accesoriu existingAccesoriu) {
        String nume = numeField.getText().trim();
        int cantitate = cantitateSpinner.getValue();
        double pret = Double.parseDouble(pretField.getText().trim());
        String locatie = locatieField.getValue();

        if (existingAccesoriu == null) {
            Accesoriu newAccesoriu = new Accesoriu(nume, cantitate, pret);
            newAccesoriu.setLocatie(locatie);
            return newAccesoriu;
        } else {
            existingAccesoriu.setNume(nume);
            existingAccesoriu.setCantitate(cantitate);
            existingAccesoriu.setPret(pret);
            existingAccesoriu.setLocatie(locatie);
            return existingAccesoriu;
        }
    }
}
