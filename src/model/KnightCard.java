package model;

import java.sql.SQLException;

import controller.DatabaseManager;

public class KnightCard extends DevelopmentCard { // met deze kaart kun je de ridder verplaatsen

	public KnightCard(String spelid, String kaartid, String username, String kaarttype, String kaartnaam) {
		super(spelid, kaartid, username, kaarttype, kaartnaam);
		// TODO Auto-generated constructor stub
	}
	
	public void playCard() {
		System.out.println("ridder");
		super.updateDatabase();
		
	}

}
