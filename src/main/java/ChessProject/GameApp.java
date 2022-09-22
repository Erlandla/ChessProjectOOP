package ChessProject;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GameApp extends Application {

	@Override
	public void start(final Stage stage) throws Exception {
		stage.setTitle("Chess not so Titans");
		stage.setScene(new Scene(FXMLLoader.load(GameApp.class.getResource("Game.fxml"))));
		stage.show();
	}
	
	public static void main(String[] args) {
		GameApp.launch(args);
	}
}
