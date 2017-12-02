package tests;

import java.sql.SQLException;

import controller.DatabaseManager;
import controller.GameController;
import javafx.application.Application;
import javafx.stage.Stage;
import model.Catan;
import model.PlayerModel;
import model.PlayerType;

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
		PlayerModel[] players = new PlayerModel[4];
		String spelId = "770";

		players[0] = new PlayerModel("bart", spelId);
		players[1] = new PlayerModel("rik", spelId);
		players[2] = new PlayerModel("lesley", spelId);
		players[3] = new PlayerModel("ger", spelId);

		
		
		GameController gameController = new GameController(null, null, 0);

		
		gameController.start(primaryStage);

	}

}
