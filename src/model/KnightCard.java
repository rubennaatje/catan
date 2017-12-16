package model;

import controller.DevelopCardController;

public class KnightCard extends DevelopmentCard { // met deze kaart kun je de ridder verplaatsen

	private DevelopCardController controller;

	
	public KnightCard(PlayerUser player, String kaartid, String kaarttype, String kaartnaam, DevelopCardController developCardController) {
		super(player, kaartid, kaarttype, kaartnaam);
		this.controller = developCardController;
	}
	
	public void playCard() {
		System.out.println("ridder");
		controller.moveRobber();
		
		super.updateDatabase();
	}
	
}
