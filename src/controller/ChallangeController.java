package controller;

import javafx.collections.ObservableList;
import model.ChallengeData;
import model.Challenges;
import view.ChallengeView;

public class ChallangeController {

	public static void main(String[] args) {
		ChallengeData model = new ChallengeData();
		ChallengeView view = new ChallengeView(null);
		
		
		ObservableList<Challenges> gegevens = model.fillChallenges();
		view.addBoard(gegevens);
	}
}
