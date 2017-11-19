package tests;

import java.awt.Point;
import view.*;
import javafx.application.Application;
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
		playboardview.show();
		
		
		Location locs = new Location(primaryStage.getHeight(), primaryStage.getWidth());
		Point[][] arff = locs.getCoordinates();
		System.out.println(locs.getCoordinate(1, 10));
		
/*		for (int i = 0; i < arff.length; i++) {
			for (int j = 0; j < arff[i].length; j++) {
				System.out.println("x=" + i+ "|" + "y=" + j + arff[i][j].toString());				
			}
		}*/
		
		
	}

}
