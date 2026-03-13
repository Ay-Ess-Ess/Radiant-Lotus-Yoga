package practica.ClaseCuDate;

public class Angajat extends Persoana{
    
    private String locatie;
    
    public Angajat() {
        super("angajat");
    }

    public Angajat(String nume, String adresa, String telefon, String email, String username, String password, String power, String locatie) {
        super(nume, adresa, telefon, email, username, password, power);
        this.locatie = locatie;
    }
    
    public void setLocatie(String locatie) {
        this.locatie = locatie;
    }
    public String getLocatie() {
        return locatie;
    }
    
    @Override
    public String toString() {
        return super.toString() + "," + locatie;
    }
}
