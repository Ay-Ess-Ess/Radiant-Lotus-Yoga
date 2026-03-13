package practica.Pagini;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import practica.ClaseCuDate.Angajat;
import practica.ClaseCuDate.Client;
import practica.ClaseCuDate.Contract;
import practica.ClaseCuDate.Locatie;
import practica.ClaseCuDate.Serviciu;
import practica.ClaseCuDate.StudiouYoga;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Optional;
import javafx.util.StringConverter;

public class ContractPanel extends VBox {

    private TableView<Contract> contractTable;
    private TextField searchField;
    private Button addButton;
    private Button modifyButton;
    private Button deleteButton;
    private StudiouYoga studio;
    private FilteredList<Contract> filteredContracts;
    private ObservableList<Contract> contracts;

    public ContractPanel(StudiouYoga studio) {
        this.studio = studio;
        setPadding(new Insets(20));
        setSpacing(10);

        // Initialize controls
        searchField = new TextField();
        searchField.setPromptText("Search...");

        addButton = new Button("Add");
        modifyButton = new Button("Modify");
        deleteButton = new Button("Delete");

        contractTable = new TableView<>();
        contractTable.setPrefHeight(550);
        setupContractTable();

        // Layout setup
        GridPane buttonPane = new GridPane();
        buttonPane.setHgap(10);
        buttonPane.add(addButton, 0, 0);
        buttonPane.add(modifyButton, 1, 0);
        buttonPane.add(deleteButton, 2, 0);

        getChildren().addAll(searchField, contractTable, buttonPane);

        // Event handlers
        addButton.setOnAction(e -> addContract());
        modifyButton.setOnAction(e -> modifyContract());
        deleteButton.setOnAction(e -> deleteContract());

        // Load and display contracts
        loadContracts();

        // Filtered list for search
        filteredContracts = new FilteredList<>(contracts, p -> true);
        contractTable.setItems(filteredContracts);

        // Add search functionality
        searchField.textProperty().addListener((observable, oldValue, newValue) ->
                filteredContracts.setPredicate(contract -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }
                    String lowerCaseFilter = newValue.toLowerCase();
                    return contract.getClient().toLowerCase().contains(lowerCaseFilter) ||
                            contract.getServiciu().toLowerCase().contains(lowerCaseFilter) ||
                            contract.getTime().toLowerCase().contains(lowerCaseFilter) ||
                            contract.getAntrenor().toLowerCase().contains(lowerCaseFilter);
                })
        );
    }

    private void setupContractTable() {
        TableColumn<Contract, Boolean> selectColumn = new TableColumn<>("Select");
        selectColumn.setCellValueFactory(cellData -> cellData.getValue().selectedProperty());
        selectColumn.setCellFactory(column -> {
            CheckBoxTableCell<Contract, Boolean> cell = new CheckBoxTableCell<>();
            cell.setEditable(true); // Make sure the cell is editable
            return cell;
        });
        
        selectColumn.setCellFactory(column -> {
        TableCell<Contract, Boolean> cell = new TableCell<>() {
            private final CheckBox checkBox = new CheckBox();

            {
                checkBox.setOnAction(event -> {
                    Contract contract = getTableView().getItems().get(getIndex());
                    contract.setSelected(checkBox.isSelected());
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

        TableColumn<Contract, String> clientColumn = new TableColumn<>("Client");
        clientColumn.setCellValueFactory(new PropertyValueFactory<>("client"));
        clientColumn.setCellFactory(column -> createClickableCell());

        TableColumn<Contract, String> serviciuColumn = new TableColumn<>("Serviciu");
        serviciuColumn.setCellValueFactory(new PropertyValueFactory<>("serviciu"));

        TableColumn<Contract, String> locatieColumn = new TableColumn<>("Locatie");
        locatieColumn.setCellValueFactory(new PropertyValueFactory<>("locatie"));

        TableColumn<Contract, LocalDate> dataInceputColumn = new TableColumn<>("Data Început");
        dataInceputColumn.setCellValueFactory(new PropertyValueFactory<>("perioadaInceput"));

        TableColumn<Contract, Integer> perioadaColumn = new TableColumn<>("Perioada");
        perioadaColumn.setCellValueFactory(new PropertyValueFactory<>("perioada"));

        TableColumn<Contract, String> timeColumn = new TableColumn<>("Time");
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("time"));

        TableColumn<Contract, String> antrenorColumn = new TableColumn<>("Antrenor");
        antrenorColumn.setCellValueFactory(new PropertyValueFactory<>("antrenor"));

        TableColumn<Contract, Double> costColumn = new TableColumn<>("Cost");
        costColumn.setCellValueFactory(new PropertyValueFactory<>("cost"));

        contractTable.getColumns().addAll(selectColumn, clientColumn, locatieColumn, serviciuColumn, antrenorColumn, dataInceputColumn, perioadaColumn, timeColumn, costColumn);
    }

    private TableCell<Contract, String> createClickableCell() {
        TableCell<Contract, String> cell = new TableCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? null : item);
                setGraphic(null);
            }
        };

        cell.setOnMouseClicked(event -> {
            if (!cell.isEmpty() && event.getButton() == MouseButton.PRIMARY) {
                Contract contract = cell.getTableView().getItems().get(cell.getIndex());
                showContractDetails(contract);
            }
        });

        return cell;
    }

    private void showContractDetails(Contract contract) {
        if (contract != null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Contract Details");
            alert.setHeaderText("Detalii despre contract:");
            alert.setContentText("Client: " + contract.getClient() + "\n" +
                    "Serviciu: " + contract.getServiciu() + "\n" +
                    "Incepe: " + contract.getPerioadaInceput() + "\n" +
                    "Perioada: " + contract.getPerioada() + "\n" +
                    "Ora: " + contract.getTime() + "\n" +
                    "Antrenor: " + contract.getAntrenor() + "\n" +
                    "Cost: " + contract.getCost());
            alert.showAndWait();
        }
    }

    private void addContract() {
        ContractFormDialog dialog = new ContractFormDialog(null, studio);
        Optional<Contract> result = dialog.showAndWait();
        result.ifPresent(contract -> {
            studio.addContract(contract);
            contracts.add(contract);
            studio.salveazaDate("src/practica/Date/locatii.txt", "src/practica/Date/users.txt", "src/practica/Date/servicii.txt", "src/practica/Date/contracte.txt", "src/practica/Date/accesorii.txt");
        });
    }

    private void modifyContract() {
        Contract selectedContract = contractTable.getSelectionModel().getSelectedItem();
        if (selectedContract != null) {
            ContractFormDialog dialog = new ContractFormDialog(selectedContract, studio);
            Optional<Contract> result = dialog.showAndWait();
            result.ifPresent(updatedContract -> {
                // Update selectedContract with updatedContract
                contractTable.refresh();
                studio.salveazaDate("src/practica/Date/locatii.txt", "src/practica/Date/users.txt", "src/practica/Date/servicii.txt", "src/practica/Date/contracte.txt", "src/practica/Date/accesorii.txt");
            });
        } else {
            showAlert("No contract selected", "Please select a contract to modify.");
        }
    }

    private void deleteContract() {
        ObservableList<Contract> selectedContracts = FXCollections.observableArrayList();
        for (Contract contract : contracts) {
            if (contract.isSelected()) {
                selectedContracts.add(contract);
            }
        }

        if (!selectedContracts.isEmpty()) {
            contracts.removeAll(selectedContracts);
            selectedContracts.forEach(studio::removeContract);
            studio.salveazaDate("src/practica/Date/locatii.txt", "src/practica/Date/users.txt", "src/practica/Date/servicii.txt", "src/practica/Date/contracte.txt", "src/practica/Date/accesorii.txt");
        } else {
            showAlert("No contracts selected", "Please select at least one contract to delete.");
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void loadContracts() {
        contracts = FXCollections.observableArrayList(studio.getContracte());
    }
}

class ContractFormDialog extends Dialog<Contract> {

    private ComboBox<String> clientField;
    private ComboBox<String> locatieField;
    private ComboBox<String> serviciuField;
    private ComboBox<String> antrenorField;
    private ComboBox<String> subscriptionField;
    private DatePicker perioadaField;
    private ComboBox<String> timeField;
    private TextField costField;

    private final StudiouYoga studio;
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public ContractFormDialog(Contract contract, StudiouYoga studio) {
        this.studio = studio;
        setTitle(contract == null ? "Add Contract" : "Modify Contract");
        setHeaderText(contract == null ? "Enter new contract details" : "Modify contract details");

        // Initialize fields
        clientField = new ComboBox<>();
        clientField.setPrefWidth(250);
        locatieField = new ComboBox<>();
        locatieField.setPrefWidth(250);
        serviciuField = new ComboBox<>();
        serviciuField.setPrefWidth(250);
        antrenorField = new ComboBox<>();
        antrenorField.setPrefWidth(250);
        subscriptionField = new ComboBox<>();
        subscriptionField.setPrefWidth(250);
        perioadaField = new DatePicker();
        perioadaField.setPrefWidth(250);
        timeField = new ComboBox<>();
        timeField.setPrefWidth(250);
        costField = new TextField();
        costField.setPrefWidth(250);

        // Populate ComboBoxes
        populateComboBoxes();

        // Populate clientField with client names
        for (Client client : studio.getClienti()) {
            if ("user".equalsIgnoreCase(client.getPower())) {
                clientField.getItems().add(client.getNume());
            }
        }

        // Populate locatieField with location names
        for (Locatie locatie : studio.getLocatii()) {
            locatieField.getItems().add(locatie.getNume());
        }

        // Listener for locatieField to update serviciuField
        locatieField.valueProperty().addListener((observable, oldValue, newValue) -> {
            serviciuField.getItems().clear(); // Clear previous items
            for (Serviciu serviciu : studio.getServicii()) {
                if (serviciu.getLocatie().equals(newValue)) {
                    serviciuField.getItems().add(serviciu.getTip());
                }
            }
        });

        // Listener for serviciuField to update antrenorField
        serviciuField.valueProperty().addListener((observable, oldValue, newValue) -> {
            antrenorField.getItems().clear(); // Clear previous items
            for (Angajat angajat : studio.getAngajati()) {
                if (angajat.getPower().equalsIgnoreCase("angajat") && angajat.getLocatie().equals(locatieField.getValue())) {
                    antrenorField.getItems().add(angajat.getNume());
                }
            }
        });

        // Initialize fields with existing contract data if modifying
        if (contract != null) {
            clientField.setValue(contract.getClient());
            antrenorField.setValue(contract.getAntrenor());
            serviciuField.setValue(contract.getServiciu());
            subscriptionField.setValue(getSubscriptionName(contract.getPerioada()));
            perioadaField.setValue(contract.getPerioadaInceput());
            timeField.setValue(contract.getTime());
            costField.setText(String.valueOf(contract.getCost()));
        }

        // Layout setup
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.add(new Label("Client:"), 0, 0);
        gridPane.add(clientField, 1, 0);
        gridPane.add(new Label("Locatie:"), 0, 1);
        gridPane.add(locatieField, 1, 1);
        gridPane.add(new Label("Serviciu:"), 0, 2);
        gridPane.add(serviciuField, 1, 2);
        gridPane.add(new Label("Antrenor:"), 0, 3);
        gridPane.add(antrenorField, 1, 3);
        gridPane.add(new Label("Tip Abonament:"), 0, 4);
        gridPane.add(subscriptionField, 1, 4);
        gridPane.add(new Label("Perioada:"), 0, 5);
        gridPane.add(perioadaField, 1, 5);
        gridPane.add(new Label("Time:"), 0, 6);
        gridPane.add(timeField, 1, 6);
        gridPane.add(new Label("Cost:"), 0, 7);
        gridPane.add(costField, 1, 7);

        getDialogPane().setContent(gridPane);

        // Update cost based on selected values
        updateCost();

        // Button setup
        ButtonType saveButtonType = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);

        // Converters
        subscriptionField.setConverter(new StringConverter<String>() {
            @Override
            public String toString(String object) {
                return object;
            }

            @Override
            public String fromString(String string) {
                return string;
            }
        });

        // Result converter
        setResultConverter(dialogButton -> {
            if (dialogButton == saveButtonType) {
                return createContractFromFields(contract);
            }
            return null;
        });

        // Listeners for auto-updating fields
        locatieField.valueProperty().addListener((observable, oldValue, newValue) -> updateAntrenorField());
        serviciuField.valueProperty().addListener((observable, oldValue, newValue) -> updateCost());
        subscriptionField.valueProperty().addListener((observable, oldValue, newValue) -> updateCost());
        perioadaField.valueProperty().addListener((observable, oldValue, newValue) -> updateCost());
        antrenorField.valueProperty().addListener((observable, oldValue, newValue) -> updateCost());
    }

    private void updateAntrenorField() {
        String selectedService = serviciuField.getValue();
        antrenorField.getItems().clear(); // Clear previous items

        if (selectedService != null) {
            for (Angajat angajat : studio.getAngajati()) {
                if ("angajat".equalsIgnoreCase(angajat.getPower()) && angajat.getNume().equals(selectedService) && angajat.getNume().equals(locatieField.getValue())) {
                    antrenorField.getItems().add(angajat.getNume());
                }
            }
        }
    }

    private void updateCost() {
        String selectedService = serviciuField.getValue();
        String selectedSubscription = subscriptionField.getValue();
        costField.setText("");

        int perioada = 0;
        if (selectedSubscription != null) {
            switch (selectedSubscription) {
                case "Per Lecție":
                    perioada = 1;
                    break;
                case "Abonament 30 zile":
                    perioada = 30;
                    break;
                case "Abonament 90 zile":
                    perioada = 90;
                    break;
                case "Abonament 360 zile":
                    perioada = 360;
                    break;
            }
        }

        if (selectedService != null && selectedSubscription != null) {
            for (Serviciu serviciu : studio.getServicii()) {
                if (serviciu.getAntrenor().equalsIgnoreCase(antrenorField.getValue()) && serviciu.getTip().equalsIgnoreCase(selectedService)) {
                    double cost = perioada * serviciu.getPretPerOra();
                    costField.setText(String.valueOf(cost));
                }
            }
        }
    }

    // Method to populate ComboBoxes
    private void populateComboBoxes() {
        subscriptionField.getItems().addAll("Per Lecție", "Abonament 30 zile", "Abonament 90 zile", "Abonament 360 zile");
        timeField.getItems().addAll("08:00", "10:00", "12:00", "14:00", "16:00", "18:00", "20:00"); // Replace with your time options
    }

    // Method to create Contract object from dialog fields
    private Contract createContractFromFields(Contract existingContract) {
        String client = clientField.getValue().trim();
        String locatia = locatieField.getValue().trim();
        String serviciu = serviciuField.getValue();
        String antrenor = antrenorField.getValue();
        String subscription = subscriptionField.getValue();
        LocalDate dataInceput = perioadaField.getValue();
        String time = timeField.getValue().trim();
        double cost = Double.parseDouble(costField.getText().trim());

        int perioada = 1;
        if (subscription.equals("Per Lecție")) perioada = 1;
        else if (subscription.equals("Abonament 30 zile")) perioada = 30;
        else if (subscription.equals("Abonament 90 zile")) perioada = 90;
        else if (subscription.equals("Abonament 360 zile")) perioada = 360;

        if (existingContract == null) {
            return new Contract(client, locatia, serviciu, dataInceput, perioada, time, antrenor, cost);
        } else {
            existingContract.setClient(client);
            existingContract.setLocatia(locatia);
            existingContract.setServiciu(serviciu);
            existingContract.setPerioada(perioada);
            existingContract.setTime(time);
            existingContract.setAntrenor(antrenor);
            existingContract.setCost(cost);
            return existingContract;
        }
    }

    // Method to retrieve subscription name based on perioada
    private String getSubscriptionName(int perioada) {
        return "Subscription " + perioada; // Replace with actual logic
    }
}