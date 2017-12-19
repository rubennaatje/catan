package model;

import java.sql.SQLException;

import controller.DatabaseManager;
import controller.DevelopCardController;

public class ProgressCard extends DevelopmentCard { // 2 grondstoffen naar keuze, 2 stratenbouwen of alle grondstoffen van 1 soort van alle spelers
	private String uitleg;
	private TileType resource;
	private String sresource;
	private DevelopCardController controller;
	
	public ProgressCard(PlayerUser player, String kaartid, String kaarttype, String kaartnaam, String uitleg, DevelopCardController developCardController) {
		super(player, kaartid, kaarttype, kaartnaam);
		this.uitleg = uitleg;
		this.controller = developCardController;
	}
	
	public void setResourceType(TileType t) {
		sresource = t.toString();
		this.resource = t;
	}
	
	public void playCard() {
		super.updateDatabase();
			switch(kaartnaam) {
			case("stratenbouw"):// bouw 2 straten
				controller.place2Streets();
			break;
			case("monopolie"):  //alle grondstoffen van 1 type naar speler
				try {	
					DatabaseManager.createStatement().executeUpdate("UPDATE `spelergrondstofkaart` SET username = '"+player.getUsername()+"' "
							+ "WHERE idgrondstofkaart LIKE '"+sresource+"%' AND idspel = "+player.getSpelId()+" AND username IS NOT NULL;");
				} catch (SQLException e) {
					e.printStackTrace();
				}
			break;
			case("uitvinding"): //2 grondstoffen kiezen
				try {
					player.addResource(resource, 2);
				} catch (SQLException e) {
					e.printStackTrace();
				} 
			break;
			}	
			player.refresh();
	}
	
	public String getUitleg() {
		return uitleg;
	}
}
