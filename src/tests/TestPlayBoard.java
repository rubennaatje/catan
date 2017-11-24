package tests;

import view.*;

import java.awt.Point;
import java.util.ArrayList;

import com.sun.javafx.geom.Shape;

import controller.DatabaseManager;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.Board;
import model.LocationGenerator;
import model.Player;
import model.PlayerType;
import model.Street;
import model.Tile;

public class TestPlayBoard extends Application {

	public static void main(String[] args) throws Exception {
		DatabaseManager.connect();
		
		Runtime.getRuntime().addShutdownHook(new Thread(() -> {
			DatabaseManager.disconnect();
			System.out.println("databasemanager shut down");
		}));
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		Player white = new Player(PlayerType.WIT, "bart");
		Player orange = new Player(PlayerType.ORANJE, "rik");
		Player blue = new Player(PlayerType.BLAUW, "lesley");
		Player red = new Player(PlayerType.ROOD, "ger");
		
		Board board = new Board();
		
		board.getLongestRoad(orange, "770");
		
	}

}
