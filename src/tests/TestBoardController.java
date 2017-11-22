package tests;

import controller.BoardController;
import controller.DatabaseManager;
import javafx.application.Application;
import javafx.stage.Stage;

public class TestBoardController extends Application{

	public static void main(String[] args) throws Exception {
		DatabaseManager.connect();
		
		Runtime.getRuntime().addShutdownHook(new Thread(() -> {
			DatabaseManager.disconnect();
			System.out.println("databasemanager shut down");
		}));
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		System.out.println("rub");
		
		BoardController boardController = new BoardController();
		
		boardController.start(primaryStage);
		

	}
}
