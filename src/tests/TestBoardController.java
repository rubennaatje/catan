package tests;

import controller.GameController;
import controller.DatabaseManager;
import javafx.application.Application;
import javafx.stage.Stage;

public class TestBoardController extends Application{

	public static void main(String[] args) {
		DatabaseManager.connect();
		
		Runtime.getRuntime().addShutdownHook(new Thread(() -> {
			DatabaseManager.disconnect();
			System.out.println("databasemanager shut down");
		}));
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {

		GameController boardController = new GameController(null, null, 0,primaryStage);
		
		boardController.start();
		

	}
}
