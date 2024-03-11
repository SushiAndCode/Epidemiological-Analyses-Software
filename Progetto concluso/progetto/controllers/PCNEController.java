package progetto.controllers;

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
import progetto.Exception.ComplicanzeException;
import progetto.Modello;
import progetto.Oggetti.Comune;
import progetto.Oggetti.PersonaleContratto;
import progetto.Oggetti.SettimanaleEpidemia;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class
PCNEController implements Initializable {
    private Modello modello;
    private ObservableList<SettimanaleEpidemia> list;

    @FXML private Label idCredenziali;
    @FXML private ChoiceBox<String> boxComuni;

    //Collegamento tabella
    @FXML TableView<SettimanaleEpidemia> tableView;

    //Collegamento ccolonne tabella di modifica
    @FXML TableColumn<SettimanaleEpidemia, Integer> columnSettimana;
    @FXML TableColumn<SettimanaleEpidemia, Integer> columnTerapiaIntensiva;
    @FXML TableColumn<SettimanaleEpidemia, Integer> columnMedicoDiBase;
    @FXML TableColumn<SettimanaleEpidemia, Integer> columnInfluenzali;
    @FXML TableColumn<SettimanaleEpidemia, Integer> columnComplicanze;
    @FXML TableColumn<SettimanaleEpidemia, Integer> columnPolmoniti;
    @FXML TableColumn<SettimanaleEpidemia, Integer> columnMeningiti;
    @FXML TableColumn<SettimanaleEpidemia, Integer> columnEpatiti;
    @FXML TableColumn<SettimanaleEpidemia, Integer> columnMorbilli;
    @FXML TableColumn<SettimanaleEpidemia, Integer> columnTubercolosi;
    @FXML TableColumn<SettimanaleEpidemia, Integer> columnGastroenteriti;

    //Collegamento zone di scrittura
    @FXML TextField textTerapiaInt;
    @FXML TextField textMedicoDiBase;
    @FXML TextField textInfluenzali;
    @FXML TextField textComplicanze;
    @FXML TextField textPolmoniti;
    @FXML TextField textMeningiti;
    @FXML TextField textEpatiti;
    @FXML TextField textMorbilli;
    @FXML TextField textTubercolosi;
    @FXML TextField textGastroenteriti;

    //Parametro pre controllare l'inserimento di numeri negativi
    @FXML Label errNumeroNegativo;

    public PCNEController(){
        this.modello = Modello.getInstance();
    }

    public void onLoad(){

        idCredenziali.setText(modello.getUtente().getNome() + " " + modello.getUtente().getCognome());
        ArrayList<String> listacomuni = ((PersonaleContratto)modello.getUtente()).getListaComuni();

        boxComuni.getItems().clear();
        for(String nome : listacomuni) {
            boxComuni.getItems().add(nome);
        }

    }

    @FXML public void changeScreenTo_PCNView() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/PCNView.fxml"));
        PCNController controller = new PCNController();
        loader.setController(controller);
        Parent parent_PCNView = loader.load();
        controller.onLoad();
        Scene scene_PCNView = new Scene(parent_PCNView);
        //adesso prendo le informazioni attuali dello stage
        Stage window = App.getApp().getView().window;
        window.setScene(scene_PCNView);
        window.show();
    }

    @FXML public void changeScreenTo_PCNVisualizza() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/PCNView_dataView.fxml"));
        PCNVController controller =  new PCNVController();
        loader.setController(controller);
        Parent parent_PCNView = loader.load();
        controller.onLoad();
        Scene scene_PCNView = new Scene(parent_PCNView);
        //adesso prendo le informazioni attuali dello stage
        Stage window = App.getApp().getView().window;
        window.setScene(scene_PCNView);
        window.show();
    }

    @FXML public void changeScreenTo_LoginView() throws IOException {
        Parent parent_LoginView = FXMLLoader.load(getClass().getResource( "../view/LoginView.fxml"));
        Scene scene_loginView = new Scene(parent_LoginView);
        //adesso prendo le informazioni attuali dello stage
        Stage window = App.getApp().getView().window;
        window.setScene(scene_loginView);
        window.show();
    }

    @FXML void changeScreenTo_SettingViewPCN() throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/SettingsViewPCN.fxml"));
        SettingsControllerPCN controller =  new SettingsControllerPCN();
        loader.setController(controller);
        Parent parent_ANLEView = loader.load();
        controller.onLoad();
        Scene scene_ANLEView = new Scene(parent_ANLEView);
        //adesso prendo le informazioni attuali dello stage
        Stage window = App.getApp().getView().window;
        window.setScene(scene_ANLEView);
        window.show();
    }

    public ObservableList<SettimanaleEpidemia> getSettimanali(String idComune){

        Comune obj = modello.searchComune(idComune);
        ArrayList<SettimanaleEpidemia> tmp = obj.getEpidemia();
        list = FXCollections.observableArrayList(tmp);

        return list;
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


    //Aggiunge una nuova istanza al settimanale epidemia del comune corrispondente
    @FXML private void modifyTable() throws ComplicanzeException {

        if(textTerapiaInt.getText().equals("") || textMedicoDiBase.getText().equals("") || textInfluenzali.getText().equals("") || textComplicanze.getText().equals("")  || textPolmoniti.getText().equals("")  || textMeningiti.getText().equals("")  || textEpatiti.getText().equals("") || textMorbilli.getText().equals("")  || textTubercolosi.getText().equals("")  || textGastroenteriti.getText().equals("")  || boxComuni.getValue() == null){
            errNumeroNegativo.setVisible(true);
            textTerapiaInt.clear();
            textMedicoDiBase.clear();
            textInfluenzali.clear();
            textComplicanze.clear();
            textPolmoniti.clear();
            textMeningiti.clear();
            textEpatiti.clear();
            textMorbilli.clear();
            textTubercolosi.clear();
            textGastroenteriti.clear();
            return;
        } else {
            errNumeroNegativo.setVisible(false);
        }



        //Trova il numero di settimana massimo per sapere quando creare la prossima
        int settMax = 0;
        for(SettimanaleEpidemia tmp : list)
            if(settMax < tmp.getNumSettimana())
                settMax = tmp.getNumSettimana();


        String terapiaInteniva = textTerapiaInt.getText();
        textTerapiaInt.clear();
        String medicoDiBase = textMedicoDiBase.getText();
        textMedicoDiBase.clear();
        String influenzali = textInfluenzali.getText();
        textInfluenzali.clear();
        String complicanze = textComplicanze.getText();
        textComplicanze.clear();
        String polmoniti = textPolmoniti.getText();
        textPolmoniti.clear();
        String meningiti = textMeningiti.getText();
        textMeningiti.clear();
        String epatiti = textEpatiti.getText();
        textEpatiti.clear();
        String morbilli = textMorbilli.getText();
        textMorbilli.clear();
        String tubercolosi = textTubercolosi.getText();
        textTubercolosi.clear();
        String gastroenteriti = textGastroenteriti.getText();
        textGastroenteriti.clear();

        if(containsNumber(terapiaInteniva) == false){
            errNumeroNegativo.setVisible(true);
            return;
        } else {
            errNumeroNegativo.setVisible(false);
        }

        if(containsNumber(medicoDiBase) == false){
            errNumeroNegativo.setVisible(true);
            return;
        } else {
            errNumeroNegativo.setVisible(false);
        }

        if(containsNumber(influenzali) == false){
            errNumeroNegativo.setVisible(true);
            return;
        } else {
            errNumeroNegativo.setVisible(false);
        }

        if(containsNumber(complicanze) == false){
            errNumeroNegativo.setVisible(true);
            return;
        } else {
            errNumeroNegativo.setVisible(false);
        }

        if(containsNumber(polmoniti) == false){
            errNumeroNegativo.setVisible(true);
            return;
        } else {
            errNumeroNegativo.setVisible(false);
        }

        if(containsNumber(meningiti) == false){
            errNumeroNegativo.setVisible(true);
            return;
        } else {
            errNumeroNegativo.setVisible(false);
        }

        if(containsNumber(epatiti) == false){
            errNumeroNegativo.setVisible(true);
            return;
        } else {
            errNumeroNegativo.setVisible(false);
        }

        if(containsNumber(morbilli) == false){
            errNumeroNegativo.setVisible(true);
            return;
        } else {
            errNumeroNegativo.setVisible(false);
        }

        if(containsNumber(tubercolosi) == false){
            errNumeroNegativo.setVisible(true);
            return;
        } else {
            errNumeroNegativo.setVisible(false);
        }

        if(containsNumber(gastroenteriti) == false){
            errNumeroNegativo.setVisible(true);
            return;
        } else {
            errNumeroNegativo.setVisible(false);
        }


        if(Integer.parseInt(terapiaInteniva) < 0 || Integer.parseInt(medicoDiBase) < 0 || Integer.parseInt(influenzali) < 0 || Integer.parseInt(complicanze) < 0 || Integer.parseInt(polmoniti) < 0 || Integer.parseInt(meningiti) < 0 || Integer.parseInt(epatiti) < 0 || Integer.parseInt(morbilli) < 0 || Integer.parseInt(tubercolosi) < 0 || Integer.parseInt(gastroenteriti) < 0){
            errNumeroNegativo.setVisible(true);
            return;
        } else {
            errNumeroNegativo.setVisible(false);
        }


        //Inserisce il settimanale epidemia nuovo nella tabella e nel database

        SettimanaleEpidemia settimanale = new SettimanaleEpidemia(settMax+1, Integer.valueOf(terapiaInteniva), Integer.valueOf(medicoDiBase),Integer.valueOf(influenzali),Integer.valueOf(complicanze),Integer.valueOf(polmoniti), Integer.valueOf(meningiti),Integer.valueOf(epatiti), Integer.valueOf(morbilli), Integer.valueOf(tubercolosi),Integer.valueOf(gastroenteriti));
        modello.inserisciSettimanale(boxComuni.getValue(), settimanale);

        //Aggiorna la lista dei record del comune
        list.clear();
        Comune com = modello.searchComune(boxComuni.getValue());
        list.addAll(com.getEpidemia());


        modello.upDate();
        tableView.getItems().clear();
        tableView.getItems().addAll(list);

    }

    @FXML private void deleteWeek(){
        if(!textTerapiaInt.getText().equals("") || !textMedicoDiBase.getText().equals("") || !textInfluenzali.getText().equals("") || !textComplicanze.getText().equals("")  || !textPolmoniti.getText().equals("")  || !textMeningiti.getText().equals("")  || !textEpatiti.getText().equals("") || !textMorbilli.getText().equals("")  || !textTubercolosi.getText().equals("")  || !textGastroenteriti.getText().equals("")  || boxComuni.getValue() == null)
            return;

        errNumeroNegativo.setVisible(false);

        Boolean check = false;
        int numSettimana = 0;

        //Trova il numsettiaman più alto
        for(SettimanaleEpidemia settimanale : list){
            if(settimanale.getNumSettimana() > numSettimana)
                numSettimana = settimanale.getNumSettimana();
        }

        //Elimina la settimana dall'oggetto e aggiorna il database
        modello.deleteSettimanale(boxComuni.getValue(), Integer.valueOf(numSettimana));
        modello.upDate();
        numSettimana--;
        list.remove(numSettimana);

        tableView.getItems().clear();
        tableView.getItems().addAll(list);

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        columnSettimana.setCellValueFactory(new PropertyValueFactory<SettimanaleEpidemia, Integer>("numSettimana"));
        columnTerapiaIntensiva.setCellValueFactory(new PropertyValueFactory<SettimanaleEpidemia, Integer>("terapieIntensive"));
        columnMedicoDiBase.setCellValueFactory(new PropertyValueFactory<SettimanaleEpidemia, Integer>("curaMedicoBase"));
        columnInfluenzali.setCellValueFactory(new PropertyValueFactory<SettimanaleEpidemia, Integer>("numInfluenzali"));
        columnComplicanze.setCellValueFactory(new PropertyValueFactory<SettimanaleEpidemia, Integer>("numComplicanze"));
        columnPolmoniti.setCellValueFactory(new PropertyValueFactory<SettimanaleEpidemia, Integer>("numPolmoniti"));
        columnMeningiti.setCellValueFactory(new PropertyValueFactory<SettimanaleEpidemia, Integer>("numMeningiti"));
        columnEpatiti.setCellValueFactory(new PropertyValueFactory<SettimanaleEpidemia, Integer>("numEpatiti"));
        columnMorbilli.setCellValueFactory(new PropertyValueFactory<SettimanaleEpidemia, Integer>("numMorbilli"));
        columnTubercolosi.setCellValueFactory(new PropertyValueFactory<SettimanaleEpidemia, Integer>("numTubercolosi"));
        columnGastroenteriti.setCellValueFactory(new PropertyValueFactory<SettimanaleEpidemia, Integer>("numGastroenteriti"));

        boxComuni.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                tableView.getItems().clear();
                ObservableList<SettimanaleEpidemia> lista = getSettimanali(boxComuni.getValue());
                tableView.getItems().addAll(lista);
            }
        });

    }
}
