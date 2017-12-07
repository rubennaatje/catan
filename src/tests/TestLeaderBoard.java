package tests;

import view.*;
import controller.CatanController;
import javafx.application.Application;
import javafx.stage.Stage;
import model.ScoreBoard;

public class TestLeaderBoard extends Application{

	public static void main(String[] args) throws Exception {

		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		CatanController controller = new CatanController(stage);
		controller.openLeaderboardScreen();	
	}

}
