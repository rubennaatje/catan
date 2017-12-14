package model;
//extended storage of users data, stores, cards, specific resources;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import controller.DatabaseManager;

public class PlayerUser extends PlayerModel {

	HashMap<TileType, Integer> resources;


	public PlayerUser(String username, String idspel){
		super(username, idspel);

	}

	public PlayerUser(String username) {
		super(username);
	}

	@Override
	public void refresh() {
		resources = new HashMap<>();

		try {
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
}
