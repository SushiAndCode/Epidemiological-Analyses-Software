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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import progetto.App;
import progetto.Modello;
import progetto.Oggetti.PersonaleContratto;
import progetto.Oggetti.Utente;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class PE1VController implements Initializable {

    private Modello modello;

    @FXML private Label idCredenziali;

    @FXML private TableView<Utente> tableView;
    @FXML private TableColumn<Utente, Integer> columnNome;
    @FXML private TableColumn<Utente, Integer> columnCognome;
    @FXML private TableColumn<Utente, Integer> columnId;
    @FXML private TableColumn<Utente, Integer> columnRuolo;
    @FXML private TableColumn<Utente, String> columnComune1;


    public PE1VController(){
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

    @FXML public void changeScreenTo_PE1Edit() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/PE1View_dataEdit.fxml"));
        PE1EController controller =  new PE1EController();
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




    @Override
    public void initialize(URL location, ResourceBundle resources) {
        columnNome.setCellValueFactory(new PropertyValueFactory<Utente, Integer>("nome"));
        columnCognome.setCellValueFactory(new PropertyValueFactory<Utente, Integer>("cognome"));
        columnId.setCellValueFactory(new PropertyValueFactory<Utente, Integer>("id"));
        columnRuolo.setCellValueFactory(new PropertyValueFactory<Utente, Integer>("ruolo"));
        //TODO Risolvere visualizzazione dei comuni, ricordarsi di chiedere al nicho come deve essere visualizzata sta tabella che non ho capito una sega
        columnComune1.setCellValueFactory(new PropertyValueFactory<Utente, String>("listaComuni"));
    }
}