package tests;

import controller.DatabaseManager;
import javafx.application.Application;
import javafx.stage.Stage;
import model.PlayerModel;
import model.PlayerType;
import model.PlayerUser;
import view.PlayerView;

public class ResourceTest extends Application
{
	public static void main(String[] args) {
		DatabaseManager.connect();
		
		Runtime.getRuntime().addShutdownHook(new Thread(() -> {
			DatabaseManager.disconnect();
			System.out.println("databasemanager shut down");
		}));
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception
	{
		PlayerView playerView = new PlayerView();
		PlayerModel playerModel = new PlayerUser("bart", "770", PlayerType.BLAUW);
		playerModel.addObserver(playerView);
		
	}
}
