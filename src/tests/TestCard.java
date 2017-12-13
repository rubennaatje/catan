package tests;

import controller.DevelopCardController;
import javafx.application.Application;
import javafx.stage.Stage;
import view.CardView;

public class TestCard extends Application {

	public static void main(String[] args) throws Exception {
		launch(args);
		
	}

	@Override
	public void start(Stage primaryStage) throws Exception {

		/*DevelopCardController controller = new DevelopCardController("1");  //example spelid
		DevelopCardController control = controller.getCardControl(); //run method which get a card controller within the developcardcontroller.
*/		
		// Load Cardview
		CardView cv = new CardView(primaryStage);
		cv.show();
	}
}
