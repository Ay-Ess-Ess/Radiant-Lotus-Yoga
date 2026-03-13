package practica.Pagini;

import java.time.LocalDate;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import practica.ClaseCuDate.*;

public class RegistrationPanel extends VBox {

    private ComboBox<String> locationComboBox;
    private ComboBox<String> trainerComboBox;
    private DatePicker datePicker;
    private ComboBox<String> subscriptionComboBox;
    private TextField costField;
    private ComboBox<String> serviceComboBox;
    private ComboBox<String> timeComboBox;
    private Button registerButton;
    private Label statusLabel;

    private static List<Contract> contractList;

    public RegistrationPanel(StudiouYoga studio, Client client) {
        contractList = studio.getContracte();
        setMaxWidth(800);
        setMaxHeight(600);
        setPadding(new Insets(20));
        setSpacing(10);
        setAlignment(Pos.CENTER);
        setStyle("-fx-background-color: rgba(255, 255, 255, 0.7); -fx-border-color: #cccccc; -fx-border-radius: 10px; -fx-background-radius: 10px; -fx-border-width: 1px;");

        // Initialize ComboBoxes with data from studio
        locationComboBox = new ComboBox<>();
        for (Locatie locatie : studio.getLocatii()) {
            locationComboBox.getItems().add(locatie.getNume());
        }
        locationComboBox.setPrefWidth(250);

        trainerComboBox = new ComboBox<>();
        locationComboBox.setOnAction(event -> updateTrainerComboBox(studio));
        trainerComboBox.setPrefWidth(250);

        String[] subscriptions = {"Per Lecție", "Abonament 30 zile", "Abonament 90 zile", "Abonament 360 zile"};

        datePicker = new DatePicker();
        datePicker.setPrefWidth(250);

        subscriptionComboBox = new ComboBox<>();
        subscriptionComboBox.getItems().addAll(subscriptions);
        subscriptionComboBox.setPrefWidth(250);

        ObservableList<String> timeOptions = FXCollections.observableArrayList(
                "08:00", "10:00", "12:00", "14:00", "16:00", "18:00", "20:00");
        timeComboBox = new ComboBox<>(timeOptions);
        timeComboBox.setPrefWidth(250);

        serviceComboBox = new ComboBox<>();
        trainerComboBox.setOnAction(event -> updateServiceComboBox(studio));
        serviceComboBox.setPrefWidth(250);

        costField = new TextField();
        costField.setText("0.00");
        costField.setEditable(false);
        costField.setPrefWidth(250);

        // Update cost when subscription or service is selected
        subscriptionComboBox.setOnAction(event -> updateCost(studio));
        serviceComboBox.setOnAction(event -> updateCost(studio));

        registerButton = new Button("Înregistrează-te");
        statusLabel = new Label();

        // Event handler for registration button
        registerButton.setOnAction(e -> register(studio, client));

        // Layout setup
        GridPane gridPane = new GridPane();
        gridPane.setVgap(10);
        gridPane.setHgap(10);
        gridPane.setAlignment(Pos.CENTER);
        gridPane.add(new Label("Locație:"), 0, 0);
        gridPane.add(locationComboBox, 1, 0);
        gridPane.add(new Label("Data de început:"), 0, 1);
        gridPane.add(datePicker, 1, 1);
        gridPane.add(new Label("Ora antrenamentului:"), 0, 2);
        gridPane.add(timeComboBox, 1, 2);
        gridPane.add(new Label("Antrenor:"), 0, 3);
        gridPane.add(trainerComboBox, 1, 3);
        gridPane.add(new Label("Tip Abonament:"), 0, 4);
        gridPane.add(subscriptionComboBox, 1, 4);
        gridPane.add(new Label("Serviciu:"), 0, 5);
        gridPane.add(serviceComboBox, 1, 5);
        gridPane.add(new Label("Cost:"), 0, 6);
        gridPane.add(costField, 1, 6);
        gridPane.add(registerButton, 1, 7);
        gridPane.add(statusLabel, 1, 8);

        getChildren().add(gridPane);

        // Diagnostic output to console for debugging
        System.out.println("Locații disponibile:");
        for (Locatie locatie : studio.getLocatii()) {
            System.out.println(locatie.getNume());
        }

        System.out.println("Angajați disponibili:");
        for (Angajat angajat : studio.getAngajati()) {
            System.out.println(angajat.getNume() + " - " + angajat.getLocatie() + " - " + angajat.getPower());
        }
    }

    private void updateTrainerComboBox(StudiouYoga studio) {
        String selectedLocation = locationComboBox.getValue();
        trainerComboBox.getItems().clear();
        System.out.println("Actualizare antrenori pentru locația: " + selectedLocation); // Diagnostic
        if (selectedLocation != null) {
            for (Angajat angajat : studio.getAngajati()) {
                if (angajat.getLocatie().equalsIgnoreCase(selectedLocation) && angajat.getPower().equalsIgnoreCase("angajat")) {
                    trainerComboBox.getItems().add(angajat.getNume());
                }
            }
        }
        trainerComboBox.setValue(null); // Reset the selection
        updateServiceComboBox(studio); // Reset the service combo box based on the new trainer selection
    }

    private void updateServiceComboBox(StudiouYoga studio) {
        String selectedTrainer = trainerComboBox.getValue();
        serviceComboBox.getItems().clear();
        System.out.println("Actualizare servicii pentru antrenorul: " + selectedTrainer); // Diagnostic
        if (selectedTrainer != null) {
            for (Serviciu service : studio.getServicii()) {
                if (service.getAntrenor().equalsIgnoreCase(selectedTrainer)) {
                    serviceComboBox.getItems().add(service.getTip());
                }
            }
        }
        serviceComboBox.setValue(null);
    }

    private void updateCost(StudiouYoga studio) {
        String selectedService = serviceComboBox.getValue();
        String selectedSubscription = subscriptionComboBox.getValue();
        costField.setText("");

        int period = 0;
        if (selectedSubscription != null) {
            switch (selectedSubscription) {
                case "Per Lecție":
                    period = 1;
                    break;
                case "Abonament 30 zile":
                    period = 30;
                    break;
                case "Abonament 90 zile":
                    period = 90;
                    break;
                case "Abonament 360 zile":
                    period = 360;
                    break;
            }
        }

        if (selectedService != null && selectedSubscription != null) {
            for (Serviciu service : studio.getServicii()) {
                if (service.getAntrenor().equalsIgnoreCase(trainerComboBox.getValue()) && service.getTip().equalsIgnoreCase(selectedService)) {
                    double cost = period * service.getPretPerOra();
                    costField.setText(String.valueOf(cost));
                }
            }
        }
    }

    private void register(StudiouYoga studio, Client client) {
        // Retrieve the values from the fields
        String location = locationComboBox.getValue();
        String trainer = trainerComboBox.getValue();
        LocalDate date = datePicker.getValue();
                String time = timeComboBox.getValue();
        String subscription = subscriptionComboBox.getValue();
        String service = serviceComboBox.getValue();
        
        // Validate if all fields are filled
        if (location == null || trainer == null || date == null || time == null || subscription == null || service == null) {
            statusLabel.setText("Te rog completează toate câmpurile.");
            return;
        }
        if (date.isBefore(LocalDate.now())) {
            statusLabel.setText("Te rog alege o dată din viitor.");
            return;
        }

        double cost = Double.parseDouble(costField.getText());
        
        int period = 1;
        switch (subscription) {
            case "Per Lecție":
                period = 1;
                break;
            case "Abonament 30 zile":
                period = 30;
                break;
            case "Abonament 90 zile":
                period = 90;
                break;
            case "Abonament 360 zile":
                period = 360;
                break;
        }

        // Check if a registration already exists for the same location, date, and time
        if (existsRegistration(trainer, date, period, time)) {
            statusLabel.setText("Există deja o înregistrare pentru această locație și oră.");
            return;
        }

        // Create a Contract object with registration data
        Contract contract = new Contract(client.getNume(), location, service, date, period, time, trainer);
        contract.setCost(cost);

        // Save the contract to the list or other storage system
        contractList.add(contract);

        // Reset fields
        locationComboBox.setValue(null);
        trainerComboBox.setValue(null);
        datePicker.setValue(null);
        timeComboBox.setValue(null);
        subscriptionComboBox.setValue(null);
        costField.clear();
        serviceComboBox.setValue(null);

        statusLabel.setText("Înregistrare reușită pentru " + service + " la ora " + time + "!");
        studio.salveazaDate("src/practica/Date/locatii.txt", "src/practica/Date/users.txt", "src/practica/Date/servicii.txt", "src/practica/Date/contracte.txt", "src/practica/Date/accesorii.txt");
    }

    private boolean existsRegistration(String trainer, LocalDate date, int period, String time) {
        for (Contract contract : contractList) {
            boolean dateOverlap = false;
            LocalDate startDate = contract.getPerioadaInceput();
            for (int i = 0; i < contract.getPerioada(); i++) {
                if (date.isEqual(startDate.plusDays(i))) {
                    dateOverlap = true;
                    break;
                }
            }
            if (contract.getAntrenor() != null && contract.getAntrenor().equals(trainer) && dateOverlap && contract.getTime().equals(time)) {
                return true;
            }
        }
        return false;
    }
}
