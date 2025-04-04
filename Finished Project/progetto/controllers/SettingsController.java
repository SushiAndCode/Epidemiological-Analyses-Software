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
import progetto.App;
import progetto.Modello;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SettingsController implements Initializable {

    private Modello modello;
    @FXML private Label idCredenziali;
    @FXML private Label id;
    @FXML private Label nome;
    @FXML private Label cognome;
    @FXML private Label ruolo;

    public SettingsController(){
        this.modello = Modello.getInstance();
    }

    public void onLoad(){
        idCredenziali.setText(modello.getUtente().getNome() + " " + modello.getUtente().getCognome());
        id.setText(modello.getUtente().getId());
        nome.setText(modello.getUtente().getNome());
        cognome.setText(modello.getUtente().getCognome());
        ruolo.setText(modello.getUtente().getRuolo());
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

    @FXML public void changeScreenTo_ANLView_export() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/ANLView_export.fxml"));
        ANLEController controller =  new ANLEController();
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

    }
}
