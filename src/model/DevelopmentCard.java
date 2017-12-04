package model;

import java.sql.ResultSet;
import java.sql.SQLException;

import controller.DatabaseManager;

public class DevelopmentCard {
	
	private int spelid;
	private String kaartid;
	private PlayerUser player;
	private ResultSet kaarttypes = null;
	private String kaarttype;
	
	public DevelopmentCard(int spelid, String kaartid, PlayerUser player) {
		this.spelid = spelid;
		this.kaartid = kaartid;
		this.player = player;
		try {
			DatabaseManager.getStatement().executeUpdate("UPDATE spelerontwikkelingskaart SET username = '" + player.getUsername()+ "'WHERE spelid = " + spelid + "AND idontwikkelingskaart = '" + kaartid +"';");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public DevelopmentCard() { //testcontstructor
		spelid = 770;
		kaartid = "o08";
	}
	
//	public String getType() {
//		try {
//		kaarttypes = DatabaseManager.getStatement().executeQuery("SELECT naam FROM `ontwikkelingskaart` WHERE `idontwikkelingskaart` ='"+ kaartid + "';");
//			kaarttypes = DatabaseManager.getStatement().executeQuery("SELECT naam FROM `ontwikkelingskaart` WHERE `idontwikkelingskaart` = 'o01'");
//			if(kaarttypes.first()) {
//			kaarttype = kaarttypes.getString(kaartid);
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		return kaarttype;
//	}
	
	public String getDescription(String type) {
		return kaartid;
		
	}

}
