package controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import model.DevelopmentCard;
import model.KnightCard;
import model.PlayerUser;
import model.ProgressCard;
import model.VictoryPointCard;

public class DevelopCardController {
	private Random random = new Random();
	private int cardint;
	private String cardId;
	private String kaarttype;
	private String kaartnaam;
	private int index = 1;
	private String spelId;
	private String uitleg;
	private String username;
	private ArrayList<DevelopmentCard> list = new ArrayList<>();
	
	public DevelopCardController(String spelId, String username) {
		this.spelId = spelId;
		this.username = username;
	}
	
	public void givePlayerCard() {
			ArrayList<String> cardsUnUsed = new ArrayList<>();
			try { // create list of unused cards
				ResultSet cardsNotUsed = DatabaseManager.createStatement().executeQuery("SELECT idontwikkelingskaart FROM spelerontwikkelingskaart WHERE username IS NULL AND idspel = "+ spelId +";");
				while(cardsNotUsed.next()) {
					cardsUnUsed.add(cardsNotUsed.getString("idontwikkelingskaart"));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			cardint = random.nextInt(cardsUnUsed.size()); 
			cardId = cardsUnUsed.get(cardint);
			cardsUnUsed.remove(cardint);
			index = 1;
			assignCard(cardId);
	}
	
	
	public void refreshDevCards() { // creates devcards objects for player
		int listsize = list.size();
		for(int x = 0; x < listsize; x++) {
			list.remove(0);
		}
		try {
			ResultSet cardsUsed = DatabaseManager.createStatement().executeQuery("SELECT idontwikkelingskaart FROM spelerontwikkelingskaart WHERE username = '"+username+"'"
					+ " AND idspel = "+ spelId +" AND gespeeld = 0;");
			while(cardsUsed.next()) {
				assignCard(cardsUsed.getString("idontwikkelingskaart"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
		
	public ArrayList<DevelopmentCard> getPlayerCards(){ //returns arraylist of playercards
		return list;
	}
	
	private void assignCard(String cardId) { //method to assign devcards to player
		try {
			ResultSet kaarttypes = DatabaseManager.createStatement().executeQuery("SELECT o.naam, k.type, k.uitleg FROM ontwikkelingskaart o "
					+ "JOIN kaarttype k ON o.naam = k.naam WHERE o.idontwikkelingskaart = '"+ cardId+ "';");
				if(kaarttypes.first()) {
				kaartnaam = kaarttypes.getString(1);
				kaarttype = kaarttypes.getString(2);
				uitleg = kaarttypes.getString(3);
				System.out.println(kaartnaam);
//				System.out.println(kaarttype);
//				System.out.println(uitleg);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}

				switch(kaarttype) { //create card object
				case "ridder":
					list.add(new KnightCard(spelId , cardId, username, kaarttype, kaartnaam));
					break;
				case "gebouw":
					list.add(new ProgressCard(spelId, cardId, username, kaarttype, kaartnaam, uitleg));
					break;
				case "vooruitgang":
					list.add(new VictoryPointCard(spelId, cardId, username, kaarttype, kaartnaam));
					break;
				}
				
	}
	

	public void playCard(int index) {
		
		if(list.get(index) instanceof ProgressCard) {
			System.out.println("progresscard");
			((ProgressCard) list.get(index)).playCard();
		} else if(list.get(index) instanceof KnightCard) {
			System.out.println("knightcard");
			((KnightCard) list.get(index)).playCard();
		} else if(list.get(index) instanceof VictoryPointCard) {
			System.out.println("VictoryPointCard");
			((VictoryPointCard) list.get(index)).playCard();
		}
		System.out.println("kaart verwijderd");
		list.remove(index);
		refreshDevCards();

	}

}
