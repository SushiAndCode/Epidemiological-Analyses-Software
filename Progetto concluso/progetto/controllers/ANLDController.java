package progetto.controllers;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import progetto.App;
import progetto.Modello;
import progetto.Oggetti.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ANLDController implements Initializable {
    private Modello modello;
    @FXML
    private Label idCredenziali;

    @FXML
    private ChoiceBox<String> choiseBox_Zona;

    @FXML
    private ChoiceBox<Integer> choiseBox_Anno;

    @FXML
    private TableView<AnnualeDecessi> tableView;

    @FXML TableColumn<AnnualeDecessi, String> columnNazione;
    @FXML TableColumn<AnnualeDecessi, String> columnRegNome;
    @FXML TableColumn<AnnualeDecessi, String> columnProvId;
    @FXML TableColumn<AnnualeDecessi, Integer> columnAnno;
    @FXML TableColumn<AnnualeDecessi, Integer> columnIncidentiStradali;
    @FXML TableColumn<AnnualeDecessi, Integer> columnTumori;
    @FXML TableColumn<AnnualeDecessi, Integer> columnCardiovascolare;
    @FXML TableColumn<AnnualeDecessi, Integer> columnContagiose;



    public ANLDController(){
        this.modello = Modello.getInstance();
    }

    //Metodo per visualizzare sulla tendina i nomi della zona da tenere in considerazione
    public void onLoad(){
        idCredenziali.setText(modello.getUtente().getNome() + " " + modello.getUtente().getCognome());

        choiseBox_Zona.getItems().add("Nazione");
        choiseBox_Zona.getItems().add("Regione");
        choiseBox_Zona.getItems().add("Provincia");

        choiseBox_Anno.getItems().add(-1);


        for(Regione regione : modello.getRegioni()){
            for(Provincia provincia : regione.getProvince()){
                for(AnnualeDecessi annuale : provincia.getDecessi()){
                    if(!choiseBox_Anno.getItems().contains(annuale.getAnnoNonProperty()))
                        choiseBox_Anno.getItems().add(annuale.getAnnoNonProperty());
                }
            }
        }


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

    @FXML public void changeScreenTo_ANLView_export(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/ANLView_export.fxml"));
        ANLEController controller =  new ANLEController();
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

    public ObservableList<AnnualeDecessi> getAnnualeDecessi(String tipoZona, int anno){

        SimpleIntegerProperty totIncidentiStradali = new SimpleIntegerProperty(0);
        SimpleIntegerProperty totTumori = new SimpleIntegerProperty(0);;
        SimpleIntegerProperty totCardiovascolare = new SimpleIntegerProperty(0);;
        SimpleIntegerProperty totContagiose = new SimpleIntegerProperty(0);;

        ArrayList<AnnualeDecessi> listaAnnuali = new ArrayList<>();


        //Controllo i decessi di un nazione in tutti gli anni
        if(tipoZona.equals("Nazione") && (anno == -1) ){

            for(Regione regione : modello.getRegioni()){
                for(Provincia provincia : regione.getProvince()){
                    for(AnnualeDecessi annuario : provincia.getDecessi()){

                        totIncidentiStradali.setValue(totIncidentiStradali.getValue() + annuario.incidentiStradaliProperty().getValue());
                        totTumori.setValue(totTumori.getValue() + annuario.tumoriProperty().getValue());
                        totCardiovascolare.setValue(totCardiovascolare.getValue() + annuario.cardiovascolareProperty().getValue());
                        totContagiose.setValue(totContagiose.getValue() + annuario.contagioseProperty().getValue());

                    }
                }
            }

            listaAnnuali.add(new AnnualeDecessi("Italia", "Nessuna","Nessuna",0,totIncidentiStradali.getValue(),totTumori.getValue(),totCardiovascolare.getValue(),totContagiose.getValue()));

        }
        //Controllo i decessi di un nazione nell'anno specificato dalla choiceBox_anno
        else if (tipoZona.equals("Nazione") && (anno != -1)) {
                for (Regione regione : modello.getRegioni()) {
                    for (Provincia provincia : regione.getProvince()) {
                        for (AnnualeDecessi annuario : provincia.getDecessi()) {

                            if (annuario.annoProperty().getValue() == anno) {

                                totIncidentiStradali.setValue(totIncidentiStradali.getValue() + annuario.incidentiStradaliProperty().getValue());
                                totTumori.setValue(totTumori.getValue() + annuario.tumoriProperty().getValue());
                                totCardiovascolare.setValue(totCardiovascolare.getValue() + annuario.cardiovascolareProperty().getValue());
                                totContagiose.setValue(totContagiose.getValue() + annuario.contagioseProperty().getValue());

                            }
                        }
                    }
                }

                listaAnnuali.add(new AnnualeDecessi("Italia", "Nessuna", "", anno, totIncidentiStradali.getValue(), totTumori.getValue(), totCardiovascolare.getValue(), totContagiose.getValue()));

            }
        //Controllo i decessi di una Regione in tutti gli anni
        else if(tipoZona.equals("Regione") && (anno == -1)){

            for(Regione regione : modello.getRegioni()){
                for(Provincia provincia : regione.getProvince()){
                    for(AnnualeDecessi annuario : provincia.getDecessi()){

                        totIncidentiStradali.setValue(totIncidentiStradali.getValue() + annuario.incidentiStradaliProperty().getValue());
                        totTumori.setValue(totTumori.getValue() + annuario.tumoriProperty().getValue());
                        totCardiovascolare.setValue(totCardiovascolare.getValue() + annuario.cardiovascolareProperty().getValue());
                        totContagiose.setValue(totContagiose.getValue() + annuario.contagioseProperty().getValue());
                    }
                }
                listaAnnuali.add(new AnnualeDecessi("Italia", regione.getNome(), "", 0, totIncidentiStradali.getValue(), totTumori.getValue(), totCardiovascolare.getValue(), totContagiose.getValue()));
                totIncidentiStradali.setValue(0);
                totTumori.setValue(0);
                totCardiovascolare.setValue(0);
                totContagiose.setValue(0);
            }

        }
        //Controllo i decessi di una Regione nell'anno specificato dalla Choice_box
        else if(tipoZona.equals("Regione") && (anno != -1)){
                for(Regione regione : modello.getRegioni()){
                    for(Provincia provincia : regione.getProvince()){
                        for(AnnualeDecessi annuario : provincia.getDecessi()){

                            if(annuario.annoProperty().getValue() == anno) {
                                totIncidentiStradali.setValue(totIncidentiStradali.getValue() + annuario.incidentiStradaliProperty().getValue());
                                totTumori.setValue(totTumori.getValue() + annuario.tumoriProperty().getValue());
                                totCardiovascolare.setValue(totCardiovascolare.getValue() + annuario.cardiovascolareProperty().getValue());
                                totContagiose.setValue(totContagiose.getValue() + annuario.contagioseProperty().getValue());
                            }

                        }
                    }
                    listaAnnuali.add(new AnnualeDecessi("Italia", regione.getNome(), "", anno, totIncidentiStradali.getValue(), totTumori.getValue(), totCardiovascolare.getValue(), totContagiose.getValue()));
                    totIncidentiStradali.setValue(0);
                    totTumori.setValue(0);
                    totCardiovascolare.setValue(0);
                    totContagiose.setValue(0);
                }
            }
        //Controllo i decessi di una Provincia in tutti gli anni
        else if(tipoZona.equals("Provincia") && (anno == -1)){
            for(Regione regione : modello.getRegioni()){
                for(Provincia provincia : regione.getProvince()){
                    for(AnnualeDecessi annuario : provincia.getDecessi()){

                        totIncidentiStradali.setValue(totIncidentiStradali.getValue() + annuario.incidentiStradaliProperty().getValue());
                        totTumori.setValue(totTumori.getValue() + annuario.tumoriProperty().getValue());
                        totCardiovascolare.setValue(totCardiovascolare.getValue() + annuario.cardiovascolareProperty().getValue());
                        totContagiose.setValue(totContagiose.getValue() + annuario.contagioseProperty().getValue());

                    }

                    listaAnnuali.add(new AnnualeDecessi("Italia", regione.getNome(), provincia.getId_prov(), 0 , totIncidentiStradali.getValue(), totTumori.getValue(), totCardiovascolare.getValue(), totContagiose.getValue()));
                    totIncidentiStradali.setValue(0);
                    totTumori.setValue(0);
                    totCardiovascolare.setValue(0);
                    totContagiose.setValue(0);

                }
            }
        }
        //Controllo i decessi di una Provincia nell'anno specificato dalla choiceBox
        else if(tipoZona.equals("Provincia") && (anno != -1)){

                for(Regione regione : modello.getRegioni()){
                    for(Provincia provincia : regione.getProvince()){
                        for(AnnualeDecessi annuario : provincia.getDecessi()){

                            if(annuario.annoProperty().getValue() == anno) {
                                totIncidentiStradali.setValue(totIncidentiStradali.getValue() + annuario.incidentiStradaliProperty().getValue());
                                totTumori.setValue(totTumori.getValue() + annuario.tumoriProperty().getValue());
                                totCardiovascolare.setValue(totCardiovascolare.getValue() + annuario.cardiovascolareProperty().getValue());
                                totContagiose.setValue(totContagiose.getValue() + annuario.contagioseProperty().getValue());
                            }
                        }
                        listaAnnuali.add(new AnnualeDecessi("Italia", regione.getNome(), provincia.getId_prov(), anno, totIncidentiStradali.getValue(), totTumori.getValue(), totCardiovascolare.getValue(), totContagiose.getValue()));
                        totIncidentiStradali.setValue(0);
                        totTumori.setValue(0);
                        totCardiovascolare.setValue(0);
                        totContagiose.setValue(0);
                    }
                }
            }


        ObservableList<AnnualeDecessi> list = FXCollections.observableArrayList(listaAnnuali);
        return list;

    }

    @FXML private void exportExcel() throws IOException {

        /*
        File file = new File(DatabaseVar.database + "/datiProvince.xlsx");
        FileInputStream dbDatiProvincie = new FileInputStream(file);
        XSSFWorkbook workbook = new XSSFWorkbook(dbDatiProvincie);
        */

        FileSystem system = FileSystems.getDefault();
        Path original = system.getPath("C:\\Users\\simon\\Desktop\\File_excel_progetto\\datiProvince");
        Path target = system.getPath("C:\\Users\\simon\\Desktop\\File_excel_progetto\\File_excel_result");

        try {
            // Throws an exception if the original file is not found.
            Files.copy(original, target, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ex) {
            System.out.println("ERROR");
        }


    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        columnNazione.setCellValueFactory(new PropertyValueFactory<AnnualeDecessi, String>("nazione"));
        columnRegNome.setCellValueFactory(new PropertyValueFactory<AnnualeDecessi, String>("reg_nome"));
        columnProvId.setCellValueFactory(new PropertyValueFactory<AnnualeDecessi, String>("prov_id"));
        columnAnno.setCellValueFactory(new PropertyValueFactory<AnnualeDecessi, Integer>("anno"));
        columnIncidentiStradali.setCellValueFactory(new PropertyValueFactory<AnnualeDecessi, Integer>("incidentiStradali"));
        columnTumori.setCellValueFactory(new PropertyValueFactory<AnnualeDecessi, Integer>("tumori"));
        columnCardiovascolare.setCellValueFactory(new PropertyValueFactory<AnnualeDecessi, Integer>("cardiovascolare"));
        columnContagiose.setCellValueFactory(new PropertyValueFactory<AnnualeDecessi, Integer>("contagiose"));

        //Azioni quando si setta un valore sulla choisebox della zona
        choiseBox_Zona.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //Se il valore di Anno non è null allora posso eseguire una ricerca
                if(!(choiseBox_Anno.getValue() == null)) {
                    int anno = choiseBox_Anno.getValue();
                    tableView.getItems().clear();
                    ObservableList<AnnualeDecessi> lista = getAnnualeDecessi(choiseBox_Zona.getValue(), anno);
                    tableView.getItems().addAll(lista);
                }
                else{ //Altrimenti mando una ricerca per zona e anno generico -1
                    tableView.getItems().clear();
                    ObservableList<AnnualeDecessi> lista = getAnnualeDecessi(choiseBox_Zona.getValue(), -1);
                    tableView.getItems().addAll(lista);
                }
            }
        });

        choiseBox_Anno.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //Se zona è null allora non ha senso visualizzare
                if(!(choiseBox_Zona.getValue() == null)) {
                    //Se zona non è null allora ordino in base alla scelta dell'anno
                    //Se l'anno è -1 allora carico tutti gli anni della zona specificata
                        int anno = choiseBox_Anno.getValue();
                        tableView.getItems().clear();
                        ObservableList<AnnualeDecessi> lista = getAnnualeDecessi(choiseBox_Zona.getValue(), anno);
                        tableView.getItems().addAll(lista);
                }
            }
        });
    }
}
