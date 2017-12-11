<<<<<<< HEAD

=======

>>>>>>> 6121dda09112b9f75045c17d50a316839b4767bc
package tests;

import controller.CatanController;
import javafx.application.Application;
import javafx.stage.Stage;

public class TestMainLogin extends Application {

	public static void main(String[] args) throws Exception {
		
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		CatanController controller = new CatanController(stage);
		controller.openLoginScreen();
	}

<<<<<<< HEAD
}

=======
}
>>>>>>> 6121dda09112b9f75045c17d50a316839b4767bc
