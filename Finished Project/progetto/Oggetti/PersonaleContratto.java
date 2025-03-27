package progetto.Oggetti;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;

public class PersonaleContratto extends Personale {
    protected ArrayList<Comune> comuni = new ArrayList<Comune>(); //Contiene la lista di comuni di cui è resposabile l'utente
    public boolean Mflag = false;

    public PersonaleContratto(String nome, String cognome, String id, String ruolo, String password, ArrayList<Regione> regioni) throws IOException {
        super(nome, cognome, id, ruolo, password);

        //Controllo di quali comuni è responsabile l'utente in questione
        FileInputStream dbUtenti = new FileInputStream(DatabaseVar.database + "/utenti.xlsx");
        XSSFWorkbook workbook = new XSSFWorkbook(dbUtenti);
        XSSFSheet sheet = workbook.getSheetAt(0);
        int TotalNumOfRows = sheet.getLastRowNum() + 1;
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

        //Controlla il db Utenti
        for(int i = 1; i < TotalNumOfRows; i++){
            //trova la riga corrispondente all'utente desiderato
            Integer sup = (int) sheet.getRow(i).getCell(0).getNumericCellValue();

            if(this.id.equals(sup.toString())) {
                int j = 5;
                while (!sheet.getRow(i).getCell(j).getCellType().equals(CellType.BLANK)){
                    //Salvo l'id in Stringa nell'array listaComuni
                    sup = (int) sheet.getRow(i).getCell(j).getNumericCellValue();
                    String id_comune = sup.toString();

                        this.listaComuni.add(id_comune);
                    //Salvo il comune in ogetto nell'array comuni
                    //Scorro le regioni e chiamo per ognuna la funzione findComune
                    Iterator<Regione> iter = regioni.iterator();
                    while(iter.hasNext()){
                        Comune next;
                        //Se la funzione non ritorna null, quindi il comune c'è, lo aggiungo alla lista
                        if( (next = iter.next().findComune(id_comune)) != null)
                            this.comuni.add(next);
                    }
                    j++;
                    if(sheet.getRow(i).getCell(j) == null){
                        break;
                    }
                }
                break;
            }
        }
        dbUtenti.close();
        workbook.close();
    }

    public ArrayList<String> getListaComuni(){
        return this.listaComuni;
    }

    public String toString(){
        return "\n**************\n"+ "NOME: " + this.getNome() + "\nCOGNOME: " + this.getCognome() + "\nID: " + this.getId() + "\nRUOLO: " + this.getRuolo() + "\nCOMUNIR: " + listaComuni.toString() + "\n**************\n";
    }

    public boolean upDate(){
        try{
            //Controllo di quali comuni è responsabile l'utente in questione
            File file = new File(DatabaseVar.database + "/utenti.xlsx");
            FileInputStream dbUtenti = new FileInputStream(file);
            XSSFWorkbook workbook = new XSSFWorkbook(dbUtenti);
            XSSFSheet sheet = workbook.getSheetAt(0);
            int TotalNumOfRows = sheet.getLastRowNum() + 1;
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

            //Scorro il file utenti fino a che trovo la riga corrispondente a questo utente e poi vi aggiorno i campi comuni da listacomuni
            for(int i = 1; i < TotalNumOfRows; i++){
                Integer sup = (int)sheet.getRow(i).getCell(0).getNumericCellValue();
                if(sup.toString().equals(this.id)){
                    //Azzero i 7 celle (Arbitrario) del file excel e successivamente vi riscriverò i valori
                    for(int j = 0; j < 7 ; j++ ){
                        if(sheet.getRow(i).getCell(5+j) == null){
                            sheet.getRow(i).createCell(5 + j);
                        }
                        sheet.getRow(i).getCell(5 + j).setBlank();
                    }
                }
            }

            //Scorro il file utenti fino a che trovo la riga corrispondente a questo utente e poi vi aggiorno i campi comuni da listacomuni
            for(int i = 1; i < TotalNumOfRows; i++){
                Integer sup = (int)sheet.getRow(i).getCell(0).getNumericCellValue();
                if(sup.toString().equals(this.id)){
                    for(String obj : listaComuni){

                        sheet.getRow(i).getCell(5 + listaComuni.indexOf(obj)).setCellValue(Integer.valueOf(obj));

                    }
                }
            }

            dbUtenti.close();
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

    //Se l'id non è presente lo aggiunge, se è presente lo rimuove
    public void updateComuni(Comune com){
        //Se è presente allora lo tolgo da entrambe le liste
        if(this.listaComuni.contains(com.getIstatId())) {
            //Rimuove il comune dalla lista degli oggetti
            for(Comune obj :comuni)
                if(obj.getIstatId().equals(com.getIstatId())) {
                    comuni.remove(obj);
                    break;
                }
            //Rimuove il comune dalla lista degli istat_id
            for(String obj : listaComuni)
                if(obj.equals(com.getIstatId())) {
                    listaComuni.remove(obj);
                    break;
                }
        }
        //Se non è presente lo aggiunge
        else {
            this.listaComuni.add(com.getIstatId());
            this.comuni.add(com);
        }
    }

    //Aggiunge il settimanale epidemia al comune corrispondente
    public void updateDecessi(String com_id, SettimanaleEpidemia tmp){
        for(Comune obj : comuni){
            if(obj.getIstatId().equals(com_id)) {
                obj.addEpidemia(tmp);
                obj.Mflag = true;
            }
        }
    }

    public void deleteDecessi(String com_id, int numerosettimana){

        for(Comune obj : comuni){
            if(obj.getIstatId().equals(com_id)) {
                obj.deleteEpidemia(numerosettimana);
                obj.Mflag = true;
            }
        }
    }
}