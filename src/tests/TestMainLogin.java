package tests;

import view.*;

import com.sun.javafx.geom.Shape;

import controller.CatanController;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.DevelopmentCard;
import model.Dice;

public class TestMainLogin extends Application {

	public static void main(String[] args) throws Exception {
		
		launch(args);
		
		Dice dice = new Dice(771);
		dice.throwDice();
		DevelopmentCard test = new DevelopmentCard();
		//System.out.println(test.getType());
		System.out.println("testsetseet");
	}

	@Override
	public void start(Stage stage) throws Exception {
		
		CatanController controller = new CatanController(stage);
		controller.openLoginScreen();
	}

}
