package model;

import java.sql.SQLException;

import controller.DatabaseManager;

public class ProgressCard extends DevelopmentCard { // 2 grondstoffen naar keuze, 2 stratenbouwen of alle grondstoffen van 1 soort van alle spelers
	private String uitleg;
	private String resource;
	public ProgressCard(String spelid, String kaartid, String username, String kaarttype, String kaartnaam, String uitleg) {
		super(spelid, kaartid, username, kaarttype, kaartnaam);
		this.uitleg = uitleg;
		// TODO Auto-generated constructor stub
	}
	
	public void setResourceType(String resource) {
		this.resource = resource;
	}
	
	public void playCard() {
		System.out.println("vooruitgang");
		super.updateDatabase();
			
			switch(kaartnaam) {
			case("stratenbouw"):// bouw 2 straten
				
			case("monopolie"): // kies grondstof, krijg alle grondstoffen van dit type die spelers bezitten 
				//UPDATE `spelergrondstofkaart` SET username = "rik" WHERE idgrondstofkaart LIKE 'h%' AND idspel = 771 AND username IS NOT NULL 
			
			case("uitvinding"): //2 grondstoffen kiezen
			}	
	}
	
	public String getUitleg() {
		return uitleg;
	}
}
