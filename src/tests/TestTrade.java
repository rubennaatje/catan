package tests;

import controller.DatabaseManager;
import controller.TradeController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import model.BoardHelper;
import model.Catan;
import model.PlayerModel;
import model.PlayerUser;

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
		PlayerUser player = new PlayerUser("lesley", "770");
        TradeController some = new TradeController(primaryStage,player);
        primaryStage.setOnCloseRequest(e -> Platform.exit());
	}
}
