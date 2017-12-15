package model;
//extended storage of users data, stores, cards, specific resources;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import controller.DatabaseManager;

public class PlayerUser extends PlayerModel {

	HashMap<TileType, Integer> resources;


	public PlayerUser(String username, String idspel, PlayerType type){
		super(username, idspel, type);

	}

	public PlayerUser(String username) {
		super(username);
	}

	@Override
	public void refresh() {
		resources = new HashMap<>();

		try {
			refreshVictoryPoints();
			ResultSet resourcesVal = DatabaseManager.createStatement().executeQuery("SELECT"
					+ " SUM(if(idgrondstofsoort = \"H\", 1, 0)) as H,"
					+ " SUM(if(idgrondstofsoort = \"W\", 1, 0)) as W,"
					+ " SUM(if(idgrondstofsoort = \"G\", 1, 0)) AS G,"
					+ " SUM(if(idgrondstofsoort = \"B\", 1, 0)) AS B," + " SUM(if(idgrondstofsoort = \"E\", 1, 0)) AS E"
					+ " from spelergrondstofkaart s inner join grondstofkaart g on s.idgrondstofkaart = g.idgrondstofkaart where idspel = "
					+ getSpelId() + " and username = '" + getUsername() + "' LIMIT 1");

			if (resourcesVal.next()) {

				resources.put(TileType.H, resourcesVal.getInt(1));
				resources.put(TileType.W, resourcesVal.getInt(2));
				resources.put(TileType.G, resourcesVal.getInt(3));
				resources.put(TileType.B, resourcesVal.getInt(4));
				resources.put(TileType.E, resourcesVal.getInt(5));

				setChanged();
				notifyObservers();
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		super.refresh();
	}

	/**
	 * Returns True if
	 * 
	 * @param t
	 * @param v
	 * @return
	 */
	public boolean hasResource(TileType t, Integer v) {
		Integer compare = resources.get(t);
		if (compare >= v) {
			return true;
		}
		return false;
	}

	public HashMap<String, Boolean> getBuyableThings() throws Exception {

		HashMap<String, Boolean> res = new HashMap<String, Boolean>();
		res.put("street", false);
		res.put("city", false);
		res.put("town", false);

		if (hasResource(TileType.B, 1) && hasResource(TileType.H, 1)) {
			res.put("street", true);
		}

		if (hasResource(TileType.B, 1) && hasResource(TileType.H, 1) && hasResource(TileType.G, 1)
				&& hasResource(TileType.W, 1)) {
			res.put("town", true);
		}

		if (hasResource(TileType.G, 2) && hasResource(TileType.E, 3)) {
			res.put("city", true);
		}

		return res;
	}

	public void setType(PlayerType type) {
		this.type = type;
	}

	public HashMap<TileType, Integer> getResources() {
		return resources;
	}
	
	public void refreshVictoryPoints() throws SQLException{
		String grmUsername = null;
		String lhrUsername = null;
		int newScore = 0;
		
		//check if player has longest road or biggest knight army
		ResultSet result = DatabaseManager.createStatement().executeQuery("SELECT grootste_rm_username, langste_hr_username FROM spel where idspel = '"+spelId+"'");
		while(result.next()) {
			grmUsername = result.getString(1);
			lhrUsername = result.getString(2);
		}
		
		if(grmUsername != null && grmUsername.equals(username)) {
			newScore += 2;
		}
		
		if(lhrUsername != null && lhrUsername.equals(username)) {
			newScore += 2;
		}
		
		//check towns and cities
		for(Piece piece: BoardHelper.getPiecesPlayer(this, spelId)) {
			if(piece.getType() == PieceType.DORP) {
				newScore += 1;
			} else if(piece.getType() == PieceType.STAD) {
				newScore +=2;
			}
		}
		
		//check played victory point cards
		ResultSet list = DatabaseManager.createStatement().executeQuery("SELECT gespeeld, username FROM spelerontwikkelingskaart s inner join ontwikkelingskaart o on s.idontwikkelingskaart = o.idontwikkelingskaart ");
		while(result.next()) {
			int iGespeeld = list.getInt(1);
			String sUsername = list.getString(2);
			if(iGespeeld == 1 && sUsername == username) {
				newScore += 1;
			}
		}
		
		//add score to old score and update cell behaaldepunten

		int rowsAffected = DatabaseManager.createStatement().executeUpdate("UPDATE speler SET behaaldepunten = '"+ newScore +"' WHERE username = '"+ username +"' AND idspel='"+spelId+"'");
		
		if(rowsAffected == 0) {
			throw new SQLException("No points to add");
		}	
	}
	
	public void takeResources (PieceType pieceType) {
		switch(pieceType){
		case DORP:
			try {
				removeResources(TileType.B, 1);
				removeResources(TileType.H, 1);
				removeResources(TileType.W, 1);
				removeResources(TileType.G, 1);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			break;
		case STAD:
			try {
				removeResources(TileType.E, 3);
				removeResources(TileType.G, 2);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			break;
		}
		
		refresh();
	}

	public void takeResourcesStreet () {
		try {
			removeResources(TileType.B, 1);
			removeResources(TileType.H, 1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		refresh();
	}
}
