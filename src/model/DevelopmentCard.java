package model;

import java.sql.ResultSet;
import java.sql.SQLException;

import controller.DatabaseManager;

public class DevelopmentCard {
	
	private String spelid;
	private String cardId;
	private String username;
	private String kaarttype;
	private String kaartnaam;
	
	public DevelopmentCard(String spelid, String cardId, String username, String kaarttype, String kaartnaam) {
		this.spelid = spelid;
		this.cardId = cardId;
		this.username = username;
		this.kaarttype = kaarttype;
		this.kaartnaam = kaartnaam;
		try {
			DatabaseManager.createStatement().executeUpdate("UPDATE spelerontwikkelingskaart SET username = '" + this.username + "' WHERE idspel = '" + spelid + "'AND idontwikkelingskaart = '" + cardId +"';");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
		
	public String getCardname() {
		return kaartnaam;
		
	}
	
	public String getCardId() {
		return cardId;
	}

}
