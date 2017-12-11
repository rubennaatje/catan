package controller;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

	public static void main(String[] args) {
		DatabaseManager.connect();
		
		launch(args);
		
	}

	@Override
	public void start(Stage stage) {
		CatanController controller = new CatanController(stage);
		controller.openSplashScreen();
	}
}