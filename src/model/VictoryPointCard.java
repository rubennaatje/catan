package model;

import java.sql.SQLException;

import controller.DatabaseManager;

public class VictoryPointCard extends DevelopmentCard { //deze kaart geeft 1 victory point
	
	public VictoryPointCard(String spelid, String kaartid, String username, String kaarttype, String kaartnaam) {
		super(spelid, kaartid, username, kaarttype, kaartnaam);
		// TODO Auto-generated constructor stub
	}
	
	public void playCard() {
		System.out.println("ontwikkeling");
		super.updateDatabase(); //ontwikkelingspunten worden geteld zodra deze kaart gespeeld is
	}
	
}
