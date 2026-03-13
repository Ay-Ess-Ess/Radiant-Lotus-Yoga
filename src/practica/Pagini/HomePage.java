package practica.Pagini;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import practica.ClaseCuDate.StudiouYoga;

public class HomePage extends BorderPane {

    private StudiouYoga studio;

    public HomePage(StudiouYoga studio) {
        this.studio = studio;
        setupUI();
    }

    private void setupUI() {
        // Content central
        VBox centerContent = new VBox();
        centerContent.setAlignment(Pos.CENTER);
        centerContent.setPadding(new Insets(20));
        centerContent.setSpacing(20);
        

        // Eticheta titlului
        Label titleLabel = new Label("Bine ați venit la Studioul nostru de Yoga Radiant Lotus.");
        titleLabel.setMaxWidth(500);
        titleLabel.setWrapText(true);
        titleLabel.setTextAlignment(TextAlignment.CENTER);
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 22));

        // Etichetele cu informații
        Label infoLabel = new Label("Bine ați venit la studioul nostru de yoga! Suntem dedicați să vă ajutăm să "
                + "atingeți echilibrul, flexibilitatea și liniștea interioară prin diversele noastre programe de yoga.");
        infoLabel.setMaxWidth(600);
        infoLabel.setWrapText(true);
        infoLabel.setTextAlignment(TextAlignment.LEFT);
        infoLabel.setFont(Font.font("Arial", 16));

        Label missionLabel = new Label("Misiunea Noastră:");
        missionLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        Label missionText = new Label("Promovăm starea de bine fizică, mentală și spirituală prin practica yoga.");
        missionText.setMaxWidth(600);
        missionText.setWrapText(true);
        missionText.setTextAlignment(TextAlignment.LEFT);
        missionText.setFont(Font.font("Arial", 16));

        Label visionLabel = new Label("Viziunea Noastră:");
        visionLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        Label visionText = new Label("Creăm o comunitate unde fiecare individ poate crește, se poate vindeca și prospera prin yoga.");
        visionText.setMaxWidth(600);
        visionText.setWrapText(true);
        visionText.setTextAlignment(TextAlignment.LEFT);
        visionText.setFont(Font.font("Arial", 16));
        
        

        // Adăugarea conținutului în panoul central
        centerContent.getChildren().addAll(titleLabel, infoLabel, missionLabel, missionText, visionLabel, visionText);
        setCenter(centerContent);

        // Stilizare
        centerContent.setStyle("-fx-background-color: rgba(256, 256, 256, 0.7);; -fx-border-color: #cccccc; -fx-border-radius: 10px; -fx-background-radius: 10px;-fx-border-width: 1px;");
        centerContent.setMaxWidth(800);
        centerContent.setMaxHeight(600);
        setPadding(new Insets(20));
    }
}
