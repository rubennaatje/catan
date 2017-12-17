package controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;

import model.DevelopmentCard;
import model.KnightCard;
import model.PlayerUser;
import model.ProgressCard;
import model.TileType;
import model.VictoryPointCard;
import view.CardView;

public class DevelopCardController {
	private Random random = new Random();
	private ArrayList<DevelopmentCard> list = new ArrayList<>();
	private GameController superController;
	private PlayerUser player;
	private TileType t;
	
	
	public DevelopCardController(PlayerUser player, GameController controller) {
		this.player = player;
		this.superController = controller;
		
	}

	public void givePlayerCard() {
		int cardint;
		String cardId;
		ArrayList<String> cardsUnUsed = new ArrayList<>();
		try { // create list of unused cards
			ResultSet cardsNotUsed = DatabaseManager.createStatement().executeQuery(
					"SELECT idontwikkelingskaart FROM spelerontwikkelingskaart WHERE username IS NULL AND idspel = "
							+ player.getSpelId() + ";");
			while (cardsNotUsed.next()) {
				cardsUnUsed.add(cardsNotUsed.getString("idontwikkelingskaart"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		cardint = random.nextInt(cardsUnUsed.size());
		cardId = cardsUnUsed.get(cardint);
		cardsUnUsed.remove(cardint);
		assignCard(cardId);
	}

	public void refreshDevCards() { // creates devcards objects for player
		list = new ArrayList<>();
		try {
			ResultSet cardsUsed = DatabaseManager.createStatement()
					.executeQuery("SELECT idontwikkelingskaart FROM spelerontwikkelingskaart WHERE username = '"
							 + player.getUsername() + "'   AND idspel = " + player.getSpelId() + " AND gespeeld = 0;");
			while (cardsUsed.next()) {
				//assignCard(cardsUsed.getString("idontwikkelingskaart"));
				assignCard(cardsUsed.getString(1));

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		superController.setDevCards(setCardView());
	}

	public ArrayList<String> setCardView() { // returns arraylist of playercards
	ArrayList<String> cards = new ArrayList<String>();
		for(int x =0 ; x < list.size(); x++)
		{
			String temp = list.get(x).getCardname();
			cards.add(temp);

		}
		return cards;
		
	}

	private void assignCard(String cardId) { // method to assign devcards to player
		try {
			ResultSet kaarttypes = DatabaseManager.createStatement()
					.executeQuery("SELECT o.naam, k.type, k.uitleg FROM ontwikkelingskaart o "
							+ "JOIN kaarttype k ON o.naam = k.naam WHERE o.idontwikkelingskaart = '" + cardId + "';");
			if (kaarttypes.first()) {
				String kaartnaam = kaarttypes.getString(1);
				String kaarttype = kaarttypes.getString(2);
				String uitleg = kaarttypes.getString(3);

				switch (kaarttype) { // create card object
				case "ridder":
					list.add(new KnightCard(player, cardId, kaarttype, kaartnaam, this));
					break;
				case "vooruitgang":
					list.add(new ProgressCard(player, cardId, kaarttype, kaartnaam, uitleg, this));
					break;
				case "gebouw":
					list.add(new VictoryPointCard(player, cardId, kaarttype, kaartnaam));
					break;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
	
	public boolean checkForResource(int index) { //checks if devcard needs resourcetype
		if(list.get(index) instanceof ProgressCard) {
			if(!((ProgressCard) list.get(index)).getCardname().equals("stratenbouw")) {
				return true;
			}	
		}
		return false;
	}
	
	public void setResourceType(int index, String resource) { // assigns resourcetype to progresscard when needed
		switch(resource) {
			case("baksteen"):
				t = TileType.valueOf("B");
			((ProgressCard) list.get(index)).setResourceType(t);
			break;
			case("hout"):
				t = TileType.valueOf("H");
			((ProgressCard) list.get(index)).setResourceType(t);
			break;
			case("graan"):
				t = TileType.valueOf("G");
			((ProgressCard) list.get(index)).setResourceType(t);
			break;
			case("wol"):
				t = TileType.valueOf("W");
			((ProgressCard) list.get(index)).setResourceType(t);
			break;
			case("erts"):
				t = TileType.valueOf("E");
			((ProgressCard) list.get(index)).setResourceType(t);
			break;
		}
		
		
	}
	
	
	public void playCard(int index) { //plays card
		
		if (list.get(index) instanceof ProgressCard) {
			((ProgressCard) list.get(index)).playCard();
		} else if (list.get(index) instanceof KnightCard) {
			((KnightCard) list.get(index)).playCard();
		} else if(list.get(index) instanceof VictoryPointCard) {
			((VictoryPointCard) list.get(index)).playCard();
		}

		refreshDevCards();

	}

	public void place2Streets() {  //plaatst 2 straten
		superController.disableButtons();
		superController.showDoubleStreetPlacable();
	}

	public void moveRobber() { // beweegt robber
		superController.disableButtons();
		superController.showRobberPlacable();
	}

}
