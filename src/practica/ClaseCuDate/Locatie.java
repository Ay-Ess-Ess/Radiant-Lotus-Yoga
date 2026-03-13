package practica.ClaseCuDate;

import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class Locatie {
    private String nume, adresa, telefon, persoanaContact;
    private List<Accesoriu> accesorii;
    private BooleanProperty selected;

    public Locatie() {
        this.nume = "none";
        this.adresa = "none";
        this.telefon = "none";
        this.persoanaContact = "none";
        accesorii = new ArrayList<>();
        this.selected = new SimpleBooleanProperty(false);
    }

    public Locatie(String nume, String adresa, String telefon, String persoanaContact) {
        this.nume = nume;
        this.adresa = adresa;
        this.telefon = telefon;
        this.persoanaContact = persoanaContact;
        accesorii = new ArrayList<>();
        this.selected = new SimpleBooleanProperty(false);
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public String getPersoanaContact() {
        return persoanaContact;
    }

    public void setPersoanaContact(String persoanaContact) {
        this.persoanaContact = persoanaContact;
    }
    
    public List<Accesoriu> getAccesorii() {
        return accesorii;
    }

    public void setAccesorii(List<Accesoriu> accesorii) {
        this.accesorii = accesorii;
    }
    
    public void addAccesoriu(Accesoriu accesoriu) {
        accesorii.add(accesoriu);
    }
    public void removeAccesoriu(Accesoriu accesoriu) {
        accesorii.remove(accesoriu);
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
        return nume + "," + adresa + "," + telefon + "," + persoanaContact;
    }
}