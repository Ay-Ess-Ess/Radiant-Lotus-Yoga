package practica.ClaseCuDate;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class Serviciu {
    private String tip;
    private String locatie;
    private double pretPerOra;
    private String antrenor;
    private BooleanProperty selected;

    public Serviciu() {
        this.tip = "none";
        this.locatie = "none";
        this.pretPerOra = 0.0;
        this.antrenor = "none";
        this.selected = new SimpleBooleanProperty(false);
    }

    public Serviciu(String tip, String locatie, double pretPerOra, String antrenor) {
        this.tip = tip;
        this.locatie = locatie;
        this.pretPerOra = pretPerOra;
        this.antrenor = antrenor;
        this.selected = new SimpleBooleanProperty(false);
    }
    
    public Serviciu(String tip, String locatie, double pretPerOra) {
        this.tip = tip;
        this.locatie = locatie;
        this.pretPerOra = pretPerOra;
        this.selected = new SimpleBooleanProperty(false);
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public String getLocatie() {
        return locatie;
    }

    public void setLocatie(String locatie) {
        this.locatie = locatie;
    }

    public double getPretPerOra() {
        return pretPerOra;
    }

    public void setPretPerOra(double pretPerOra) {
        this.pretPerOra = pretPerOra;
    }

    public String getAntrenor() {
        return antrenor;
    }

    public void setAntrenor(String antrenor) {
        this.antrenor = antrenor;
    }
    public boolean isSelected() {
        return selected.get();
    }

    public void setSelected(boolean selected) {
        this.selected.set(selected);
    }

    public BooleanProperty selectedProperty() {
        return selected;
    }
    

    @Override
    public String toString() {
        return tip + "," + locatie + "," + pretPerOra + "," + antrenor;
    }
}