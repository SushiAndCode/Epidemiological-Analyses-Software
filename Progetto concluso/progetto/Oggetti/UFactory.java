package progetto.Oggetti;

import java.io.IOException;
import java.util.ArrayList;

public class UFactory{

    public Utente createPC(String stringCellValue, String stringCellValue1, String stringCellValue4, String stringCellValue2, String stringCellValue3, ArrayList<Regione> lista) throws IOException {
        return new PersonaleContratto(stringCellValue,stringCellValue1,stringCellValue4,stringCellValue2,stringCellValue3 ,lista);
    }

    public Utente createAN(String stringCellValue, String stringCellValue1, String stringCellValue4, String stringCellValue2, String stringCellValue3){
        return new Analista(stringCellValue,stringCellValue1,stringCellValue4,stringCellValue2,stringCellValue3);
    }

    public Utente createE0(String stringCellValue, String stringCellValue1,String stringCellValue4, String stringCellValue2, String stringCellValue3) throws IOException {
        return new PersonaleEnte0(stringCellValue,stringCellValue1,stringCellValue4,stringCellValue2,stringCellValue3);
    }

    public Utente createE1(String stringCellValue, String stringCellValue1,String stringCellValue4, String stringCellValue2, String stringCellValue3) throws IOException {
        return new PersonaleEnte1(stringCellValue,stringCellValue1,stringCellValue4,stringCellValue2,stringCellValue3);
    }

    public Utente createE2(String stringCellValue, String stringCellValue1,String stringCellValue4, String stringCellValue2, String stringCellValue3) throws IOException {
        return new PersonaleEnte2(stringCellValue,stringCellValue1,stringCellValue4,stringCellValue2,stringCellValue3);
    }

}