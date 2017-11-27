package tests;

import view.*;

import com.sun.javafx.geom.Shape;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class TestChallangeView extends Application {

	public static void main(String[] args) throws Exception {
		
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		System.out.println("geordiiiii");
		
		ChallangeViewDemo demo = new ChallangeViewDemo(primaryStage);
		
		demo.show();
	}

}
