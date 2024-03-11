package progetto.Oggetti;

import javafx.beans.property.SimpleIntegerProperty;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import progetto.Exception.InvalidIdException;
import progetto.Exception.ComplicanzeException;

import java.io.*;
import java.util.ArrayList;

public class Provincia{
    //protected final static String database = "C:\\Users\\Daniele\\IdeaProjects\\progetto\\src\\progetto/database";     //un path di file jSOn o excel
    private final String nome;
    private String capoluogo;
    private String superficie;
    private Regione regione;
    private final String id_prov;
    private ArrayList<Comune> comuni = new ArrayList<Comune>();
    private ArrayList<AnnualeDecessi> registroDecessi = new ArrayList<AnnualeDecessi>();
    private XSSFSheet recordSheet;
    public boolean Mflag = false;

    public Provincia(String id_prov ,String nome, String capoluogo, String superficie, Regione regione, XSSFWorkbook book) throws IOException {
        this.nome = nome;
        this.capoluogo = capoluogo;
        this.superficie = superficie;
        this.regione = regione;
        this.id_prov = id_prov;
        this.creaComuni(book);

        //Apre il foglio del record dei decessi
        FileInputStream dbRecord = new FileInputStream(DatabaseVar.database + "/datiProvince.xlsx"); //apro il file database che contiene i dati dei record delle province
        XSSFWorkbook workbook = new XSSFWorkbook(dbRecord);
        //Prende il foglio relativo alla Regione di riferimento
        XSSFSheet foglioRec = workbook.getSheet(this.id_prov);


        //Parte per leggere i record dell'epidemia
        int TotalNumOfRows = foglioRec.getLastRowNum()+1;
        int blankRows = 0;

        //Controllare le righe vuote
        for(int i = 1; i < TotalNumOfRows; i++){
            if(foglioRec.getRow(i) == null ){
                blankRows++;
            }
            else if(foglioRec.getRow(i).getCell(1) == null ){
                blankRows++;
            }
            else if (foglioRec.getRow(i).getCell(1).getCellType().equals(CellType.BLANK)) {
                blankRows++;
            }
            else{}
        }

        TotalNumOfRows = TotalNumOfRows - blankRows;


        for(int i = 1; i < TotalNumOfRows; i++){ //Scorro la parte del record delle province e creo i corrispettivi oggetti
            AnnualeDecessi sample = new AnnualeDecessi("Italia", this.regione.getNome() , this.id_prov ,(int) foglioRec.getRow(i).getCell(0).getNumericCellValue(), (int) foglioRec.getRow(i).getCell(1).getNumericCellValue(),
                    (int) foglioRec.getRow(i).getCell(2).getNumericCellValue(), (int) foglioRec.getRow(i).getCell(3).getNumericCellValue(),
                    (int) foglioRec.getRow(i).getCell(4).getNumericCellValue());
            this.registroDecessi.add(sample);
        }
        this.recordSheet = foglioRec;
        workbook.close();

    }

    public String getNome(){
        return nome;
    };

    public String getCapoluogo(){
        return capoluogo;
    };

    public String getSuperficie(){
        return superficie;
    };

    public Regione getRegione(){
        return regione;
    }

    public String getId_prov() {
        return id_prov;
    }

    private void creaComuni(XSSFWorkbook workbook) throws FileNotFoundException {
        //Apre il db, scorre i comuni presenti nella provincia e per ogni comune che trova nel database, crea il corrispettivo oggetto comune e lo aggiunge al campo comuni
       //apro il file database che contiene le regioni
        try {
            //XSSFWorkbook workbook = new XSSFWorkbook(dbProvince);
            XSSFSheet sheet = workbook.getSheetAt(2);
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

            for(int i = 1 ; i < TotalNumOfRows; i++){
                if(this.nome.equals(sheet.getRow(i).getCell(6).getStringCellValue())){
                    Comune oggetto = new Comune(sheet, this, i);
                    comuni.add(oggetto);
                    System.out.println(oggetto.toString());
                }

            }

            this.recordSheet = sheet;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ComplicanzeException e) {
            e.printStackTrace();
        } catch (InvalidIdException e) {
            e.printStackTrace();
        }

    }

    //aggiorna il registro decessi
    public void updateDecessi(SimpleIntegerProperty anno, SimpleIntegerProperty incidentiStradali, SimpleIntegerProperty tumori, SimpleIntegerProperty cardiovascolare, SimpleIntegerProperty contagiose){
        AnnualeDecessi obj = null;
        for(AnnualeDecessi tmp : registroDecessi){
            if(anno.getValue() == tmp.annoProperty().getValue()) {
                obj = tmp;
            }
        }
        registroDecessi.remove(obj);

        obj.setAnno(anno);
        obj.setIncidentiStradali(incidentiStradali);
        obj.setTumori(tumori);
        obj.setCardiovascolare(cardiovascolare);
        obj.setContagiose(contagiose);

        registroDecessi.add(obj);
    }

    public void deleteDecessi(int anno){
        AnnualeDecessi obj = null;
        for(AnnualeDecessi tmp : registroDecessi)
            if(tmp.getAnnoNonProperty() == anno)
                obj = tmp;

        registroDecessi.remove(obj);
    }

    //restituisce il registro dei decessi
    public ArrayList<AnnualeDecessi> getDecessi(){
        return registroDecessi;
    }

    public ArrayList<Comune> getComuni() {
        return comuni;
    }

    public Comune findComune(String id){

        for(Comune tmp : this.comuni){
            if(tmp.getIstatId().equals(id))
                return tmp;
        }
        return null;
    }

/*
    public String toString(){
        return "\n*******\n" + "NOME: " + this.nome + "\n" + "CAPOLUOGO: " + this.capoluogo + "\n" + "SUPERFICE: " + this. superficie + "\n" + "REGIONE: " + this.regione.getNome() +  "\n" + "COMUNI: \n\n" + comuni.toString() + "\n*******\n";
    }
*/

    public String toString(){return this.nome;}

    public boolean upDate(){
        try {
            //Controllo di quali comuni è responsabile l'utente in questione
            File file = new File(DatabaseVar.database + "/zone.xlsx");
            FileInputStream dbProvince = new FileInputStream(file);
            XSSFWorkbook workbook = new XSSFWorkbook(dbProvince);
            XSSFSheet sheet = workbook.getSheetAt(1);
            int TotalNumOfRows = sheet.getLastRowNum() + 1;
            int blankRows = 0;

            //Controllare le righe vuote
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

            for(int i = 1; i < TotalNumOfRows; i++){
                Integer sup = (int)sheet.getRow(i).getCell(0).getNumericCellValue();
                if(sup.toString().equals(this.id_prov)){
                        sheet.getRow(i).getCell(2).setCellValue(this.capoluogo);
                        sheet.getRow(i).getCell(4).setCellValue(Double.valueOf(this.superficie));
                }
            }

            dbProvince.close();

            FileOutputStream outputStream = new FileOutputStream(file);
            workbook.write(outputStream);
            workbook.close();
            outputStream.close();


        } catch (FileNotFoundException e) {
            return false;
        } catch (IOException e) {
            return false;
        }

        try {
            File file = new File(DatabaseVar.database + "/datiProvince.xlsx");
            FileInputStream dbProvince = new FileInputStream(file);
            XSSFWorkbook workbook = new XSSFWorkbook(dbProvince);
            XSSFSheet sheet = workbook.getSheet(this.id_prov);

            for(int i = 1; i < registroDecessi.size() + 1 ; i++) {
                XSSFRow riga = sheet.createRow(i);
                riga.createCell(0).setCellType(CellType.BLANK);
                riga.createCell(1).setCellType(CellType.BLANK);
                riga.createCell(2).setCellType(CellType.BLANK);
                riga.createCell(3).setCellType(CellType.BLANK);
                riga.createCell(4).setCellType(CellType.BLANK);
            }

            for(AnnualeDecessi tmp : registroDecessi){
                sheet.getRow(tmp.annoProperty().getValue()+1).getCell(0).setCellValue(tmp.annoProperty().getValue());
                sheet.getRow(tmp.annoProperty().getValue()+1).getCell(1).setCellValue(tmp.incidentiStradaliProperty().getValue());
                sheet.getRow(tmp.annoProperty().getValue()+1).getCell(2).setCellValue(tmp.tumoriProperty().getValue());
                sheet.getRow(tmp.annoProperty().getValue()+1).getCell(3).setCellValue(tmp.cardiovascolareProperty().getValue());
                sheet.getRow(tmp.annoProperty().getValue()+1).getCell(4).setCellValue(tmp.contagioseProperty().getValue());
            }

            dbProvince.close();
            FileOutputStream outputStream = new FileOutputStream(file);
            workbook.write(outputStream);
            workbook.close();
            outputStream.close();

            Mflag = false;
            return true;

        } catch (FileNotFoundException e) {
            return false;
        } catch (IOException e) {
            return false;
        }

    }

};