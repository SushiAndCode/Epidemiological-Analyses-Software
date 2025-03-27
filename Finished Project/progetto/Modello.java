package progetto;

import javafx.beans.property.SimpleIntegerProperty;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import progetto.Oggetti.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class Modello {

    private static Modello instance = null;

    public static Modello getInstance(){
        if(instance == null){
          instance = new Modello();
          return instance;
        }
        return instance;
    }

    //public final static String database = "C:\\Users\\Daniele\\IdeaProjects\\progetto\\src\\progetto/database";   //un path di file excel
    private ArrayList<Utente> utenti = new ArrayList<Utente>();    //Lista utenti
    private ArrayList<Regione> regioni = new ArrayList<Regione>();  //Lista delle regioni
    private ArrayList<Comune> comuni = new ArrayList<Comune>();
    private ArrayList<Provincia> province = new ArrayList<Provincia>();

    private Utente currentUser;
    protected UFactory utentiFactory = new UFactory();

    //Crea gli oggetti Regione,Provincia,Comune e li collega fra loro,inserendo le regioni nel campo di modello. Crea gli utenti e li inserisce nel campo apposito
    public Modello(){
        //Codice per inizializzare i dati da database
        try {
            //Apertura database per le regioni e get delle regioni
            FileInputStream dbRegioni = new FileInputStream(DatabaseVar.database + "/zone.xlsx"); //apro il file database che contiene le regioni
            OPCPackage tmp = OPCPackage.open(dbRegioni);
            XSSFWorkbook workbook =  new XSSFWorkbook(tmp);

                //Crea gli oggetti di tipo regione leggendo dal database e li inserisce nella lista Regioni
                XSSFSheet sheet = workbook.getSheetAt(0);
                int TotalNumOfRows = sheet.getLastRowNum()+1;
                int blankRows = 0;

            //Conta le righe vuote del file excel
            for(int i = 1; i < TotalNumOfRows; i++){
                if(sheet.getRow(i) == null ){
                    blankRows++;
                }
                else if(sheet.getRow(i).getCell(1) == null ){
                    blankRows++;
                }
                else if (sheet.getRow(i).getCell(1).getCellType().equals(CellType.BLANK)) {
                    blankRows++;
                }
                else{}
            }

                TotalNumOfRows = TotalNumOfRows - blankRows;

                for(int j = 1; j < TotalNumOfRows ; j++){
                    XSSFCell c = sheet.getRow(j).getCell(2);
                        Integer sup = (int) sheet.getRow(j).getCell(3).getNumericCellValue();
                        Regione obj = new Regione(sheet.getRow(j).getCell(1).getStringCellValue(), sheet.getRow(j).getCell(2).getStringCellValue(), sup.toString(), workbook);
                        this.regioni.add(obj);
                        comuni.addAll(obj.getComuni());
                        province.addAll(obj.getProvince());
                }


            //Apertura database per utenti e get degli utenti //
            FileInputStream dbUtenti = new FileInputStream(DatabaseVar.database + "/utenti.xlsx");
            workbook = new XSSFWorkbook(dbUtenti);
            sheet = workbook.getSheetAt(0);
            TotalNumOfRows = sheet.getLastRowNum()+1;
            blankRows = 0;

            //Conta le righe vuote del file excel
            for(int i = 1; i < TotalNumOfRows; i++){
                if(sheet.getRow(i) == null ){
                    blankRows++;
                }
                else if(sheet.getRow(i).getCell(1) == null ){
                    blankRows++;
                }
                else if (sheet.getRow(i).getCell(1).getCellType().equals(CellType.BLANK)) {
                    blankRows++;
                }
                else{}
            }

            TotalNumOfRows = TotalNumOfRows - blankRows;

            //Crea ogni oggetto utente prendendo i dati dal database e usando i metodi di factoryUtenti
            for(int j = 1; j < TotalNumOfRows; j++){
                if(!sheet.getRow(j).getCell(0).getCellType().equals(CellType.BLANK)) {
                    if (sheet.getRow(j).getCell(3).getStringCellValue().equals("PCN")) {
                        Integer sup =(int) sheet.getRow(j).getCell(0).getNumericCellValue();
                        Utente obj = utentiFactory.createPC(sheet.getRow(j).getCell(2).getStringCellValue(), sheet.getRow(j).getCell(1).getStringCellValue(),sup.toString(), sheet.getRow(j).getCell(3).getStringCellValue(), sheet.getRow(j).getCell(4).getStringCellValue(), this.regioni);
                        utenti.add(obj);
                    } else if (sheet.getRow(j).getCell(3).getStringCellValue().equals("ANL")) {
                        Integer sup =(int) sheet.getRow(j).getCell(0).getNumericCellValue();
                        Utente obj = utentiFactory.createAN(sheet.getRow(j).getCell(2).getStringCellValue(), sheet.getRow(j).getCell(1).getStringCellValue(),sup.toString(), sheet.getRow(j).getCell(3).getStringCellValue(), sheet.getRow(j).getCell(4).getStringCellValue());
                        utenti.add(obj);
                    } else if (sheet.getRow(j).getCell(3).getStringCellValue().equals("PE0")) {
                        Integer sup =(int) sheet.getRow(j).getCell(0).getNumericCellValue();
                        Utente obj = utentiFactory.createE0(sheet.getRow(j).getCell(2).getStringCellValue(), sheet.getRow(j).getCell(1).getStringCellValue(),sup.toString(), sheet.getRow(j).getCell(3).getStringCellValue(), sheet.getRow(j).getCell(4).getStringCellValue());
                        utenti.add(obj);
                    } else if (sheet.getRow(j).getCell(3).getStringCellValue().equals("PE1")) {
                        Integer sup =(int) sheet.getRow(j).getCell(0).getNumericCellValue();
                        Utente obj = utentiFactory.createE1(sheet.getRow(j).getCell(2).getStringCellValue(), sheet.getRow(j).getCell(1).getStringCellValue(),sup.toString(), sheet.getRow(j).getCell(3).getStringCellValue(), sheet.getRow(j).getCell(4).getStringCellValue());
                        utenti.add(obj);
                    } else if (sheet.getRow(j).getCell(3).getStringCellValue().equals("PE2")) {
                        Integer sup =(int) sheet.getRow(j).getCell(0).getNumericCellValue();
                        Utente obj = utentiFactory.createE2(sheet.getRow(j).getCell(2).getStringCellValue(), sheet.getRow(j).getCell(1).getStringCellValue(),sup.toString(), sheet.getRow(j).getCell(3).getStringCellValue(), sheet.getRow(j).getCell(4).getStringCellValue());
                        utenti.add(obj);
                    }
                }
            }
            workbook.close();
            dbUtenti.close();
            dbRegioni.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        }
    }

    //Effettua il login prendendo i dati dall'utente via controller
    public String login(String id, String password){
        Iterator<Utente> iter = utenti.iterator();
        Utente item;

        while(iter.hasNext()){
            if(id.hashCode() + password.hashCode() == (item = iter.next()).hashCode()) {
                this.currentUser = item;
                return item.getRuolo();
            }
        }

        return "ERROR";

    } //TODO da implementare in base a cosa si usa nel controller

    public Utente getUtente(){
        return currentUser;
    }

    public ArrayList<Utente> getUtenti() {
        return utenti;
    }

    public String toString(){
        return "MODELLO: \n" + this.regioni.toString() + "\n" + utenti.toString();
    }

    //Cerca il comune identificato da id nel database del modello
    public Comune searchComune(String id ){

        for(Regione tmp : regioni){
            Comune comune = tmp.findComune(id);
            if(comune != null)
                return comune;
        }
        return null;

    }


    public ArrayList<Regione> getRegioni(){
        return this.regioni;
    }

    public ArrayList<Comune> getComuni() {
        return comuni;
    }

    public ArrayList<Provincia> getProvince() {
        return province;
    }

    public boolean upDate(){
        boolean goOn = true;
            for(Regione tmp : this.regioni){
                if(tmp.Mflag == true){
                    goOn = tmp.upDate();
            }
        }
        if(goOn == true) {
            for (Provincia tmp : this.province) {
                if (tmp.Mflag == true) {
                    goOn = tmp.upDate();
                }
            }
        }
        if(goOn == true) {
            for (Comune tmp : this.comuni) {
                if (tmp.Mflag == true) {
                    goOn = tmp.upDate();
                }
            }
        }
        if(goOn == true) {
            for (Utente tmp : this.utenti) {
               if(tmp instanceof PersonaleContratto){
                    if(((PersonaleContratto)tmp).Mflag == true){
                        goOn = ((PersonaleContratto)tmp).upDate();
                    }
               }
            }
        }

        if(goOn == true)
            return true;
        else
            return false;
    }

    //Aggiunge il record annuale all'utente
    public void aggiornaAnnuario(String prov_id, AnnualeDecessi list){
       Provincia prov = null;
        for(Provincia tmp : province){
            if(tmp.getId_prov().equals(prov_id)) {
               prov = tmp;
            }
        }
        province.remove(prov);
        prov.updateDecessi(list.annoProperty(), list.incidentiStradaliProperty(), list.tumoriProperty(), list.cardiovascolareProperty(), list.contagioseProperty());
        prov.Mflag = true;
        province.add(prov);
    }

    public void deleteAnnuale(String prov_id, int numAnno){
        Provincia prov_temp = null;
       for(Provincia prov : province){
           if(prov.getId_prov().equals(prov_id))
               prov_temp = prov;
       }
       province.remove(prov_temp);
       prov_temp.deleteDecessi(numAnno);
       prov_temp.Mflag = true;
       province.add(prov_temp);
    }

    public void inserisciAnnuario(String prov_id, AnnualeDecessi list){
        Provincia prov = null;
        for(Provincia tmp : province){
            if(tmp.getId_prov().equals(prov_id)) {
                prov = tmp;
            }
        }
        province.remove(prov);
        prov.getDecessi().add(list);
        prov.Mflag = true;
        province.add(prov);
    }

    public void inserisciSettimanale(String com_id, SettimanaleEpidemia list){
        Utente obj = null;
        for(Utente person : utenti){
            if(person.equals(currentUser))
                obj = person;
        }
        utenti.remove(obj);
        ((PersonaleContratto)obj).updateDecessi(com_id, list);
        ((PersonaleContratto) obj).Mflag = true;
        utenti.add(obj);
    }

    public void deleteSettimanale(String com_id, int numerosettimana){
        Utente obj = null;
        for(Utente person : utenti){
            if(person.equals(currentUser))
                obj = person;
        }
        utenti.remove(obj);
        ((PersonaleContratto)obj).deleteDecessi(com_id , numerosettimana);
        ((PersonaleContratto) obj).Mflag = true;
        utenti.add(obj);
    }

    //Aggiunge o toglie il comune id_comune all'utente
    public void aggiornaUtente(String id_utente , String id_comune){
        Utente utente = null;
        for(Utente tmp : utenti){
            if(tmp.getId().equals(id_utente))
                utente = tmp;
        }


        for(Comune tmp : comuni){
            if(tmp.getIstatId().equals(id_comune))
                ((PersonaleContratto)utente).updateComuni(tmp);
        }
        ((PersonaleContratto)utente).Mflag = true;

    }
}
