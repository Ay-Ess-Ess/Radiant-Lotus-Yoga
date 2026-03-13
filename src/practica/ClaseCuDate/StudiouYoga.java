package practica.ClaseCuDate;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class StudiouYoga {
    private List<Locatie> locatii;
    private List<Client> clienti;
    private List<Angajat> angajati;
    private List<Serviciu> servicii;
    private List<Contract> contracte;
    

    public StudiouYoga() {
        this.locatii = new ArrayList<>();
        this.clienti = new ArrayList<>();
        this.angajati = new ArrayList<>();
        this.servicii = new ArrayList<>();
        this.contracte = new ArrayList<>();
    }
    
    public List<Locatie> getLocatii() {
        return locatii;
    }

    public void setLocatii(List<Locatie> locatii) {
        this.locatii = locatii;
    }
    
    public void removeLocatie(Locatie locatie) {
        locatii.remove(locatie);
    }
    public void removeAccesoriiFromLocatii(Accesoriu selectedAccesorii) {
        locatii.forEach(locatie -> locatie.removeAccesoriu(selectedAccesorii));
    }

    public List<Client> getClienti() {
        return clienti;
    }

    public void setClienti(List<Client> clienti) {
        this.clienti = clienti;
    }
    
    public void removeClient(Client client) {
        clienti.remove(client);
    }
    
    public List<Angajat> getAngajati() {
        return angajati;
    }

    public void setAngajati(List<Angajat> angajati) {
        this.angajati = angajati;
    }
    
    public void removeAngajat(Angajat angajat) {
        angajati.remove(angajat);
    }

    public List<Serviciu> getServicii() {
        return servicii;
    }

    public void setServicii(List<Serviciu> servicii) {
        this.servicii = servicii;
    }
    
    public void removeServiciu(Serviciu serviciu) {
        servicii.remove(serviciu);
    }
    
    public void adaugaServiciu(Serviciu serviciu) {
        servicii.add(serviciu);
    }
    

    public List<Contract> getContracte() {
        return contracte;
    }

    public void setContracte(List<Contract> contracte) {
        this.contracte = contracte;
    }
    
    public void removeContract(Contract contract) {
        contracte.remove(contract);
    }

    // Getteri și Setteri pentru accesorii
    

    public void addLocatie(Locatie locatie) {
        locatii.add(locatie);
    }

    public void addClient(Client client) {
        clienti.add(client);
    }

    public void addAngajat(Angajat angajat) {
        angajati.add(angajat);
    }
    
    public void addServiciu(Serviciu serviciu) {
        servicii.add(serviciu);
    }

    public void addContract(Contract contract) {
        contracte.add(contract);
    }
    


    public void salveazaDate(String numeFisierLocatii, String numeFisierClienti, String numeFisierServicii,
                             String numeFisierContracte, String numeFisierAccesorii) {
        salveazaLocatii(numeFisierLocatii);
        salveazaClienti(numeFisierClienti);
        salveazaServicii(numeFisierServicii);
        salveazaContracte(numeFisierContracte);
        salveazaAccesorii(numeFisierAccesorii);
    }

    // Metoda pentru incarcarea datelor din fisiere
    public void incarcaDate(String numeFisierLocatii, String numeFisierClienti, String numeFisierServicii,
                            String numeFisierContracte, String numeFisierAccesorii) {
        incarcaLocatii(numeFisierLocatii);
        incarcaClienti(numeFisierClienti);
        incarcaServicii(numeFisierServicii);
        incarcaContracte(numeFisierContracte);
        incarcaAccesorii(numeFisierAccesorii);
    }

    // Metode private pentru salvarea datelor fiecărui tip în fișiere individuale

    private void salveazaLocatii(String numeFisier) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(numeFisier))) {
            for (Locatie locatie : locatii) {
                writer.write(locatie.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void salveazaClienti(String numeFisier) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(numeFisier))) {
            for (Angajat anjagat : angajati) {
                writer.write(anjagat.toString());
                writer.newLine();
            }
            for (Client client : clienti) {
                writer.write(client.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void salveazaServicii(String numeFisier) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(numeFisier))) {
            for (Serviciu serviciu : servicii) {
                writer.write(serviciu.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void salveazaContracte(String numeFisier) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(numeFisier))) {
            for (Contract contract : contracte) {
                writer.write(contract.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void salveazaAccesorii(String numeFisier) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(numeFisier))) {
            for(Locatie l : locatii) 
                for (Accesoriu accesoriu : l.getAccesorii()) {
                    writer.write(accesoriu.toString());
                    writer.newLine();
                }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Metode private pentru încărcarea datelor fiecărui tip din fișiere individuale

    private void incarcaLocatii(String numeFisier) {
        locatii.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader(numeFisier))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split(",");
                if (tokens.length >= 4) {
                    String nume = tokens[0];
                    String adresa = tokens[1];
                    String telefon = tokens[2];
                    String persoanaContact = tokens[3];
                    Locatie locatie = new Locatie(nume, adresa, telefon, persoanaContact);
                    locatii.add(locatie);
                } else {
                    System.err.println("Linie invalidă în fișier: " + line);
                }
            }
            reader.close();
        } catch (IOException e) {
            System.err.println("Eroare la citirea fișierului: " + e.getMessage());
        }
    }

    private void incarcaClienti(String numeFisier) {
        clienti.clear();
        angajati.clear();

        try (BufferedReader reader = new BufferedReader(new FileReader(numeFisier))) {
            String line;
            Set<String> usernames = new HashSet<>();

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length < 7) {
                    System.out.println("Eroare la citirea liniei: " + line);
                    continue;
                }

                String power = parts[0].trim();
                String nume = parts[1].trim();
                String adresa = parts[2].trim();
                String telefon = parts[3].trim();
                String email = parts[4].trim();
                String username = parts[5].trim();
                String password = parts[6].trim();

                // Validation checks
                if (!validateInput(nume, adresa, telefon, email, username, password)) {
                    continue;
                }

                if (power.equalsIgnoreCase("user")) {
                    Client client = new Client(nume, adresa, telefon, email, username, password);
                    clienti.add(client);
                } else if (power.equalsIgnoreCase("angajat") || power.equalsIgnoreCase("manager")) {
                    if (parts.length < 8) {
                        System.out.println("Eroare la citirea liniei: " + line);
                        continue;
                    }
                    String locatie = parts[7].trim();
                    Angajat angajat = new Angajat(nume, adresa, telefon, email, username, password, power, locatie);
                    angajati.add(angajat);
                } else {
                    System.out.println("Eroare la citirea informatiei din fisierul cu Useri: " + power);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean validateInput(String nume, String adresa, String telefon, String email, String username, String password) {
        try {
            if (nume.matches(".*\\d.*") || nume.length() < 6) {
                throw new Exception("Numele contine cifre sau e prea scurt.");
            } else if (adresa.length() <= 5) {
                throw new Exception("Adresa invalida.");
            } else if (telefon.length() < 9 || (!telefon.startsWith("06") && !telefon.startsWith("07"))) {
                throw new Exception("Numar de telefon invalid.");
            } else if (email.length() <= 8 || (!email.contains("@gmail.com") && !email.contains("@mail.ru") && !email.contains("@example.com"))) {
                throw new Exception("Adresa de email invalida.");
            } else if (password.length() <= 4 || password.length() > 16) {
                throw new Exception("Parola trebuie sa fie intre 5 si 16 caractere.");
            }
            return true;
        } catch (Exception e) {
            System.out.println("Eroare de validare: " + e.getMessage());
            return false;
        }
    }


    private void incarcaServicii(String numeFisier) {
        servicii.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader(numeFisier))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] part = line.split(",");
                String tip = part[0];
                String locatie = part[1];
                double pret = Double.parseDouble(part[2]);
                String antrenor = part[3];
                Serviciu serviciu = new Serviciu(tip, locatie, pret, antrenor);
                servicii.add(serviciu);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void incarcaContracte(String numeFisier) {
        contracte.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader(numeFisier))) {
            String line;
            while ((line = reader.readLine()) != null) {
                try {
                    String[] part = line.split(",");
                    if (part.length != 8) {
                        System.err.println("Linie Invalida: " + line);
                        continue;
                    }
                    String client = part[0];
                    String locatie = part[1];
                    String serviciu = part[2];
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    Date dataInceput = dateFormat.parse(part[3]);
                    LocalDate localDate = dataInceput.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();
                    int perioada = Integer.parseInt(part[4]);
                    String time = part[5];
                    String antrenor = part[6];
                    double cost = Double.parseDouble(part[7]);

                    if (localDate.isBefore(LocalDate.now())) {
                        System.out.println("Abonamentul clientului " + client + " a expirat.");
                    } else {
                        Contract contract = new Contract(client, locatie, serviciu, localDate, perioada, time, antrenor);
                        contract.setCost(cost);
                        contracte.add(contract);
                    }
                } catch (NumberFormatException | ParseException e) {
                    System.err.println("Eroare linie: " + line + " - " + e.getMessage());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    
    private void incarcaAccesorii(String numeFisier) {
        try (BufferedReader reader = new BufferedReader(new FileReader(numeFisier))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] part = line.split(",");
                String nume = part[0];
                double pret = Double.parseDouble(part[1]);
                if(pret < 0)
                    throw new Exception("Pretul este invalid pentur linia: " + line);
                int cantitate = Integer.parseInt(part[2]);
                if(cantitate < 0)
                    throw new Exception("Cantitatea este invalid pentur linia: " + line);
                String locatilate = part[3];

                // Caută locația corespunzătoare în lista de locații
                for (Locatie locatie : locatii) {
                    if (locatie.getNume().equalsIgnoreCase(locatilate)) {
                        Accesoriu accesoriu = new Accesoriu(nume, cantitate, pret, locatilate);
                        locatie.addAccesoriu(accesoriu);
                        break; // Ieși din buclă după ce ai găsit locația potrivită
                    }
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch(Exception n) {
            System.out.println(n);
        }
    }

    
    
    
    
    @Override
    public String toString() {
        return "StudiouYoga{" +
                "locatii=" + locatii +
                ", clienti=" + clienti +
                ", servicii=" + servicii +
                ", contracte=" + contracte;
    }
}

class InvalidContractException extends Exception {
    public InvalidContractException(String message) {
        super(message);
    }
}

