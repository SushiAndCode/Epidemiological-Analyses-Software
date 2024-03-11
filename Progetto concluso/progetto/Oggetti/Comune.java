package progetto.Oggetti;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import progetto.Exception.InvalidIdException;
import progetto.Exception.ComplicanzeException;

import java.io.*;
import java.util.ArrayList;

public class Comune{
    //protected final static String database = "C:\\Users\\Daniele\\IdeaProjects\\progetto\\src\\progetto/database";     //un path di file jSOn o excel
    private final String nome;
    private Provincia provincia;
    private String superficie;
    private final String istatId;
    private String dataIstituzione;
    private String territorio;
    private String mare;
    public boolean Mflag = false;
    private ArrayList<SettimanaleEpidemia> recordEpidemia = new ArrayList<SettimanaleEpidemia>(); // Contiene i dati epidemiologici settimanali relativi al comune
    private XSSFSheet recordsheet;

    public Comune(XSSFSheet foglio, Provincia provincia, int index) throws IOException, ComplicanzeException, InvalidIdException {
        this.provincia = provincia;
        this.nome = foglio.getRow(index).getCell(1).getStringCellValue();
        Integer sup = (int) foglio.getRow(index).getCell(3).getNumericCellValue();
        this.superficie = sup.toString();
        sup = (int) foglio.getRow(index).getCell(0).getNumericCellValue();
        this.istatId = sup.toString();
        this.dataIstituzione = foglio.getRow(index).getCell(2).getStringCellValue();
        sup = (int) foglio.getRow(index).getCell(4).getNumericCellValue();
        this.territorio = sup.toString();
        this.mare = foglio.getRow(index).getCell(5).getStringCellValue();


        //Apre il foglio del record dell'epidemia
        FileInputStream dbRecord = new FileInputStream(DatabaseVar.database + "/datiComuni.xlsx"); //apro il file database che contiene i dati sui record dei comuni
        XSSFWorkbook workbook = new XSSFWorkbook(dbRecord);
        //Prende il foglio relativo al comune di riferimento
        XSSFSheet foglioRec = workbook.getSheet(this.istatId);
        if(foglioRec == null)
            throw new InvalidIdException();

        //Parte per leggere i record dell'epidemia
        //Scorro la parte del record del comune,
        int TotalNumOfRows = foglioRec.getLastRowNum()+1;
        int blankRows = 0;

        System.out.println(TotalNumOfRows);
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

        for(int i = 1; i < TotalNumOfRows ;  i++) {
            SettimanaleEpidemia sample = new SettimanaleEpidemia((int) foglioRec.getRow(i).getCell(0).getNumericCellValue(), (int) foglioRec.getRow(i).getCell(1).getNumericCellValue(),
                    (int) foglioRec.getRow(i).getCell(2).getNumericCellValue(), (int) foglioRec.getRow(i).getCell(3).getNumericCellValue(),
                    (int) foglioRec.getRow(i).getCell(4).getNumericCellValue(), (int) foglioRec.getRow(i).getCell(5).getNumericCellValue(),
                    (int) foglioRec.getRow(i).getCell(6).getNumericCellValue(), (int) foglioRec.getRow(i).getCell(7).getNumericCellValue(),
                    (int) foglioRec.getRow(i).getCell(8).getNumericCellValue(), (int) foglioRec.getRow(i).getCell(9).getNumericCellValue(),
                    (int) foglioRec.getRow(i).getCell(10).getNumericCellValue());
                    this.recordEpidemia.add(sample);
        }

        this.recordsheet = foglioRec;
        workbook.close();
    }

    public String getNome(){
        return nome;
    };

    public String getIstatId(){
        return istatId;
    };

    public String getSuperficie(){
        return superficie;
    };

    public Provincia getProvincia(){
        return provincia;
    }

    public String getDataIstituzione() {
        return dataIstituzione;
    }

    public String getTerritorio() {
        return territorio;
    }

    public String getMare() {
        return mare;
    }

    public void setSuperficie(String superficie) {
        this.superficie = superficie;
    }

    public void setDataIstituzione(String dataIstituzione) {
        this.dataIstituzione = dataIstituzione;
    }

    public void setTerritorio(String territorio) {
        this.territorio = territorio;
    }

    public void setMare(String mare) {
        this.mare = mare;
    }


    //Aggiorna il recordEpidemia dell'epidemia
    public void addEpidemia(SettimanaleEpidemia sample){  //Aggiunge l'Epidemia sample al recordEpidemia del comune
        this.recordEpidemia.add(sample);
    }

    public void deleteEpidemia(int numerosettimana){
        SettimanaleEpidemia obj = null;
        for(SettimanaleEpidemia tmp : recordEpidemia)
            if(tmp.getNumSettimana() == numerosettimana)
                obj = tmp;

        recordEpidemia.remove(obj);
    }

    //restituisce il recordEpidemia dell'epidemia
    public ArrayList<SettimanaleEpidemia> getEpidemia(){
        return this.recordEpidemia;
    }

    /*
    public String toString(){
        return "\n********************************\n" + "COMUNE: " + this.nome + "\n" + "PROVINCIA: " + this.provincia.getNome() + "\n" + "SUPERFICE: " + this. superficie + "\n" + "ISTAT ID: " + this. istatId + "\nDATA ISTITUZIONE: " + this. dataIstituzione +  "\n" + "TERRITORIO: " + this. territorio +"\n"+  "MARE? : " + this.mare + "\n********************************\n";
    }
    */

    public String toString(){
        return this.nome;
    }

    public boolean upDate(){
        try{

            File file = new File(DatabaseVar.database + "/zone.xlsx");
            FileInputStream dbComuni = new FileInputStream(file);
            XSSFWorkbook workbook = new XSSFWorkbook(dbComuni);
            XSSFSheet sheet = workbook.getSheetAt(2);
            int TotalNumOfRows = sheet.getLastRowNum() + 1;
            int blankRows = 0;

            //Controllo righe vuote
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

            //Aggiorna i dati del comune
            for(int i = 1; i < TotalNumOfRows; i++){
                Integer sup = (int)sheet.getRow(i).getCell(0).getNumericCellValue();
                if(sup.toString().equals(this.istatId)){
                    sheet.getRow(i).getCell(2).setCellValue(this.dataIstituzione);
                    sheet.getRow(i).getCell(3).setCellValue(Integer.valueOf(this.superficie));
                    sheet.getRow(i).getCell(4).setCellValue(Integer.valueOf(this.territorio));
                    sheet.getRow(i).getCell(5).setCellValue(this.mare);
                }
            }
            dbComuni.close();
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
            File file = new File(DatabaseVar.database + "/datiComuni.xlsx");
            FileInputStream dbComuni = new FileInputStream(file);
            XSSFWorkbook workbook = new XSSFWorkbook(dbComuni);
            XSSFSheet sheet = workbook.getSheet(this.istatId);
            //Resets all values
            for(int i = 1; i < recordEpidemia.size() + 1 ; i++) {
                XSSFRow riga = sheet.createRow(i);
                riga.createCell(0).setCellType(CellType.BLANK);
                riga.createCell(1).setCellType(CellType.BLANK);
                riga.createCell(2).setCellType(CellType.BLANK);
                riga.createCell(3).setCellType(CellType.BLANK);
                riga.createCell(4).setCellType(CellType.BLANK);
                riga.createCell(5).setCellType(CellType.BLANK);
                riga.createCell(6).setCellType(CellType.BLANK);
                riga.createCell(7).setCellType(CellType.BLANK);
                riga.createCell(8).setCellType(CellType.BLANK);
                riga.createCell(9).setCellType(CellType.BLANK);
                riga.createCell(10).setCellType(CellType.BLANK);
            }

            //Aggiorna i record settimanali del comune
            for(SettimanaleEpidemia tmp : this.recordEpidemia){
                sheet.getRow(tmp.getNumSettimana()).getCell(0).setCellValue(tmp.getNumSettimana());
                sheet.getRow(tmp.getNumSettimana()).getCell(1).setCellValue(tmp.terapieIntensiveProperty().getValue());
                sheet.getRow(tmp.getNumSettimana()).getCell(2).setCellValue(tmp.curaMedicoBaseProperty().getValue());
                sheet.getRow(tmp.getNumSettimana()).getCell(3).setCellValue(tmp.numInfluenzaliProperty().getValue());
                sheet.getRow(tmp.getNumSettimana()).getCell(4).setCellValue(tmp.numComplicanzeProperty().getValue());
                sheet.getRow(tmp.getNumSettimana()).getCell(5).setCellValue(tmp.numPolmonitiProperty().getValue());
                sheet.getRow(tmp.getNumSettimana()).getCell(6).setCellValue(tmp.numMeningitiProperty().getValue());
                sheet.getRow(tmp.getNumSettimana()).getCell(7).setCellValue(tmp.numEpatitiProperty().getValue());
                sheet.getRow(tmp.getNumSettimana()).getCell(8).setCellValue(tmp.numMorbilliProperty().getValue());
                sheet.getRow(tmp.getNumSettimana()).getCell(9).setCellValue(tmp.numTubercolosiProperty().getValue());
                sheet.getRow(tmp.getNumSettimana()).getCell(10).setCellValue(tmp.numGastroenteritiProperty().getValue());
            }

            dbComuni.close();
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