package progetto.Oggetti;

import javafx.beans.property.SimpleIntegerProperty;
import progetto.Exception.ComplicanzeException;

public class SettimanaleEpidemia{
    private int numSettimana; //indica il numero della settimana da considerare
    private SimpleIntegerProperty terapieIntensive; //indica il numero di pazienti in terapia intensiva
    private SimpleIntegerProperty curaMedicoBase; //indica il numero di pazienti in cura dal medico di base
    private SimpleIntegerProperty numInfluenzali; //indica il numero di pazienti soggetti a episodi influenzali
    private SimpleIntegerProperty numComplicanze; //indica il numero di pazienti influenzati soggetti a complicanze
    private SimpleIntegerProperty numPolmoniti;   //indica il numero di pazienti affetti da Polmonite
    private SimpleIntegerProperty numMeningiti;   //indica il numero di pazienti affetti da Meningite
    private SimpleIntegerProperty numEpatiti;     //indica il numero di pazienti affetti da Epatite
    private SimpleIntegerProperty numMorbilli;    //indica il numero di pazienti affetti da Morbillo
    private SimpleIntegerProperty numTubercolosi; //indica il numero di pazienti affetti da Tubercolosi
    private SimpleIntegerProperty numGastroenteriti;//indica il numero di pazienti affetti da Gastroenterite

    public SettimanaleEpidemia(int numSettimana, int terapieIntensive, int curaMedicoBase, int numInfluenzali,
                               int numComplicanze, int numPolmoniti , int numMeningiti, int numEpatiti, int numMorbilli,
                               int numTubercolosi, int numGastroenteriti) throws ComplicanzeException {
        this.numSettimana = numSettimana;
        this.terapieIntensive = new SimpleIntegerProperty( terapieIntensive);
        this.curaMedicoBase =new SimpleIntegerProperty( curaMedicoBase);
        this.numInfluenzali =new SimpleIntegerProperty( numInfluenzali);
        if(numComplicanze > numInfluenzali)
            throw new ComplicanzeException(); //
        this.numComplicanze =new SimpleIntegerProperty( numComplicanze);
        this.numPolmoniti =new SimpleIntegerProperty( numPolmoniti);
        this.numMeningiti =new SimpleIntegerProperty( numMeningiti);
        this.numEpatiti =new SimpleIntegerProperty( numEpatiti);
        this.numMorbilli =new SimpleIntegerProperty( numMorbilli);
        this.numTubercolosi =new SimpleIntegerProperty( numTubercolosi);
        this.numGastroenteriti =new SimpleIntegerProperty( numGastroenteriti);
    }

    public int getNumSettimana() {
        return numSettimana;
    }

    public SimpleIntegerProperty terapieIntensiveProperty() {
        return terapieIntensive;
    }

    public SimpleIntegerProperty curaMedicoBaseProperty() {
        return curaMedicoBase;
    }

    public SimpleIntegerProperty numInfluenzaliProperty() {
        return numInfluenzali;
    }

    public SimpleIntegerProperty numComplicanzeProperty() {
        return numComplicanze;
    }

    public SimpleIntegerProperty numPolmonitiProperty() {
        return numPolmoniti;
    }

    public SimpleIntegerProperty numMeningitiProperty() {
        return numMeningiti;
    }

    public SimpleIntegerProperty numEpatitiProperty() {
        return numEpatiti;
    }

    public SimpleIntegerProperty numMorbilliProperty() {
        return numMorbilli;
    }

    public SimpleIntegerProperty numTubercolosiProperty() {
        return numTubercolosi;
    }

    public SimpleIntegerProperty numGastroenteritiProperty() {
        return numGastroenteriti;
    }

    public String toString(){
        return "*********************"+ this.curaMedicoBase+ "\n" + this.numComplicanze + "\n" + "*********************";
    }

}