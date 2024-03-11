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
import progetto.Oggetti.Provincia;
import progetto.Oggetti.Regione;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class PE0VController implements Initializable {
    private Modello modello;
    @FXML private Label idCredenziali;


    public PE0VController(){
        this.modello = Modello.getInstance();
    }

    //@FXML private ChoiceBox<String> choiceBox_regioni;

    @FXML private ChoiceBox<String> choiseBox_province;

    public void onLoad(){
        idCredenziali.setText(modello.getUtente().getNome() + " " + modello.getUtente().getCognome());

        //choiceBox_regioni.getItems().clear();
        choiseBox_province.getItems().clear();

        for(Regione regione : modello.getRegioni()){
            //choiceBox_regioni.getItems().add(regione.getNome());
            for(Provincia provincia : regione.getProvince()){
                choiseBox_province.getItems().add(provincia.getNome());
            }
        }
    }

    @FXML TableView<Comune> tableView;
    @FXML TableColumn<Comune, String> columnIstat;
    @FXML TableColumn<Comune, String> columnNome;
    @FXML TableColumn<Comune, String> columnDataIstit;
    @FXML TableColumn<Comune, String> columnSuperficie;
    @FXML TableColumn<Comune, String> columnTerritorio;
    @FXML TableColumn<Comune, String> columnLitorale;
    @FXML TableColumn<Comune, String> columnProvincia;

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
    
    @FXML public void changeScreenTo_PE0Edit() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/PE0View_dataEdit.fxml"));
        PE0EController controller =  new PE0EController();
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
        //adesso prendo le informazioni attuali dello stageINF
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

    public ObservableList<Comune> getComuni(String nomeProv){

        ArrayList<Comune> listacomuni = new ArrayList<>();
        for(Regione regione : modello.getRegioni()){
            for(Provincia provincia : regione.getProvince()){
                if(provincia.getNome().equals(nomeProv)){

                    for(Comune comune : provincia.getComuni()){

                        listacomuni.add(comune);
                    }

                }
            }
        }
        ObservableList<Comune> list = FXCollections.observableArrayList(listacomuni);
        return list;

    }

    @Override public void initialize(URL location, ResourceBundle resources) {

        columnIstat.setCellValueFactory(new PropertyValueFactory<Comune, String>("istatId"));
        columnNome.setCellValueFactory(new PropertyValueFactory<Comune, String>("nome"));
        columnDataIstit.setCellValueFactory(new PropertyValueFactory<Comune, String>("dataIstituzione"));
        columnSuperficie.setCellValueFactory(new PropertyValueFactory<Comune, String>("superficie"));
        columnTerritorio.setCellValueFactory(new PropertyValueFactory<Comune, String>("territorio"));
        columnLitorale.setCellValueFactory(new PropertyValueFactory<Comune, String>("mare"));
        columnProvincia.setCellValueFactory(new PropertyValueFactory<Comune, String>("provincia"));

        choiseBox_province.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                tableView.getItems().clear();
                ObservableList<Comune> lista = getComuni(choiseBox_province.getValue());
                tableView.getItems().addAll(lista);
            }
        });

    }

}
