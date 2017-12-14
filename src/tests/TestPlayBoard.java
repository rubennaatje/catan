
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
import model.DevelopmentCard;
import model.Dice;
import model.BoardHelper;
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
		
		Dice dice = new Dice(771);
		dice.throwDice();
		//DevelopmentCard test = new DevelopmentCard();
		//System.out.println(test.getType());
		System.out.println("testsetseet");
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
        PlayerModel white = new PlayerModel("bart", null);
        PlayerModel orange = new PlayerModel("rik", null);
        PlayerModel blue = new PlayerModel("lesley", null);
        PlayerModel red = new PlayerModel("ger", null);
		
		BoardHelper.getLongestRoad(orange, "770");
		
	}

}

