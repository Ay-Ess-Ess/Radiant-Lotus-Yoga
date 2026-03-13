package practica.Pagini;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import practica.ClaseCuDate.StudiouYoga;

public class DespreNoiPage extends BorderPane {

    private StudiouYoga studio;

    public DespreNoiPage(StudiouYoga studio) {
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
        Label titleLabel = new Label("Despre Noi");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));

        // Etichetele cu informații
        Label infoLabel = new Label("La Studioul de Yoga Radiant Lotus, ne dedicăm să aducem echilibru și armonie în viața ta. "
                + "Oferim diverse programe de yoga menite să îmbunătățească starea de bine fizică, mentală și spirituală.");
        infoLabel.setMaxWidth(600);
        infoLabel.setWrapText(true);
        infoLabel.setTextAlignment(TextAlignment.LEFT);
        infoLabel.setFont(Font.font("Arial", 16));

        Label historyLabel = new Label("Istoria Noastră:");
        historyLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        Label historyText = new Label("Studioul de Yoga Radiant Lotus a fost fondat în anul 2010 cu scopul de a crea un spațiu sigur "
                + "și primitor pentru practicarea yoga. De atunci, am crescut și am devenit o comunitate vibrantă de practicanți dedicați.");
        historyText.setMaxWidth(600);
        historyText.setWrapText(true);
        historyText.setTextAlignment(TextAlignment.LEFT);
        historyText.setFont(Font.font("Arial", 16));

        Label teamLabel = new Label("Echipa Noastră:");
        teamLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        Label teamText = new Label("Echipa noastră este formată din instructori experimentați și pasionați care sunt dedicați să vă "
                + "ghideze în fiecare pas al călătoriei voastre yoga.");
        teamText.setMaxWidth(600);
        teamText.setWrapText(true);
        teamText.setTextAlignment(TextAlignment.LEFT);
        teamText.setFont(Font.font("Arial", 16));

        Label philosophyLabel = new Label("Filosofia Noastră:");
        philosophyLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        Label philosophyText = new Label("Credem că yoga este mai mult decât o practică fizică. Este un mod de viață care promovează "
                + "starea de bine holistică și conexiunea cu sinele interior.");
        philosophyText.setMaxWidth(600);
        philosophyText.setWrapText(true);
        philosophyText.setTextAlignment(TextAlignment.LEFT);
        philosophyText.setFont(Font.font("Arial", 16));

        // Adăugarea conținutului în panoul central
        centerContent.getChildren().addAll(titleLabel, infoLabel, historyLabel, historyText, teamLabel, teamText, philosophyLabel, philosophyText);
        setCenter(centerContent);

        // Stilizare
        centerContent.setStyle("-fx-background-color: rgba(256, 256, 256, 0.7); -fx-border-color: #cccccc; -fx-border-radius: 10px; -fx-background-radius: 10px; -fx-border-width: 1px;");
        centerContent.setMaxWidth(800);
        centerContent.setMaxHeight(600);
        setPadding(new Insets(20));
    }
}
