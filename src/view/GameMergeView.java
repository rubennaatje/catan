package view;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class GameMergeView extends Pane {
	PlayBoardView playBoard;

	GameControlerView buttons;
	
	public GameMergeView(PlayBoardView playBoard, GameControlerView buttons) {

		this.playBoard = playBoard;
		this.buttons = buttons;
		
		getChildren().add(playBoard);
		getChildren().add(buttons);
		
		setPrefWidth(playBoard.getPrefWidth());
	}
	

}
