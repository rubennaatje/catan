package tests;

import controller.DatabaseManager;
import model.DevelopmentCard;
import model.Dice;

public class Testdevelopmentcard {

	public Testdevelopmentcard() {
		
	}

	public static void main(String[] args) {
		DatabaseManager.connect();
		
		Runtime.getRuntime().addShutdownHook(new Thread(() -> {
			DatabaseManager.disconnect();
			System.out.println("databasemanager shut down");
		}));
		//launch(args);
		Dice dice = new Dice(771);
		dice.throwDice();
		DevelopmentCard test = new DevelopmentCard();
		System.out.println(test.getType());
		System.out.println("testsetseet");
	}

}
