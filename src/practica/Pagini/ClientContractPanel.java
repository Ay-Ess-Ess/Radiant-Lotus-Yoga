package practica.Pagini;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import practica.ClaseCuDate.Client;
import practica.ClaseCuDate.Contract;
import practica.ClaseCuDate.StudiouYoga;
import practica.ClaseCuDate.Angajat;

import java.time.LocalDate;
import java.util.Optional;
import java.util.function.Supplier;

public class ClientContractPanel extends VBox {

    private TableView<Contract> contractTable;
    private TextField searchField;
    private Button modifyButton;
    private Button deleteButton;
    private StudiouYoga studio;
    private Supplier<String> nameSupplier;
    private FilteredList<Contract> filteredContracts;
    private ObservableList<Contract> contracts;

    // Constructor using Client object
    public ClientContractPanel(StudiouYoga studio, Client client) {
        this(studio, client::getNume);
    }

    // Constructor using Angajat object
    public ClientContractPanel(StudiouYoga studio, Angajat angajat) {
        this(studio, angajat::getNume);
    }

    // Private constructor to be called by the public constructors
    private ClientContractPanel(StudiouYoga studio, Supplier<String> nameSupplier) {
        this.studio = studio;
        this.nameSupplier = nameSupplier;
        setPadding(new Insets(20));
        setSpacing(10);

        // Initialize controls
        searchField = new TextField();
        searchField.setPromptText("Search...");

        modifyButton = new Button("Modify");
        deleteButton = new Button("Delete");

        contractTable = new TableView<>();
        contractTable.setPrefHeight(550);
        setupContractTable();

        // Layout setup
        GridPane buttonPane = new GridPane();
        buttonPane.setHgap(10);
        buttonPane.add(modifyButton, 0, 0);
        buttonPane.add(deleteButton, 1, 0);

        getChildren().addAll(searchField, contractTable, buttonPane);

        // Event handlers
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
                    return contract.getServiciu().toLowerCase().contains(lowerCaseFilter) ||
                            contract.getTime().toLowerCase().contains(lowerCaseFilter) ||
                            contract.getAntrenor().toLowerCase().contains(lowerCaseFilter);
                })
        );
    }

    private void setupContractTable() {
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

        contractTable.getColumns().addAll(serviciuColumn, locatieColumn, antrenorColumn, dataInceputColumn, perioadaColumn, timeColumn, costColumn);
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
        Contract selectedContract = contractTable.getSelectionModel().getSelectedItem();
        if (selectedContract != null) {
            contracts.remove(selectedContract);
            studio.removeContract(selectedContract);
            studio.salveazaDate("src/practica/Date/locatii.txt", "src/practica/Date/users.txt", "src/practica/Date/servicii.txt", "src/practica/Date/contracte.txt", "src/practica/Date/accesorii.txt");
        } else {
            showAlert("No contract selected", "Please select a contract to delete.");
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void loadContracts() {
        contracts = FXCollections.observableArrayList();
        String name = nameSupplier.get();
        for (Contract contract : studio.getContracte()) {
            if (contract.getClient().equals(name) || contract.getAntrenor().equals(name)) {
                contracts.add(contract);
            }
                 
        }
    }
}
