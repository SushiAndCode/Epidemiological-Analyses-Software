package progetto.Oggetti;

import java.io.IOException;

public class Personale extends Utente {
    public Personale(String nome, String cognome, String id, String ruolo, String password) throws IOException {
        super(nome, cognome, id, ruolo, password);

    }
}