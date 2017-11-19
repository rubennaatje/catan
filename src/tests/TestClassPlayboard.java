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
		
		
		Location locs = new Location(500);
		System.out.println(locs.getCoordinate(1, 10));
		
/*		for (int i = 0; i < arff.length; i++) {
			for (int j = 0; j < arff[i].length; j++) {
				System.out.println("x=" + i+ "|" + "y=" + j + arff[i][j].toString());				
			}
		}*/
		Point[] points = {locs.getCoordinate(3, 1),locs.getCoordinate(4, 1),locs.getCoordinate(5, 2),locs.getCoordinate(5, 3),locs.getCoordinate(4, 3),locs.getCoordinate(3, 2)};
		
		
		
		playboardview.addHex(locs.getHexEdges(2, 4), new Point(2,4));
		playboardview.addHex(locs.getHexEdges(3,6), new Point(3,6));
		playboardview.addHex(locs.getHexEdges(4,8), new Point(4,8));
		playboardview.addHex(locs.getHexEdges(3,3), new Point(3,3));
		playboardview.addHex(locs.getHexEdges(4,5), new Point(4,5));
		playboardview.addHex(locs.getHexEdges(5,7), new Point(5,7));
		playboardview.addHex(locs.getHexEdges(6,9), new Point(6,9));
		playboardview.addHex(locs.getHexEdges(4,2), new Point(4,2));
		playboardview.addHex(locs.getHexEdges(5,4), new Point(5,4));
		playboardview.addHex(locs.getHexEdges(6,6), new Point(6,6));
		playboardview.addHex(locs.getHexEdges(7,8), new Point(7,8));
		playboardview.addHex(locs.getHexEdges(8,10), new Point(8,10));
		playboardview.addHex(locs.getHexEdges(6,3), new Point(6,3));
		playboardview.addHex(locs.getHexEdges(7,5), new Point(7,5));
		playboardview.addHex(locs.getHexEdges(8,7), new Point(8,7));
		playboardview.addHex(locs.getHexEdges(9,9), new Point(9,9));
		playboardview.addHex(locs.getHexEdges(8,4), new Point(8,4));
		playboardview.addHex(locs.getHexEdges(9,6), new Point(9,6));
		playboardview.addHex(locs.getHexEdges(10,8), new Point(10,8));
		Scene scene = new Scene(playboardview);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

}
