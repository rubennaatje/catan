
package tests;

import controller.StealResourceController;
import javafx.stage.Stage;
import javafx.application.Application;

public class TestWin extends Application {
	
	public static void main(String[] args) throws Exception {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		StealResourceController controller = new StealResourceController();
		controller.showResources();
	}
}
