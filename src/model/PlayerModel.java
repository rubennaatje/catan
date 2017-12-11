package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Observable;
import controller.DatabaseManager;
import model.BoardHelper;

public class PlayerModel extends Observable {

	protected PlayerType type;
	private String username=null;
	private String cards =null;
	private String devCards=null;
	private String knights=null;
	private String score=null;
	private String road=null;
	private String spelId=null;
	private int playerNumber;
	private boolean hasTurn=false;

	public PlayerModel(String username, String spelId, PlayerType type)  {
		this.username = username;
		this.spelId = spelId;
		this.type = type;
		refresh();
	}
	
	public PlayerModel(String username, String idspel) {
		this(username, idspel, null);
	}
	
	public PlayerModel(String username) {
		this(username, null, null);
	}

	public void refresh()  {
		try {
			ResultSet list = DatabaseManager.createStatement().executeQuery(
					"SELECT s1.behaaldepunten, count(idgrondstofkaart) as grond, (SELECT COUNT(idontwikkelingskaart)"
							+ " FROM spelerontwikkelingskaart s3 WHERE username = s1.username and s3.idspel = s1.idspel) as ontwikkel,(SELECT COUNT(*)"
							+ " FROM spelerontwikkelingskaart s4 WHERE username = s1.username AND s4.idspel = s1.idspel AND s4.gespeeld = 1 AND s4.idontwikkelingskaart IN"
							+ " (SELECT idontwikkelingskaart FROM ontwikkelingskaart WHERE naam =  'ridder')) as knights, volgnr FROM speler s1 left join spelergrondstofkaart s2 "
							+ "on s1.idspel = s2.idspel and s1.username = s2.username WHERE s1.username ='"
							+ this.username + "' AND s1.idspel =" + this.getSpelId());
			while (list.next()) {
				this.score = list.getString(1);
				this.cards = list.getString(2);
				this.devCards = list.getString(3);
				this.knights = list.getString(4);
				this.playerNumber = list.getInt(5);
			}
			this.road = Integer.toString(BoardHelper.getLongestRoad(this, getSpelId()));
		} catch (SQLException e) {
			System.out.println("PlayerInfo error : " + e.getMessage());
		}
		this.hasTurn = hasTurn();
		setChanged();
		notifyObservers();
	}

	public void addResource(String tileType, Piece piece) throws Exception {
		ResultSet results = DatabaseManager.createStatement().executeQuery(
				"select g.idgrondstofkaart, g.idgrondstofsoort, username from grondstofkaart as g inner join spelergrondstofkaart as s ON s.idgrondstofkaart = g.idgrondstofkaart WHERE idspel = '"
						+ getSpelId() + "' AND username IS NULL AND g.idgrondstofsoort = '" + tileType
						+ "' ORDER BY s.idgrondstofkaart LIMIT 2;");

		while (results.next()) {
			DatabaseManager.createStatement()
					.executeUpdate("UPDATE spelergrondstofkaart SET username = '" + username + "' WHERE idspel='"
							+ getSpelId() + "' AND idgrondstofkaart = '" + results.getString("idgrondstofkaart")
							+ "';");
			if (piece.getType() == PieceType.DORP )
				break;
		}
		results.close();
	}
	public void removeResource(TileType t) 
	{
		try
		{
		ResultSet results = DatabaseManager.createStatement().executeQuery("SELECT g.idgrondstofkaart FROM grondstofkaart AS g INNER JOIN spelergrondstofkaart AS s ON s.idgrondstofkaart = g.idgrondstofkaart WHERE idspel = '" 
			+ getSpelId() + "' AND username = '" + getUsername() + "' AND g.idgrondstofsoort = '" + t.toString() + "' ORDER BY s.idgrondstofkaart LIMIT 1;");
			while(results.next())
			{
				DatabaseManager.createStatement().executeUpdate("UPDATE spelergrondstofkaart SET username = NULL WHERE idspel='"
						+ getSpelId() + "' AND idgrondstofkaart = '" + results.getString(1)
						+ "';");
			}
			results.close();
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	private boolean hasTurn()
	{
		try
		{
			ResultSet results = DatabaseManager.createStatement().executeQuery("SELECT beurt_username FROM spel WHERE beurt_username ='"+ this.username + "' AND idspel =" + this.spelId);
		while(results.next())
		{
			if(results.getString(1).equals(this.username))
			{
				return true;
			}
		}
		}catch (SQLException e)
		{
			e.printStackTrace();
		}
		return false;
	}

	public PlayerType getType() {
		return type;
	}

	public String getUsername() {
		return username;
	}

	public String getCards() {
		return cards;
	}

	public String getDevCards() {
		return devCards;
	}

	public String getKnights() {
		return knights;
	}

	public String getScore() {
		return score;
	}

	public String getRoad() {
		return road;
	}

	public int getPlayerNumber() {
		return playerNumber;
	}
	
    public void setPlayerNumber(int playerNumber) {
        if(playerNumber <= 4 && playerNumber >=1)
            this.playerNumber = playerNumber;
    }
    
	public String getSpelId() {
		return spelId;
	}
	public boolean getPlayerTurn()
	{
		return hasTurn;
	}
}