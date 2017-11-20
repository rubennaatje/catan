package tests;

import java.awt.Point;
import view.*;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.Location;
import view.LoginView;

public class TestClassPlayboard extends Application{

	public static void main(String[] args) throws Exception {

		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		PlayBoardView playboardview = new PlayBoardView(primaryStage);
		
		
		Location locs = new Location(1000);

		
		playboardview.addHex(locs.getHexEdges(2,4), locs.getCoordinate(2,4), 0, Color.GREEN);
		playboardview.addHex(locs.getHexEdges(3,6), locs.getCoordinate(3,6), 0, Color.RED);
		playboardview.addHex(locs.getHexEdges(4,8), locs.getCoordinate(4,8), 0, Color.BROWN);
		playboardview.addHex(locs.getHexEdges(3,3), locs.getCoordinate(3,3), 0, Color.PINK);
		playboardview.addHex(locs.getHexEdges(4,5), locs.getCoordinate(4,5), 0, Color.GREEN);
		playboardview.addHex(locs.getHexEdges(5,7), locs.getCoordinate(5,7), 0, Color.GREEN);
		playboardview.addHex(locs.getHexEdges(6,9), locs.getCoordinate(6,9), 0, Color.GREEN);
		playboardview.addHex(locs.getHexEdges(4,2), locs.getCoordinate(4,2), 0, Color.GREEN);
		playboardview.addHex(locs.getHexEdges(5,4), locs.getCoordinate(5,4), 0, Color.GREEN);
		playboardview.addHex(locs.getHexEdges(6,6), locs.getCoordinate(6,6), 0, Color.GREEN);
		playboardview.addHex(locs.getHexEdges(7,8), locs.getCoordinate(7,8), 3, Color.GREEN);
		playboardview.addHex(locs.getHexEdges(8,10), locs.getCoordinate(8,10), 3, Color.GREEN);
		playboardview.addHex(locs.getHexEdges(6,3), locs.getCoordinate(6,3), 9, Color.GREEN);
		playboardview.addHex(locs.getHexEdges(7,5), locs.getCoordinate(7,5), 3, Color.GREEN);
		playboardview.addHex(locs.getHexEdges(8,7), locs.getCoordinate(8,7), 2, Color.GREEN);
		playboardview.addHex(locs.getHexEdges(9,9), locs.getCoordinate(9,9), 1, Color.GREEN);
		playboardview.addHex(locs.getHexEdges(8,4), locs.getCoordinate(8,4), 9, Color.GREEN);
		playboardview.addHex(locs.getHexEdges(9,6), locs.getCoordinate(9,6), 2, Color.GREEN);
		playboardview.addHex(locs.getHexEdges(10,8), locs.getCoordinate(10,8), 0, Color.GREEN);
		
		playboardview.addStreet(locs.getCoordinate(4,4), locs.getCoordinate(4,3), Color.YELLOW);
		playboardview.addStreet(locs.getCoordinate(5,3), locs.getCoordinate(6,4), Color.GREEN);
		
		playboardview.addPiece(locs.getCoordinate(2, 5), Color.YELLOW);
		playboardview.addRobber(locs.getCoordinate(6,6));
		
		playboardview.show();
	//new ChallengeView(primaryStage).show();
		//new PlayerView(primaryStage).show();
		//new SplashScreenView(primaryStage).show();
		
		
	}

}
