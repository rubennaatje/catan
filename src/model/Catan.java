package model;

import java.sql.ResultSet;
import java.sql.SQLException;

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
			return DatabaseManager.createStatement().execute(String.format("INSERT INTO account VALUES ('%s', '%s')", username, password));
		} catch (SQLException e) {

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

		//TODO: remove after implementing Geordi's function. 
		// Board board = new Board();
		// board.createBoard(gameId);
		DatabaseManager.createStatement()
				.executeUpdate("INSERT INTO tegel (idspel, idtegel,X,Y,idgrondstofsoort,idgetalfiche) VALUES(	"
						+ gameId + " , 1  , 2 , 4  , 'H' , 12	)," + "(	" + gameId
						+ " , 2  , 3 , 6  , 'G' , 18	),(	" + gameId + " , 3  , 4 , 8  , 'E' , 14	)," + "(	"
						+ gameId + " , 4  , 3 , 3  , 'W' , 10	),(	" + gameId + " , 5  , 4 , 5  , 'H' , 16	),"
						+ "(	" + gameId + " , 6  , 5 , 7  , 'G' , 8	),(	" + gameId + " , 7  , 6 , 9  , 'E' , 1	),"
						+ "(	" + gameId + " , 8  , 4 , 2  , 'B' , 6	),(	" + gameId + " , 9  , 5 , 4  , 'W' , 2	),"
						+ "(	" + gameId + " , 10 , 6 , 6  , 'X' , NULL ),(	" + gameId
						+ " , 11 , 7 , 8  , 'G' , 4	)," + "(	" + gameId + " , 12 , 8 , 10 , 'E' , 13	),(	" + gameId
						+ " , 13 , 6 , 3  , 'B' , 9	)," + "(	" + gameId + " , 14 , 7 , 5  , 'W' , 5	),(	" + gameId
						+ " , 15 , 8 , 7  , 'H' , 3	)," + "(	" + gameId + " , 16 , 9 , 9  , 'G' , 15	),(	" + gameId
						+ " , 17 , 8 , 4  , 'B' , 17	)," + "(	" + gameId + " , 18 , 9 , 6  , 'W' , 7	),(	"
						+ gameId + " , 19 , 10, 8  , 'H' , 11	);");

		addDevelopmentCards();
		addResourceCards();
		addPlayers();

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

	/**
	 * adds all of the players for now until the challenge system works.
	 * TODO: fix this.
	 * @throws Exception
	 */
	private void addPlayers() throws Exception {

		// TODO: fix up when we get further.
		PlayerModel[] players = new PlayerModel[4];
        players[0] = new PlayerModel("bart", Catan.getGameId());
        players[1] = new PlayerModel("rik", Catan.getGameId());
        players[2] = new PlayerModel("lesley", Catan.getGameId());
        players[3] = new PlayerModel("ger", Catan.getGameId());

		int index = 0;
		for (PlayerModel p : players) {
			String playStatus = "uitgedaagde";

			if (index == 0)
				playStatus = "uitdager";

			DatabaseManager.createStatement().executeUpdate(
					"INSERT INTO speler ( idspel, username, kleur, speelstatus, shouldrefresh, volgnr, behaaldepunten)\r\n"
							+ "VALUES (" + gameId + ", '" + p.getUsername() + "',    '" + p.getType().getColorString()
							+ "',  '" + playStatus + "',    0, " + (index + 1) + ", 0)");
			index++;
		}
	}
	
    public PlayerModel[] getCurrentPlayers() throws Exception {

        PlayerModel[] players = new PlayerModel[4];

        ResultSet results = DatabaseManager.createStatement()
                .executeQuery("SELECT * FROM speler WHERE idspel = '" + Catan.getGameId() + "' ORDER BY volgnr ASC;");
        
        while (results.next()) {
            
            if(!results.getString("username").equals(player.getUsername())) {
                PlayerModel competitor = new PlayerModel(results.getString("username"), Catan.getGameId(), PlayerType.valueOf(results.getString("kleur").toUpperCase()));
                competitor.setPlayerNumber(results.getInt("volgnr"));
                
                players[results.getInt("volgnr") - 1] = competitor;
            } else {
                player.setPlayerNumber(results.getInt("volgnr"));
                player.setType(PlayerType.valueOf(results.getString("kleur").toUpperCase()));
                
                players[results.getInt("volgnr") - 1] = player;
            }
            
        }
        
        results.close();

        return players;
    }
    
    public void invitePlayers() throws Exception {
        DatabaseManager.createStatement().executeUpdate(" ");
    }


}
