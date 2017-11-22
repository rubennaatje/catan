package tests;

import view.*;
import javafx.application.Application;
import javafx.stage.Stage;
import model.ScoreBoard;

public class TestLeaderBoard extends Application{

	public static void main(String[] args) throws Exception {

		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		LeaderBoardView leaderBoardView = new LeaderBoardView(primaryStage);		

		ScoreBoard board = new ScoreBoard();
		
		leaderBoardView.addBoard(board.fillLeaderboard());
		leaderBoardView.show();
	}

}
