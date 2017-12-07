package tests;

import controller.DatabaseManager;
import controller.DevelopCardController;
import model.DevelopmentCard;
import model.Dice;
import model.PlayerUser;

public class Testdevelopmentcard {

	public Testdevelopmentcard() {
		
	}

	public static void main(String[] args) throws Exception {
		DatabaseManager.connect();
		
		Runtime.getRuntime().addShutdownHook(new Thread(() -> {
			DatabaseManager.disconnect();
			System.out.println("databasemanager shut down");
		}));
		
		PlayerUser player = new PlayerUser("bart", "770");
		DevelopCardController control = new DevelopCardController("770");
		control.givePlayerCard("rik");
	
	}

}
