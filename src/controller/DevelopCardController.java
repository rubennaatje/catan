package controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;

public class DevelopCardController {
	private Random random = new Random();
	private int cardint;
	private ArrayList<Integer> cardsused = new ArrayList<>(); //list of drawn cards
	private String cardId;
	public DevelopCardController() {
	
	}
	
	public void givePlayerCard(String username) {
		boolean gavecard = false;
		while(!gavecard) {
		cardint = random.nextInt(25) + 1;
		if(!cardsused.contains(cardint)) { // check if card is already drawn
			cardsused.add(cardint);
			if(cardint < 10 ) { //create cardid
				cardId = "o0" + cardint;
			} else if( cardint >= 10) {
				cardId = "o" + cardint;
			}
			try {
			ResultSet kaarttypes = DatabaseManager.getStatement().executeQuery("SELECT naam FROM `ontwikkelingskaart` WHERE `idontwikkelingskaart` ='"+ cardId + "';");
				if(kaarttypes.first()) {
				String kaarttype = kaarttypes.getString(cardId);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			//create developmentcard object and assign to player
			gavecard = true; //cancel loop if card is given
			}
		}
	}
	


}
