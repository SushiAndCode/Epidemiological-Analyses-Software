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
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import progetto.App;
import progetto.Modello;
import progetto.Oggetti.Comune;
import progetto.Oggetti.PersonaleContratto;
import progetto.Oggetti.SettimanaleEpidemia;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class PCNVController implements Initializable {
    private Modello modello;
    @FXML
    private Label idCredenziali;

    @FXML
    private ChoiceBox<String> choiseBox_Comuni;

    @FXML
    TableView<SettimanaleEpidemia> tableView;

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

    public PCNVController(){
        this.modello = Modello.getInstance();
    }

    public void onLoad(){
        idCredenziali.setText(modello.getUtente().getNome() + " " + modello.getUtente().getCognome());

        ArrayList<String> listacomuni = ((PersonaleContratto)modello.getUtente()).getListaComuni();

        choiseBox_Comuni.getItems().clear();
        for(String nome : listacomuni) {
            choiseBox_Comuni.getItems().add(nome);
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

    @FXML public void changeScreenTo_PCNEdit() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/PCNView_dataEdit.fxml"));
        PCNEController controller =  new PCNEController();
        loader.setController(controller);
        Parent parent_PCNView = loader.load();
        controller.onLoad();
        Scene scene_PCNView = new Scene(parent_PCNView);
        //adesso prendo le informazioni attuali dello stage
        Stage window = App.getApp().getView().window;
        window.setScene(scene_PCNView);
        window.show();

    }

    @FXML
    void changeScreenTo_LoginView() throws IOException {
        Parent parent_LoginView = FXMLLoader.load(getClass().getResource( "../view/LoginView.fxml"));
        Scene scene_loginView = new Scene(parent_LoginView);
        //adesso prendo le informazioni attuali dello stage
        Stage window = App.getApp().getView().window;
        window.setScene(scene_loginView);
        window.show();
    }

    @FXML void getCurrentUser() throws IOException {

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

    //Ottengo una ObsList da mettere nelle tabelle
    public ObservableList<SettimanaleEpidemia> getEpidemie(String idcomune){
        Comune obj = modello.searchComune(idcomune);
        ArrayList<SettimanaleEpidemia> tmp = obj.getEpidemia();
        ObservableList<SettimanaleEpidemia> list = FXCollections.observableArrayList(obj.getEpidemia());
        return list;
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

    
    @Override
    public void initialize(URL location, ResourceBundle resources) {

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

        choiseBox_Comuni.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
               tableView.getItems().clear();
                ObservableList<SettimanaleEpidemia> lista = getEpidemie(choiseBox_Comuni.getValue());
                tableView.getItems().addAll(lista);
            }
        });


    }


}
