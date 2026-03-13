package practica.ClaseCuDate;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import practica.ClaseCuDate.StudiouYoga;

public class Contract implements Plata {
    private String client;
    private String serviciu, locatie;
    private LocalDate perioadaInceput;
    private int perioada;
    private String time;
    private double cost = 0.0;
    private String antrenor;
    private BooleanProperty selected;
    
    private String locatia;

    public Contract() {
        this.client = "none";
        this.serviciu = "none";
        this.perioadaInceput = LocalDate.now();
        this.perioada = 1;
        this.antrenor = "none";
        this.selected = new SimpleBooleanProperty(false);
    }

    public Contract(String client, String locatie, String serviciu, LocalDate perioadaInceput, int perioada, String time, String antrenor) {
        this.client = client;
        this.serviciu = serviciu;
        this.perioadaInceput = perioadaInceput;
        this.perioada = perioada;
        this.time = time;
        this.locatie = locatie;
        this.antrenor = antrenor;
        this.cost = 0;
        this.selected = new SimpleBooleanProperty(false);
    }
    public Contract(String client, String locatie, String serviciu, LocalDate perioadaInceput, int perioada, String time, String antrenor, double cost) {
        this.client = client;
        this.serviciu = serviciu;
        this.perioadaInceput = perioadaInceput;
        this.perioada = perioada;
        this.time = time;
        this.locatie = locatie;
        this.antrenor = antrenor;
        this.cost = cost;
        this.selected = new SimpleBooleanProperty(false);
    }

    
    public void setLocatia(String locatia) {
        this.locatia = locatia;
    }
    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }
    
    public void setLocatie(String locatie) {
        this.locatie= locatie;
    }
    public String getLocatie() {
        return locatie;
    }
    

    public String getServiciu() {
        return serviciu;
    }

    public void setServiciu(String serviciu) {
        this.serviciu = serviciu;
    }

    public LocalDate getPerioadaInceput() {
        return perioadaInceput;
    }

    public void setPerioadaInceput(LocalDate perioadaInceput) {
        this.perioadaInceput = perioadaInceput;
    }

    public int getPerioada() {
        return perioada;
    }

    public void setPerioada(int perioada) {
        this.perioada = perioada;
    }
    
    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
    
    public String getAntrenor() {
        return antrenor;
    }

    public void setAntrenor(String antrenor) {
        this.antrenor = antrenor;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
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
    public void plateste() {
        System.out.println("Plata a fost realizată cu succes pentru contractul cu clientul " + client);
    }

    @Override
    public String toString() {
        return client + "," + locatie + "," + serviciu + "," + perioadaInceput + "," + perioada + "," + time + "," + antrenor + "," + cost;
    }
}
