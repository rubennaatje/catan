package tests;

import controller.DatabaseManager;
import javafx.application.Application;
import javafx.stage.Stage;
import model.BoardHelper;
import model.PlayerModel;

public class TestTrade extends Application {

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
        PlayerModel white = new PlayerModel("bart", null);
        PlayerModel orange = new PlayerModel("rik", null);
        PlayerModel blue = new PlayerModel("lesley", null);
        PlayerModel red = new PlayerModel("ger", null);
		
		
		
	}

}
