package progetto.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import progetto.App;
import progetto.Modello;
import progetto.Oggetti.AnnualeDecessi;
import progetto.Oggetti.DatabaseVar;
import progetto.Oggetti.Provincia;
import progetto.Oggetti.Regione;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.net.URL;
import java.nio.file.*;
import java.util.Iterator;
import java.util.ResourceBundle;

public class ANLEController implements Initializable {
    private Modello modello;
    @FXML private Label idCredenziali;

    public ANLEController(){
        this.modello = Modello.getInstance();
    }

    public void onLoad(){
        idCredenziali.setText(modello.getUtente().getNome() + " " + modello.getUtente().getCognome());
    }

    @FXML public void changeScreenTo_ANLView() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/ANLView.fxml"));
        ANLController controller =  new ANLController();
        loader.setController(controller);
        Parent parent_ANLView = loader.load();
        controller.onLoad();
        Scene scene_ANLView = new Scene(parent_ANLView);
        //adesso prendo le informazioni attuali dello stage
        Stage window = App.getApp().getView().window;
        window.setScene(scene_ANLView);
        window.show();
    }

    @FXML public void changeScreenTo_ANLView_data(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/ANLView_data.fxml"));
        ANLDController controller =  new ANLDController();
        loader.setController(controller);
        Parent parent_PCNView = loader.load();
        controller.onLoad();
        Scene scene_PCNView = new Scene(parent_PCNView);
        //adesso prendo le informazioni attuali dello stage
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene_PCNView);
        window.show();
    }

    @FXML public void changeScreenTo_ANLView_chart(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/ANLView_chart.fxml"));
        ANLCController controller =  new ANLCController();
        loader.setController(controller);
        Parent parent_PCNView = loader.load();
        controller.onLoad();
        Scene scene_PCNView = new Scene(parent_PCNView);
        //adesso prendo le informazioni attuali dello stage
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene_PCNView);
        window.show();
    }

    @FXML void changeScreenTo_LoginView(ActionEvent event) throws IOException {
        Parent parent_LoginView = FXMLLoader.load(getClass().getResource( "../view/LoginView.fxml"));
        Scene scene_loginView = new Scene(parent_LoginView);
        //adesso prendo le informazioni attuali dello stage
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene_loginView);
        window.show();
    }

    @FXML private void exportExcel() throws IOException {

        //Variabile per calcolare il numero di pagine dentro il file da copiare
        int numberOfSheets = 0;

        File file = new File("C:\\Users\\simon\\Desktop\\File_excel_progetto\\File_excel_result\\exported.xlsx");
        //Sto leggendo il file che voglio copiare
        FileInputStream dbImportProvince = new FileInputStream(DatabaseVar.database + "/datiProvince.xlsx");

        //Creo il workbook da cui devo andare a leggere i fogli excel da copiare
        XSSFWorkbook workbook = new XSSFWorkbook(dbImportProvince);

        //Creo il workbook sul quale incollerò i file excel e che userò per creare il nuovo file excel
        XSSFWorkbook workbookExport = workbook;

        
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        workbookExport.write(fileOutputStream);
        dbImportProvince.close();
        fileOutputStream.close();

    }

    @FXML void changeScreenTo_SettingView() throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/SettingsView.fxml"));
        SettingsController controller =  new SettingsController();
        loader.setController(controller);
        Parent parent_ANLEView = loader.load();
        controller.onLoad();
        Scene scene_ANLEView = new Scene(parent_ANLEView);
        //adesso prendo le informazioni attuali dello stage
        Stage window = App.getApp().getView().window;
        window.setScene(scene_ANLEView);
        window.show();

    }

    @FXML private void exportCsv() throws IOException {

        //Creo il file CSV
        File file = new File("C:\\Users\\simon\\Desktop\\File_excel_progetto\\File_excel_result\\exported2.csv");

        //Buffer per contenere i dati da scrivere sul file CSV
        StringBuffer data = new StringBuffer();

        //Prendo lo stream in input del file excel che mi serve copiare
        FileInputStream dbExcelToCopy =  new FileInputStream(DatabaseVar.database + "/datiProvince.xlsx");

        //Creo lo stream di output con il quale poi creerò il file csv
        FileOutputStream dbExccelToCsv = new FileOutputStream(file);

        //Prendo il workBook dallo stream di input
        XSSFWorkbook workbook = new XSSFWorkbook(dbExcelToCopy);

        //Prendo il numero di fogli dentro il file excel
        int numberOfSheet = workbook.getNumberOfSheets();

        Cell cella;
        Row riga;

        //Facccio un ciclo per selezionare tutti i fogli
        for(int i = 0; i < numberOfSheet; i++){

            XSSFSheet sheet = workbook.getSheetAt(i);
            //creo l'iteratore delle pagine del file excel
            //L'iteratore delle pagine scorre tutte le righe di ogni pagina
            Iterator<Row> iteratorRow = sheet.iterator();

            while(iteratorRow.hasNext()){

                //Prendo come elemento la prossima riga
                riga = iteratorRow.next();
                //Creo l'iteratore che andrà a scorrere ogni cella di ogni riga
                Iterator<Cell> iteratorCell = riga.iterator();

                while(iteratorCell.hasNext()){

                    //Prendo come elemento la prossima cella di ogni riga
                    cella = iteratorCell.next();
                    //Faccio lo switch per controllare il contenuto di ogni cella e copiarlo nel file CSV
                    switch(cella.getCellType()) {

                        case BOOLEAN:
                            data.append(cella.getBooleanCellValue() + ",");
                            break;

                        case NUMERIC:
                            data.append(cella.getNumericCellValue() + ",");
                            break;

                        case STRING:
                            data.append(cella.getStringCellValue() + "," );
                            break;

                        case BLANK:
                            data.append("" + ",");

                        default:
                            data.append(cella + ",");

                    }


                }

                //Conto le righe vuote o nulle all'interno del file excel e le salto per poi collegare solo le parti che mi interessano
                if(riga == null){
                }
                else if(riga.getCell(1) == null){
                }
                else if(riga.getCell(1).getCellType().equals(CellType.BLANK)){
                }
                else {
                    data.append('\n');
                }

            }

        }

        //Creo il file csv compilato
        dbExccelToCsv.write(data.toString().getBytes());
        dbExcelToCopy.close();
        dbExccelToCsv.close();


    }

    @FXML private void exportTxt() throws IOException{

        //Creo il file CSV
        File file = new File("C:\\Users\\simon\\Desktop\\File_excel_progetto\\File_excel_result\\exported3.txt");

        //Buffer per contenere i dati da scrivere sul file CSV
        StringBuffer data = new StringBuffer();

        //Prendo lo stream in input del file excel che mi serve copiare
        FileInputStream dbExcelToCopy =  new FileInputStream(DatabaseVar.database + "/datiProvince.xlsx");

        //Creo lo stream di output con il quale poi creerò il file csv
        FileOutputStream dbExccelToCsv = new FileOutputStream(file);

        //Prendo il workBook dallo stream di input
        XSSFWorkbook workbook = new XSSFWorkbook(dbExcelToCopy);

        //Prendo il numero di fogli dentro il file excel
        int numberOfSheet = workbook.getNumberOfSheets();

        Cell cella;
        Row riga;
        char tab = '\t';
        String spazio = String.valueOf(tab);


        //Facccio un ciclo per selezionare tutti i fogli
        for(int i = 0; i < numberOfSheet; i++){

            XSSFSheet sheet = workbook.getSheetAt(i);
            //creo l'iteratore delle pagine del file excel
            //L'iteratore delle pagine scorre tutte le righe di ogni pagina
            Iterator<Row> iteratorRow = sheet.iterator();

            while(iteratorRow.hasNext()){

                //Prendo come elemento la prossima riga
                riga = iteratorRow.next();
                //Creo l'iteratore che andrà a scorrere ogni cella di ogni riga
                Iterator<Cell> iteratorCell = riga.iterator();

                while(iteratorCell.hasNext()){

                    //Prendo come elemento la prossima cella di ogni riga
                    cella = iteratorCell.next();
                    //Faccio lo switch per controllare il contenuto di ogni cella e copiarlo nel file CSV
                    switch(cella.getCellType()) {

                        case BOOLEAN:
                            data.append(cella.getBooleanCellValue() + spazio);
                            break;

                        case NUMERIC:
                            data.append(cella.getNumericCellValue() + spazio);
                            break;

                        case STRING:
                            data.append(cella.getStringCellValue() + " ");
                            break;

                        case BLANK:
                            data.append("");

                        default:
                            data.append(cella);

                    }

                }

                //Conto le righe vuote o nulle all'interno del file excel e le salto per poi collegare solo le parti che mi interessano
                if(riga == null){
                }
                else if(riga.getCell(1) == null){
                }
                else if(riga.getCell(1).getCellType().equals(CellType.BLANK)){
                }
                else {
                    data.append('\n');
                }

            }

        }

        //Creo il file csv compilato
        dbExccelToCsv.write(data.toString().getBytes());
        dbExcelToCopy.close();
        dbExccelToCsv.close();


    }

    @FXML private void exportXml() throws IOException, ParserConfigurationException, TransformerException {

        String filePath = "C:\\Users\\simon\\Desktop\\File_excel_progetto\\File_excel_result\\exported4.xml";

        DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();

        DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();

        Document document = documentBuilder.newDocument();

        Element whole = document.createElement("DataBase");

        for(Regione regione : modello.getRegioni()){
            for(Provincia provincia : regione.getProvince()){
                for(AnnualeDecessi annuale : provincia.getDecessi()){

                    //Elemento radice
                    Element root = document.createElement("ProvinciaId");
                    whole.appendChild(root);

                    //Setto il l'id della provincia
                    Attr attrid = document.createAttribute("id");
                    attrid.setValue(annuale.getProv_id());
                    root.setAttributeNode(attrid);

                    //Elemento anno dentro la provincia
                    Element anno = document.createElement("Anno");
                    root.appendChild(anno);

                    //Setto il valore dell'anno
                    Attr attranno = document.createAttribute("valore");
                    attranno.setValue(String.valueOf(annuale.getAnnoNonProperty()));
                    anno.setAttributeNode(attranno);

                    //Elemento incidenti dentro anno
                    Element incidenti = document.createElement("Incidenti_Stradali");
                    anno.appendChild(incidenti);

                    //Setto il valore di incidenti
                    Attr attrincidenti = document.createAttribute("valore");
                    attrincidenti.setValue(String.valueOf(annuale.incidentiStradaliProperty().getValue()));
                    incidenti.setAttributeNode(attrincidenti);

                    //Elemento tumori dentro anno
                    Element tumori = document.createElement("Tumori");
                    anno.appendChild(tumori);

                    //Setto il valore di tumori
                    Attr attrtumori = document.createAttribute("valore");
                    attrtumori.setValue(String.valueOf(annuale.tumoriProperty().getValue()));
                    tumori.setAttributeNode(attrtumori);

                    //Elemento cardiovascolare dentro anno
                    Element cardiovascolare = document.createElement("Cardiovascolare");
                    anno.appendChild(cardiovascolare);

                    //Setto il valore di cardiovascolari
                    Attr attrcardiovascolari = document.createAttribute("valore");
                    attrcardiovascolari.setValue(String.valueOf(annuale.cardiovascolareProperty().getValue()));
                    cardiovascolare.setAttributeNode(attrcardiovascolari);

                    //Elemento contagiose dentro anno
                    Element contagiose = document.createElement("Contagiose");
                    anno.appendChild(contagiose);

                    //Setto il valore di contagiose
                    Attr attrcontagiose = document.createAttribute("valore");
                    attrcontagiose.setValue(String.valueOf(annuale.contagioseProperty().getValue()));
                    contagiose.setAttributeNode(attrcontagiose);


                }
            }
        }

        document.appendChild(whole);
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource domSource = new DOMSource(document);
        StreamResult streamResult = new StreamResult(new File(filePath));

        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
        transformer.transform(domSource, streamResult);

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
