package model;

import controller.DevelopCardController;

public class ProgressCard extends DevelopmentCard { // 2 grondstoffen naar keuze, 2 stratenbouwen of alle grondstoffen van 1 soort van alle spelers
	private String uitleg;
	private DevelopCardController controller;
	
	
	public ProgressCard(String spelid, String kaartid, String username, String kaarttype, String kaartnaam, String uitleg, DevelopCardController developCardController) {
		super(spelid, kaartid, username, kaarttype, kaartnaam);
		this.uitleg = uitleg;
		this.controller = developCardController;
		// TODO Auto-generated constructor stub
	}
	
	public void playCard() {
		System.out.println("vooruitgang");
		super.updateDatabase();
			
			switch(kaartnaam) {
			case("stratenbouw"):// bouw 2 straten
				controller.place2Streets();
			case("monopolie"): // kies grondstof, krijg alle grondstoffen van dit type die spelers bezitten
			
			case("uitvinding"): //2 grondstoffen kiezen
			}	
	}
	
	public String getUitleg() {
		return uitleg;
	}
}
