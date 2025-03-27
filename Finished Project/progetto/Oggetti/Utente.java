package progetto.Oggetti;

import java.util.ArrayList;

public abstract class Utente {

    protected String[] ruoli = {"PE0", "PE1", "PE2", "PCN" , "ANL"};
    protected String[] posizioni = {"zone.xlsx", "utenti.xlsx", "datiProvince.xlsx", "datiComuni.xlsx"};
    private String nome;
    private String cognome;
    protected final String id;
    private String ruolo; // che tipo di utente è : personale ente, a contratto , ecc. {PE0, PE1, PE2, PCN, ANL}
    private String password;
    protected ArrayList<String> listaComuni = new ArrayList<String>();

    public Utente(String nome, String cognome, String id, String ruolo, String password){
        this.nome = nome;
        this.cognome = cognome;
        this.id = id;
        this.ruolo = ruolo;
        this.password = password;
    };

    public String getNome(){
        return this.nome;
    };
    public String getCognome(){
        return this.cognome;
    };
    public String getId(){
        return this.id;
    };
    public String getRuolo(){
        return this.ruolo;
    };
    public ArrayList<String> getLista(){return listaComuni; };


    //definisce l'hashcode per l'utente
    public int hashCode(){
        return (this.id.hashCode() + password.hashCode());
    }

    //if obj are equals return true else false
    public boolean equals(Object obj){
        //se obj è un utente confronta
        if(obj instanceof Utente) {
            if(this.compareTo(((Utente)obj)) == 0)
                return true;
            else
                return false;
        }
        //Se obj non è un utente ritorna comunque false
        else{
            System.out.println("ATTENZIONE: Obj non è un Utente!!");
            return false;
        }
    }

    //Compara gli oggetti: 1 viene prima, -1 viene dopo, 0 è uguale
    public int compareTo(Utente obj){
        if(this.hashCode() > obj.hashCode())
            return 1;
        else if(this.hashCode() == obj.hashCode())
            return 0;
        else
            return -1;
    }

};