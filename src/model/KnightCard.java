package model;

public class KnightCard extends DevelopmentCard { // met deze kaart kun je de ridder verplaatsen

	public KnightCard(String spelid, String kaartid, String username, String kaarttype, String kaartnaam) {
		super(spelid, kaartid, username, kaarttype, kaartnaam);
	}
	
	public void playCard() {
		System.out.println("ridder");
		super.updateDatabase();
	}

}
