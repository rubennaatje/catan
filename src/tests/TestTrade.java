package tests;

import controller.DatabaseManager;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import model.PlayerType;
import model.PlayerUser;

public class TestTrade extends Application {

	public static void main(String[] args) throws Exception {
		DatabaseManager.connect();
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		PlayerUser player = new PlayerUser("lesley", "770", PlayerType.BLAUW);
//        TradeController some = new TradeController(player, "770");
        primaryStage.setOnCloseRequest(e -> Platform.exit());
	}
}
