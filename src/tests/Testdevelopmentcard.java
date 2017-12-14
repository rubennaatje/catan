package tests;

import java.util.HashMap;

import controller.CatanController;
import controller.DatabaseManager;
import controller.DevelopCardController;
import controller.GameController;
import model.DevelopmentCard;
import model.Dice;
import model.KnightCard;
import model.PlayerUser;
import model.ProgressCard;
import model.VictoryPointCard;
import view.CardView;

public class Testdevelopmentcard {

	public Testdevelopmentcard() {
		
	}

	public static void main(String[] args) throws Exception {
		DatabaseManager.connect();
		
		Runtime.getRuntime().addShutdownHook(new Thread(() -> {
			DatabaseManager.disconnect();
			System.out.println("databasemanager shut down");
		}));
//		PlayerUser player = new PlayerUser("rik", "770");
//		CatanController catan = new CatanController(null);
//		GameController controller = new GameController("770", );
//		DevelopCardController control = new DevelopCardController(player, );
//		control.refreshDevCards();
//		System.out.println("---------------------------------------------");
//		System.out.println(control.getPlayerCards().size());
//		control.getPlayerCards().get(2).getCardId();
//		control.playCard(2);
//		System.out.println(control.getPlayerCards().size());
		
		
		
	
//		String kaarten[] = null;
//		for(int x = 0; x < control.getPlayerCards().size(); x++) {
//			kaarten[x] = control.getPlayerCards().get(x).getCardname();
			//System.out.println(control.getPlayerCards().get(x).getCardId());
			
		}
	}


