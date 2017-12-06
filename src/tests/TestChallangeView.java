package tests;

import controller.CatanController;
import javafx.application.Application;
import javafx.stage.Stage;

public class TestChallangeView extends Application {

	public static void main(String[] args) throws Exception {
		
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		CatanController controller = new CatanController(stage);
		controller.openChallengeScreen();
	}

}
