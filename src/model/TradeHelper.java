package model;

import java.sql.ResultSet;
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

	public static void acceptOffer(String spelId, PlayerModel counterer, PlayerModel user) throws SQLException {
		ResultSet r = DatabaseManager.createStatement().executeQuery(
				"SELECT geeft_baksteen, geeft_wol, geeft_erts, geeft_graan , geeft_hout, "
				+ "vraagt_baksteen, vraagt_wol, vraagt_erts, vraagt_graan, vraagt_hout, geaccepteerd FROM catan.ruilaanbod "
						+ "WHERE idspel = " + spelId + " AND username = '" + counterer.getUsername() + "';");
		if(r.next()) {
			
			counterer.removeResource(TileType.B, r.getInt("geeft_baksteen"));
			counterer.removeResource(TileType.E, r.getInt("geeft_erts"));
			counterer.removeResource(TileType.G, r.getInt("geeft_graan"));
			counterer.removeResource(TileType.H, r.getInt("geeft_hout"));
			counterer.removeResource(TileType.W, r.getInt("geeft_wol"));

			user.addResource(TileType.B, r.getInt("geeft_baksteen"));  
			user.addResource(TileType.E, r.getInt("geeft_erts"));      
			user.addResource(TileType.G, r.getInt("geeft_graan"));     
			user.addResource(TileType.H, r.getInt("geeft_hout"));      
			user.addResource(TileType.W, r.getInt("geeft_wol"));       
			
			user.removeResource(TileType.B, r.getInt("vraagt_baksteen"));
			user.removeResource(TileType.E, r.getInt("vraagt_erts"));
			user.removeResource(TileType.G, r.getInt("vraagt_graan"));
			user.removeResource(TileType.H, r.getInt("vraagt_hout"));
			user.removeResource(TileType.W, r.getInt("vraagt_wol"));
			
			counterer.addResource(TileType.B, r.getInt("vraagt_baksteen"));  
			counterer.addResource(TileType.E, r.getInt("vraagt_erts"));      
			counterer.addResource(TileType.G, r.getInt("vraagt_graan"));     
			counterer.addResource(TileType.H, r.getInt("vraagt_hout"));      
			counterer.addResource(TileType.W, r.getInt("vraagt_wol"));       
		}
	}
}
