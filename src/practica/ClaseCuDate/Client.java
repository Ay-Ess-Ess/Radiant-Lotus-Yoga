package practica.ClaseCuDate;

public class Client extends Persoana {

    public Client() {
        super("user");
    }

    public Client(String nume, String adresa, String telefon, String email, String username, String password) {
        super(nume, adresa, telefon, email, username, password, "user");
    }
}
