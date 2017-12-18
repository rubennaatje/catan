
package tests;

import controller.CatanController;
import controller.StealFromPlayerController;
import javafx.application.Application;
import javafx.stage.Stage;
import model.PlayerModel;

public class TestWin extends Application {
	
	
	private CatanController Catancontroller; 
	
	public static void main(String[] args) throws Exception {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		
		this.Catancontroller = Catancontroller; 
		
		PlayerModel[] players = {new PlayerModel("bart", null), new PlayerModel("lesley", null), new PlayerModel("ger", null)};
		StealFromPlayerController controller = new StealFromPlayerController(null, players);

		controller.showPlayers();
	}
}
