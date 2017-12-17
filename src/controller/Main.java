package controller;

import javafx.application.Application;

import javafx.stage.Stage;

public class Main extends Application {

	public static void main(String[] args) {
		DatabaseManager.connect();
		launch(args);
		System.exit(0);
	}

	@Override
	public void start(Stage stage) {
		stage.setResizable(false);
		CatanController controller = new CatanController(stage);
		controller.openSplashScreen();
	}
}