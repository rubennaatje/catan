package view;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class GameMergeView extends Pane {
	PlayBoardView playBoard;
	Stage stage;

	GameControlerView buttons;
	
	public GameMergeView(PlayBoardView playBoard, GameControlerView buttons, Stage stage, PlayerView[] players) {

		this.stage = stage;
		this.playBoard = playBoard;
		this.buttons = buttons;
		
		getChildren().add(playBoard);
		getChildren().add(buttons);
		for (int i = 0; i < players.length; i++) {
			players[i].setLayoutX(100 * i);
			players[i].setLayoutY(100);
			getChildren().add(players[i]);
			
		}
		
		setPrefWidth(playBoard.getPrefWidth());
	}
	
	@Deprecated
	public void show() {
		Scene scene = new Scene(this);
		scene.getStylesheets().add(getClass().getResource("/view/style/application.css").toExternalForm());
		stage.setScene(scene);
		stage.centerOnScreen();
		stage.show();
	}
	
}
