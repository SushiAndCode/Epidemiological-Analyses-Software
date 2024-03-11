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

public class SettingsControllerPCN implements Initializable {

    private Modello modello;
    @FXML private Label idCredenziali;
    @FXML private Label id;
    @FXML private Label nome;
    @FXML private Label cognome;
    @FXML private Label ruolo;

    public SettingsControllerPCN(){
        this.modello = Modello.getInstance();
    }

    public void onLoad(){
        idCredenziali.setText(modello.getUtente().getNome() + " " + modello.getUtente().getCognome());
        id.setText(modello.getUtente().getId());
        nome.setText(modello.getUtente().getNome());
        cognome.setText(modello.getUtente().getCognome());
        ruolo.setText(modello.getUtente().getRuolo());
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

    @FXML void changeScreenTo_LoginView() throws IOException {
        Parent parent_LoginView = FXMLLoader.load(getClass().getResource( "../view/LoginView.fxml"));
        Scene scene_loginView = new Scene(parent_LoginView);
        //adesso prendo le informazioni attuali dello stage
        Stage window = App.getApp().getView().window;
        window.setScene(scene_loginView);
        window.show();
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
