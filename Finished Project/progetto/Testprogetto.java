package progetto;

import org.junit.Test;

import java.io.FileNotFoundException;

public class Testprogetto {

    @Test
    public void Testing() throws FileNotFoundException {

        Modello modello = Modello.getInstance();
       /* Modello.Regione regione = modello.new Regione("Nicho" , "Simo", "Dani");
        assertNotNull(regione);
        Modello modello1 = new Modello();
        Modello.provincia provincia = modello1.new Provincia(/*INSERIRE PARAMETRI PROVINCIA*///);

        //assertNotNull(provincia);


        System.out.println(modello.searchComune("15086").toString());

    }


}
