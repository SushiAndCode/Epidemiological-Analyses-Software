package progetto.controllers;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import progetto.App;
import progetto.Modello;
import progetto.Oggetti.AnnualeDecessi;
import progetto.Oggetti.Provincia;
import progetto.Oggetti.Regione;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class PE2EController implements Initializable {
    private Modello modello;
    @FXML private Label idCredenziali;
    private  ArrayList<AnnualeDecessi> annuarioDecessi = new ArrayList<>();


    public PE2EController(){
        this.modello = Modello.getInstance();
    }

    // Parametri per visualizzare la tabella in modifica
    @FXML TableView<AnnualeDecessi> tableView;
    @FXML TableColumn<AnnualeDecessi, String> columnProvinciaId;
    @FXML TableColumn<AnnualeDecessi, Integer> columnAnno;
    @FXML TableColumn<AnnualeDecessi, Integer> columnIncidenti;
    @FXML TableColumn<AnnualeDecessi, Integer> columnTumori;
    @FXML TableColumn<AnnualeDecessi, Integer> columnCardiovascolare;
    @FXML TableColumn<AnnualeDecessi, Integer> columnContagiose;


    //Parametri per modifcare la tabella
    @FXML ChoiceBox<String> choiceBox_provinciaId;
    @FXML ChoiceBox<Integer> choiceBox_annoId;
    @FXML TextField textIncidenti;
    @FXML TextField textTumori;
    @FXML TextField textCardioVasc;
    @FXML TextField textContagiose;

    //Parametri per gestire gli errori
    @FXML Label errNumOccorrenze;

    public void onLoad(){
        idCredenziali.setText(modello.getUtente().getNome() + " " + modello.getUtente().getCognome());

        for(Regione regione : modello.getRegioni()){
            for(Provincia provincia : regione.getProvince()){
                for(AnnualeDecessi annuario : provincia.getDecessi()){
                    annuarioDecessi.add(annuario);
                }
            }
        }

        choiceBox_annoId.getItems().add(-1);
        choiceBox_provinciaId.getItems().add("--");

        for(Regione regione : modello.getRegioni()){
            for(Provincia provincia : regione.getProvince()){
                choiceBox_provinciaId.getItems().add(provincia.getId_prov());
                for(AnnualeDecessi aDecessi : provincia.getDecessi()){
                    if(!choiceBox_annoId.getItems().contains(aDecessi.annoProperty().get()))
                        choiceBox_annoId.getItems().add(aDecessi.annoProperty().get());
                }
            }
        }

        tableView.getItems().clear();
        ObservableList<AnnualeDecessi> list = FXCollections.observableArrayList(annuarioDecessi);
        tableView.getItems().addAll(list);

    }

    @FXML public void changeScreenTo_PE2View() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/PE2View.fxml"));
        PE2Controller controller =  new PE2Controller();
        loader.setController(controller);
        Parent parent_PCNView = loader.load();
        controller.onLoad();
        Scene scene_PCNView = new Scene(parent_PCNView);
        //adesso prendo le informazioni attuali dello stage
        Stage window = App.getApp().getView().window;
        window.setScene(scene_PCNView);
        window.show();
    }

    @FXML public void changeScreenTo_PE2Visualizza() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/PE2View_dataView.fxml"));
        PE2VController controller =  new PE2VController();
        loader.setController(controller);
        Parent parent_PCNView = loader.load();
        controller.onLoad();
        Scene scene_PCNView = new Scene(parent_PCNView);
        //adesso prendo le informazioni attuali dello stage
        Stage window = App.getApp().getView().window;
        window.setScene(scene_PCNView);
        window.show();
    }

    @FXML void changeScreenTo_LoginView() throws IOException {
        Parent parent_LoginView = FXMLLoader.load(getClass().getResource( "../view/LoginView.fxml"));
        Scene scene_loginView = new Scene(parent_LoginView);
        //adesso prendo le informazioni attuali dello stage
        Stage window = App.getApp().getView().window;
        window.setScene(scene_loginView);
        window.show();
    }

    @FXML void changeScreenTo_SettingViewPE2() throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/SettingsViewPE2.fxml"));
        SettingsControllerPE2 controller =  new SettingsControllerPE2();
        loader.setController(controller);
        Parent parent_ANLEView = loader.load();
        controller.onLoad();
        Scene scene_ANLEView = new Scene(parent_ANLEView);
        //adesso prendo le informazioni attuali dello stage
        Stage window = App.getApp().getView().window;
        window.setScene(scene_ANLEView);
        window.show();
    }

    //Funzione che controlla che non siano presenti lettere nell'inserimento di occorrenze di malattie nelle caselle
    private Boolean containsNumber(String malattia){

        for(int i = 0; i < malattia.length(); i++){
            if(Character.getNumericValue(malattia.charAt(i)) > 9){
                return false;
            }
        }
        return true;

    }

    public void modifyAnnualeDecessi(){

        //Lista per aggiungere gli annuali ad una determinata provincia
        ArrayList<AnnualeDecessi> listaF = new ArrayList<AnnualeDecessi>();

        if (textIncidenti.getText().equals("") || textContagiose.getText().equals("") || textCardioVasc.getText().equals("") || textTumori.getText().equals("") || choiceBox_provinciaId.getValue() == null) {
            errNumOccorrenze.setVisible(true);
            return;
        } else {
            errNumOccorrenze.setVisible(false);
        }

        if(Integer.valueOf(textIncidenti.getText()) < 0 || Integer.valueOf(textContagiose.getText()) < 0 || Integer.valueOf(textCardioVasc.getText()) < 0 || Integer.valueOf(textTumori.getText()) < 0){
            errNumOccorrenze.setVisible(true);
            return;
        } else {
            errNumOccorrenze.setVisible(false);
        }

        if(containsNumber(textIncidenti.getText()) == false || containsNumber(textCardioVasc.getText()) == false || containsNumber(textContagiose.getText()) == false || containsNumber(textTumori.getText()) == false){
            errNumOccorrenze.setVisible(true);
            return;
        } else {
            errNumOccorrenze.setVisible(false);
        }

        String appo;
        appo = textIncidenti.getText();
        SimpleIntegerProperty incidentiId = new SimpleIntegerProperty(Integer.valueOf(appo));
        appo = textTumori.getText();
        SimpleIntegerProperty tumoriId = new SimpleIntegerProperty(Integer.valueOf(appo));
        appo = textCardioVasc.getText();
        SimpleIntegerProperty cardioVascId = new SimpleIntegerProperty(Integer.valueOf(appo));
        appo = textContagiose.getText();
        SimpleIntegerProperty contagioseId = new SimpleIntegerProperty(Integer.valueOf(appo));

        String provId = choiceBox_provinciaId.getValue();
        int Anno = choiceBox_annoId.getValue();


        ArrayList<AnnualeDecessi> annuarioDecessi = new ArrayList<>();
        AnnualeDecessi annuarioToUpdate;
        //Scorro le regioni per prendere da ogni provincia il record dei decessi
        for(Regione regione : modello.getRegioni()){
            for(Provincia provincia : regione.getProvince()){
                //Aggiungo alla lista totale il record di ogni provincia
                    annuarioDecessi.addAll(provincia.getDecessi());
            }
        }

        for(AnnualeDecessi annuario : annuarioDecessi){
            if(annuario.getProv_id().equals(provId) && (annuario.annoProperty().get() == Anno)){
                annuario.setIncidentiStradali(incidentiId);
                annuario.setTumori(tumoriId);
                annuario.setCardiovascolare(cardioVascId);
                annuario.setContagiose(contagioseId);
                //mando l'update al modello che aggiornera il campo della provincia opportuna
                modello.aggiornaAnnuario(provId, annuario);
            }

        }
        modello.upDate();

        textTumori.clear();
        textIncidenti.clear();
        textCardioVasc.clear();
        textContagiose.clear();

        //Aggiorna la tabella da mostrare
        tableView.getItems().clear();

        for(AnnualeDecessi tmp : annuarioDecessi){
            if(tmp.getProv_id().equals(provId) && tmp.getAnnoNonProperty() == Anno)
                listaF.add(tmp);
        }

        ObservableList<AnnualeDecessi> list = FXCollections.observableArrayList(listaF);
        tableView.getItems().addAll(list);
    }


    public void insertAnnualeDecessi() {

        //Lista per aggiungere gli annuali ad una determinata provincia
        ArrayList<AnnualeDecessi> listaF = new ArrayList<AnnualeDecessi>();

        if (textIncidenti.getText().equals("") || textContagiose.getText().equals("") || textCardioVasc.getText().equals("") || textTumori.getText().equals("") || choiceBox_provinciaId.getValue() == null) {
            errNumOccorrenze.setVisible(true);
            return;
        } else {
            errNumOccorrenze.setVisible(false);
        }

        if(Integer.valueOf(textIncidenti.getText()) < 0 || Integer.valueOf(textContagiose.getText()) < 0 || Integer.valueOf(textCardioVasc.getText()) < 0 || Integer.valueOf(textTumori.getText()) < 0){
            errNumOccorrenze.setVisible(true);
            return;
        } else {
            errNumOccorrenze.setVisible(false);
        }

        if(containsNumber(textIncidenti.getText()) == false || containsNumber(textCardioVasc.getText()) == false || containsNumber(textContagiose.getText()) == false || containsNumber(textTumori.getText()) == false){
            errNumOccorrenze.setVisible(true);
            return;
        } else {
            errNumOccorrenze.setVisible(false);
        }

        String id_prov = choiceBox_provinciaId.getValue();
        String nome_prov = null;
        int maxAnno = 0;

        for (Provincia tmp : modello.getProvince()) {
            if (tmp.getId_prov().equals(id_prov)) {
                nome_prov = tmp.getNome();
                maxAnno = tmp.getDecessi().get(0).getAnnoNonProperty();
                for (AnnualeDecessi obj : tmp.getDecessi()) {
                    if (obj.getAnnoNonProperty() > maxAnno)
                        maxAnno = obj.getAnnoNonProperty();
                }
            }
        }
        AnnualeDecessi annuario = new AnnualeDecessi("Italia", nome_prov, id_prov, maxAnno + 1, Integer.valueOf(textIncidenti.getText()), Integer.valueOf(textTumori.getText()), Integer.valueOf(textCardioVasc.getText()), Integer.valueOf(textContagiose.getText()));
        modello.inserisciAnnuario(id_prov, annuario);
        modello.upDate();
        annuarioDecessi.add(annuario);
        tableView.getItems().clear();

        for(AnnualeDecessi tmp : annuarioDecessi){
            if(tmp.getProv_id().equals(choiceBox_provinciaId.getValue()))
                listaF.add(tmp);
        }

        choiceBox_annoId.getItems().add(maxAnno+1);

        ObservableList<AnnualeDecessi> list = FXCollections.observableArrayList(listaF);
        tableView.getItems().addAll(list);

    }


    public void deleteAnnualeDecessi() {

        AnnualeDecessi removeObj = null;

        //Lista per aggiungere gli annuali ad una determinata provincia
        ArrayList<AnnualeDecessi> listaF = new ArrayList<AnnualeDecessi>();

        if (!(textIncidenti.getText().equals("")) || !(textContagiose.getText().equals("")) || !(textCardioVasc.getText().equals("")) || !(textTumori.getText().equals("")) || choiceBox_provinciaId.getValue() == null || choiceBox_provinciaId.getValue().equals("--")) {
            errNumOccorrenze.setVisible(true);
            return;
        } else {
            errNumOccorrenze.setVisible(false);
        }

        int numAnnuale = 0;

        //Trova il numAnno relativo alla provincia più alto
        for (AnnualeDecessi annualeDecessi : annuarioDecessi) {
            if (annualeDecessi.getAnnoNonProperty() > numAnnuale && choiceBox_provinciaId.getValue().equals(annualeDecessi.getProv_id())) {

                numAnnuale = annualeDecessi.getAnnoNonProperty();
                removeObj = annualeDecessi;
                
            }
        }

        //Elimina l'annuario dall'oggetto e aggiorna il database
        modello.deleteAnnuale(choiceBox_provinciaId.getValue(), numAnnuale);
        modello.upDate();
        annuarioDecessi.remove(removeObj);

        for(AnnualeDecessi tmp : annuarioDecessi){
            if(tmp.getProv_id().equals(choiceBox_provinciaId.getValue()))
                listaF.add(tmp);
        }

        choiceBox_annoId.getItems().remove(numAnnuale+1);
        ObservableList<AnnualeDecessi> list = FXCollections.observableArrayList(listaF);
        tableView.getItems().clear();
        tableView.getItems().addAll(list);

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        columnProvinciaId.setCellValueFactory(new PropertyValueFactory<AnnualeDecessi,String>("prov_id"));
        columnAnno.setCellValueFactory(new PropertyValueFactory<AnnualeDecessi, Integer>("anno"));
        columnIncidenti.setCellValueFactory(new PropertyValueFactory<AnnualeDecessi, Integer>("incidentiStradali"));
        columnTumori.setCellValueFactory(new PropertyValueFactory<AnnualeDecessi, Integer>("tumori"));
        columnCardiovascolare.setCellValueFactory(new PropertyValueFactory<AnnualeDecessi, Integer>("cardiovascolare"));
        columnContagiose.setCellValueFactory(new PropertyValueFactory<AnnualeDecessi, Integer>("contagiose"));


        choiceBox_annoId.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ArrayList<AnnualeDecessi> listaF = new ArrayList<AnnualeDecessi>();
                tableView.getItems().clear();

                //Se solo la choiseBox_anno è piena filtro per anno
                if(choiceBox_provinciaId.getValue() == null){
                    for(AnnualeDecessi tmp : annuarioDecessi){
                        if(tmp.annoProperty().getValue() == choiceBox_annoId.getValue())
                            listaF.add(tmp);
                    }
                }
                //Se l'anno e la provincia sono risp -1 e -- allora visualizzo tutta la lista
                else if(choiceBox_annoId.getValue() == -1  && choiceBox_provinciaId.getValue().equals("--")){
                    listaF = annuarioDecessi;
                }
                //Se l'anno è -1 filtro solo per provincia
                else if(choiceBox_annoId.getValue() == -1){
                    for(AnnualeDecessi tmp : annuarioDecessi){
                        if(tmp.getProv_id().equals(choiceBox_provinciaId.getValue()))
                            listaF.add(tmp);
                    }
                }
                //Se la provincia è -- filtro solo per anno
                else if(choiceBox_provinciaId.getValue().equals("--")){
                    for(AnnualeDecessi tmp : annuarioDecessi){
                        if(tmp.annoProperty().getValue() == choiceBox_annoId.getValue())
                            listaF.add(tmp);

                    }
                }
                //Altrimenti filtro anche per Provincia
                else{
                        for(AnnualeDecessi tmp : annuarioDecessi) {
                            if (tmp.annoProperty().getValue() == choiceBox_annoId.getValue() && tmp.getProv_id().equals(choiceBox_provinciaId.getValue()))
                                listaF.add(tmp);
                        }
                }
                ObservableList<AnnualeDecessi> list = FXCollections.observableArrayList(listaF);
                tableView.getItems().addAll(list);
            }
        });

        choiceBox_provinciaId.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ArrayList<AnnualeDecessi> listaF = new ArrayList<AnnualeDecessi>();
                tableView.getItems().clear();

                //Se solo la choiseBox_provinciaId è piena filtro per provincia
                if(choiceBox_annoId.getValue() == null ){
                    for(AnnualeDecessi tmp : annuarioDecessi){
                        if(tmp.getProv_id().equals(choiceBox_provinciaId.getValue()))
                            listaF.add(tmp);
                    }
                }
                //Se l'anno e la provincia sono risp -1 e -- allora visualizzo tutta la lista
                else if(choiceBox_annoId.getValue() == -1  && choiceBox_provinciaId.getValue().equals("--")){
                    listaF = annuarioDecessi;
                }
                //Se la choisebox di provincia è -- allora ordino per anno
                else if(choiceBox_provinciaId.getValue().equals("--")){
                    for(AnnualeDecessi tmp : annuarioDecessi){
                        if(tmp.annoProperty().getValue() == choiceBox_annoId.getValue())
                            listaF.add(tmp);
                    }
                }
                //Se l'anno è -1 ordino solo per provincia
                else if(choiceBox_annoId.getValue() == -1 ){
                    for(AnnualeDecessi tmp : annuarioDecessi){
                        if(tmp.getProv_id().equals(choiceBox_provinciaId.getValue()))
                            listaF.add(tmp);
                        System.out.println("Provincia : valore");
                    }
                }
                //Altrimenti filtro anche per Anno
                else{
                    for(AnnualeDecessi tmp : annuarioDecessi) {
                        if (tmp.annoProperty().getValue() == choiceBox_annoId.getValue() && tmp.getProv_id().equals(choiceBox_provinciaId.getValue()))
                            listaF.add(tmp);
                    }
                }
                ObservableList<AnnualeDecessi> list = FXCollections.observableArrayList(listaF);
                tableView.getItems().addAll(list);
            }
        });
    }
}
