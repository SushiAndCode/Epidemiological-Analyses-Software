package progetto.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import progetto.App;
import progetto.Modello;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SettingsControllerPE0 implements Initializable {

    private Modello modello;
    @FXML
    private Label idCredenziali;
    @FXML private Label id;
    @FXML private Label nome;
    @FXML private Label cognome;
    @FXML private Label ruolo;

    public SettingsControllerPE0(){
        this.modello = Modello.getInstance();
    }

    public void onLoad(){
        idCredenziali.setText(modello.getUtente().getNome() + " " + modello.getUtente().getCognome());
        id.setText(modello.getUtente().getId());
        nome.setText(modello.getUtente().getNome());
        cognome.setText(modello.getUtente().getCognome());
        ruolo.setText(modello.getUtente().getRuolo());
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
        //adesso prendo le informazioni attuali dello stage
        Stage window = App.getApp().getView().window;
        window.setScene(scene_loginView);
        window.show();
    }




    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
