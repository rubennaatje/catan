package tests;

import controller.DatabaseManager;
import javafx.application.Application;
import javafx.stage.Stage;
import model.PlayerModel;
import model.PlayerType;
import view.PlayerView;

public class PlayerTest extends Application{

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
		PlayerView[] playerViews = new PlayerView[4];
		PlayerModel[] playerModels = new PlayerModel[4];
		
		playerModels[0] = new PlayerModel(PlayerType.WIT, "bart", "770");
		playerModels[1] = new PlayerModel(PlayerType.ORANJE, "rik", "770");
		playerModels[2] = new PlayerModel(PlayerType.BLAUW, "lesley", "770");
		playerModels[3] = new PlayerModel(PlayerType.ROOD, "ger", "770");
		
		for (int i = 0; i < playerModels.length; i++)
		{
			playerViews[i] = new PlayerView(primaryStage);
			playerModels[i].addObserver(playerViews[i]);
		}
	}
}