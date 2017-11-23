package view;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class GameMergeView extends Pane {
	ChatView playBoard;
	PlayerView players;
	Stage stage;
	
	public GameMergeView(ChatView playBoard, PlayerView players, Stage stage) {
		this.playBoard = playBoard;
		this.players = players;
		this.stage = stage;
		
		getChildren().add(playBoard);
		getChildren().add(players);
	}
	
	public void show() {
		Scene scene = new Scene(this);
		scene.getStylesheets().add(getClass().getResource("/view/style/application.css").toExternalForm());
		stage.setScene(scene);
		stage.centerOnScreen();
		stage.show();
	}
}
