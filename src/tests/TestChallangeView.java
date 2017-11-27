package tests;

import javafx.application.Application;
import javafx.stage.Stage;
import view.ChallengeView;

public class TestChallangeView extends Application {

	public static void main(String[] args) throws Exception {
		
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		System.out.println("geordiiiii");
		
		ChallengeView demo = new ChallengeView(primaryStage);
		
		demo.show();
	}

}
