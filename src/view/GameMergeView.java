package view;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class GameMergeView extends Pane {
	PlayBoardView playBoard;
	Stage stage;

	GameControlerView buttons;
	
	public GameMergeView(PlayBoardView playBoard, GameControlerView buttons, Stage stage, PlayerView[] players, ResourceView resourceView, DiceView dice, ChatView chatView, CardView cardView) {
		this.stage = stage;
		this.playBoard = playBoard;
		this.buttons = buttons;
		
		getChildren().add(playBoard);
		
		buttons.setLayoutX(225.0);
		getChildren().add(buttons);

		players[0].setLayoutX(0);
		players[0].setLayoutY(0);

		players[1].setLayoutX(0);
		players[1].setLayoutY(playBoard.getPrefHeight() - 180.0);
		

		players[2].setLayoutX(playBoard.getPrefWidth() - 225.0);
		players[2].setLayoutY(0);
		

		players[3].setLayoutX(playBoard.getPrefWidth() - 225.0);
		players[3].setLayoutY(playBoard.getPrefHeight() - 180.0);
		
		for (int i = 0; i < players.length; i++) {
			getChildren().add(players[i]);
		}
		
		
		resourceView.setLayoutX((playBoard.getPrefWidth() / 2) - (resourceView.getPrefWidth() /2));
		resourceView.setLayoutY(playBoard.getPrefHeight() - (resourceView.getPrefHeight()/2));
		getChildren().add(resourceView);

		

		dice.setLayoutX(10);
		dice.setLayoutY((playBoard.getPrefHeight() / 2) - (dice.getPrefHeight() / 2) );
		getChildren().add(dice);
		
		chatView.setLayoutX(playBoard.getPrefWidth());
		getChildren().add(chatView);
		
		setPrefHeight(playBoard.getPrefHeight());
		setWidth(playBoard.getPrefWidth() + chatView.getPrefWidth());
		
		cardView.setLayoutX(playBoard.getLayoutX()+playBoard.getPrefWidth());
		cardView.setLayoutY(chatView.getLayoutY()+ 400);
		getChildren().add(cardView);
		
		
	}
	
	
	public void show() {
		Scene scene = new Scene(this);
		scene.getStylesheets().add(getClass().getResource("/view/style/application.css").toExternalForm());
		stage.setScene(scene);
		stage.centerOnScreen();
		stage.show();
	}
	
}
