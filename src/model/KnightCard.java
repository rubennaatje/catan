package model;

import controller.DevelopCardController;

public class KnightCard extends DevelopmentCard { // met deze kaart kun je de ridder verplaatsen

	private DevelopCardController controller;

	
	public KnightCard(String spelid, String kaartid, String username, String kaarttype, String kaartnaam, DevelopCardController developCardController) {
		super(spelid, kaartid, username, kaarttype, kaartnaam);
		this.controller = developCardController;
	}
	
	public void playCard() {
		System.out.println("ridder");
		controller.moveRobber();
		super.updateDatabase();
	}
	
}
