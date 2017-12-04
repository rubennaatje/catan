package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Observable;
import controller.DatabaseManager;
import model.BoardHelper;

public class PlayerModel extends Observable {

	private PlayerType type;
	private String username;
	private String cards;
	private String devCards;
	private String knights;
	private String score;
	private String road;
	private String spelId;
	private int playerNumber;

	public PlayerModel(String username, String spelId) throws Exception {
		this.username = username;
		refresh(spelId);
		this.spelId = spelId;
	}

	public void refresh(String spelId) throws Exception {
		try {
			ResultSet list = null;
			DatabaseManager.connect();
			list = DatabaseManager.createStatement().executeQuery(
					"SELECT s1.behaaldepunten, count(idgrondstofkaart) as grond, (SELECT COUNT(idontwikkelingskaart)"
							+ " FROM spelerontwikkelingskaart s3 WHERE username = s1.username and s3.idspel = s1.idspel) as ontwikkel,(SELECT COUNT(*)"
							+ " FROM spelerontwikkelingskaart s4 WHERE username = s1.username AND s4.idspel = s1.idspel AND s4.gespeeld = 1 AND s4.idontwikkelingskaart IN"
							+ " (SELECT idontwikkelingskaart FROM ontwikkelingskaart WHERE naam =  'ridder')) as knights, volgnr FROM speler s1 left join spelergrondstofkaart s2 "
							+ "on s1.idspel = s2.idspel and s1.username = s2.username WHERE s1.username ='"
							+ this.username + "' AND s1.idspel =" + this.spelId);
			while (list.next()) {
				this.score = list.getString(1);
				this.cards = list.getString(2);
				this.devCards = list.getString(3);
				this.knights = list.getString(4);
				this.playerNumber = list.getInt(5);
			}
			this.road = Integer.toString(BoardHelper.getLongestRoad(this, spelId));
		} catch (SQLException e) {
			System.out.println("PlayerInfo error : " + e.getMessage());
		}
		setChanged();
		notifyObservers();
	}

	public void addResource(String tileType, Piece piece) throws Exception {
		ResultSet results = DatabaseManager.createStatement().executeQuery(
				"select g.idgrondstofkaart, g.idgrondstofsoort, username from grondstofkaart as g inner join spelergrondstofkaart as s ON s.idgrondstofkaart = g.idgrondstofkaart WHERE idspel = '"
						+ Catan.getGameId() + "' AND username IS NULL AND g.idgrondstofsoort = '" + tileType
						+ "' ORDER BY s.idgrondstofkaart LIMIT 2;");

		while (results.next()) {
			DatabaseManager.createStatement()
					.executeUpdate("UPDATE spelergrondstofkaart SET username = '" + username + "' WHERE idspel='"
							+ Catan.getGameId() + "' AND idgrondstofkaart = '" + results.getString("idgrondstofkaart")
							+ "';");
			if (piece.getType().toString().equals("dorp"))
				break;
		}

		results.close();

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

	public void setType(PlayerType type) {
		this.type = type;
	}

}