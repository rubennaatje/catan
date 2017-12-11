package model;

import java.sql.SQLException;
import java.util.HashMap;

import controller.DatabaseManager;

//handles logic for trade, will be done later
public class TradeHelper {
	private TradeHelper() {

	}

	/** registers trade in the database
	 * @param spelId
	 * @param player
	 * @param tradeSuggestion
	 * @param accepted
	 * @throws SQLException
	 */
	public static void registerTrade(String spelId, PlayerModel player, HashMap<TileType, Integer>[] tradeSuggestion, String accepted) throws SQLException {
		DatabaseManager.createStatement().executeUpdate("INSERT INTO ruilaanbod VALUES ("
				+ spelId + ", '" 
				+ player.getUsername() + "', " 
				+ tradeSuggestion[0].get(TileType.B) + ", "
				+ tradeSuggestion[0].get(TileType.W) + ", "
				+ tradeSuggestion[0].get(TileType.E) + ", "
				+ tradeSuggestion[0].get(TileType.G) + ", "
				+ tradeSuggestion[0].get(TileType.H) + ", "
				+ tradeSuggestion[1].get(TileType.B) + ", "
				+ tradeSuggestion[1].get(TileType.W) + ", "
				+ tradeSuggestion[1].get(TileType.E) + ", "
				+ tradeSuggestion[1].get(TileType.G) + ", "
				+ tradeSuggestion[1].get(TileType.H) + ", "
				+ accepted
				+")");
	}

	/**
	 * @param spelId
	 * @param player
	 * @throws SQLException
	 */
	public static void registerReject(String spelId, PlayerUser player) throws SQLException {
		DatabaseManager.createStatement().executeUpdate("INSERT INTO ruilaanbod VALUES ("
				+ spelId + ", '" 
				+ player.getUsername() + "', " 
				+ "0, "
				+ "0, "
				+ "0, "
				+ "0, "
				+ "0, "
				+ "0, "
				+ "0, "
				+ "0, "
				+ "0, "
				+ "0, "
				+ "FALSE"
				+")");
	}

	public static void acceptOffer(String spelId) {
		
		
		
	}
}
