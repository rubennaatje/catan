package model;

import java.sql.SQLException;

import controller.DatabaseManager;

public class ProgressCard extends DevelopmentCard { // 2 grondstoffen naar keuze, 2 stratenbouwen of alle grondstoffen van 1 soort van alle spelers
	private String uitleg;
	public ProgressCard(String spelid, String kaartid, String username, String kaarttype, String kaartnaam, String uitleg) {
		super(spelid, kaartid, username, kaarttype, kaartnaam);
		this.uitleg = uitleg;
		// TODO Auto-generated constructor stub
	}
	
	public void playCard() {
		System.out.println("vooruitgang");
		super.updateDatabase();
			
			switch(kaartnaam) {
			case("stratenbouw"):// bouw 2 straten
				
			case("monopolie"): // kies grondstof, krijg alle grondstoffen van dit type die spelers bezitten
			
			case("uitvinding"): //2 grondstoffen kiezen
			}	
	}
	
	public String getUitleg() {
		return uitleg;
	}
}
