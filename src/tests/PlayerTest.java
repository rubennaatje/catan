package tests;

import controller.ChatController;
import controller.DatabaseManager;
import javafx.application.Application;
import javafx.stage.Stage;
import model.PlayerModel;
import model.PlayerType;
import model.PlayerUser;
import view.DiceView;
import view.GameControlerView;
import view.GameMergeView;
import view.PlayBoardView;
import view.PlayerView;
import view.ResourceView;

public class PlayerTest extends Application{

	public static void main(String[] args) {
		DatabaseManager.connect();
		
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		PlayerView[] playerViews = new PlayerView[4];
		PlayerModel[] playerModels = new PlayerModel[4];
		
		playerModels[0] = new PlayerUser("bart", "770", PlayerType.BLAUW);
		playerModels[1] = new PlayerModel("rik", "770", PlayerType.BLAUW);
		playerModels[2] = new PlayerModel("lesley", "770", PlayerType.BLAUW);
		playerModels[3] = new PlayerModel("ger", "770", PlayerType.BLAUW);
		
		PlayBoardView playView = new PlayBoardView();
		GameControlerView buttons = new GameControlerView(null, null, null);
		
		for (int i = 0; i < playerModels.length; i++)
		{
			playerViews[i] = new PlayerView();
			playerModels[i].addObserver(playerViews[i]);
			playerModels[i].refresh();
		}
		ResourceView resourceView = new ResourceView();
		playerModels[0].addObserver(resourceView);
		ChatController chat = new ChatController(playerModels[1], "770");
		
		DiceView dice = new DiceView();
		GameMergeView view = new  GameMergeView(playView, buttons, primaryStage, playerViews, resourceView, dice, chat.getView(), null);
		
		view.show();
	}
}