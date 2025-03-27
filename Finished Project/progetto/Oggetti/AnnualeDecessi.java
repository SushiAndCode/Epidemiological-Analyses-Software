package progetto.Oggetti;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class AnnualeDecessi{
    private SimpleStringProperty nazione;
    private String reg_nome;
    private String prov_id;
    private SimpleIntegerProperty anno;
    private SimpleIntegerProperty incidentiStradali; //indica il numero di morti per incidenti stradali
    private SimpleIntegerProperty tumori;            //indica il numero di morti per tumore
    private SimpleIntegerProperty cardiovascolare;   //indica il numero di morti per problemi cardiovascolari
    private SimpleIntegerProperty contagiose;        //indica il numero di morti per malattie contagiose

    public AnnualeDecessi(String nazione, String reg_nome,String prov_id, int anno, int incidentiStradali, int tumori, int cardiovascolare, int contagiose){
        this.nazione = new SimpleStringProperty(nazione);
        this.reg_nome = reg_nome;
        this.prov_id = prov_id;
        this.anno = new SimpleIntegerProperty(anno);
        this.cardiovascolare =  new SimpleIntegerProperty(cardiovascolare);
        this.incidentiStradali =  new SimpleIntegerProperty(incidentiStradali);
        this.tumori =  new SimpleIntegerProperty(tumori);
        this.contagiose =  new SimpleIntegerProperty(contagiose);
    }

    public String getNazione(){ return this.nazione.getValue();};

    public SimpleStringProperty nazioneProperty(){
        return this.nazione;
    }

    public String getProv_id() {
        return this.prov_id;
    }

    public SimpleStringProperty prov_idProperty(){return new SimpleStringProperty(this.prov_id);};

    public String getReg_nome(){ return this.reg_nome; };

    public SimpleStringProperty reg_nomeProperty(){return new SimpleStringProperty(reg_nome);}

    public int getAnnoNonProperty(){ return this.anno.getValue(); };

    public SimpleIntegerProperty annoProperty(){
        return this.anno;
    }

    public void setAnno(SimpleIntegerProperty anno){
        this.anno = anno;
    }

    public SimpleIntegerProperty incidentiStradaliProperty(){
        return this.incidentiStradali;
    }

    public void setIncidentiStradali(SimpleIntegerProperty incidentiStradali){
        this.incidentiStradali = incidentiStradali;
    }

    public SimpleIntegerProperty cardiovascolareProperty(){
        return this.cardiovascolare;
    }

    public void setCardiovascolare(SimpleIntegerProperty cardiovascolare){
        this.cardiovascolare = cardiovascolare;
    }

    public SimpleIntegerProperty tumoriProperty(){
        return this.tumori;
    }

    public void setTumori(SimpleIntegerProperty tumori){
        this.tumori = tumori;
    }

    public SimpleIntegerProperty contagioseProperty(){
        return this.contagiose;
    }

    public void setContagiose(SimpleIntegerProperty contagiose){
        this.contagiose = contagiose;
    }

}