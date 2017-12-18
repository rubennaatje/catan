package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import controller.DatabaseManager;

public class Catan {

	private static String gameId;
	
	
	private PlayerUser player;

	public boolean login(String username, String password) {
		try {
			ResultSet result = DatabaseManager.createStatement().executeQuery(String.format("SELECT wachtwoord FROM account WHERE username = '%s'", username));
			if (result != null && result.next()) {
				if (result.getString("wachtwoord").equals(password)) {
					return true;
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}

	public boolean register(String username, String password) {
		try {
			return DatabaseManager.createStatement().executeUpdate(String.format("INSERT INTO account VALUES ('%s', '%s')", username, password)) == 1 ? true : false;
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

		return false;
	}

	public static void setGameId(String gameId2) {
		gameId = gameId2;
	}

	public static String getGameId() {
		return gameId;
	}
	
    public PlayerUser getPlayer() {
        return player;
    }

    public void setPlayer(PlayerUser player) {
        this.player = player;
    }

	
	/**
	 * initializes a new game in the database, with all of it's standard values ( generates cards and playeres for now)
	 * @throws Exception
	 */
	public void initGame(String gameId) throws Exception {		
		setGameId(gameId);
		
		List<String> hextypes = Arrays.asList("H","G","E","W","H","G","E","B","W","G","E","B","W","H","G","B","W","H");
		
		Collections.shuffle(hextypes);
		hextypes.get(0);
		DatabaseManager.createStatement()
				.executeUpdate("INSERT INTO tegel (idspel, idtegel,X,Y,idgrondstofsoort,idgetalfiche) VALUES"
								+ "(" + gameId + " , 1  , 2 , 4  , '" + hextypes.get(0) + "' , 12	)," 
								+ "(" + gameId + " , 2  , 3 , 6  , '" + hextypes.get(1) + "' , 18	),"
								+ "(" + gameId + " , 3  , 4 , 8  , '" + hextypes.get(2) + "' , 14	),"
								+ "(" + gameId + " , 4  , 3 , 3  , '" + hextypes.get(3) + "' , 10	),"
								+ "(" + gameId + " , 5  , 4 , 5  , '" + hextypes.get(4) + "' , 16	),"
								+ "(" + gameId + " , 6  , 5 , 7  , '" + hextypes.get(5) + "' , 8	),"
								+ "(" + gameId + " , 7  , 6 , 9  , '" + hextypes.get(6) + "' , 1	),"
								+ "(" + gameId + " , 8  , 4 , 2  , '" + hextypes.get(7) + "' , 6	),"
								+ "(" + gameId + " , 9  , 5 , 4  , '" + hextypes.get(8) + "' , 2	),"
								+ "(" + gameId + " , 10 , 6 , 6  , 'X' , NULL ),"
								+ "(" + gameId + " , 11 , 7 , 8  , '" + hextypes.get(9) + "' , 4	),"
								+ "(" + gameId + " , 12 , 8 , 10 , '" + hextypes.get(10) + "' , 13	),"
								+ "(" + gameId + " , 13 , 6 , 3  , '" + hextypes.get(11) + "' , 9	),"
								+ "(" + gameId + " , 14 , 7 , 5  , '" + hextypes.get(12) + "' , 5	),"
								+ "(" + gameId + " , 15 , 8 , 7  , '" + hextypes.get(13) + "' , 3	),"
								+ "(" + gameId + " , 16 , 9 , 9  , '" + hextypes.get(14) + "' , 15	),"
								+ "(" + gameId + " , 17 , 8 , 4  , '" + hextypes.get(15) + "' , 17	),"
								+ "(" + gameId + " , 18 , 9 , 6  , '" + hextypes.get(16) + "' , 7	),"
								+ "(" + gameId + " , 19 , 10, 8  , '" + hextypes.get(17) + "' , 11	);");

		addDevelopmentCards();
		addResourceCards();
	}
	
	public void initGame(String gameId, boolean creation, boolean random) throws Exception {		
		setGameId(gameId);
		
		System.out.println(gameId);

		if (creation) {
			List<String> hextypes = Arrays.asList("H","G","E","W","H","G","E","B","W","G","E","B","W","H","G","B","W","H");
			
			if(random)
				Collections.shuffle(hextypes);

			DatabaseManager.createStatement()
					.executeUpdate("INSERT INTO tegel (idspel, idtegel,X,Y,idgrondstofsoort,idgetalfiche) VALUES"
									+ "(" + gameId + " , 1  , 2 , 4  , '" + hextypes.get(0) + "' , 12	)," 
									+ "(" + gameId + " , 2  , 3 , 6  , '" + hextypes.get(1) + "' , 18	),"
									+ "(" + gameId + " , 3  , 4 , 8  , '" + hextypes.get(2) + "' , 14	),"
									+ "(" + gameId + " , 4  , 3 , 3  , '" + hextypes.get(3) + "' , 10	),"
									+ "(" + gameId + " , 5  , 4 , 5  , '" + hextypes.get(4) + "' , 16	),"
									+ "(" + gameId + " , 6  , 5 , 7  , '" + hextypes.get(5) + "' , 8	),"
									+ "(" + gameId + " , 7  , 6 , 9  , '" + hextypes.get(6) + "' , 1	),"
									+ "(" + gameId + " , 8  , 4 , 2  , '" + hextypes.get(7) + "' , 6	),"
									+ "(" + gameId + " , 9  , 5 , 4  , '" + hextypes.get(8) + "' , 2	),"
									+ "(" + gameId + " , 10 , 6 , 6  , 'X' , NULL ),"
									+ "(" + gameId + " , 11 , 7 , 8  , '" + hextypes.get(9) + "' , 4	),"
									+ "(" + gameId + " , 12 , 8 , 10 , '" + hextypes.get(10) + "' , 13	),"
									+ "(" + gameId + " , 13 , 6 , 3  , '" + hextypes.get(11) + "' , 9	),"
									+ "(" + gameId + " , 14 , 7 , 5  , '" + hextypes.get(12) + "' , 5	),"
									+ "(" + gameId + " , 15 , 8 , 7  , '" + hextypes.get(13) + "' , 3	),"
									+ "(" + gameId + " , 16 , 9 , 9  , '" + hextypes.get(14) + "' , 15	),"
									+ "(" + gameId + " , 17 , 8 , 4  , '" + hextypes.get(15) + "' , 17	),"
									+ "(" + gameId + " , 18 , 9 , 6  , '" + hextypes.get(16) + "' , 7	),"
									+ "(" + gameId + " , 19 , 10, 8  , '" + hextypes.get(17) + "' , 11	);");
			
			addDevelopmentCards();
			addResourceCards();
			addRobber();
		}
	}
	/**
	 * initializes all of the development cards for the current gameId in the database.
	 * @throws Exception
	 */
	private void addDevelopmentCards() throws Exception {

		String query = "INSERT INTO spelerontwikkelingskaart (idspel, idontwikkelingskaart, gespeeld, username) VALUES ";

		for (int i = 1; i < 26; i++) {
			String s = String.format("%02d", i);
			query += "('" + Catan.gameId + "','" + ("o" + s) + "','0',NULL)";
			// check if the last added one isn't the last one for the query.
			if (i == 25)
				query += ";";
			else
				query += ",";
		}
		DatabaseManager.createStatement().executeUpdate(query);
	}
	/**
	 * initializes all of the resource cards with the current gameid
	 * @throws Exception
	 */
	public void addResourceCards() throws Exception {
		String[] resourceCards = { "h", "w", "e", "b", "g" };

		String query = "INSERT INTO spelergrondstofkaart (idspel, idgrondstofkaart, username) VALUES ";

		for (String r : resourceCards) {
			for (int i = 1; i < 20; i++) {
				String s = String.format("%02d", i);
				query += "('" + Catan.gameId + "','" + (r + s) + "',NULL)";
				// check if the last added one isn't the last one for the query.
				if (r.equals("g") && i == 19)
					query += ";";
				else
					query += ",";
			}
		}

		DatabaseManager.createStatement().executeUpdate(query);
	}
	
	public void addRobber() throws SQLException {
		DatabaseManager.createStatement().executeUpdate("INSERT INTO `struikrover` (`idspel`, `idtegel`) VALUES ('" + Catan.getGameId() + "', '10');");
	}
	
    public PlayerModel[] getCurrentPlayers() throws Exception {

        PlayerModel[] players = new PlayerModel[4];

        ResultSet results = DatabaseManager.createStatement()
                .executeQuery("SELECT * FROM speler WHERE idspel = '" + Catan.getGameId() + "' ORDER BY volgnr ASC;");
        
        while (results.next()) {
            
            if(!results.getString("username").toLowerCase().equals(player.getUsername().toLowerCase())) {
                PlayerModel competitor = new PlayerModel(results.getString("username"), Catan.getGameId(), PlayerType.valueOf(results.getString("kleur").toUpperCase()));
                competitor.setPlayerNumber(results.getInt("volgnr"));
                players[results.getInt("volgnr") - 1] = competitor;
            } else {
                player.setPlayerNumber(results.getInt("volgnr"));
                player.setType(PlayerType.valueOf(results.getString("kleur").toUpperCase()));
                player.setSpelId(Catan.getGameId());
                player.refresh();
                players[results.getInt("volgnr") - 1] = player;
            }
            
        }
        
        results.close();

        return players;
    }
    
    public void invitePlayers() throws Exception {
        DatabaseManager.createStatement().executeUpdate(" ");
    }
    
    public void addPlayerPieces(PlayerModel[] players) throws SQLException {
    	String sql = "INSERT INTO `spelerstuk` (`idspel`, `username`, `idstuk`, `x_van`, `y_van`, `x_naar`, `y_naar`) VALUES ";
    	HashMap<String, Integer> pieceCount = new HashMap<String, Integer>();
    	
    	pieceCount.put("r", 13);
    	pieceCount.put("d", 5);
    	pieceCount.put("c", 4);
    	
    	int amountOfPieces = 0;
    	
    	for(PlayerModel p : players) {
    		
    		for (Entry<String, Integer> entry : pieceCount.entrySet()) {
    		    String key = entry.getKey();
    		    Integer count =  entry.getValue();
    		    
    		    for(int i = 1; i <= count; i++) {
    		    	String s = String.format("%02d", i);
    		    	sql = sql + ("('" + Catan.getGameId() + "', '" + p.getUsername() + "', '" + (key + s) + "', NULL, NULL, NULL, NULL)");
    		    	amountOfPieces++;
    		    	
    		    	if(amountOfPieces == 88)
    		    		sql = sql + ";";
    		    	else 
    		    		sql = sql + ",";
    		    		
    		    }
    		    
    		}
    		
    	}
    	DatabaseManager.createStatement().executeUpdate(sql);
    }

    public void randomBoard() {
    	
    }

}
