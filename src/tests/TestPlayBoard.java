package tests;

import view.*;

import java.awt.Point;
import java.util.ArrayList;

import com.sun.javafx.geom.Shape;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.Board;

public class TestPlayBoard extends Application {

	public static void main(String[] args) throws Exception {
		
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		Board demo = new Board();

		ArrayList<Point[]> tempvar = demo.getEmptyPiecePos("");

		
		
	}

}
