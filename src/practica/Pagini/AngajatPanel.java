package practica.Pagini;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import practica.ClaseCuDate.Angajat;
import practica.ClaseCuDate.Client;
import practica.ClaseCuDate.Locatie;
import practica.ClaseCuDate.StudiouYoga;

public class AngajatPanel extends VBox {

    private TableView<Angajat> employeeTable;
    private TextField searchField;
    private Button addButton;
    private Button modifyButton;
    private Button deleteButton;
    private static StudiouYoga studio;
    private FilteredList<Angajat> filteredEmployees;
    private static ObservableList<Angajat> angajati = FXCollections.observableArrayList();

    public AngajatPanel(StudiouYoga studio) {
        this.studio = studio;
        setPadding(new Insets(20));
        setSpacing(10);

        // Initialize controls
        searchField = new TextField();
        searchField.setPromptText("Search...");

        addButton = new Button("Add");
        modifyButton = new Button("Modify");
        deleteButton = new Button("Delete");

        employeeTable = new TableView<>();
        employeeTable.setPrefHeight(550);
        setupEmployeeTable();

        // Layout setup
        GridPane buttonPane = new GridPane();
        buttonPane.setHgap(10);
        buttonPane.add(addButton, 0, 0);
        buttonPane.add(modifyButton, 1, 0);
        buttonPane.add(deleteButton, 2, 0);

        getChildren().addAll(searchField, employeeTable, buttonPane);

        // Event handlers
        addButton.setOnAction(e -> addEmployee());
        modifyButton.setOnAction(e -> modifyEmployee());
        deleteButton.setOnAction(e -> deleteEmployee());

        // Load and display employees
        loadEmployees();

        filteredEmployees = new FilteredList<>(angajati, p -> true);
        employeeTable.setItems(filteredEmployees);

        // Add search functionality
        searchField.textProperty().addListener((observable, oldValue, newValue) ->
                filteredEmployees.setPredicate(employee -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }

                    String lowerCaseFilter = newValue.toLowerCase();
                    return employee.getNume().toLowerCase().contains(lowerCaseFilter) ||
                            employee.getAdresa().toLowerCase().contains(lowerCaseFilter) ||
                            employee.getTelefon().toLowerCase().contains(lowerCaseFilter) ||
                            employee.getEmail().toLowerCase().contains(lowerCaseFilter) ||
                            employee.getUsername().toLowerCase().contains(lowerCaseFilter);
                })
        );
    }

    private void setupEmployeeTable() {
        TableColumn<Angajat, Boolean> selectColumn = new TableColumn<>("Select");
        selectColumn.setCellValueFactory(cellData -> cellData.getValue().selectedProperty());
        selectColumn.setCellFactory(column -> {
            CheckBoxTableCell<Angajat, Boolean> cell = new CheckBoxTableCell<>();
            cell.setEditable(true); // Make sure the cell is editable
            return cell;
        });
        
        selectColumn.setCellFactory(column -> {
        TableCell<Angajat, Boolean> cell = new TableCell<>() {
            private final CheckBox checkBox = new CheckBox();

            {
                checkBox.setOnAction(event -> {
                    Angajat angajat = getTableView().getItems().get(getIndex());
                    angajat.setSelected(checkBox.isSelected());
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

        TableColumn<Angajat, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getNume()));
        nameColumn.setCellFactory(column -> createClickableCell());

        TableColumn<Angajat, String> addressColumn = new TableColumn<>("Address");
        addressColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getAdresa()));

        TableColumn<Angajat, String> phoneColumn = new TableColumn<>("Phone");
        phoneColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getTelefon()));

        TableColumn<Angajat, String> emailColumn = new TableColumn<>("Email");
        emailColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getEmail()));
        
        TableColumn<Angajat, String> locationColumn = new TableColumn<>("Locatie");
        locationColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getLocatie()));
        
        TableColumn<Angajat, String> powerColumn = new TableColumn<>("Power");
        powerColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getPower()));

        TableColumn<Angajat, String> usernameColumn = new TableColumn<>("Username");
        usernameColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getUsername()));
        
        employeeTable.getColumns().addAll(selectColumn, nameColumn, addressColumn, phoneColumn, emailColumn, locationColumn, powerColumn, usernameColumn);
    }

    private TableCell<Angajat, String> createClickableCell() {
        TableCell<Angajat, String> cell = new TableCell<>() {
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
                showEmployeeDetails(cell.getTableRow().getItem());
            }
        });

        return cell;
    }

    private void showEmployeeDetails(Angajat employee) {
        if (employee != null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Employee Details");
            alert.setHeaderText("Details for " + employee.getNume());
            alert.setContentText("Name: " + employee.getNume() + "\n" +
                    "Address: " + employee.getAdresa() + "\n" +
                    "Phone: " + employee.getTelefon() + "\n" +
                    "Email: " + employee.getEmail() + "\n" +
                    "Username: " + employee.getUsername() + "\n" +
                    "Password: " + employee.getPassword());
            alert.showAndWait();
        }
    }

    private void addEmployee() {
        AngajatFormDialog dialog = new AngajatFormDialog(null, studio);
        dialog.showAndWait().ifPresent(employee -> {
            studio.addAngajat(employee);
            angajati.add(employee);
        });
        studio.salveazaDate("src/practica/Date/locatii.txt", "src/practica/Date/users.txt", "src/practica/Date/servicii.txt", "src/practica/Date/contracte.txt", "src/practica/Date/accesorii.txt");
    }

    private void modifyEmployee() {
        Angajat selectedEmployee = employeeTable.getSelectionModel().getSelectedItem();
        if (selectedEmployee != null) {
            AngajatFormDialog dialog = new AngajatFormDialog(selectedEmployee, studio);
            dialog.showAndWait().ifPresent(updatedEmployee -> {
                selectedEmployee.setNume(updatedEmployee.getNume());
                selectedEmployee.setAdresa(updatedEmployee.getAdresa());
                selectedEmployee.setTelefon(updatedEmployee.getTelefon());
                selectedEmployee.setEmail(updatedEmployee.getEmail());
                selectedEmployee.setLocatie(updatedEmployee.getLocatie());
                selectedEmployee.setPower(updatedEmployee.getPower());
                selectedEmployee.setUsername(updatedEmployee.getUsername());
                selectedEmployee.setPassword(updatedEmployee.getPassword());
                employeeTable.refresh();
            });
            studio.salveazaDate("src/practica/Date/locatii.txt", "src/practica/Date/users.txt", "src/practica/Date/servicii.txt", "src/practica/Date/contracte.txt", "src/practica/Date/accesorii.txt");
        } else {
            showAlert("No employee selected", "Please select an employee to modify.");
        }
    }

    private void deleteEmployee() {
        ObservableList<Angajat> selectedEmployees = FXCollections.observableArrayList();
        for (Angajat angajat : angajati) {
            if (angajat.isSelected()) {
                selectedEmployees.add(angajat);
            }
        }

        if (!selectedEmployees.isEmpty()) {
            angajati.removeAll(selectedEmployees);
            for (Angajat employee : selectedEmployees) {
                studio.removeAngajat(employee);
            }
            studio.salveazaDate("src/practica/Date/locatii.txt", "src/practica/Date/users.txt", "src/practica/Date/servicii.txt", "src/practica/Date/contracte.txt", "src/practica/Date/accesorii.txt");
        } else {
            showAlert("No employees selected", "Please select at least one employee to delete.");
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void loadEmployees() {
        angajati.clear();
        angajati.addAll(studio.getAngajati());
    }
}
