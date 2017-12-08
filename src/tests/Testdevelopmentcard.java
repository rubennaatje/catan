package tests;

import java.util.HashMap;

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
		//control.givePlayerCard("lesley");
		control.refreshDevCards("bart");
		//System.out.println(control.getPlayerCards());
//		for (HashMap.Entry entry : control.getPlayerCards().entrySet())
//		{
//		     System.out.println(control.getPlayerCards().get(entry.getKey()).getCardname());
//			System.out.println("hoi");
//		}
		for(int x = 0; x < control.getPlayerCards().size(); x++) {
			System.out.println(control.getPlayerCards().get(x).getCardname());
			System.out.println(control.getPlayerCards().get(x).getCardId());
			
		}
		
	
	}

}
