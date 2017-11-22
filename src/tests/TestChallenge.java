package tests;

import java.awt.Point;
import view.*;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.ChallengeData;
import model.Challenges;
import model.Location;
import view.LoginView;

public class TestChallenge extends Application{

	public static void main(String[] args) throws Exception {

		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		ChallengeData model = new ChallengeData();
		ChallengeView view = new ChallengeView(primaryStage);


		ObservableList<Challenges> gegevens = model.fillChallenges();
		view.addBoard(gegevens);
		view.show();
		
	}

}

