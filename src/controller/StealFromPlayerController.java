package controller;

import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.PickPlayer;
import model.PlayerModel;
import model.PlayerUser;
import view.PickPlayerView;
import view.SplashScreenView;

public class StealFromPlayerController {
	
	private PickPlayerView pickPlayerview;
	private Stage popUpPickPlayer;
	private GameController controller; 

	public StealFromPlayerController(GameController controller, PlayerModel[] playerModel) {
		
		this.controller = controller ;		
		popUpPickPlayer = new Stage();
		pickPlayerview = new PickPlayerView(this, controller, playerModel);

		Scene scenePickPlayers = new Scene(pickPlayerview);
		popUpPickPlayer.setScene(scenePickPlayers);
		scenePickPlayers.getStylesheets().add(getClass().getResource("/view/style/application.css").toExternalForm());

		popUpPickPlayer.setResizable(false);
		popUpPickPlayer.initStyle(StageStyle.UNDECORATED);
	}
	
	public void showPlayers() {
		popUpPickPlayer.show();
	}
	public void hidePlayers(PlayerModel playerModel) {
		popUpPickPlayer.hide();
		controller.registerSteal(playerModel); 
	}

}
