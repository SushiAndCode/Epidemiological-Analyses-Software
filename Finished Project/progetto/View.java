package progetto;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import progetto.controllers.Controller;

public class View {
	public final Stage window;

	public View(Stage primaryStage) throws Exception{
		this.window = primaryStage;

		Parent root = FXMLLoader.load(getClass().getResource("view/LoginView.fxml"));
		window.setTitle("PandemicHub");
		window.setScene(new Scene(root));
//		primaryStage.initStyle(StageStyle.UNDECORATED);
		window.show();
	}
	
	public void registerListener(Controller controller) {
		//TODO
	}
}