package tests;

import controller.CatanController;
import controller.DatabaseManager;
import javafx.stage.Stage;
import javafx.application.Application;
import javafx.stage.Stage;

public class TestWin extends Application {
	
	public static void main(String[] args) {
		DatabaseManager.connect();
		launch(args);
		//System.exit(0);
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		CatanController controller = new CatanController(stage);
		controller.openWinView();
	}
}
