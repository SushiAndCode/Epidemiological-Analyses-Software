package progetto.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import progetto.Oggetti.Comune;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class PE0EController implements Initializable {
    private Modello modello;
    @FXML
    private Label idCredenziali;

    @FXML
    private ChoiceBox<?> choiseBox_Comuni;

    @FXML
    private ChoiceBox<?> choiseBox_Comuni1;

    public PE0EController(){
        this.modello = Modello.getInstance();
    }

    //Parametri per visualizzare la tabella
    @FXML TableView<Comune> tableView;
    @FXML TableColumn<Comune, String> columnIstat;
    @FXML TableColumn<Comune, String> columnDataIstit;
    @FXML TableColumn<Comune, String> columnSuperficie;
    @FXML TableColumn<Comune, String> columnTerritorio;
    @FXML TableColumn<Comune, String> columnLitorale;

    //Parametri per modificare la tabella
    @FXML ChoiceBox<String> boxId;

    @FXML TextField textDataIstit;
    @FXML TextField textSuperficie;
    @FXML TextField textTerritorio;
    @FXML TextField textLitorale;

    //Parametri per gestire gli errori
    @FXML Label errorText;
    @FXML Label errorTextData;


    public void onLoad(){
        idCredenziali.setText(modello.getUtente().getNome() + " " + modello.getUtente().getCognome());
        tableView.getItems().clear();
        ObservableList<Comune> lista = FXCollections.observableArrayList(modello.getComuni());

        for(Comune tmp : modello.getComuni()){
            if(!boxId.getItems().contains(tmp.getIstatId()))
                boxId.getItems().add(tmp.getIstatId());
        }
        tableView.getItems().addAll(lista);
    }

    @FXML public void changeScreenTo_PE0View() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/PE0View.fxml"));
        PE0Controller controller =  new PE0Controller();
        loader.setController(controller);
        Parent parent_PCNView = loader.load();
        controller.onLoad();
        Scene scene_PCNView = new Scene(parent_PCNView);
        //adesso prendo le informazioni attuali dello stage
        Stage window = App.getApp().getView().window;
        window.setScene(scene_PCNView);
        window.show();
    }

    @FXML public void changeScreenTo_PE0Visualizza() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/PE0View_dataView.fxml"));
        PE0VController controller =  new PE0VController();
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

    @FXML void changeScreenTo_SettingViewPE0() throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/SettingsViewPE0.fxml"));
        SettingsControllerPE0 controller =  new SettingsControllerPE0();
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
    private Boolean containsNumber(String dato){

        for(int i = 0; i < dato.length(); i++){
            if(Character.getNumericValue(dato.charAt(i)) > 9){
                return false;
            }
        }
        return true;

    }

    public void modifyComune(){
        String istat =boxId.getValue();
        String dataIstit = this.textDataIstit.getText();
        String dataSuperficie = this.textSuperficie.getText();
        String dataTerritorio = this.textTerritorio.getText();
        String dataLitorale = this.textLitorale.getText();

        System.out.println(dataLitorale);

        //Questo if controlla se i numeri inseriti sono stringhe vuote
        if(dataSuperficie.equals("") || dataTerritorio.equals("") || dataLitorale.equals("")){
            //Controlla se i numeri inseriti sono stringhe vuote e se la data è sbagliata
            if(!checkValidDate(dataIstit)){
                //Pulisce i campi
                this.textDataIstit.clear();
                this.textSuperficie.clear();
                this.textTerritorio.clear();
                this.textLitorale.clear();
                //Fa vedere gli errori
                errorText.setVisible(true);
                errorTextData.setVisible(true);
                return;
            } else {
                //Qui solo se i numeri inseriti sono negativi
                errorText.setVisible(true);
                errorTextData.setVisible(false);
                return;
            }
        } else {
            errorText.setVisible(false);
            errorTextData.setVisible(false);
        }


        //Controlla se su litoraneo ho scritto "Yes" o "No"
        if((!dataLitorale.equals("Yes") && !dataLitorale.equals("No"))){
            //Controllo se ho scritto si o no o se la data è errata
            if(!checkValidDate(dataIstit)){
                //Pulisce i campi
                //this.boxId.getItems().clear();
                this.textDataIstit.clear();
                this.textSuperficie.clear();
                this.textTerritorio.clear();
                this.textLitorale.clear();
                //Fa vedere gli errori
                errorText.setVisible(true);
                errorTextData.setVisible(true);
                return;
            } else {
                //Qui solo se i numeri inseriti sono negativi
                this.textDataIstit.clear();
                this.textSuperficie.clear();
                this.textTerritorio.clear();
                this.textLitorale.clear();
                errorText.setVisible(true);
                errorTextData.setVisible(false);
                return;
            }
        } else {
            errorText.setVisible(false);
        }


        //Con i 2 if controllo se i dati non della data sono corretti , in caso non lo siano viene mostrato il messaggio di errore

        //Questo if controlla se i numeri inseriti sono negativi e quindi non validi
        if((Integer.valueOf(dataSuperficie) < 0 ) || (Integer.valueOf(dataTerritorio) < 0 ) || (Integer.valueOf(dataTerritorio) > 5 )){
            //Controlla se i numeri inseriti sono negativi e se la data è sbagliata
            if(!checkValidDate(dataIstit)){
                //Pulisce i campi
                //this.boxId.getItems().clear();
                this.textDataIstit.clear();
                this.textSuperficie.clear();
                this.textTerritorio.clear();
                this.textLitorale.clear();
                //Fa vedere gli errori
                errorText.setVisible(true);
                errorTextData.setVisible(true);
                return;
            } else {
                //Qui solo se i numeri inseriti sono negativi
                this.textDataIstit.clear();
                this.textSuperficie.clear();
                this.textTerritorio.clear();
                this.textLitorale.clear();
                errorText.setVisible(true);
                errorTextData.setVisible(false);
                return;
            }
        } else {
            errorText.setVisible(false);
            errorTextData.setVisible(false);
        }

        //Questo if controlla che non siano inserite lettere all'interno dei campi appositi
        if((containsNumber(dataSuperficie) == false) || (containsNumber(dataTerritorio) == false)){
            //Controlla se i valori inseriti non contengano lettere e che la data sia inseriti correttamente
            if(!checkValidDate(dataIstit)){
                //Pulisce i campi
                this.textDataIstit.clear();
                this.textSuperficie.clear();
                this.textTerritorio.clear();
                this.textLitorale.clear();
                //Fa vedere gli errori
                errorText.setVisible(true);
                errorTextData.setVisible(true);
                return;
            } else {
                //Qui solo che i numeri inseriti non contengano lettere
                this.textDataIstit.clear();
                this.textSuperficie.clear();
                this.textTerritorio.clear();
                this.textLitorale.clear();
                errorText.setVisible(true);
                errorTextData.setVisible(false);
                return;
            }
        } else {
            errorText.setVisible(false);
            errorTextData.setVisible(false);
        }


        //Controlla se la data è sbagliata e fa vedere l'errore
        if(!checkValidDate(dataIstit)) {

            //Pulisce i campi
            errorTextData.setVisible(true);
            //this.boxId.getItems().clear();
            this.textDataIstit.clear();
            this.textSuperficie.clear();
            this.textTerritorio.clear();
            this.textLitorale.clear();
            
            return;

        } else {

            errorTextData.setVisible(false);

        }



        ArrayList<Comune> lista = modello.getComuni();
        //Aggiorna i campi del comune
        for(Comune comune : lista){
            if(comune.getIstatId().equals(istat)){
                comune.setDataIstituzione(dataIstit);
                comune.setSuperficie(dataSuperficie);
                comune.setTerritorio(dataTerritorio);
                comune.setMare(dataLitorale);
                comune.Mflag = true;
            }
        }

        modello.upDate();
        tableView.getItems().clear();
        ObservableList<Comune> listaModded = FXCollections.observableArrayList(modello.getComuni());
        tableView.getItems().addAll(listaModded);


        //Pulisce i campi
        //this.boxId.getItems().clear();
        this.textDataIstit.clear();
        this.textSuperficie.clear();
        this.textTerritorio.clear();
        this.textLitorale.clear();
    }

    //Controlla la validità della data inserita
    private boolean checkValidDate(String data){
        if(data.length() != 10)
            return false;

        //Considerando il formato "dd/mm/aaaa"
        String day = data.substring(0,1);
        String month = data.substring(3,4);
        String year = data.substring(6,9);

        Integer giorno= Integer.valueOf(day);
        Integer mese = Integer.valueOf(month);
        Integer anno = Integer.valueOf(year);

        if(mese > 12 || giorno > 31)
            return false;
        if((mese == 1 || mese == 3 || mese == 5 || mese == 7 || mese == 8 || mese == 10 || mese == 12) && giorno > 31)
            return false;
        if((mese == 4 || mese == 6 || mese ==9 || mese == 11) && giorno > 30)
            return false;
        if(mese == 2 && giorno > 29)
            return false;
        if(mese == 2 && !isBisestile(anno) && giorno > 28)
            return false;

        return true;
    }

    private boolean isBisestile(Integer anno){
       if((anno%400==0)||(anno%100!=0)&&(anno%4==0))
           return true;
       else return false;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        columnIstat.setCellValueFactory(new PropertyValueFactory<Comune, String>("istatId"));
        columnDataIstit.setCellValueFactory(new PropertyValueFactory<Comune, String>("dataIstituzione"));
        columnSuperficie.setCellValueFactory(new PropertyValueFactory<Comune, String>("superficie"));
        columnTerritorio.setCellValueFactory(new PropertyValueFactory<Comune, String>("territorio"));
        columnLitorale.setCellValueFactory(new PropertyValueFactory<Comune, String>("mare"));
    }
}
