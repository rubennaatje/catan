package model;

import java.sql.ResultSet;
import java.sql.SQLException;

import controller.DatabaseManager;

public class DevelopmentCard {
	
	private String spelid;
	private String kaartid;
	private String username;
	private String kaarttype;
	
	public DevelopmentCard(String spelid, String kaartid, String username, String kaarttype) {
		this.spelid = spelid;
		this.kaartid = kaartid;
		this.username = username;
		try {
			DatabaseManager.createStatement().executeUpdate("UPDATE spelerontwikkelingskaart SET username = '" + this.username + "' WHERE idspel = '" + spelid + "'AND idontwikkelingskaart = '" + kaartid +"';");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
		
	public String getDescription(String type) {
		return kaartid;
		
	}

}
