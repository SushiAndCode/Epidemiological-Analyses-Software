package progetto.Oggetti;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;

public class Regione {
    //protected final static String database = "C:\\Users\\Daniele\\IdeaProjects\\progetto\\src\\progetto/database";     //un path di file jSOn o excel
    private final String nome;
    private String capoluogo;
    private String superficie;
    public boolean Mflag = false;
    private ArrayList<Provincia> province = new ArrayList<Provincia>();

    public Regione(String nome, String capoluogo, String superficie, XSSFWorkbook book) throws FileNotFoundException, InvalidFormatException {
        this.nome = nome;
        this.capoluogo = capoluogo;
        this.superficie = superficie;
        this.creaProvince(book);
    }

    public String getNome() {
        return nome;
    };

    public String getCapoluogo() {
        return capoluogo;
    };

    public String getSuperficie() {

        return superficie;
    };

    public ArrayList<Provincia> getProvince() {
        return province;
    }

    private void creaProvince(XSSFWorkbook book) throws FileNotFoundException, InvalidFormatException {
        try {

            XSSFSheet sheet = book.getSheetAt(1);
            int TotalNumOfRows = sheet.getLastRowNum();
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

            for (int i = 1; i < TotalNumOfRows; i++) {
                //Se nel foglio dei comuni trovo corrispondenza fra regione e provincia allora la creo
                if (this.nome.equals(sheet.getRow(i).getCell(3).getStringCellValue())) {
                    Double sup = (double) sheet.getRow(i).getCell(4).getNumericCellValue();
                    Integer support = (int)sheet.getRow(i).getCell(0).getNumericCellValue();
                    Provincia prov = new Provincia( support.toString() , sheet.getRow(i).getCell(1).getStringCellValue(), sheet.getRow(i).getCell(2).getStringCellValue(),
                                                    sup.toString(), this, book);
                    province.add(prov);
                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //Ritorna la provincia dato il nome, se non è presente ritorna null
    public Provincia findProv(String nome) {
        Iterator<Provincia> iter = this.province.iterator();

        while (iter.hasNext()) {
            Provincia next;
            if ((next = iter.next()).getNome().equals(nome)){
                return next;
            }
        }

        return null;
    }

    //Ritorna il comune dato l'id, se non è presente ritorna null
    public Comune findComune(String id) {
        for(Provincia tmp : province){
            if(tmp.findComune(id) != null)
                return tmp.findComune(id);
        }
        return null;
    }

    public ArrayList<Comune> getComuni(){
        ArrayList<Comune> lista = new ArrayList<Comune>();
        for(Provincia tmp : province){
            lista.addAll(tmp.getComuni());
        }
        return lista;
    }

    public String toString(){
        return "\n********************************\n" + "REGIONE: " + this.nome + "\n" + "CAPOLUOGO: " + this.capoluogo + "\n" + "SUPERFICE: " + this. superficie + "\n"+ "PROVINCE: \n\n" + province.toString() + "\n********************************\n";
    }

    public boolean upDate(){
        try{
            File file = new File(DatabaseVar.database + "/zone.xlsx");
            FileInputStream dbProvince = new FileInputStream(file);
            XSSFWorkbook workbook = new XSSFWorkbook(dbProvince);
            XSSFSheet sheet = workbook.getSheetAt(0);
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
                if(sup.toString().equals(this.nome)){
                    sheet.getRow(i).getCell(2).setCellValue(this.capoluogo);
                    sheet.getRow(i).getCell(3).setCellValue(this.superficie);
                }
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
}
