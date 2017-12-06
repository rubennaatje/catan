package view;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class GameMergeView extends Pane {
	PlayBoardView playBoard;
	Stage stage;

	GameControlerView buttons;
	
	public GameMergeView(PlayBoardView playBoard, GameControlerView buttons, Stage stage) {

		this.stage = stage;
		this.playBoard = playBoard;
		this.buttons = buttons;
		
		getChildren().add(playBoard);
		getChildren().add(buttons);

		
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
