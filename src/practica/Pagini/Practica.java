package practica.Pagini;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;
import practica.ClaseCuDate.Angajat;
import practica.ClaseCuDate.Client;
import practica.ClaseCuDate.StudiouYoga;



public class Practica extends Application {
    private static StudiouYoga studio = new StudiouYoga();
    private static App app = new App();
    private static Client client = new Client();
    private static VBox textField, loginField;
    private static Scene scene;
    private static Text text; 
    private static Text signIn;
    private static Button btn;
    private static TextField login;
    private static PasswordField pass;
    private static Stage primaryStage;
    
    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        studio.incarcaDate("src/practica/Date/locatii.txt", "src/practica/Date/users.txt", "src/practica/Date/servicii.txt", "src/practica/Date/contracte.txt", "src/practica/Date/accesorii.txt");
//        studio.addServiciu(new Serviciu("Flotari", "Locatie 1", 200, "Plop Ion"));
//        studio.incarcaServicii("src/practica/Date/servicii.txt");
        text = text(); 
        signIn = signIn();
        btn = button();
        login = textFields();
        pass = passwordFields();
        
        signIn.setOnMouseClicked((MouseEvent event) -> {
            if(signIn.getText().equals("LOGIN")) {
                loginMenu();
                
            } else if(signIn.getText().equals("SIGN IN")) {
                signInMenu();
            }
        });
        
        createGUI(primaryStage);
        loginMenu();
        centerStage(primaryStage);
        
    }
    
    private void createGUI(Stage primaryStage) {
        java.io.InputStream iconStream = getClass().getResourceAsStream("/practica/images/logo.png");
        if (iconStream != null) {
            primaryStage.getIcons().add(new Image(iconStream));
        }
        primaryStage.setHeight(700);
        primaryStage.setWidth(800);
        primaryStage.setTitle("Radiant Lotus Yoga");
        primaryStage.show();
    }
    
    private void centerStage(Stage stage) {
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        
        double centerX = (screenBounds.getWidth() - stage.getWidth()) / 2;
        double centerY = (screenBounds.getHeight() - stage.getHeight()) / 2;

        stage.setX(centerX);
        stage.setY(centerY);
    }
    
    private Text text() {
        Text text = new Text();
        text.setText("Login");
        text.setStyle("-fx-fill: white; -fx-font-size: 28px; -fx-font-weight: bold");
        return text;
    }
    
    private Button button() {
        Button btn = new Button();
        btn.setText("LOGIN");
        btn.setMinHeight(40);
        btn.setMaxWidth(210);
        btn.setCursor(Cursor.HAND);
        btn.setStyle("-fx-font-size: 15px; -fx-font-weight: bold; -fx-background-color: #2a3747; -fx-text-fill: white; -fx-border: none; -fx-background-radius: 40px;");
        return btn;
    }

    private TextField textFields() {
        TextField text = new TextField();
        text.setPromptText("Username");
        text.setMinHeight(40);
        text.setMaxWidth(250);
        text.setStyle("-fx-font-size: 13px; -fx-font-weight: bold; -fx-border: none; -fx-background-radius: 8px; -fx-text-fill: #909292");
        text.setAlignment(Pos.CENTER);
        return text;
    }
    private PasswordField passwordFields() {
        PasswordField text = new PasswordField();
        text.setPromptText("Password");
        text.setMinHeight(40);
        text.setMaxWidth(250);
        text.setStyle("-fx-font-size: 13px; -fx-font-weight: bold; -fx-border: none; -fx-background-radius: 8px; -fx-text-fill: #909292");
        text.setAlignment(Pos.CENTER);
        return text;
    }
    
    private Text signIn() {
        Text text = new Text();
        text.setText("SIGN IN");
        text.setStyle("-fx-text-fill: red; -fx-font-size: 15px; -fx-font-weight: bold;");
        text.setFill(Color.WHITE);
        text.setCursor(Cursor.HAND);
        return text;
    }
    
    private void signInMenu() {
        text.setText("SIGN IN");
        btn.setText("SIGN IN");
        signIn.setText("LOGIN");
        TextField nume = textFields();
        nume.setPromptText("Nume");
        TextField adresa = textFields();
        adresa.setPromptText("Adresa");
        TextField telefon = textFields();
        telefon.setPromptText("Telefon");
        TextField email = textFields();
        email.setPromptText("Email");
        textField = new VBox(10, nume, adresa, login, telefon, email, pass);
        textField.setSpacing(15);
        loginField = new VBox(10, text, textField, btn, signIn);
        loginField.setSpacing(30);
        textField.setAlignment(Pos.CENTER);
        loginField.setAlignment(Pos.CENTER);
        loginField.setMaxWidth(380);
        loginField.setPrefHeight(550);
        loginField.setMaxHeight(560);
        loginField.setStyle("-fx-background-color: rgba(256, 256, 256, 0.3); -fx-border-color: white; -fx-border-width: 3px; -fx-border-radius: 10px; -fx-background-radius: 10px;");
        StackPane.setAlignment(loginField, Pos.CENTER);
        StackPane.setAlignment(signIn, Pos.BASELINE_RIGHT);
        
        btn.setOnAction(event ->{
            register(nume, adresa, login, telefon, email, pass);
        });
        
        StackPane root = new StackPane();
        root.setAlignment(Pos.TOP_CENTER);
        root.getChildren().add(loginField);
        root.setStyle("-fx-background-color: linear-gradient(to right top, #051937, #004d7a, #008793, #00bf72, #a8eb12); ");
        
        scene = new Scene(root, Screen.getPrimary().getVisualBounds().getWidth(), Screen.getPrimary().getVisualBounds().getHeight());
        
        primaryStage.setScene(scene);
        
        btn.requestFocus(); 
    }
    
    private void loginMenu() {
        text.setText("LOGIN");
        btn.setText("LOGIN");
        signIn.setText("SIGN IN");
        StackPane root = new StackPane();
        //root.setStyle("-fx-background-color: linear-gradient(to right top, #051937, #004d7a, #008793, #00bf72, #a8eb12); ");
        root.setStyle("-fx-background-color: linear-gradient(to right top, #051937, #004d7a, #008793, #00bf72, #a8eb12); ");
        textField = new VBox(10, login, pass);
        textField.setSpacing(15);
        loginField = new VBox(10, text, textField, btn, signIn);
        loginField.setSpacing(30);
        textField.setAlignment(Pos.CENTER);
        loginField.setAlignment(Pos.CENTER);
        loginField.setMaxWidth(380);
        loginField.setPrefHeight(380);
        loginField.setMaxHeight(400);
        loginField.setStyle("-fx-background-color: rgba(256, 256, 256, 0.3); -fx-border-color: white; -fx-border-width: 3px; -fx-border-radius: 10px; -fx-background-radius: 10px;");
        StackPane.setAlignment(loginField, Pos.CENTER);
        StackPane.setAlignment(signIn, Pos.BASELINE_RIGHT);
        
        root.setAlignment(Pos.TOP_CENTER);
        root.getChildren().add(loginField);
        
        btn.setOnAction(event ->{
            String user, password;
            for(Client c : studio.getClienti()) {
                user = c.getUsername(); password = c.getPassword();
                user = user.replaceAll(" ", ""); password = password.replaceAll(" ", "");
                if(login.getText().equals(user) && pass.getText().equals(password)) {
                    new App().setPower("user");
                    new App().setClient(c);
                    app.start(primaryStage);
                    break;
                }
            }
            for(Angajat a : studio.getAngajati()) {
                user = a.getUsername(); password = a.getPassword();
                user = user.replaceAll(" ", ""); password = password.replaceAll(" ", "");
                if(login.getText().equals(user) && pass.getText().equals(password)) {
                    new App().setPower(a.getPower());
                    new App().setAngajat(a);
                    app.start(primaryStage);
                    break;
                }
            }
        });
        
        login.setOnKeyPressed(new EventHandler<KeyEvent>() {
            //System.out.print();
            String user = client.getUsername(), password = client.getPassword();
            @Override
            public void handle(KeyEvent event) {
                for(Client c : studio.getClienti()) {
                    user = c.getUsername(); password = c.getPassword();
                    user = user.replaceAll(" ", ""); password = password.replaceAll(" ", "");
                    if (event.getCode() == KeyCode.ENTER && login.getText().equals(user) && pass.getText().equals(password)) {
                        new App().setPower("user");
                        new App().setClient(c);
                        app.start(primaryStage);
                    }
                }
                for(Angajat a : studio.getAngajati()) {
                    user = a.getUsername(); password = a.getPassword();
                    user = user.replaceAll(" ", ""); password = password.replaceAll(" ", "");
                    if (event.getCode() == KeyCode.ENTER && login.getText().equals(user) && pass.getText().equals(password)) {
                        new App().setPower(a.getPower());
                        new App().setAngajat(a);
                        app.start(primaryStage);
                    }
                }
                if (event.getCode() == KeyCode.ENTER) {
                    pass.requestFocus();
                }
            }
        });
//        
        pass.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER) {
                    String enteredUsername = login.getText().replaceAll(" ", "");
                    String enteredPassword = pass.getText().replaceAll(" ", "");

                    // Check clients
                    for (Client c : studio.getClienti()) {
                        String user = c.getUsername().replaceAll(" ", "");
                        String password = c.getPassword().replaceAll(" ", "");
                        if (enteredUsername.equals(user) && enteredPassword.equals(password)) {
                            App app = new App();
                            app.setPower("user");
                            app.setClient(c);
                            app.start(primaryStage);
                            return;
                        }
                    }

                    // Check employees
                    for (Angajat a : studio.getAngajati()) {
                        String user = a.getUsername().replaceAll(" ", "");
                        String password = a.getPassword().replaceAll(" ", "");
                        if (enteredUsername.equals(user) && enteredPassword.equals(password)) {
                            App app = new App();
                            app.setPower(a.getPower());
                            app.setAngajat(a);
                            app.start(primaryStage);
                            return;
                        }
                    }
                }
            }
        });

        
        scene = new Scene(root);
        
        primaryStage.setScene(scene);
        
        btn.requestFocus(); 
    }
    
    public StudiouYoga getStudiouYoga() {
        return studio;
    }
    
    public static void main(String[] args) {
        launch(args);
    }
    
    private void register(TextField nume, TextField adresa, TextField username, TextField telefon, TextField email, PasswordField password) {
        String rNume = nume.getText();
        String rAdresa = adresa.getText();
        String rUsername = username.getText();
        String rTelefon = telefon.getText();
        String rEmail = email.getText();
        String rPassword = password.getText();

        // Validate if all fields are filled
        if(rNume.isEmpty() || rAdresa.isEmpty() || rUsername.isEmpty() || rTelefon.isEmpty() || rEmail.isEmpty() || rPassword.isEmpty()) {
            showAlert("Eroare de registrare", "Completati toate blancurile cu date");
            return;
        } 
        if(rNume.matches(".*\\d.*") || rNume.length() < 6) {
            showAlert("Eroare de registrare", "Numele contine cifre.");
            return;
        }
        if(rAdresa.length() <= 5) {
            showAlert("Eroare de registrare", "Introduceti corect adresa.");
            return;
        }
        for(Client client : studio.getClienti()) {
            if(client.getUsername().equals(rUsername)) {
                showAlert("Eroare de registrare", "Acest username se foloseste deja.");
                return;
            }
        }
        if(rTelefon.length() != 9 || (!rTelefon.contains("06") && !rTelefon.contains("07"))) {
            showAlert("Eroare de registrare", "Introduceti corect numarul de telefon.");
            return;
        }
        if(rEmail.length() <= 8 || (!rEmail.contains("@gmail.com") && !rEmail.contains("@mail.ru") && !rEmail.contains("@example.com"))) {
            showAlert("Eroare de registrare", "Introduceti corect adresa e-mail.");
            return;
        }
        for(Client client : studio.getClienti()) {
            if(client.getEmail().equals(rEmail)) {
                showAlert("Eroare de registrare", "Acest email se foloseste deja.");
                return;
            }
        }
        if(rPassword.length() <= 4 || rPassword.length() > 16) {
            showAlert("Eroare de registrare", "Introduceti corect parola.");
            return;
        }

        studio.addClient(new Client(rNume, rAdresa, rTelefon, rEmail, rUsername, rPassword));
        studio.salveazaDate("src/practica/Date/locatii.txt", "src/practica/Date/users.txt", "src/practica/Date/servicii.txt", "src/practica/Date/contracte.txt", "src/practica/Date/accesorii.txt");
        loginMenu();
    }
    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }   
}