package progetto.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import progetto.App;
import progetto.Modello;
import progetto.Oggetti.Comune;
import progetto.Oggetti.PersonaleContratto;
import progetto.Oggetti.Utente;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class PE1EController implements Initializable {
    private Modello modello;
    @FXML
    private Label idCredenziali;


    //Parametri per visualizzazione tabella in modifica
    @FXML private TableView<Utente> tableView;
    @FXML TableColumn<Utente, Integer> columnNome;
    @FXML TableColumn<Utente, Integer> columnCognome;
    @FXML TableColumn<Utente, Integer> columnId;
    @FXML TableColumn<Utente, String> columnComune1;

    //Parametri per effettiva modifica della tabella
    @FXML private TextField idText;
    @FXML private TextField comText;


    //Parametri per mostrare a video l'errore di inserimento credenziali
    @FXML private Label errorIdText;
    @FXML private Label errorComuneIdText;

    public PE1EController(){
        this.modello = Modello.getInstance();
    }

    @FXML public void changeScreenTo_PE1View() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/PE1View.fxml"));
        PE1Controller controller =  new PE1Controller();
        loader.setController(controller);
        Parent parent_PE1View = loader.load();
        controller.onLoad();
        Scene scene_PE1View = new Scene(parent_PE1View);
        //adesso prendo le informazioni attuali dello stage
        Stage window = App.getApp().getView().window;
        window.setScene(scene_PE1View);
        window.show();
    }

    @FXML public void changeScreenTo_PE1Visualizza() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/PE1View_dataView.fxml"));
        PE1VController controller = new PE1VController();
        loader.setController(controller);
        Parent parent_PE1View = loader.load();
        controller.onLoad();
        Scene scene_PE1View = new Scene(parent_PE1View);
        //adesso prendo le informazioni attuali dello stage
        Stage window = App.getApp().getView().window;
        window.setScene(scene_PE1View);
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

    @FXML void changeScreenTo_SettingViewPE1() throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/SettingsViewPE1.fxml"));
        SettingsControllerPE1 controller =  new SettingsControllerPE1();
        loader.setController(controller);
        Parent parent_ANLEView = loader.load();
        controller.onLoad();
        Scene scene_ANLEView = new Scene(parent_ANLEView);
        //adesso prendo le informazioni attuali dello stage
        Stage window = App.getApp().getView().window;
        window.setScene(scene_ANLEView);
        window.show();
    }

    public void onLoad(){
        idCredenziali.setText(modello.getUtente().getNome() + " " + modello.getUtente().getCognome());
        tableView.getItems().clear();
        ArrayList<Utente> listaUtenti = modello.getUtenti();
        ArrayList<Utente> listaF = new ArrayList<Utente>();
        for(Utente tmp : listaUtenti){
            if(tmp instanceof PersonaleContratto)
                listaF.add(tmp);
        }
        ObservableList<Utente> lista = FXCollections.observableArrayList(listaF);
        tableView.getItems().addAll(lista);
    }

    public void deleteComune(){
        String id = this.idText.getText();
        String comune = this.comText.getText() + " ";

        //Parametri per gestire gli errori
        ArrayList<Utente> listaErr = new ArrayList<Utente>();
        Boolean prova = true;
        Boolean foundComune = false;

        int controllo = comune.length();

        for(Utente tmp : modello.getUtenti()){
            if(tmp instanceof PersonaleContratto)
                listaErr.add(tmp);
        }


        for(Utente utente : listaErr){
            if(utente.getId().equals(id)){

                prova = true;
                break;


            } else {

                prova = false;


            }
        }
        if(id.equals("") || prova == false){
            if(controllo % 6 != 0){

                errorComuneIdText.setVisible(true);
                errorIdText.setVisible(true);
                comText.clear();
                idText.clear();
                return;

            } else {

                comText.clear();
                idText.clear();
                errorComuneIdText.setVisible(false);
                errorIdText.setVisible(true);
                return;

            }

        } else {

            errorIdText.setVisible(false);

        }

        if(controllo % 6 != 0){

            errorComuneIdText.setVisible(true);
            comText.clear();
            idText.clear();
            return;

        } else {

            errorComuneIdText.setVisible(false);

        }

        int nstringhe = controllo/6;
        ArrayList<Utente> listaUtenti = modello.getUtenti();
        ArrayList<Utente> listaF = new ArrayList<Utente>();


        for(Utente utente : listaUtenti){
            if(utente.getId().equals(id)){
                for(int i = 0; i < nstringhe;i++){
                    //sottostringa che corrisponde a un istat_id
                    String appo = comune.substring(i * 6, i * 6 + 5);
                    appo = appo.substring(0, 5);
                    for(Comune test : modello.getComuni()){
                        if(test.getIstatId().equals(appo)) {
                            foundComune = true;
                            if ((utente.getLista().contains(appo))) {
                                modello.aggiornaUtente(id, appo);
                                break;
                            }
                        }
                    }
                    if(foundComune == false){
                        errorComuneIdText.setVisible(true);
                    } else {
                        errorComuneIdText.setVisible(false);
                    }
                }
            }
        }
        modello.upDate();
        tableView.getItems().clear();
        for(Utente tmp : listaUtenti){
            if(tmp instanceof PersonaleContratto)
                listaF.add(tmp);
        }
        idText.clear();
        comText.clear();
        ObservableList<Utente> lista = FXCollections.observableArrayList(listaF);
        tableView.getItems().addAll(lista);
    }

    public void addComune(){
        String id = this.idText.getText();
        String comune = this.comText.getText() + " ";


        //Parametri per gestire gli errori
        ArrayList<Utente> listaErr = new ArrayList<Utente>();
        Boolean prova = true;
        Boolean foundComune = false;

        int controllo = comune.length();


        for(Utente tmp : modello.getUtenti()){
            if(tmp instanceof PersonaleContratto)
                listaErr.add(tmp);
        }


        for(Utente utente : listaErr){
            if(utente.getId().equals(id)){

                prova = true;
                break;

            } else {

                prova = false;

            }
        }

        if(id.equals("") || prova == false){
            if(controllo % 6 != 0){

                errorComuneIdText.setVisible(true);
                errorIdText.setVisible(true);
                comText.clear();
                idText.clear();
                return;

            } else {

                comText.clear();
                idText.clear();
                errorComuneIdText.setVisible(false);
                errorIdText.setVisible(true);
                return;

            }

        } else {

            errorIdText.setVisible(false);

        }

        if(controllo % 6 != 0){

            errorComuneIdText.setVisible(true);
            comText.clear();
            idText.clear();
            return;

        } else {

            errorComuneIdText.setVisible(false);

        }


        int nstringhe = comune.length()/6;

        ArrayList<Utente> listaUtenti = modello.getUtenti();
        ArrayList<Utente> listaF = new ArrayList<Utente>();


        for(Utente utente : listaUtenti){
            if(utente.getId().equals(id)){
                for(int i = 0; i < nstringhe;i++){
                    //sottostringa che corrisponde a un istat_id
                    String appo = comune.substring(i * 6, i * 6 + 5);
                    appo = appo.substring(0, 5);
                   ArrayList<Comune> mod =  modello.getComuni();
                    for(Comune test : mod){
                        if(test.getIstatId().equals(appo)) {
                            foundComune = true;
                            if (!(utente.getLista().contains(appo))) {
                                modello.aggiornaUtente(id, appo);
                                break;
                            }
                        }
                    }
                    if(foundComune == false){
                        errorComuneIdText.setVisible(true);
                    } else {
                        errorComuneIdText.setVisible(false);
                    }
                }
            }
        }

        modello.upDate();
        tableView.getItems().clear();
        for(Utente tmp : listaUtenti){
            if(tmp instanceof PersonaleContratto)
                listaF.add(tmp);
        }

        idText.clear();
        comText.clear();
        ObservableList<Utente> lista = FXCollections.observableArrayList(listaF);
        tableView.getItems().addAll(lista);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        columnNome.setCellValueFactory(new PropertyValueFactory<Utente, Integer>("nome"));
        columnCognome.setCellValueFactory(new PropertyValueFactory<Utente, Integer>("cognome"));
        columnId.setCellValueFactory(new PropertyValueFactory<Utente, Integer>("id"));
        //TODO Risolvere visualizzazione dei comuni, ricordarsi di chiedere al nicho come deve essere visualizzata sta tabella che non ho capito una sega
        columnComune1.setCellValueFactory(new PropertyValueFactory<Utente, String>("listaComuni"));

    }
}