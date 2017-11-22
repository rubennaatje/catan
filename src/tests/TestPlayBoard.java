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
import model.Location;

public class TestPlayBoard extends Application {

	public static void main(String[] args) throws Exception {
		
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		Board demo = new Board();
		
		PlayBoardView playView = new PlayBoardView(primaryStage);
		PlayerView playerView = new PlayerView(primaryStage);
		ChatView chat = new ChatView(primaryStage);
		
		GameMergeView gameView = new GameMergeView(chat, playerView, primaryStage);
		
		gameView.show();
		
		
	}

}
