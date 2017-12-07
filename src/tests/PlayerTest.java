package tests;

import controller.DatabaseManager;
import javafx.application.Application;
import javafx.stage.Stage;
import model.PlayerModel;
import view.GameControlerView;
import view.GameMergeView;
import view.PlayBoardView;
import view.PlayerView;
import view.ResourceView;

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
		
		playerModels[0] = new PlayerModel("bart", "770");
		playerModels[1] = new PlayerModel("rik", "770");
		playerModels[2] = new PlayerModel("lesley", "770");
		playerModels[3] = new PlayerModel("ger", "770");
		
		PlayBoardView playView = new PlayBoardView();
		GameControlerView buttons = new GameControlerView(null, null);
		
		for (int i = 0; i < playerModels.length; i++)
		{
			playerViews[i] = new PlayerView();
			playerModels[i].addObserver(playerViews[i]);
			playerModels[i].refresh();
		}
		ResourceView resourceView = new ResourceView();
		GameMergeView view = new  GameMergeView(playView, buttons, primaryStage, playerViews, resourceView);
		
		view.show();
	}
}