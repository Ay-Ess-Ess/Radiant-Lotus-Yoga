package practica.Pagini;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Screen;
import javafx.stage.Stage;
import practica.ClaseCuDate.Angajat;
import practica.ClaseCuDate.Client;
import practica.ClaseCuDate.Locatie;
import practica.ClaseCuDate.StudiouYoga;

public class App extends Application {
    private static StudiouYoga studio = new Practica().getStudiouYoga();
    private static String power = "user";
    private static Client client;
    private static Angajat angajat;
    private static Scene scene;
    private static BorderPane root;
    private static HBox homeItem, signupItem, editItem, serviciiItem, clientsItem, accesoriiItem, placesItem, employeesItem, contractItem, aboutItem;
    
    @Override
    public void start(Stage primaryStage) {
        studio.incarcaDate("src/practica/Date/locatii.txt", "src/practica/Date/users.txt", "src/practica/Date/servicii.txt", "src/practica/Date/contracte.txt", "src/practica/Date/accesorii.txt");
        if(client == null)
            client = studio.getClienti().getFirst();
        if(angajat == null)
            angajat = studio.getAngajati().getFirst();
        createGUI(primaryStage);
        
        
        
    }
    
    //User
    
    
    private void createGUI(Stage primaryStage) {
        primaryStage.getIcons().add(new Image("file:C:\\Users\\suhar\\OneDrive\\Documente\\NetBeansProjects\\Practica\\src\\practica\\images\\logo.png"));
        primaryStage.setWidth(1280);
        primaryStage.setHeight(820);
        
        StackPane leftSide = new StackPane();
        if(power.equalsIgnoreCase("manager")) {
            leftSide = managerLeftSide(primaryStage);
        } else if(power.equalsIgnoreCase("angajat")) {
            leftSide = angajatLeftSide(primaryStage);
        } else {
            leftSide = userLeftSide(primaryStage);
        }
        
        root = new BorderPane();
        root.setLeft(leftSide);
        root.setCenter(new HomePage(studio));
        //root.setStyle("-fx-background-color: linear-gradient(to right top, #051937, #004d7a, #008793, #00bf72, #a8eb12); ");
        root.setStyle("-fx-background-color: linear-gradient(to right top, #051937, #004d7a, #008793, #00bf72, #a8eb12);");
        
        scene = new Scene(root, 800, 600);
        
        centerStage(primaryStage);
        
        primaryStage.setTitle("Radiant Lotus Yoga");
        //primaryStage.setFullScreen(true);
        primaryStage.setScene(scene);
        primaryStage.show();
        managerRightSide();
    }
    
    private void centerStage(Stage stage) {
        // Get screen bounds
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();

        // Calculate the center position of the screen
        double centerX = (screenBounds.getWidth() - stage.getWidth()) / 2;
        double centerY = (screenBounds.getHeight() - stage.getHeight()) / 2;

        // Set the stage position to the center of the screen
        stage.setX(centerX);
        stage.setY(centerY);
    }
    
    //Manager
    private StackPane managerLeftSide(Stage primaryStage){
    VBox leftSide = new VBox();
    leftSide.setPadding(new Insets(20));
    leftSide.setSpacing(20);
    leftSide.setPrefWidth(primaryStage.getWidth() * 0.25);
    leftSide.setStyle("-fx-background-color: rgba(0, 0, 0, 0.3); -fx-background-radius: 0 30 30 0;");
    leftSide.setAlignment(Pos.TOP_LEFT);

    // Icon at the top
    ImageView icon = new ImageView(new Image("file:///C:/Users/suhar/OneDrive/Documente/NetBeansProjects/Practica/src/practica/images/meniu-logo.png"));
    icon.setFitWidth(300);
    icon.setFitHeight(250);
    icon.setPreserveRatio(true);

    // Menu items
    homeItem = createMenuItem("Acasa", "file:C:\\Users\\suhar\\OneDrive\\Documente\\NetBeansProjects\\Practica\\src\\practica\\images\\Acasa");
    signupItem = createMenuItem("Inscriere", "file:///C:/path/to/signup/icon.png");
    editItem = createMenuItem("Modificare", "file:///C:/path/to/signup/icon.png");
    clientsItem = createMenuItem("Clienti", "file:///C:/path/to/clients/icon.png");
    employeesItem = createMenuItem("Angajati", "file:///C:/path/to/employees/icon.png");
    placesItem = createMenuItem("Filiale", "file:///C:/path/to/employees/icon.png");
    aboutItem = createMenuItem("Despre noi", "file:///C:/path/to/about/icon.png");
    contractItem = createMenuItem("Contracte", "file:///C:/path/to/about/icon.png");
    accesoriiItem = createMenuItem("Accesorii", "file:///C:/path/to/about/icon.png");

    // User information and buttons
    Label usernameLabel = new Label(angajat.getNume());
    usernameLabel.setStyle("-fx-text-fill: white; -fx-font-weight: bold");
    usernameLabel.setPadding(new Insets(5));
    Button editProfileButton = new Button("Editare profil");
    Button logoutButton = new Button("Deconectare");

    // Event handlers for edit and logout buttons
    editProfileButton.setOnAction(event -> {
        modifyAngajat(usernameLabel);
    });

    logoutButton.setOnAction(event -> {
        new Practica().start(primaryStage);
    });

    HBox userInfoBox = new HBox(40);
    userInfoBox.setPadding(new Insets(20));
    userInfoBox.setSpacing(20);
    userInfoBox.getChildren().addAll(usernameLabel, editProfileButton, logoutButton);
    userInfoBox.setAlignment(Pos.BOTTOM_CENTER);
    StackPane.setAlignment(userInfoBox, Pos.BOTTOM_CENTER);
    userInfoBox.setMaxWidth(leftSide.getMaxWidth());
    userInfoBox.setPrefHeight(8);
    userInfoBox.setMaxHeight(10);
    userInfoBox.setStyle("-fx-background-color: rgba(0, 0, 0, 0.1); -fx-background-radius: 0 0 30 0;");

    // StackPane to layer icon, menu items, and user info box
    StackPane stackPane = new StackPane();
    stackPane.getChildren().addAll(leftSide, userInfoBox);

    // Add icon and menu items to the VBox
    leftSide.getChildren().addAll(icon, homeItem, clientsItem, accesoriiItem, employeesItem, contractItem, placesItem, aboutItem);

    return stackPane;
}

    
    //Angajat
    private StackPane angajatLeftSide(Stage primaryStage) {
        VBox leftSide = new VBox();
        leftSide.setPadding(new Insets(20));
        leftSide.setSpacing(20);
        leftSide.setStyle("-fx-background-color: rgba(0, 0, 0, 0.3); -fx-background-radius: 0 30 30 0;");
        leftSide.setAlignment(Pos.TOP_LEFT);

        // Icon at the top
        ImageView icon = new ImageView(new Image("file:///C:/Users/suhar/OneDrive/Documente/NetBeansProjects/Practica/src/practica/images/meniu-logo.png"));
        icon.setFitWidth(300);
        icon.setFitHeight(250);
        icon.setPreserveRatio(true);

        // Menu items
        homeItem = createMenuItem("Acasa", "file:C:\\Users\\suhar\\OneDrive\\Documente\\NetBeansProjects\\Practica\\src\\practica\\images\\Acasa");
        signupItem = createMenuItem("Inscriere", "file:///C:/path/to/signup/icon.png");
        editItem = createMenuItem("Modificare", "file:///C:/path/to/signup/icon.png");
        serviciiItem = createMenuItem("Servicii", "file:///C:/path/to/signup/icon.png");
        employeesItem = createMenuItem("Angajati", "file:///C:/path/to/employees/icon.png");
        aboutItem = createMenuItem("Despre noi", "file:///C:/path/to/about/icon.png");

        // User information and buttons
        Label usernameLabel = new Label(angajat.getNume());
        usernameLabel.setStyle("-fx-text-fill: white; -fx-font-weight: bold");
        usernameLabel.setPadding(new Insets(5));
        Button editProfileButton = new Button("Editare profil");
        Button logoutButton = new Button("Deconectare");

        // Event handlers for edit and logout buttons
        editProfileButton.setOnAction(event -> {
            modifyAngajat(usernameLabel);
        });

        logoutButton.setOnAction(event -> {
            new Practica().start(primaryStage);
        });

        HBox userInfoBox = new HBox(40);
        userInfoBox.setPadding(new Insets(20));
        userInfoBox.setSpacing(20);
        userInfoBox.getChildren().addAll(usernameLabel, editProfileButton, logoutButton);
        userInfoBox.setAlignment(Pos.BOTTOM_CENTER);
        StackPane.setAlignment(userInfoBox, Pos.BOTTOM_CENTER);
        userInfoBox.setMaxWidth(leftSide.getMaxWidth());
        userInfoBox.setPrefHeight(8);
        userInfoBox.setMaxHeight(10);
        userInfoBox.setStyle("-fx-background-color: rgba(0, 0, 0, 0.1); -fx-background-radius: 0 0 30 0;");

        // StackPane to layer icon, menu items, and user info box
        StackPane stackPane = new StackPane();
        stackPane.getChildren().addAll(leftSide, userInfoBox);

        // Add icon and menu items to the VBox
        leftSide.getChildren().addAll(icon, homeItem, editItem, serviciiItem, aboutItem);

        return stackPane;
    }

    //User
    private StackPane userLeftSide(Stage primaryStage) {
        VBox leftSide = new VBox();
        leftSide.setPadding(new Insets(20));
        leftSide.setSpacing(20);
        leftSide.setStyle("-fx-background-color: rgba(0, 0, 0, 0.3); -fx-background-radius: 0 30 30 0;");
        leftSide.setAlignment(Pos.TOP_LEFT);

        // Icon at the top
        ImageView icon = new ImageView(new Image("file:///C:/Users/suhar/OneDrive/Documente/NetBeansProjects/Practica/src/practica/images/meniu-logo.png"));
        icon.setFitWidth(300);
        icon.setFitHeight(250);
        icon.setPreserveRatio(true);

        // Menu items
        homeItem = createMenuItem("Acasa", "file:C:\\Users\\suhar\\OneDrive\\Documente\\NetBeansProjects\\Practica\\src\\practica\\images\\Acasa");
        signupItem = createMenuItem("Inscriere", "file:///C:/path/to/signup/icon.png");
        editItem = createMenuItem("Modificare", "file:///C:/path/to/signup/icon.png");
        clientsItem = createMenuItem("Clienti", "file:///C:/path/to/clients/icon.png");
        employeesItem = createMenuItem("Angajati", "file:///C:/path/to/employees/icon.png");
        aboutItem = createMenuItem("Despre noi", "file:///C:/path/to/about/icon.png");

        // User information and buttons
        Label usernameLabel = new Label(client.getNume());
        usernameLabel.setStyle("-fx-text-fill: white; -fx-font-weight: bold");
        usernameLabel.setPadding(new Insets(5));
        Button editProfileButton = new Button("Editare profil");
        Button logoutButton = new Button("Deconectare");

        // Event handlers for edit and logout buttons
        editProfileButton.setOnAction(event ->{
            modifyClient(usernameLabel);
        });

        logoutButton.setOnAction(event -> {
            new Practica().start(primaryStage);
        });

        HBox userInfoBox = new HBox(40);
        userInfoBox.setPadding(new Insets(20));
        userInfoBox.setSpacing(20);
        userInfoBox.getChildren().addAll(usernameLabel, editProfileButton, logoutButton);
        userInfoBox.setAlignment(Pos.BOTTOM_CENTER);
        StackPane.setAlignment(userInfoBox, Pos.BOTTOM_CENTER);
        userInfoBox.setMaxWidth(leftSide.getMaxWidth());
        userInfoBox.setPrefHeight(8);
        userInfoBox.setMaxHeight(10);
        userInfoBox.setStyle("-fx-background-color: rgba(0, 0, 0, 0.1); -fx-background-radius: 0 0 30 0;");

        // StackPane to layer icon, menu items, and user info box
        StackPane stackPane = new StackPane();
        stackPane.getChildren().addAll(leftSide, userInfoBox);

        // Add icon, menu items to the VBox
        leftSide.getChildren().addAll(icon, homeItem, signupItem, editItem, aboutItem);
        
        return stackPane;
    }
    
    private void modifyClient(Label usernameLabel) {
        if (client != null) {
            ClientFormDialog dialog = new ClientFormDialog(client, studio);
            dialog.showAndWait().ifPresent(updatedClient -> {
                client.setNume(updatedClient.getNume());
                client.setAdresa(updatedClient.getAdresa());
                client.setTelefon(updatedClient.getTelefon());
                client.setEmail(updatedClient.getEmail());
                client.setUsername(updatedClient.getUsername());
                client.setPassword(updatedClient.getPassword());
            });
            usernameLabel.setText(client.getNume());
            studio.salveazaDate("src/practica/Date/locatii.txt", "src/practica/Date/users.txt", "src/practica/Date/servicii.txt", "src/practica/Date/contracte.txt", "src/practica/Date/accesorii.txt");
        }
    }
    private void modifyAngajat(Label usernameLabel) {
        if (angajat != null) {
            AngajatFormDialog dialog = new AngajatFormDialog(angajat, studio);
            dialog.showAndWait().ifPresent(updatedEmployee -> {
                angajat.setNume(updatedEmployee.getNume());
                angajat.setAdresa(updatedEmployee.getAdresa());
                angajat.setTelefon(updatedEmployee.getTelefon());
                angajat.setEmail(updatedEmployee.getEmail());
                angajat.setLocatie(updatedEmployee.getLocatie());
                angajat.setPower(updatedEmployee.getPower());
                angajat.setUsername(updatedEmployee.getUsername());
                angajat.setPassword(updatedEmployee.getPassword());
            });
            usernameLabel.setText(angajat.getNume());
            studio.salveazaDate("src/practica/Date/locatii.txt", "src/practica/Date/users.txt", "src/practica/Date/servicii.txt", "src/practica/Date/contracte.txt", "src/practica/Date/accesorii.txt");
        }
    }
    
    
    private void managerRightSide() {
        
        if(power.equalsIgnoreCase("manager")) {
            homeItem.setOnMouseClicked(event -> {
                root.setCenter(new HomePage(studio));
            });

//            signupItem.setOnMouseClicked(event -> {
//                root.setCenter(new RegistrationPanel(studio));
//            });

            clientsItem.setOnMouseClicked(event -> {
                root.setCenter(new ClientPanel(studio));
            });
            
            accesoriiItem.setOnMouseClicked(event -> {
                root.setCenter(new AccesoriiPanel(studio));
            });

            employeesItem.setOnMouseClicked(event -> {
                root.setCenter(new AngajatPanel(studio));
            });

            placesItem.setOnMouseClicked(event -> {
                root.setCenter(new LocatiePanel(studio));
            });
            
            aboutItem.setOnMouseClicked(event -> {
                root.setCenter(new DespreNoiPage(studio));
            });
            
            contractItem.setOnMouseClicked(event -> {
                root.setCenter(new ContractPanel(studio));
            });
        } else if(power.equalsIgnoreCase("angajat")) {
            homeItem.setOnMouseClicked(event -> {
                root.setCenter(new HomePage(studio));
            });
            editItem.setOnMouseClicked(event -> {
                root.setCenter(new ClientContractPanel(studio, angajat));
            });
            serviciiItem.setOnMouseClicked(event -> {
                root.setCenter(new ServiciiPanel(studio, angajat));
            });
            aboutItem.setOnMouseClicked(event -> {
                root.setCenter(new DespreNoiPage(studio));
            });


        } else {
            homeItem.setOnMouseClicked(event -> {
                root.setCenter(new HomePage(studio));
            });
            editItem.setOnMouseClicked(event -> {
                root.setCenter(new ClientContractPanel(studio, client));
            });
            signupItem.setOnMouseClicked(event -> {
                root.setCenter(new RegistrationPanel(studio, client));
            });
            clientsItem.setOnMouseClicked(event -> {
                root.setCenter(new ClientPanel(studio));
            });
            aboutItem.setOnMouseClicked(event -> {
                root.setCenter(new DespreNoiPage(studio));
            });
        }
        
    }
    
    private HBox createMenuItem(String text, String iconPath) {
        ImageView icon = new ImageView(new Image(iconPath));
        icon.setFitWidth(20);
        icon.setFitHeight(20);
        icon.setPreserveRatio(true);

        Label label = new Label(text);
        label.setFont(new Font("Arial", 18));
        label.setTextFill(Color.WHITE);
        label.setStyle("-fx-cursor: hand;");

        HBox menuItem = new HBox(10, icon, label);
        menuItem.setAlignment(Pos.CENTER_LEFT);

        return menuItem;
    }
   
    
    public void setPower(String power) {
        this.power = power;
    }
    public void setAngajat(Angajat angajat) {
        this.angajat = angajat;
    }
    public void setClient(Client client) {
        this.client = client;
    }
    public Client getClient() {
        return client;
    }
    
    public static void main(String[] args) {
        launch(args);
    }
    public void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
    
}

class ClientFormDialog extends Dialog<Client> {

    private TextField nameField;
    private TextField addressField;
    private TextField phoneField;
    private TextField emailField;
    private TextField usernameField;
    private TextField passwordField;

    public ClientFormDialog(Client client, StudiouYoga studio) {
        App a = new App();
        setTitle(client == null ? "Add Client" : "Modify Client");
        setHeaderText(client == null ? "Enter new client details" : "Modify client details");

        // Create form fields
        nameField = new TextField();
        nameField.setPrefWidth(200);
        addressField = new TextField();
        addressField.setPrefWidth(200);
        phoneField = new TextField();
        phoneField.setPrefWidth(200);
        emailField = new TextField();
        emailField.setPrefWidth(200);
        usernameField = new TextField();
        usernameField.setPrefWidth(200);
        passwordField = new TextField();
        passwordField.setPrefWidth(200);

        if (client != null) {
            nameField.setText(client.getNume());
            addressField.setText(client.getAdresa());
            phoneField.setText(client.getTelefon());
            emailField.setText(client.getEmail());
            usernameField.setText(client.getUsername());
            passwordField.setText(client.getPassword());
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
        gridPane.add(new Label("Email:"), 0, 3);
        gridPane.add(emailField, 1, 3);
        gridPane.add(new Label("Username:"), 0, 4);
        gridPane.add(usernameField, 1, 4);
        gridPane.add(new Label("Password:"), 0, 5);
        gridPane.add(passwordField, 1, 5);

        getDialogPane().setContent(gridPane);

        ButtonType saveButtonType = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);

        Button saveButton = (Button) getDialogPane().lookupButton(saveButtonType);
        saveButton.addEventFilter(ActionEvent.ACTION, event -> {
            if (!validateInput(a, studio, client)) {
                event.consume();
            }
        });

        setResultConverter(dialogButton -> {
            if (dialogButton == saveButtonType) {
                return new Client(
                        nameField.getText(),
                        addressField.getText(),
                        phoneField.getText(),
                        emailField.getText(),
                        usernameField.getText(),
                        passwordField.getText()
                );
            }
            return null;
        });
    }

    private boolean validateInput(App a, StudiouYoga studio, Client client) {
        boolean verUser = studio.getClienti().stream()
                .anyMatch(c -> c.getUsername().equals(usernameField.getText()) && !usernameField.getText().equals(client.getUsername()));
        boolean verEmail = studio.getClienti().stream()
                .anyMatch(c -> c.getEmail().equals(emailField.getText()) && !emailField.getText().equals(client.getEmail()));

        if (nameField.getText().matches(".*\\d.*") || nameField.getText().length() < 6) {
            a.showAlert("Registration Error", "Numele contine cifre sau e prea scurt.");
            return false;
        } else if (addressField.getText().length() <= 5) {
            a.showAlert("Registration Error", "Adresa invalida.");
            return false;
        } else if (verEmail) {
            a.showAlert("Registration Error", "Email deja exista.");
            return false;
        } else if (phoneField.getText().length() != 9 || (!phoneField.getText().startsWith("06") && !phoneField.getText().startsWith("07"))) {
            a.showAlert("Registration Error", "Nr telefon invalid.");
            return false;
        } else if (emailField.getText().length() <= 8 || (!emailField.getText().contains("@gmail.com") && !emailField.getText().contains("@mail.ru") && !emailField.getText().contains("@example.com"))) {
            a.showAlert("Registration Error", "Adresa invalida");
            return false;
        } else if (verUser) {
            a.showAlert("Registration Error", "Username deja exista");
            return false;
        } else if (passwordField.getText().length() <= 4 || passwordField.getText().length() > 16) {
            a.showAlert("Registration Error", "Parola trebuie sa aiba intre 4 si 15 caractere.");
            return false;
        }
        return true;
    }
}

class AngajatFormDialog extends Dialog<Angajat> {

    private TextField nameField;
    private TextField addressField;
    private TextField phoneField;
    private TextField emailField;
    private TextField usernameField;
    private TextField passwordField;
    private ComboBox<String> locationField;
    private ComboBox<String> powerField;

    public AngajatFormDialog(Angajat angajat, StudiouYoga studio) {
        App a = new App();
        setTitle(angajat == null ? "Add Employee" : "Modify Employee");
        setHeaderText(angajat == null ? "Enter new employee details" : "Modify employee details");

        // Create form fields
        nameField = new TextField();
        addressField = new TextField();
        phoneField = new TextField();
        emailField = new TextField();
        usernameField = new TextField();
        passwordField = new TextField();
        locationField = new ComboBox<>();
        for (Locatie locatie : studio.getLocatii()) {
            locationField.getItems().add(locatie.getNume());
        }
        String[] power = {"angajat", "manager"};
        powerField = new ComboBox<>();
        powerField.getItems().addAll(power);

        if (angajat != null) {
            nameField.setText(angajat.getNume());
            addressField.setText(angajat.getAdresa());
            phoneField.setText(angajat.getTelefon());
            emailField.setText(angajat.getEmail());
            locationField.setValue(angajat.getLocatie());
            powerField.setValue(angajat.getPower());
            usernameField.setText(angajat.getUsername());
            passwordField.setText(angajat.getPassword());
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
        gridPane.add(new Label("Email:"), 0, 3);
        gridPane.add(emailField, 1, 3);
        gridPane.add(new Label("Location:"), 0, 4);
        gridPane.add(locationField, 1, 4);
        gridPane.add(new Label("Power:"), 0, 5);
        gridPane.add(powerField, 1, 5);
        gridPane.add(new Label("Username:"), 0, 6);
        gridPane.add(usernameField, 1, 6);
        gridPane.add(new Label("Password:"), 0, 7);
        gridPane.add(passwordField, 1, 7);

        getDialogPane().setContent(gridPane);

        ButtonType saveButtonType = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);

        Button saveButton = (Button) getDialogPane().lookupButton(saveButtonType);
        saveButton.addEventFilter(ActionEvent.ACTION, event -> {
            if (!validateInput(a, studio, angajat)) {
                event.consume();
            }
        });

        setResultConverter(dialogButton -> {
            if (dialogButton == saveButtonType) {
                return new Angajat(
                        nameField.getText(),
                        addressField.getText(),
                        phoneField.getText(),
                        emailField.getText(),
                        usernameField.getText(),
                        passwordField.getText(),
                        powerField.getSelectionModel().getSelectedItem(),
                        locationField.getSelectionModel().getSelectedItem()
                );
            }
            return null;
        });
    }

    private boolean validateInput(App a, StudiouYoga studio, Angajat angajat) {
        boolean verUser = studio.getAngajati().stream()
                .anyMatch(c -> c.getUsername().equals(usernameField.getText()) && !usernameField.getText().equals(angajat.getUsername()));
        boolean verEmail = studio.getAngajati().stream()
                .anyMatch(c -> c.getEmail().equals(emailField.getText()) && !emailField.getText().equals(angajat.getEmail()));

        if (nameField.getText().matches(".*\\d.*") || nameField.getText().length() < 6) {
            a.showAlert("Registration Error", "Name contains numbers or is too short.");
            return false;
        } else if (addressField.getText().length() <= 5) {
            a.showAlert("Registration Error", "Invalid address.");
            return false;
        } else if (verEmail) {
            a.showAlert("Registration Error", "Email is already in use.");
            return false;
        } else if (phoneField.getText().length() != 9 || (!phoneField.getText().startsWith("06") && !phoneField.getText().startsWith("07"))) {
            a.showAlert("Registration Error", "Invalid phone number.");
            return false;
        } else if (emailField.getText().length() <= 8 || (!emailField.getText().contains("@gmail.com") && !emailField.getText().contains("@mail.ru") && !emailField.getText().contains("@example.com"))) {
            a.showAlert("Registration Error", "Invalid email address.");
            return false;
        } else if (verUser) {
            a.showAlert("Registration Error", "Username is already in use.");
            return false;
        } else if (passwordField.getText().length() <= 4 || passwordField.getText().length() > 16) {
            a.showAlert("Registration Error", "Password must be between 5 and 16 characters.");
            return false;
        } else if (locationField.getSelectionModel().isEmpty()) {
            a.showAlert("Registration Error", "Please select a location.");
            return false;
        } else if (powerField.getSelectionModel().isEmpty()) {
            a.showAlert("Registration Error", "Please select a power.");
            return false;
        }
        return true;
    }
};