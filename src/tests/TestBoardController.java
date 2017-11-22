package tests;

import controller.BoardController;
import javafx.application.Application;
import javafx.stage.Stage;

public class TestBoardController extends Application{

	public static void main(String[] args) throws Exception {
		
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		System.out.println("rub");
		
		BoardController boardController = new BoardController();
		
		boardController.start(primaryStage);
		

	}
}
