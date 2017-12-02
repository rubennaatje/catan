package tests;

import java.sql.SQLException;

import controller.DatabaseManager;
import controller.GameController;
import javafx.application.Application;
import javafx.stage.Stage;
import model.Catan;

public class TestStartController extends Application {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		
		DatabaseManager.connect();

		Runtime.getRuntime().addShutdownHook(new Thread(() -> {
			DatabaseManager.disconnect();
			System.out.println("databasemanager shut down");
		}));
		
		Catan catan = new Catan();
		//catan.initGame();
		Catan.setGameId("770");
		
		//catan.addResourceCards();
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {

		GameController boardController = new GameController(null, null, 0);

		boardController.start(primaryStage);

	}

}
