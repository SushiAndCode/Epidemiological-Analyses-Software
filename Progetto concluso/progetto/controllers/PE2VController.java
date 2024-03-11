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
import progetto.Oggetti.AnnualeDecessi;
import progetto.Oggetti.Provincia;
import progetto.Oggetti.Regione;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class PE2VController implements Initializable {
    private Modello modello;

    @FXML private Label idCredenziali;
    @FXML TableView<AnnualeDecessi> tableView;
    @FXML private ChoiceBox<String> choiceBox_province;

    @FXML TableColumn<Provincia, String> columnProvincia;
    @FXML TableColumn<AnnualeDecessi, Integer> columnAnno;
    @FXML TableColumn<AnnualeDecessi, Integer> columnIncidenti;
    @FXML TableColumn<AnnualeDecessi, Integer> columnTumori;
    @FXML TableColumn<AnnualeDecessi, Integer> columnCardiovascolare;
    @FXML TableColumn<AnnualeDecessi, Integer> columnContagiose;

    public PE2VController(){
        this.modello = Modello.getInstance();
    }

    public void onLoad(){
        idCredenziali.setText(modello.getUtente().getNome() + " " + modello.getUtente().getCognome());

        choiceBox_province.getItems().clear();

        for(Regione regione : modello.getRegioni()){
            for(Provincia provincia : regione.getProvince()){
                choiceBox_province.getItems().add(provincia.getNome());
            }
        }

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

    @FXML public void changeScreenTo_PE2Edit() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/PE2View_dataEdit.fxml"));
        PE2EController controller =  new PE2EController();
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

    public ObservableList<AnnualeDecessi> getAnnualeDecessi(String nomeProv){
        ArrayList<AnnualeDecessi> listaAnnuali = new ArrayList<AnnualeDecessi>();
        Provincia prov;
        for(Regione regione : modello.getRegioni()){
           if((prov = regione.findProv(nomeProv)) != null){
               for(AnnualeDecessi annuario : prov.getDecessi()){
                   listaAnnuali.add(annuario);
               }
           }
        }
        ObservableList<AnnualeDecessi> list = FXCollections.observableArrayList(listaAnnuali);
        return list;
    }


    @Override public void initialize(URL location, ResourceBundle resources) {
        columnProvincia.setCellValueFactory(new PropertyValueFactory<Provincia,String>("prov_id"));
        columnAnno.setCellValueFactory(new PropertyValueFactory<AnnualeDecessi, Integer>("anno"));
        columnIncidenti.setCellValueFactory(new PropertyValueFactory<AnnualeDecessi, Integer>("incidentiStradali"));
        columnTumori.setCellValueFactory(new PropertyValueFactory<AnnualeDecessi, Integer>("tumori"));
        columnCardiovascolare.setCellValueFactory(new PropertyValueFactory<AnnualeDecessi, Integer>("cardiovascolare"));
        columnContagiose.setCellValueFactory(new PropertyValueFactory<AnnualeDecessi, Integer>("contagiose"));


        choiceBox_province.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                tableView.getItems().clear();
                ObservableList<AnnualeDecessi> lista = getAnnualeDecessi(choiceBox_province.getValue());
                tableView.getItems().addAll(lista);
            }
        });
    }
}