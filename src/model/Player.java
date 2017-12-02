package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Observable;

import controller.DatabaseManager;

public class Player extends Observable {
	
	private PlayerType type;
	private String username;
	private int spelId = 770;
	private ResultSet playerInfo = null;
	
	public Player(PlayerType type, String username)
	{
		this.type = type;
		this.username = username;
	}
	public ArrayList<String[]> getPlayerInfo()
	{
		try
		{
			ResultSet list1 = null;
			DatabaseManager.connect();
			list1 = DatabaseManager.createStatement().executeQuery(
					"SELECT behaaldepunten FROM speler WHERE username ='" + this.username + "' AND idspel =" + this.spelId );
			while (list1.next())
			{
				
			}
			
			
		} catch (SQLException e)
		{
			//"SELECT COUNT(idgrondstofkaart) FROM spelergrondstofkaart WHERE username ='" + this.userName + "' AND idspel =" + this.spelId);
			//"SELECT COUNT(idontwikkelingskaart) FROM spelerontwikkelingskaart WHERE username ='" + this.userName + "' AND idspel =" + this.spelId);
			//"SELECT COUNT(*) FROM spelerontwikkelingskaart WHERE username ='" + this.userName + "' AND idspel =" + this.spelId + "AND idontwikkelingskaart IN (SELECT idontwikkelingskaart FROM ontwikkelingskaart WHERE naam =  'ridder'");
			System.out.println("Scoreboard error : " + e.getMessage());
		}
		return null;
	}
	
	
	public PlayerType getType() {
		return type;
	}
	public String getUsername() {
		return username;
	}
	public void addResource(String tileType, Piece piece) throws Exception {
		ResultSet results = DatabaseManager.createStatement().executeQuery(
				"select g.idgrondstofkaart, g.idgrondstofsoort, username from grondstofkaart as g inner join spelergrondstofkaart as s ON s.idgrondstofkaart = g.idgrondstofkaart WHERE idspel = '" + Catan.getGameId() + "' AND username IS NULL AND g.idgrondstofsoort = '" + tileType + "' ORDER BY s.idgrondstofkaart LIMIT 2;");
		
		while (results.next()) {
			DatabaseManager.createStatement().executeUpdate("UPDATE spelergrondstofkaart SET username = '" + username + "' WHERE idspel='" + Catan.getGameId() + "' AND idgrondstofkaart = '" + results.getString("idgrondstofkaart") + "';");
			if(piece.getType().toString().equals("dorp"))
				break;
		}
		
		results.close();
		
	}
}
