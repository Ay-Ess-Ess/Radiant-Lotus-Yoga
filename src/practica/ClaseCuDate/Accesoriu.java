package practica.ClaseCuDate;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class Accesoriu {
    private String nume, locatie;
    private int cantitate;
    private double pret;
    private BooleanProperty selected;

    public Accesoriu() {
        this.nume = "none";
        this.pret = 0.0;
        this.cantitate = 0;
        this.locatie = "none";
        this.selected = new SimpleBooleanProperty(false);
    }

    public Accesoriu(String nume, int cantitate, double pret, String locatie) {
        this.nume = nume;
        this.pret = pret;
        this.cantitate = cantitate;
        this.locatie = locatie;
        this.selected = new SimpleBooleanProperty(false);
    }
    
    public Accesoriu(String nume, int cantitate, double pret) {
        this.nume = nume;
        this.pret = pret;
        this.cantitate = cantitate;
        this.selected = new SimpleBooleanProperty(false);
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public double getPret() {
        return pret;
    }

    public void setPret(double pret) {
        this.pret = pret;
    }
    
    public int getCantitate() {
        return cantitate;
    }
    public void setCantitate(int cantitate) {
        this.cantitate = cantitate;
    }
    
    public String getLocatie() {
        return locatie;
    }

    public void setLocatie(String locatie) {
        this.locatie = locatie;
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
        return nume + "," + pret + "," + cantitate + "," + locatie;
    }
}
