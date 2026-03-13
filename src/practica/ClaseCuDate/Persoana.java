package practica.ClaseCuDate;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public abstract class Persoana {
    private String nume, adresa, telefon, email, power, username, password;
    private BooleanProperty selected;

    public Persoana(String power) {
        this.nume = "none";
        this.adresa = "none";
        this.telefon = "none";
        this.email = "none";
        this.username = "none";
        this.password = "none";
        this.power = power;
        this.selected = new SimpleBooleanProperty(false);
    }

    public Persoana(String nume, String adresa, String telefon, String email, String username, String password, String power) {
        this.nume = nume;
        this.adresa = adresa;
        this.telefon = telefon;
        this.email = email;
        this.username = username;
        this.password = password;
        this.power = power;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPower() {
        return power;
    }

    public void setPower(String power) {
        this.power = power;
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
        return power + "," + nume + "," + adresa + "," + telefon + "," + email + "," + username + "," + password;
    } 
}