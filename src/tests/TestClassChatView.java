package tests;

import controller.ChatController;
import controller.DatabaseManager;
import javafx.application.Application;
import javafx.stage.Stage;
import model.Catan;
import model.PlayerType;
import model.PlayerUser;

public class TestClassChatView extends Application{

	public static void main(String[] args) throws Exception {

		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		
		
		PlayerUser player = new PlayerUser("ger", Catan.getGameId(), PlayerType.BLAUW);
		DatabaseManager.connect();
		

		Runnable chat = new ChatController(player, "770");
		
		Thread p = new Thread(chat);
		p.start();
	}

}
