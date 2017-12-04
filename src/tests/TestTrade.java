package tests;

import controller.DatabaseManager;
import controller.TradeController;
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

		
        TradeController some = new TradeController(primaryStage);
		
		
	}

}
