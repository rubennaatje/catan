package model;

import java.sql.ResultSet;
import java.sql.SQLException;

import controller.DatabaseManager;

public class DevelopmentCard {
	
	protected String spelid;
	protected String cardId;
	protected String username;
	protected String kaarttype;
	protected String kaartnaam;
	
	public DevelopmentCard(String spelid, String cardId, String username, String kaarttype, String kaartnaam) {
		this.spelid = spelid;
		this.cardId = cardId;
		this.username = username;
		this.kaarttype = kaarttype;
		this.kaartnaam = kaartnaam;
		try {
			DatabaseManager.createStatement().executeUpdate("UPDATE spelerontwikkelingskaart SET username = '" + this.username + 
					"' WHERE idspel = '" + spelid + "'AND idontwikkelingskaart = '" + cardId +"';");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
		
	public String getCardname() {
		return kaartnaam;
		
	}
	
	protected void updateDatabase() {
		try {
		DatabaseManager.createStatement().executeUpdate("UPDATE `spelerontwikkelingskaart` SET gespeeld = 1 "
				+ "WHERE idontwikkelingskaart = '"+this.cardId+"' AND `idspel` ='"+this.spelid+"' AND username = '"+this.username+"';");
		System.out.println("query");
	} catch (SQLException e) {
		e.printStackTrace();
	}
	}
	
	public String getCardId() {
		return cardId;
	}
	
//	public void playCard()
//	{
//		System.out.println("hohhiohoh");
//	}
}
