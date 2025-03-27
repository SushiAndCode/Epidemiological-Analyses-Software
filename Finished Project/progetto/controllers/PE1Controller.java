package progetto.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import progetto.App;
import progetto.Modello;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PE1Controller implements Initializable {
    private Modello modello;
    @FXML private Label idCredenziali;

    public PE1Controller(){
        this.modello = Modello.getInstance();
    }

    public void onLoad(){
        idCredenziali.setText(modello.getUtente().getNome() + " " + modello.getUtente().getCognome());
    }

    @FXML
    private ChoiceBox<?> choiseBox_Comuni;

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
        PE1VController controller =  new PE1VController();
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
