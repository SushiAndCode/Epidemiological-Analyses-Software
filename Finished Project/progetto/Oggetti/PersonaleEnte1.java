package progetto.Oggetti;

import java.io.IOException;

public class PersonaleEnte1 extends Personale {
    public PersonaleEnte1(String nome, String cognome, String id, String ruolo, String password) throws IOException {
        super(nome, cognome, id, ruolo, password);
    }
    public String toString(){
        return "\n**************\n"+ "NOME: " + this.getNome() + "\nCOGNOME: " + this.getCognome() + "\nID: " + this.getId() + "\nRUOLO: " + this.getRuolo() + "\nCOMUNIR: " + "\n**************\n";
    }
}
