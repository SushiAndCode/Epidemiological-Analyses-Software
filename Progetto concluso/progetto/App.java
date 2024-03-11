package progetto;

import javafx.application.Application;
import javafx.stage.Stage;
import progetto.controllers.Controller;

public class App extends Application {
	private static App APP;
	private View view;
	private Controller controller;

	public App(){
		super();
		APP = this;
	}
	public static App getApp(){ return APP;}

	public View getView() {
		return view;
	}

	// classe start sostituisce main
	public void start(Stage primaryStage) throws Exception{
		// Creazione degli oggetti principali
		view = new View(primaryStage);
		controller = new Controller();
		
		// INIZIO
		view.registerListener(controller);
	}

}