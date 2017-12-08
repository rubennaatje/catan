package tests;

import javafx.application.Application;
import javafx.stage.Stage;
import view.CardView;

public class TestCard extends Application {

	public static void main(String[] args) throws Exception {
		launch(args);
		
	}

	@Override
	public void start(Stage primaryStage) throws Exception {

		// Load Cardview
		CardView cv = new CardView(primaryStage);
		cv.show();
	}
}
