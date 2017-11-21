package tests;

import view.*;
import javafx.application.Application;
import javafx.stage.Stage;
import model.Location;

public class TestClassPlayboard extends Application{

	public static void main(String[] args) throws Exception {

		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {

		PlayBoardView playboardview = new PlayBoardView(primaryStage);

		
		
		Location locs = new Location(1000);

		
		playboardview.addHex(locs.getHexEdges(2,4), locs.getCoordinate(2,4), 0, "hout");
		playboardview.addHex(locs.getHexEdges(3,6), locs.getCoordinate(3,6), 0, "hout");
		playboardview.addHex(locs.getHexEdges(4,8), locs.getCoordinate(4,8), 0, "wol");
		playboardview.addHex(locs.getHexEdges(3,3), locs.getCoordinate(3,3), 0, "graan");
		playboardview.addHex(locs.getHexEdges(4,5), locs.getCoordinate(4,5), 0, "baksteen");
		playboardview.addHex(locs.getHexEdges(5,7), locs.getCoordinate(5,7), 0, "baksteen");
		playboardview.addHex(locs.getHexEdges(6,9), locs.getCoordinate(6,9), 0, "erts");
		playboardview.addHex(locs.getHexEdges(4,2), locs.getCoordinate(4,2), 0, "baksteen");
		playboardview.addHex(locs.getHexEdges(5,4), locs.getCoordinate(5,4), 0, "erts");
		playboardview.addHex(locs.getHexEdges(6,6), locs.getCoordinate(6,6), 0, "woestijn");
		playboardview.addHex(locs.getHexEdges(7,8), locs.getCoordinate(7,8), 3, "wol");
		playboardview.addHex(locs.getHexEdges(8,10), locs.getCoordinate(8,10), 3, "hout");
		playboardview.addHex(locs.getHexEdges(6,3), locs.getCoordinate(6,3), 9, "graan");
		playboardview.addHex(locs.getHexEdges(7,5), locs.getCoordinate(7,5), 3, "baksteen");
		playboardview.addHex(locs.getHexEdges(8,7), locs.getCoordinate(8,7), 2, "graan");
		playboardview.addHex(locs.getHexEdges(9,9), locs.getCoordinate(9,9), 1, "wol");
		playboardview.addHex(locs.getHexEdges(8,4), locs.getCoordinate(8,4), 9, "hout");
		playboardview.addHex(locs.getHexEdges(9,6), locs.getCoordinate(9,6), 2, "wol");
		playboardview.addHex(locs.getHexEdges(10,8), locs.getCoordinate(10,8), 0, "baksteen");
		
		playboardview.addStreet(locs.getCoordinate(4,4), locs.getCoordinate(4,3), "player_blue", false);
		playboardview.addStreet(locs.getCoordinate(5,3), locs.getCoordinate(6,4), "player_blue", false);
		
		playboardview.addPiece(locs.getCoordinate(2, 5), "player_blue", true);
		playboardview.addRobber(locs.getCoordinate(6,6));
		
		playboardview.show();
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
