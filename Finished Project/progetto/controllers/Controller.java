package progetto.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import progetto.App;
import progetto.Modello;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
	private Modello modello;
	@FXML private PasswordField idField;
	@FXML private PasswordField pswField;
	@FXML private Label label;
	@FXML private Label errorText;


	public Controller(){
		modello = Modello.getInstance();
	}

	// INIZIO espressioni cambio di scena

	/*
	* quando un bottone di log out viene premuto, cambia la schermata a quella di login
	* */
	public void changeScreenTo_LoginView() throws IOException {
		Parent parent_LoginView = FXMLLoader.load(getClass().getResource( "../view/LoginView.fxml"));
		Scene scene_loginView = new Scene(parent_LoginView);
		//adesso prendo le informazioni attuali dello stage
		Stage window = App.getApp().getView().window;
		window.setScene(scene_loginView);
		window.show();
	}

	//Controlla i campi di id e login e password e valuta se esiste un utente oppure no
	public void checkLogin() throws IOException {
		String ret;
		if((ret = modello.login(this.idField.getText(), this.pswField.getText())).equals("ERROR")){
			errorText.setVisible(false);
			this.idField.clear();
			this.pswField.clear();
		}else if(ret.equals("PCN")){
			changeScreenTo_PCNView();
		}else if(ret.equals("ANL")){
			changeScreenTo_ANLView();
		}else if(ret.equals("PE0")){
			changeScreenTo_PE0View();
		}else if(ret.equals("PE1")){
			changeScreenTo_PE1View();
		}else if(ret.equals("PE2")){
			changeScreenTo_PE2View();
		}else{
			throw new IllegalArgumentException();
		}
		errorText.setVisible(true);
	}

	public void changeScreenTo_PE0View() throws IOException {
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
	
	public void changeScreenTo_PE1View() throws IOException {
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
	
	public void changeScreenTo_PE2View() throws IOException {
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
	
	public void changeScreenTo_PCNView() throws IOException {
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

	public void changeScreenTo_ANLView() throws IOException {
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

	@FXML public void onEnterPressed(KeyEvent event) throws IOException {
		if(event.getCode().equals(KeyCode.ENTER))
			checkLogin();
	 }
	@FXML private ChoiceBox choiseBox_Comuni;
	
	public void initialize(URL url, ResourceBundle rb){

	}
	
}