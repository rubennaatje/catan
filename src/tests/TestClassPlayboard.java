package tests;

import java.awt.Point;
import view.*;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
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
		System.out.println(locs.getCoordinate(1, 10));
		
/*		for (int i = 0; i < arff.length; i++) {
			for (int j = 0; j < arff[i].length; j++) {
				System.out.println("x=" + i+ "|" + "y=" + j + arff[i][j].toString());				
			}
		}*/
		Point[] points = {locs.getCoordinate(3, 1),locs.getCoordinate(4, 1),locs.getCoordinate(5, 2),locs.getCoordinate(5, 3),locs.getCoordinate(4, 3),locs.getCoordinate(3, 2)};
		
		
		
		playboardview.addHex(locs.getHexEdges(2,4), locs.getCoordinate(2,4), 0);
		playboardview.addHex(locs.getHexEdges(3,6), locs.getCoordinate(3,6), 0);
		playboardview.addHex(locs.getHexEdges(4,8), locs.getCoordinate(4,8), 0);
		playboardview.addHex(locs.getHexEdges(3,3), locs.getCoordinate(3,3), 0);
		playboardview.addHex(locs.getHexEdges(4,5), locs.getCoordinate(4,5), 0);
		playboardview.addHex(locs.getHexEdges(5,7), locs.getCoordinate(5,7), 0);
		playboardview.addHex(locs.getHexEdges(6,9), locs.getCoordinate(6,9), 0);
		playboardview.addHex(locs.getHexEdges(4,2), locs.getCoordinate(4,2), 0);
		playboardview.addHex(locs.getHexEdges(5,4), locs.getCoordinate(5,4), 0);
		playboardview.addHex(locs.getHexEdges(6,6), locs.getCoordinate(6,6), 0);
		playboardview.addHex(locs.getHexEdges(7,8), locs.getCoordinate(7,8), 3);
		playboardview.addHex(locs.getHexEdges(8,10), locs.getCoordinate(8,10), 3);
		playboardview.addHex(locs.getHexEdges(6,3), locs.getCoordinate(6,3), 9);
		playboardview.addHex(locs.getHexEdges(7,5), locs.getCoordinate(7,5), 3);
		playboardview.addHex(locs.getHexEdges(8,7), locs.getCoordinate(8,7), 2);
		playboardview.addHex(locs.getHexEdges(9,9), locs.getCoordinate(9,9), 1);
		playboardview.addHex(locs.getHexEdges(8,4), locs.getCoordinate(8,4), 9);
		playboardview.addHex(locs.getHexEdges(9,6), locs.getCoordinate(9,6), 2);
		playboardview.addHex(locs.getHexEdges(10,8), locs.getCoordinate(10,8), 0);
		Scene scene = new Scene(playboardview);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

}
