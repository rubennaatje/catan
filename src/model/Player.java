package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import controller.DatabaseManager;

public class Player {
	
	private String userName = "bart";
	private int spelId = 770;
	private ResultSet playerInfo = null;
	public ArrayList<String> getPlayerInfo()
	{
		try
		{
			DatabaseManager.connect();
			playerInfo = DatabaseManager.executeSelectQuery(
					"SELECT behaaldepunten FROM speler WHERE username ='" + this.userName + "' AND idspel =" + this.spelId );
			while (playerInfo.next())
			{
				
			}
		} catch (SQLException e)
		{
			System.out.println("Scoreboard error : " + e.getMessage());
		}
		return null;
	}
	"SELECT COUNT(idgrondstofkaart) FROM spelergrondstofkaart WHERE username ='" + this.userName + "' AND idspel =" + this.spelId);
	"SELECT COUNT(idontwikkelingskaart) FROM spelerontwikkelingskaart WHERE username ='" + this.userName + "' AND idspel =" + this.spelId);
	"SELECT COUNT(*) FROM spelerontwikkelingskaart WHERE username ='" + this.userName + "' AND idspel =" + this.spelId + "AND idontwikkelingskaart IN (SELECT idontwikkelingskaart FROM ontwikkelingskaart WHERE naam =  'ridder'");
}
