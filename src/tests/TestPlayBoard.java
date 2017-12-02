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
import model.PlayerModel;
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
		PlayerModel white = new PlayerModel(PlayerType.WIT, "bart", null);
		PlayerModel orange = new PlayerModel(PlayerType.ORANJE, "rik", null);
		PlayerModel blue = new PlayerModel(PlayerType.BLAUW, "lesley", null);
		PlayerModel red = new PlayerModel(PlayerType.ROOD, "ger", null);
		
		Board board = new Board();
		
		board.getLongestRoad(orange, "770");
		
	}

}
