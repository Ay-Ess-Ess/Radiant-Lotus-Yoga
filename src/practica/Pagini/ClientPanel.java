package practica.Pagini;

import javafx.application.Application;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import practica.ClaseCuDate.StudiouYoga;
import practica.ClaseCuDate.Client;

public class ClientPanel extends VBox {

    private TableView<Client> clientTable;
    private TextField searchField;
    private Button addButton;
    private Button modifyButton;
    private Button deleteButton;
    private static StudiouYoga studio;
    private FilteredList<Client> filteredClients;
    private static ObservableList<Client> clients = FXCollections.observableArrayList();

    public ClientPanel(StudiouYoga studio) {
        this.studio = studio;
        setPadding(new Insets(20));
        setSpacing(10);

        // Initialize controls
        searchField = new TextField();
        searchField.setPromptText("Search...");

        addButton = new Button("Add");
        modifyButton = new Button("Modify");
        deleteButton = new Button("Delete");

        clientTable = new TableView<>();
        clientTable.setPrefHeight(550);
        setupClientTable();

        // Layout setup
        GridPane buttonPane = new GridPane();
        buttonPane.setHgap(10);
        buttonPane.add(addButton, 0, 0);
        buttonPane.add(modifyButton, 1, 0);
        buttonPane.add(deleteButton, 2, 0);

        getChildren().addAll(searchField, clientTable, buttonPane);

        // Event handlers
        addButton.setOnAction(e -> addClient());
        modifyButton.setOnAction(e -> modifyClient());
        deleteButton.setOnAction(e -> deleteClient());

        // Load and display clients
        loadClients();

        filteredClients = new FilteredList<>(clients, p -> true);
        clientTable.setItems(filteredClients);

        // Add search functionality
        searchField.textProperty().addListener((observable, oldValue, newValue) -> 
            filteredClients.setPredicate(client -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();
                return client.getNume().toLowerCase().contains(lowerCaseFilter) ||
                       client.getAdresa().toLowerCase().contains(lowerCaseFilter) ||
                       client.getTelefon().toLowerCase().contains(lowerCaseFilter) ||
                       client.getEmail().toLowerCase().contains(lowerCaseFilter) ||
                       client.getUsername().toLowerCase().contains(lowerCaseFilter);
            })
        );
    }

    private void setupClientTable() {
        TableColumn<Client, Boolean> selectColumn = new TableColumn<>("Select");
        selectColumn.setCellValueFactory(cellData -> cellData.getValue().selectedProperty());
        selectColumn.setCellFactory(column -> {
            CheckBoxTableCell<Client, Boolean> cell = new CheckBoxTableCell<>();
            cell.setEditable(true); // Make sure the cell is editable
            return cell;
        });
        
        selectColumn.setCellFactory(column -> {
        TableCell<Client, Boolean> cell = new TableCell<>() {
            private final CheckBox checkBox = new CheckBox();

            {
                checkBox.setOnAction(event -> {
                    Client client = getTableView().getItems().get(getIndex());
                    client.setSelected(checkBox.isSelected());
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

        TableColumn<Client, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getNume()));
        nameColumn.setCellFactory(column -> createClickableCell());

        TableColumn<Client, String> addressColumn = new TableColumn<>("Address");
        addressColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getAdresa()));

        TableColumn<Client, String> phoneColumn = new TableColumn<>("Phone");
        phoneColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getTelefon()));

        TableColumn<Client, String> emailColumn = new TableColumn<>("Email");
        emailColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getEmail()));

        TableColumn<Client, String> usernameColumn = new TableColumn<>("Username");
        usernameColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getUsername()));

        clientTable.getColumns().addAll(selectColumn, nameColumn, addressColumn, phoneColumn, emailColumn, usernameColumn);
    }



    private TableCell<Client, String> createClickableCell() {
        TableCell<Client, String> cell = new TableCell<>() {
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
                showClientDetails(cell.getTableRow().getItem());
            }
        });

        return cell;
    }

    private void showClientDetails(Client client) {
        if (client != null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Client Details");
            alert.setHeaderText("Detalii despre " + client.getNume());
            alert.setContentText("Name: " + client.getNume() + "\n" +
                                 "Address: " + client.getAdresa() + "\n" +
                                 "Phone: " + client.getTelefon() + "\n" +
                                 "Email: " + client.getEmail() + "\n" +
                                 "Username: " + client.getUsername() + "\n" +
                                 "Password: " + client.getPassword());
            alert.showAndWait();
        }
    }

    private void addClient() {
        ClientFormDialog dialog = new ClientFormDialog(null, studio);
        dialog.showAndWait().ifPresent(client -> {
            studio.addClient(client);
            clients.add(client);
        });
        studio.salveazaDate("src/practica/Date/locatii.txt", "src/practica/Date/users.txt", "src/practica/Date/servicii.txt", "src/practica/Date/contracte.txt", "src/practica/Date/accesorii.txt");
    }

    private void modifyClient() {
        Client selectedClient = clientTable.getSelectionModel().getSelectedItem();
        if (selectedClient != null) {
            ClientFormDialog dialog = new ClientFormDialog(selectedClient, studio);
            dialog.showAndWait().ifPresent(updatedClient -> {
                selectedClient.setNume(updatedClient.getNume());
                selectedClient.setAdresa(updatedClient.getAdresa());
                selectedClient.setTelefon(updatedClient.getTelefon());
                selectedClient.setEmail(updatedClient.getEmail());
                selectedClient.setUsername(updatedClient.getUsername());
                selectedClient.setPassword(updatedClient.getPassword());
                clientTable.refresh();
            });
            studio.salveazaDate("src/practica/Date/locatii.txt", "src/practica/Date/users.txt", "src/practica/Date/servicii.txt", "src/practica/Date/contracte.txt", "src/practica/Date/accesorii.txt");
        } else {
            showAlert("No client selected", "Please select a client to modify.");
        }
    }

    private void deleteClient() {
        ObservableList<Client> selectedClients = FXCollections.observableArrayList();
        for (Client client : clients) {
            if (client.isSelected()) {
                selectedClients.add(client);
            }
        }

        if (!selectedClients.isEmpty()) {
            clients.removeAll(selectedClients);
            for (Client client : selectedClients) {
                studio.removeClient(client);
            }
            studio.salveazaDate("src/practica/Date/locatii.txt", "src/practica/Date/users.txt", "src/practica/Date/servicii.txt", "src/practica/Date/contracte.txt", "src/practica/Date/accesorii.txt");
        } else {
            showAlert("No clients selected", "Please select at least one client to delete.");
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void loadClients() {
        clients.clear();
        clients.addAll(studio.getClienti());
    }
}