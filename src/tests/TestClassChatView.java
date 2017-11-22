package tests;

import view.*;
import controller.DatabaseManager;
import javafx.application.Application;
import javafx.stage.Stage;

public class TestClassChatView extends Application{

	public static void main(String[] args) throws Exception {

		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		ChatView chatView = new ChatView(primaryStage);
		

		DatabaseManager.connect();
		chatView.show();
//		new ChallengeView(primaryStage).show();
//		new PlayerView(primaryStage).show();
//		new SplashScreenView(primaryStage).show();
//		
//		Board board = new Board();
//		DatabaseManager.connect();
//		board.createBoard(40);
//		DatabaseManager.disconnect();
	}

}
